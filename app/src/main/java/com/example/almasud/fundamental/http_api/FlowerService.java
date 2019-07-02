package com.example.almasud.fundamental.http_api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FlowerService {
    @GET("feeds/flowers.json")
    Call<List<FlowerResponse>> getAllFlowers();
}
