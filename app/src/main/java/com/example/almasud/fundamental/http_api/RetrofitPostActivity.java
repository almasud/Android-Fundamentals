package com.example.almasud.fundamental.http_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.almasud.fundamental.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitPostActivity extends AppCompatActivity {

    private RetrofitPostService postService;
    private EditText postET;
    private TextView postTV;
    private Button postBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_post);
        postET = findViewById(R.id.postET);
        postTV = findViewById(R.id.postTV);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postService = retrofit.create(RetrofitPostService.class);
    }

    public void post(View view) {
        JSONPlaceholderRequest request = new JSONPlaceholderRequest(postET.getText().toString());
        Call<JSONPlaceholderResponse> requestCall = postService.getJSONPlaceholderData(request);
        requestCall.enqueue(new Callback<JSONPlaceholderResponse>() {
            @Override
            public void onResponse(Call<JSONPlaceholderResponse> call, Response<JSONPlaceholderResponse> response) {
                JSONPlaceholderResponse jsonPlaceholderResponse = response.body();
                postTV.setText(String.format("Request: %s and ID: %d", jsonPlaceholderResponse.getRequest(), jsonPlaceholderResponse.getId()));
            }

            @Override
            public void onFailure(Call<JSONPlaceholderResponse> call, Throwable t) {

            }
        });
    }
}
