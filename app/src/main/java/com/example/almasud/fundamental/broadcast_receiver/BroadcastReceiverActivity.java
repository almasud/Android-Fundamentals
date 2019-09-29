package com.example.almasud.fundamental.broadcast_receiver;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.almasud.fundamental.MainActivity;
import com.example.almasud.fundamental.R;

public class BroadcastReceiverActivity extends AppCompatActivity implements View.OnClickListener {
    private Button smsCallReceiverBtn, customReceiverBtn;
    private CustomReceiver customReceiver;
    private PhoneNumberReceiver numberReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcast_receiver);

        smsCallReceiverBtn = findViewById(R.id.button_sms_call_receive);
        customReceiverBtn = findViewById(R.id.button_custom_broadcast_receiver);
        smsCallReceiverBtn.setOnClickListener(this);
        customReceiverBtn.setOnClickListener(this);

        // Register for a broadcast receiver
        // Receive incoming number that send from PhoneNumberReceiver broadcast receiver.
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("phone");
        numberReceiver = new PhoneNumberReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                if (intent != null) {
                    Toast.makeText(context, "Incoming Number: " + intent.getStringExtra("number"), Toast.LENGTH_SHORT).show();
                }
            }
        };
        /*
          If you are only interested in receiving a broadcast while you are running,
          it is better to use registerReceiver(). If you register a receiver in
          onCreate(Bundle) using the activity's context, you should unregister it in
          onDestroy() to prevent leaking the receiver out of the activity context.
         */

        // Here, you don't need any broadcast registration in manifest except activity registration.
        registerReceiver(numberReceiver, intentFilter);

        /*
          If your app targets Android 8.0 or higher, you cannot use the manifest to
          declare a receiver for most implicit broadcasts (broadcasts that don't target
          your app specifically). You can still use a context-registered receiver when
          the user is actively using your app.
         */
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("custom_broadcast");
        customReceiver = new CustomReceiver();
        registerReceiver(customReceiver, intentFilter1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sms_call_receive:
                /*
                Beginning in Android 6.0 (API level 23), we can ask permissions at run time.
                However, according to the docs, all permissions still need to be defined in
                AndroidManifest.xml so in APIs lower than 23, these permissions will be
                granted prior to installing the app.
            */
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECEIVE_SMS)
                        != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_PHONE_STATE)
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
                            Manifest.permission.RECEIVE_SMS) &&
                            ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_PHONE_STATE)) {

                        String message = "Broadcast Receiver is not related in UI.\nSMS and Phone App permissions is needed to capture incoming SMS.";
                        MainActivity.messageDialog(this, "Alert", message, "We hope you understand.");

                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.RECEIVE_SMS,
                                        Manifest.permission.READ_PHONE_STATE},
                                1);

                        // requestCode is app-defined int constant. The callback
                        // method gets the result of the request.
                    }
                } else {
                    // Permission has already been granted
                    MainActivity.messageDialog(this, "Info", "No need additional action.\nYour SMS and Incoming Phone number is captured by this app.", null);
                }
                break;
            case R.id.button_custom_broadcast_receiver:
                // Create a custom broadcast
                Intent intent = new Intent();
                intent.setAction("custom_broadcast");
                intent.putExtra("msg", "BITM Mobile Application Batch 25");
                sendBroadcast(intent);
                break;
        }
    }

    // Permission callback method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            MainActivity.messageDialog(this,"Success", "Your permission is successfully granted.\nNow your SMS is captured by this app.", null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(numberReceiver);
        unregisterReceiver(customReceiver);
    }
}
