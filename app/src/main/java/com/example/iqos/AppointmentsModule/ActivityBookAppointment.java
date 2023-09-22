package com.example.iqos.AppointmentsModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.iqos.Constants;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityBookAppointmentBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBookAppointment extends AppCompatActivity {


    ActivityBookAppointmentBinding mBinding;
    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
mBinding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        mBinding.swipe.setRefreshing(true);
        getAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));

    }
});

        //  appointmentsRecycler();

        getAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
    }

    private void appointmentsRecycler(List<Model.Appointment> appointments) {



        AppointmentAdapter appointmentAdapter;
        mBinding.rvAppointments.setLayoutManager(new LinearLayoutManager(ActivityBookAppointment.this, LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new AppointmentAdapter(ActivityBookAppointment.this, appointments);
        mBinding.rvAppointments.setAdapter(appointmentAdapter);


    }


    public void getAppointment(String token) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityBookAppointment.this).create(ApiService.class);
        Call<Model.GetAppointmentModel> call = apiService.getAppointment("application/json",token);
        call.enqueue(new Callback<Model.GetAppointmentModel>() {
            @Override
            public void onResponse(Call<Model.GetAppointmentModel> call, Response<Model.GetAppointmentModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.GetAppointmentModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipe.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);

                                    if (keyModel.getData().getAppointments().size() >0){
                                        appointmentsRecycler(keyModel.getData().getAppointments())   ;
                                    }



                                }

                            } else {
                                Toast.makeText(ActivityBookAppointment.this, "Error", Toast.LENGTH_SHORT).show();
                                mBinding.progress.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipe.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            mBinding.progress.setVisibility(View.GONE);
                            Toast.makeText(ActivityBookAppointment.this, "key model null", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.GetAppointmentModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipe.setRefreshing(false);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityBookAppointment.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }


}