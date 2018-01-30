package com.tarasevich.test.CustomImageRetrofitLoader;

import android.util.Base64;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;

import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ImageRetrofit {

    @Multipart
    @POST("/api/image")
    Call<ServerImageResponse<ImageResponse>>  imageLoad(
            @Part ("base64Image") Base64 image,
            @Part ( "date") int date,
            @Part ("lat") byte lat,
            @Part  ("lng") byte lng,
            @Header("Access-Token")String token
    );

    @Headers("Accept:application/json")
    @GET("/api/image")
    Call<ServerImageResponse<ImageResponse>> imageDownload(@Header("Access-Token")String token, @Query("page")int page);

    @DELETE("/api/image")
    Call<ServerImageResponse<ImageResponse>> imageDelete(@Header("Access-Token")String token,@Path("id") int id);

}
