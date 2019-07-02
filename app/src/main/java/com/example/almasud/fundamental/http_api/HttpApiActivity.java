package com.example.almasud.fundamental.http_api;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.almasud.fundamental.R;


public class HttpApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_api);
    }

    // Download and show an image from web using AsyncTask or Picasso Library
    public void activityDownloadImg(View view) {
        startActivity(new Intent(this, DownloadImageActivity.class));
    }

    // GET web api using Retrofit Library
    public void activityRetrofitGet(View view) {
        startActivity(new Intent(this, RetrofitGetActivity.class));
    }

    // GET web api dynamic URL using Retrofit Library
    public void activityRetrofitDynamicURL(View view) {
        startActivity(new Intent(this, RetrofitDynamicURLActivity.class));
    }

    // POST web api using Retrofit Library
    public void activityRetrofitPost(View view) {
        startActivity(new Intent(this, RetrofitPostActivity.class));
    }
}
