package com.example.almasud.fundamental.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        CharSequence charSequence = intent.getCharSequenceExtra("msg");
        Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
    }
}
