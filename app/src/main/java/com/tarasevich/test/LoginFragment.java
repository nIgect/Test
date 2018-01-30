package com.tarasevich.test;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tarasevich.test.Constants.Constants;

import com.tarasevich.test.CustomRetrofitModelSignUp.ServerResponse;
import com.tarasevich.test.CustomRetrofitModelSignUp.SignInRequest;
import com.tarasevich.test.CustomRetrofitModelSignUp.SignInResponse;

import com.tarasevich.test.CustomRetrofitModelSignUp.SignUpRequest;
import com.tarasevich.test.CustomRetrofitModelSignUp.SignUpResponse;
import com.tarasevich.test.CustomRetrofitModelSignUp.UserRetrofit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment implements View.OnClickListener
{
   @BindView(R.id.btnSignIn)
    Button btnSignIn;
   @BindView(R.id.editLogin)
    EditText editLogin;
   @BindView(R.id.loginPassword)
   EditText loginPassword;

   private SharedPreferences mSharedPreferences;


   private UserRetrofit userRetrofit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        ButterKnife.bind(this,view);
        initListeners(view);
        mSharedPreferences = getContext().getSharedPreferences(Constants.SHARED, Context.MODE_PRIVATE);
        return view;

    }


    @Override
    public void onClick(View view) {


        String login = editLogin.getText().toString();
        String password = loginPassword.getText().toString();
        if (!login.isEmpty() && !password.isEmpty()) {

               loginProcess(login, password);


        } else {

          Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();

        }
    }

  private void loginProcess(String login , String password) {


      Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
              .addConverterFactory(GsonConverterFactory.create()).build();

       UserRetrofit userRetrofit = retrofit.create(UserRetrofit.class);

        Call<ServerResponse<SignInResponse>> responseCall = userRetrofit.signIn(new SignInRequest(login, password));

        responseCall.enqueue(new Callback<ServerResponse<SignInResponse>>() {
            @Override
            public void onResponse(Call<ServerResponse<SignInResponse>> call, Response<ServerResponse<SignInResponse>> response) {
               if(response.isSuccessful()){
                   ServerResponse<SignInResponse> resp = response.body();
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
            public void onFailure(Call<ServerResponse<SignInResponse>> call, Throwable t) {
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });



    }


    private void initListeners(View view){
        mSharedPreferences = getActivity().getPreferences(0);
        btnSignIn.setOnClickListener(this);

    }

}





