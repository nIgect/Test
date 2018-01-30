package com.tarasevich.test.CustomRetrofitModelSignUp;

import com.google.gson.annotations.SerializedName;



public class SignUpRequest {

    @SerializedName("login")
    public  String login;
    @SerializedName("password")
    public  String password;

    public SignUpRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
