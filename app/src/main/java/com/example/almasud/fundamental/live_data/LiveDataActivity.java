package com.example.almasud.fundamental.live_data;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.almasud.fundamental.R;

/**
 * LiveData is an observable data holder class. Keeps data and allows dat to be
 * observed. Observe LiveData from app component's onCreate() method.
 * Benefits of LiveData:
    * Keeps the UI updated in case of changes.
    * Automatically destroyed when associated LifecycleOwner is destroyed.
    * No crashes due to stopped activities.
    * Can be shared by multiple resources.
 * Best when used with ViewModel.
 */
public class LiveDataActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);

        mTextView = findViewById(R.id.textViewLiveData);
        mButton = findViewById(R.id.buttonLiveData);

        LiveDataViewModel model = ViewModelProviders.of(this).get(LiveDataViewModel.class);
        LiveData<String> randomNumber = model.getRandomNumber();
        randomNumber.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mTextView.setText(s);
                Log.i(TAG, "Data updated in UI");
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.createRandomNumber();
            }
        });
        Log.i(TAG, "Random number set");
    }
}
