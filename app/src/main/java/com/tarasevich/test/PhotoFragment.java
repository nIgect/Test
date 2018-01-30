package com.tarasevich.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tarasevich.test.Constants.Constants;
import com.tarasevich.test.CustomImageRetrofitLoader.ImageResponse;
import com.tarasevich.test.CustomImageRetrofitLoader.ImageRetrofit;
import com.tarasevich.test.CustomImageRetrofitLoader.ServerImageResponse;
import com.tarasevich.test.MyImageViewAdapter.MyImageViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PhotoFragment extends Fragment {

    private SharedPreferences mSharedPreferences;

    @BindView(R.id.image_recycler_view)
    RecyclerView imageRecyclerView;
    MyImageViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo,container,false);
        ButterKnife.bind(this,view);
        mSharedPreferences = getContext().getSharedPreferences(Constants.SHARED, Context.MODE_PRIVATE);
        adapter = new MyImageViewAdapter(Glide.with(this));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        imageRecyclerView.setLayoutManager(layoutManager);
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        final String token = mSharedPreferences.getString(Constants.TOKEN,null);
        if (!TextUtils.isEmpty(token)){
            loadImageProcess(token,1);
        }
    }

    private void loadImageProcess(String token, int page){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        ImageRetrofit imageRetrofit = retrofit.create(ImageRetrofit.class);

        Call<ServerImageResponse<ImageResponse>> imageResponseCall = imageRetrofit.imageDownload(token, page);

        imageResponseCall.enqueue(new Callback<ServerImageResponse<ImageResponse>>() {
            @Override
            public void onResponse(Call<ServerImageResponse<ImageResponse>> call, Response<ServerImageResponse<ImageResponse>> response) {
               if (response.isSuccessful()){
                   ServerImageResponse<ImageResponse> body = response.body();
                   if (body != null){
                       adapter.updateImages(body.data);
                   }
                   Snackbar.make(getView(),"Download is successful",Snackbar.LENGTH_LONG);
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

    }


}
