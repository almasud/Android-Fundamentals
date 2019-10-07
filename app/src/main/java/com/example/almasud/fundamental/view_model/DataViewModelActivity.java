package com.example.almasud.fundamental.view_model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.almasud.fundamental.R;

/**
 * The ViewModel class is designed to store and manage UI-related data in a lifecycle
 * conscious way. The ViewModel class allows data to survive configuration changes such
 * as screen rotations.
 */

public class DataViewModelActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view_model);

        mTextView = findViewById(R.id.textViewModel);
        DataViewModel model = ViewModelProviders.of(this).get(DataViewModel.class);
        mTextView.setText(model.getRandNumber());
        Log.i(TAG, "Random number set");
    }
}
