package com.tarasevich.test.CustomRetrofitModelSignUp;


import com.google.gson.annotations.SerializedName;

public class SignInResponse {
    @SerializedName("userId")
    public int userId;
    @SerializedName("login")
    public String login;
    @SerializedName("token")
    public String token;

}
