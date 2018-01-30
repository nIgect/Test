package com.tarasevich.test;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tarasevich.test.Constants.Constants;
import com.tarasevich.test.CustomImageRetrofitLoader.ImageResponse;
import com.tarasevich.test.CustomImageRetrofitLoader.ImageRetrofit;
import com.tarasevich.test.CustomImageRetrofitLoader.ServerImageResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static com.tarasevich.test.MainActivity.CODE_URI_PHOT0;


public class CameraFragment extends Fragment {

    @BindView(R.id.imageToTheServer)
    ImageView imageToTheServer;
    @BindView(R.id.sendButton)
        Button sendButton;

    private SharedPreferences mSharedPreferences;
  //  static final int REQUEST_IMAGE_CAPTURE = 1;

     static  final int PERMISSION_REQ_CODE = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera,container,false);
        ButterKnife.bind(this,view);
        mSharedPreferences = getContext().getSharedPreferences(Constants.SHARED, Context.MODE_PRIVATE);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final String token = mSharedPreferences.getString(Constants.TOKEN,null);
        if(TextUtils.isEmpty(token)){
         //  imageUpload(token);
        }

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CODE_URI_PHOT0 && resultCode == RESULT_OK) {
            Bitmap photo = data.getParcelableExtra("data");
            imageToTheServer.setImageBitmap(photo);

        }
    }

  private void imageUpload(final String token , final android.util.Base64 image, final int date, final byte lat, final byte lng){

      Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).
              addConverterFactory(GsonConverterFactory.create()).build();
      ImageRetrofit imageRetrofit = retrofit.create(ImageRetrofit.class);

      Call<ServerImageResponse<ImageResponse>> imageResponseCall = imageRetrofit.imageLoad(image,date,lat,lng,token);


      imageResponseCall.enqueue(new Callback<ServerImageResponse<ImageResponse>>() {
          @Override
          public void onResponse(Call<ServerImageResponse<ImageResponse>> call, Response<ServerImageResponse<ImageResponse>> response) {
              if (response.isSuccessful()){
                  ServerImageResponse<ImageResponse> body = response.body();
                  if(body!=null){
                      Snackbar.make(getView(),"Upload is successful",Snackbar.LENGTH_LONG);
                  }
              }else {
                  Snackbar.make(getView(), "Error", Snackbar.LENGTH_LONG).show();

              }
          }

          @Override
          public void onFailure(Call<ServerImageResponse<ImageResponse>> call, Throwable t) {
              Log.d(Constants.TAG,"failed");
              Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
          }
      });

      sendButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              imageUpload(token, image, date, lat, lng);
          }
      });



  }


}
