package com.example.almasud.fundamental.view_model;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.Random;

public class DataViewModel extends ViewModel {
    private String TAG = this.getClass().getSimpleName();
    private String randNumber;

    public String getRandNumber() {
        if (randNumber == null) {
            randNumber = "Number is: " + (new Random().nextInt(11) + 1);
            Log.i(TAG, "Create new random number");
        }
        Log.i(TAG, "Get random number");
        return randNumber;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }
}
