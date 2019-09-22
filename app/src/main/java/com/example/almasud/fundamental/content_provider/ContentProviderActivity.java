package com.example.almasud.fundamental.content_provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.almasud.fundamental.R;

public class ContentProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
    }

    public void contactProviderActivity(View view) {
        startActivity(new Intent(this, ContactProviderActivity.class));
    }

    public void eventProviderActivity(View view) {
        startActivity(new Intent(new Intent(this, EventProviderActivity.class)));
    }
}
