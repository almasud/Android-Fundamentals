package com.example.almasud.fundamental.service.bound_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

/**
 * A bound service is the server in a client-server interface. It allows components
 * (such as activities) to bind to the service, send requests, receive responses, and perform
 * interprocess communication (IPC). A bound service typically lives only while it serves
 * another application component and does not run in the background indefinitely.
 */

public class MyBoundService extends Service {

    public class MyLocalBinder extends Binder {
        public MyBoundService getService() {
            return MyBoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = new MyLocalBinder();
        return binder;
    }

    public int generateRandomNumber() {
        Random random = new Random();
        return 1 + random.nextInt(100);
    }
}
