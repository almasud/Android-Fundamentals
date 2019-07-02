package com.example.almasud.fundamental.http_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FlowerResponse implements Serializable {
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("productId")
    @Expose
    private Integer productId;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
