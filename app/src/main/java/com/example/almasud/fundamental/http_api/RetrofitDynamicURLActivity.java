package com.example.almasud.fundamental.http_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDynamicURLActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private double latitude = 23.956;
    private double longitude = 90.956;
    private String unit = "metric";
    private WeatherService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_dynamic_url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(WeatherService.class);
        String urlString = String.format("weather?lat=%f&lon=%f&units=%s&appid=%s", latitude, longitude, unit, getString(R.string.weather_api));
        Call<CurrentWeatherResponse> call = service.getCurrentWeatherResponse(urlString);
        call.enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                CurrentWeatherResponse currentWeatherResponse = response.body();
                String city = currentWeatherResponse.getName();
                double temp = currentWeatherResponse.getMain().getTemp();
                Toast.makeText(RetrofitDynamicURLActivity.this, "City: " + city + " and Temperature: " + temp, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {

            }
        });
    }
}
