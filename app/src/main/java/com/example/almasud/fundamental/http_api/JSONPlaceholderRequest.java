package com.example.almasud.fundamental.http_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONPlaceholderRequest {

    @SerializedName("request")
    @Expose
    private String request;

    public JSONPlaceholderRequest(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

}
