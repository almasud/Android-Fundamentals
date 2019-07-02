package com.example.almasud.fundamental.http_api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherService {
    @GET
    Call<CurrentWeatherResponse> getCurrentWeatherResponse(@Url String urlString);
}
