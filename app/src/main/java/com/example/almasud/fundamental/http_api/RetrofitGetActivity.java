package com.example.almasud.fundamental.http_api;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.almasud.fundamental.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGetActivity extends AppCompatActivity implements FlowerResponseAdapter.ClickListener {
    public static final String BASE_URL = "http://services.hanselandpetal.com/";
    private static final String TAG = RetrofitGetActivity.class.getSimpleName();
    private FlowerResponseAdapter responseAdapter;
    private List<FlowerResponse> flowers;
    private RecyclerView flowerRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_get);

        flowerRV = findViewById(R.id.retrofitGetRV);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlowerService service = retrofit.create(FlowerService.class);
        Call<List<FlowerResponse>> call = service.getAllFlowers();
        call.enqueue(new Callback<List<FlowerResponse>>() {
            @Override
            public void onResponse(Call<List<FlowerResponse>> call, Response<List<FlowerResponse>> response) {
                if (response.code() == 200) {
                    flowers = response.body();
                    if (flowers != null) {
                        responseAdapter = new FlowerResponseAdapter(RetrofitGetActivity.this, flowers);
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(RetrofitGetActivity.this, LinearLayout.VERTICAL, false);
                        flowerRV.setLayoutManager(manager);
                        flowerRV.setAdapter(responseAdapter);
                        Log.e(TAG, "onResponse:" + flowers.size());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FlowerResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(FlowerResponse flower) {
        startActivity(new Intent(this, FlowerViewActivity.class).putExtra("flower",  flower));
    }
}
