package com.example.almasud.fundamental.service.bound_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class MyBoundService extends Service {
    public MyBoundService() {
    }

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
