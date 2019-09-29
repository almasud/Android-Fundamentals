package com.example.almasud.fundamental.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneNumberReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
       String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
       if (state != null) {
           if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
               // After receive a system broadcast send another broadcast to receive from activity.
               Intent localIntent = new Intent();
               localIntent.setAction("phone");
               localIntent.putExtra("number", incomingNumber);
               context.sendBroadcast(localIntent);
           }
       }
    }
}
