package com.example.iqos.LoginModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.iqos.Constants;
import com.example.iqos.MainActivity;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {

    ActivityLoginBinding mBinding;
    SharedPreferences mSharedPreferences;

    String username = "";
    String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
       // FirebaseApp.initializeApp(ActivityLogin.this);
            if(mSharedPreferences.getString(Constants.API_KEY,"").contains("skip")){
            Intent intent = new Intent(ActivityLogin.this,MainActivity.class);
            startActivity(intent);
            }else{
                mBinding.ivLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dexter.withContext(ActivityLogin.this)
                                .withPermissions(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.RECORD_AUDIO
                                ).withListener(new MultiplePermissionsListener() {
                                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {


                                        username = mBinding.etUsername.getText().toString();
                                        password = mBinding.etPassword.getText().toString();
                                        if (username.isEmpty()){
                                            mBinding.etUsername.setError("Enter Username");
                                        }else if (password.isEmpty()){
                                            mBinding.etUsername.setError("Enter Password");
                                        }else {
                                            getDeviceToken();
                                        }


                                        /* ... */}
                                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                                }).check();
                            /*Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                            startActivity(intent);*/
                    }
                });

            }



    }

    public void getDeviceToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                String deviceToken = task.getResult();
                Log.e("TGED", "DEVICE: " + deviceToken);
                login(username,password,deviceToken);


            }
        });
    }



    /////////////////////////Function that is used to Login the Delivery Person/////////////////////////////////
    public void login(String email, String password, String deviceToken) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityLogin.this).create(ApiService.class);
        Call<Model.LoginModel> call = apiService.login(email, password,deviceToken);
        call.enqueue(new Callback<Model.LoginModel>() {
            @Override
            public void onResponse(Call<Model.LoginModel> call, Response<Model.LoginModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.LoginModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus() == 1) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);
                                    mSharedPreferences.edit().putString(Constants.BAREAR_TOKEN, "Bearer " + keyModel.getAccessToken().toString()).commit();
                                    mSharedPreferences.edit().putString(Constants.API_KEY, "skip").commit();
                                    mSharedPreferences.edit().putString(Constants.USER_NAME, ""+ keyModel.getData().getName()).commit();
                                    mSharedPreferences.edit().putString(Constants.EMAIL, ""+ keyModel.getData().getCity()).commit();
                                    mSharedPreferences.edit().putString(Constants.HYPER_CARE, ""+ keyModel.getData().getHyper_care()).commit();

                                    Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }

                            } else {
                                Toast.makeText(ActivityLogin.this, "Invalid Email/Password", Toast.LENGTH_SHORT).show();
                                mBinding.progress.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            mBinding.progress.setVisibility(View.GONE);
                            Toast.makeText(ActivityLogin.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.LoginModel> call, Throwable t) {
                 runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityLogin.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

}