package com.example.almasud.fundamental.activity_cycle_and_intent;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almasud.fundamental.R;


public class EmployeeInfoActivity extends AppCompatActivity {
    // Application specific request code. Should be >= 0
    public static final int PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_and_intent_employee_info);

        TextView employeeInfo = findViewById(R.id.employeeInfo);
        Button callButton = findViewById(R.id.callButton);

        Intent intent = getIntent();  // Get intent of previous activity.
        // Get previous instance of employee object.
        final Employee employee = (Employee) intent.getSerializableExtra(EmployeeContactActivity.EMPLOYEE_INFO);

        // View Employee info in a text view
        employeeInfo.setText(new StringBuilder().append("Name: ")
                .append(employee.getFirstName().isEmpty() ? "" : employee.getFirstName() + " ")
                .append(employee.getLastName().isEmpty() ? "" : employee.getLastName())
                .append("\nPhone Number: ").append(employee.getPhoneNumber())
                .append("\nEmail: ").append(employee.getEmailAddress())
                .toString());

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + employee.getPhoneNumber()));

                // Check whether request component is found or not.
                if (intent.resolveActivity(getPackageManager()) != null) {

                    // Code for Android version Marshmallow (API Level 23) or higher.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    /*
                        Beginning in Android 6.0 (API level 23), we can ask permissions at run time.
                        However, according to the docs, all permissions still need to be defined in
                        AndroidManifest.xml so in APIs lower than 23, these permissions will be
                        granted prior to installing the app.
                    */
                        if (ContextCompat.checkSelfPermission(EmployeeInfoActivity.this,
                                Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Permission is not granted
                            // Should we show an explanation why need permission.
                            /*
                               This method returns true if the user has previously denied the request,
                               and returns false if a user has denied a permission and selected the
                               Don't ask again option in the permission request dialog, or if a device
                               policy prohibits the permission.
                             */
                            if (ActivityCompat.shouldShowRequestPermissionRationale(EmployeeInfoActivity.this,
                                    Manifest.permission.CALL_PHONE)) {

                                String message = "Need a permission to call any number.\nCall permissions is needed to call the number.";
                                messageDialog("Alert", message, "We hope you understand.");

                            } else {
                                // No explanation needed; request the permission
                                ActivityCompat.requestPermissions(EmployeeInfoActivity.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        PERMISSIONS_REQUEST_CALL_PHONE);

                                // PERMISSIONS_REQUEST_CALL_PHONE is an app-defined int constant.
                                // The callback method gets the result of the request.
                            }
                        } else {
                            // Permission has already been granted
                            startActivity(intent);
                        }
                    } else {
                        // Code for Android version Lollipop (API Level 22) or lower.
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(EmployeeInfoActivity.this, "Not found any componet in this system to call", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Show a dialog
    private void messageDialog(String title, String message, final String toastMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (toastMsg != null)
                    Toast.makeText(EmployeeInfoActivity.this, toastMsg, Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }
}
