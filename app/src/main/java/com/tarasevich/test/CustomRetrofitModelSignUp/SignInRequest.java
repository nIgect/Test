package com.tarasevich.test.CustomRetrofitModelSignUp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 15.01.2018.
 */

public class SignInRequest {
    @SerializedName("login")
    public  String login;
    @SerializedName("password")
    public  String password;


    public SignInRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }


}
