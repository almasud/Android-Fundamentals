package com.example.almasud.fundamental.life_cycle_aware;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

/**
 * Life cycle owner class.
 */
public class LifeCycleAwareActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();

    // From onCreate to onResume call before this activity's observer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_aware);

        Log.i(TAG, "Owner ON_CREATE");
        Toast.makeText(this, TAG + "Owner ON_CREATE", Toast.LENGTH_SHORT).show();
        getLifecycle().addObserver(new LifeCycleAwareObserver(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Owner ON_START");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Owner ON_RESUME");
    }

    // From onPause to onDestroy call after this activity's observer
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Owner ON_PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Owner ON_STOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Owner ON_DESTROY");
        Toast.makeText(this, TAG + "Owner ON_DESTROY", Toast.LENGTH_SHORT).show();
    }
}
