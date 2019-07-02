package com.example.almasud.fundamental.navigation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherService {
    @GET
    Call<CurrentWeatherResponse> getCurrentWeather(@Url String urlString);

    @GET
    Call<WeatherForecastResponse> getWeatherForecast(@Url String url);
}
