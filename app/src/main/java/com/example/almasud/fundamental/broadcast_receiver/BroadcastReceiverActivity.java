package com.example.almasud.fundamental.broadcast_receiver;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.almasud.fundamental.MainActivity;
import com.example.almasud.fundamental.R;

public class BroadcastReceiverActivity extends AppCompatActivity implements View.OnClickListener {
    Button smsReceiverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcast_receiver);
        smsReceiverBtn = findViewById(R.id.button_sms_receive);

        smsReceiverBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_sms_receive) {

            /*
                Beginning in Android 6.0 (API level 23), we can ask permissions at run time.
                However, according to the docs, all permissions still need to be defined in
                AndroidManifest.xml so in APIs lower than 23, these permissions will be
                granted prior to installing the app.
            */
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECEIVE_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation why need permission.
            /*
               This method returns true if the user has previously denied the request,
               and returns false if a user has denied a permission and selected the
               Don't ask again option in the permission request dialog, or if a device
               policy prohibits the permission.
             */
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.RECEIVE_SMS)) {

                    String message = "Broadcast Receiver is not related in UI.\nSMS App permissions is needed to capture incoming SMS.";
                    MainActivity.messageDialog(this, "Alert", message, "We hope you understand.");

                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.RECEIVE_SMS},
                            1);

                    // requestCode is app-defined int constant. The callback
                    // method gets the result of the request.
                }
            } else {
                // Permission has already been granted
                MainActivity.messageDialog(this, "Info", "No need additional action.\nYour SMS is captured by this app.", null);
            }
        }
    }

    // Permission callback method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            MainActivity.messageDialog(this,"Success", "Your permission is successfully granted.\nNow you can show your current location.", null);
        }
    }
}
