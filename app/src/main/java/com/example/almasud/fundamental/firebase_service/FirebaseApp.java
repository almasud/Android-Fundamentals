package com.example.almasud.fundamental.firebase_service;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * According to Firebase Documentations setPersistenceEnabled is to be called only once (
 * before any other instances of FirebaseDatabase are made)
 */

public class FirebaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /* Enable disk persistence to save firebase data locally  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
