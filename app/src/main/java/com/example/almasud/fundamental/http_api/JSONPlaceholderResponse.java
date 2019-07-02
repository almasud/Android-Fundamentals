package com.example.almasud.fundamental.http_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONPlaceholderResponse {

    @SerializedName("request")
    @Expose
    private String request;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
