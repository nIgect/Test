package com.tarasevich.test.CustomImageRetrofitLoader;

import com.google.gson.annotations.SerializedName;



public class ServerImageResponse<T> {
    @SerializedName("status")
    public int status;
    @SerializedName("data")
    public T data;
    @SerializedName("error")
    public String error;
}
