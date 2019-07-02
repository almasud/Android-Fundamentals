package com.example.almasud.fundamental.service.started_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class MyStartedService extends Service {

    private static final String TAG = "Started Service";

    /*
      The system invokes this method by calling bindService() when another component
      wants to bind with the service (such as to perform RPC). In our implementation
      of this method, we must provide an interface that clients use to communicate
      with the service by returning an IBinder. we must always implement this method;
      however, if we don't want to allow binding, we should return null.
    */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Started service created", Toast.LENGTH_LONG).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /*
       The system invokes this method by calling startService() when another component
       (such as an activity) requests that the service be started. When this method
       executes, the service is started and can run in the background indefinitely.
       If we implement this, it is our responsibility to stop the service when its
       work is complete by calling stopSelf() or stopService(). If we only want to
       provide binding, we don't need to implement this method.
     */
        Toast.makeText(this, "Started service started: " + intent.getStringExtra("msg"), Toast.LENGTH_LONG).show();
        Log.e(TAG, "Service Id: " + startId);
        /*
         Note: We can start a new thread for long background processing because
         this method codes execute in main thread. Many activity or component can
         start the same started service individually.
         */
//        stopSelf();  // After finish the work of service it should stop
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Started service destroyed", Toast.LENGTH_LONG).show();
    }
}
