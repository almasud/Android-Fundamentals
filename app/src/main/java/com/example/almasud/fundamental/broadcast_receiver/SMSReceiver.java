package com.example.almasud.fundamental.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            // Extract number and message from intent.
            Bundle bundle = intent.getExtras();  // Retrieves a map of extended data from the intent.
            SmsMessage[] chunks = null;
            String number = "";
            String message = "";

            if (bundle != null) {
             /*
              Most smart phones and mobile phones support what is known as
              "PDU mode" for sending and receiving SMS. PDU (protocol data unit)
              contains not only the SMS message, but also metadata about the SMS
              message such as text encoding, the sender, SMS service center address,
              and much more. To access this metadata, SMS apps almost always use PDUs
              to encode the contents of a SMS message. The sendTextMessage() and
              sendMultimediaMessage() methods of the SmsManager class encode the
              contents for you. When receiving a PDU, you can create an SmsMessage
              object from the raw PDU using createFromPdu().
             */
                Object[] pdus = (Object[]) bundle.get("pdus");
                String sms = "";
                chunks = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // Code for Marshmallow (API Level 23) or higher version
                        /*
                         Format was introduced for Android Marshmallow to support
                         "3gpp" for GSM/UMTS/LTE messages in 3GPP format or
                         "3gpp2" for CDMA/LTE messages in 3GPP2 format.
                         */
                        String format = bundle.getString("format");  // Return 3gpp or 3gpp2
                        // Create an SmsMessage from a raw PDU with the specified message format.
                        chunks[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        // This method was deprecated in API level 23.
                        chunks[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }

                    number = chunks[i].getOriginatingAddress();
                    message = chunks[i].getMessageBody();

                    sms += "From: " + number + "\n";
                    sms += "Message: " + message + "\n";
                }

                Toast.makeText(context, sms, Toast.LENGTH_LONG).show();
            }
        }
    }
}
