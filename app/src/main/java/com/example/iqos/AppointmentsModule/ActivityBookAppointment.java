package com.example.iqos.AppointmentsModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);



        //  appointmentsRecycler();


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
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityBookAppointment.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }


}