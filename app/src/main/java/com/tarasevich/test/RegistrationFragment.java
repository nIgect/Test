package com.tarasevich.test;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tarasevich.test.Constants.Constants;

import com.tarasevich.test.CustomRetrofitModelSignUp.ServerResponse;
import com.tarasevich.test.CustomRetrofitModelSignUp.SignInResponse;
import com.tarasevich.test.CustomRetrofitModelSignUp.SignUpRequest;
import com.tarasevich.test.CustomRetrofitModelSignUp.SignUpResponse;

import com.tarasevich.test.CustomRetrofitModelSignUp.UserRetrofit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegistrationFragment extends Fragment implements View.OnClickListener{


    @BindView(R.id.registrationName)
    EditText registrationName;
    @BindView(R.id.registrationPassword)
    EditText registrationPassword;
    @BindView(R.id.registrationConfirmPassword)
    EditText registrationConfirmPassword;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    private SharedPreferences mSharedPreferences;
    private static final int CODE_LOGIN = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_registration,container,false);
       ButterKnife.bind(this,view);
       initListeners();
        mSharedPreferences = getContext().getSharedPreferences(Constants.SHARED, Context.MODE_PRIVATE);
       return view;
    }



    public void initListeners()
    {
      btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String login = registrationName.getText().toString();
        String password = registrationPassword.getText().toString();


        if(!login.isEmpty() && !password.isEmpty())
        {
            registerProcess(login,password);
            Snackbar.make(getView(), "Register successful", Snackbar.LENGTH_LONG).show();
            Intent loginIntent = new Intent();
            loginIntent.putExtra("login",login);
            startActivityForResult(loginIntent,CODE_LOGIN);

        } else {

            Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
        }

    }


    private void registerProcess(String login ,String password){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();



        UserRetrofit userRetrofit = retrofit.create(UserRetrofit.class);


        Call<ServerResponse<SignUpResponse>> responseCall = userRetrofit.signUp(new SignUpRequest(login, password));


        responseCall.enqueue(new Callback<ServerResponse<SignUpResponse>>() {
            @Override
            public void onResponse(Call<ServerResponse<SignUpResponse>> call, Response<ServerResponse<SignUpResponse>> response) {
                if(response.isSuccessful()){
                    ServerResponse<SignUpResponse> resp = response.body();
                    if(resp!=null){
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString(Constants.TOKEN,resp.data.token);
                        editor.apply();
                        Intent intent = new Intent(getContext(),MainActivity.class);
                        startActivity(intent);
                    }else{
                        Snackbar.make(getView(), "Error", Snackbar.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<ServerResponse<SignUpResponse>> call, Throwable t) {
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });


    }
}
