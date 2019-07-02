package com.example.almasud.fundamental.http_api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitPostService {
    @POST("posts/")
    Call<JSONPlaceholderResponse> getJSONPlaceholderData(@Body JSONPlaceholderRequest request);
}
