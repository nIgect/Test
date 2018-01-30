package com.tarasevich.test.CustomRetrofitModelSignUp;


import com.google.gson.annotations.SerializedName;

public class ServerResponse<T> {

   @SerializedName("status")
    public int status;
   @SerializedName("data")
    public T data;
   @SerializedName("error")
    public String error;


}
