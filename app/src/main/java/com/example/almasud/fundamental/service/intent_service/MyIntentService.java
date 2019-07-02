package com.example.almasud.fundamental.service.intent_service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyIntentService extends IntentService {

    private static final String TAG = "Intent Service";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /*
         Note: We need not stop this service because it's stop itself and need not
         create a new thread for long background processing because this method codes
         execute in background thread. Only one component or activity can start
         intent service.
         */
        // This Toast cannot show because we cannot update UI from background thread
        Toast.makeText(this, "Intent service started", Toast.LENGTH_LONG).show();
        String msg = intent.getStringExtra("msg");
        Log.e(TAG, "Service started: " + msg);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Intent service created", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Intent service destroyed", Toast.LENGTH_LONG).show();
    }

}
