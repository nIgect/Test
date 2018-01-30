package com.tarasevich.test.CustomRetrofitModelSignUp;

import com.google.gson.annotations.SerializedName;



public class SignUpResponse {
    @SerializedName("userId")
    public int userId;
    @SerializedName("login")
    public String login;
    @SerializedName("token")
    public String token;
}
