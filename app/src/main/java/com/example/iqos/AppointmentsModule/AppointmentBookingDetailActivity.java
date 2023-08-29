package com.example.iqos.AppointmentsModule;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iqos.databinding.ActivityBookAppointmentBinding;
import com.example.iqos.databinding.ActivityBookAppointmentDetailBinding;
import com.example.iqos.databinding.ActivityBookingAppointmentBinding;

public class AppointmentBookingDetailActivity extends AppCompatActivity {


    ActivityBookingAppointmentBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBookingAppointmentBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);


    }



}