package com.tarasevich.test.CustomImageRetrofitLoader;

import com.google.gson.annotations.SerializedName;

import java.util.Base64;


public class ImageRequest {

    @SerializedName("base64Image")
    public Base64 base64Image;
    @SerializedName("date")
    public int date;
    @SerializedName("lat")
    public byte lat;
    @SerializedName("lng")
    public byte lng;

    public ImageRequest(Base64 base64Image, int date, byte lat, byte lng) {
        this.base64Image = base64Image;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }
}
