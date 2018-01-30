package com.tarasevich.test.CustomRetrofitModelSignUp;


import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserRetrofit
{
    @Headers({"Accept:application/json","Content-Type:application/json"})
   @POST("/api/account/signup")
    Call<ServerResponse<SignUpResponse>> signUp(@Body SignUpRequest request);

    @Headers({"Accept:application/json","Content-Type:application/json"})
  @POST("/api/account/signin")
   Call<ServerResponse<SignInResponse>> signIn(@Body SignInRequest request);

}
