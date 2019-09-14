package com.example.almasud.fundamental.map_location;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DirectionService {
    @GET
    Call<DirectionResponse> getDirection(@Url String url);
}
