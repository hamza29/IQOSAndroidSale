package com.example.iqos.MeetingModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iqos.AppointmentsModule.AppointmentAdapter;
import com.example.iqos.AppointmentsModule.AppointmentBookingDetailActivity;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityBookAppointmentBinding;
import com.example.iqos.databinding.ActivityBookAppointmentDetailBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentDetailActivity extends AppCompatActivity {


    ActivityBookAppointmentDetailBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBookAppointmentDetailBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

mBinding.startAppoint.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(BookAppointmentDetailActivity.this, ActivityAppointmentMeetingCheckList.class);
        startActivity(intent);
    }
});
    }


    public void boookApp(String token,String id,String status,String app_at,String location) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(BookAppointmentDetailActivity.this).create(ApiService.class);
        Call<Model.GenerealModel> call = apiService.bookAppointment("application/json",token,id,status,app_at,location);
        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.GenerealModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);

                                    Toast.makeText(BookAppointmentDetailActivity.this, "Appointment Book Successfully", Toast.LENGTH_SHORT).show();




                                }

                            } else {
                                Toast.makeText(BookAppointmentDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BookAppointmentDetailActivity.this, "key model null", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(BookAppointmentDetailActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }





}