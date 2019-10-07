package com.example.almasud.fundamental.live_data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class LiveDataViewModel extends ViewModel {
    private String TAG = this.getClass().getSimpleName();
    private MutableLiveData<String> randomNumber;

    public MutableLiveData<String> getRandomNumber() {
        if (randomNumber == null) {
            randomNumber = new MutableLiveData<>();
           createRandomNumber();
        }
        Log.i(TAG, "Get new number");
        return randomNumber;
    }

    public void createRandomNumber() {
        Log.i(TAG, "Create new number");
        randomNumber.setValue("Number is: " + (new Random().nextInt(10) + 1));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }
}
