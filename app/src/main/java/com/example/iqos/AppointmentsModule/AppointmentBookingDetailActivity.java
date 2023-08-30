package com.example.iqos.AppointmentsModule;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iqos.Constants;
import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityBookAppointmentBinding;
import com.example.iqos.databinding.ActivityBookAppointmentDetailBinding;
import com.example.iqos.databinding.ActivityBookingAppointmentBinding;
import com.google.android.gms.common.internal.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentBookingDetailActivity extends AppCompatActivity {


    ActivityBookingAppointmentBinding mBinding;
    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBookingAppointmentBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);

        mBinding.confirmApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // boookApp();
            }
        });

        boookAppDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),"1");

    }
    public void boookAppDetails(String token,String id) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(AppointmentBookingDetailActivity.this).create(ApiService.class);
        Call<Model.BookAppointmentDetailsModel> call = apiService.bookAppointmentDetails("application/json",token,id);
        call.enqueue(new Callback<Model.BookAppointmentDetailsModel>() {
            @Override
            public void onResponse(Call<Model.BookAppointmentDetailsModel> call, Response<Model.BookAppointmentDetailsModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.BookAppointmentDetailsModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {
                                    mBinding.progress.setVisibility(View.GONE);
                                    Toast.makeText(AppointmentBookingDetailActivity.this, "Book app details  load Successfully", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(AppointmentBookingDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AppointmentBookingDetailActivity.this, "key model null", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.BookAppointmentDetailsModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(AppointmentBookingDetailActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }



}