package com.example.iqos.AppointmentsModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.LeadsModule.LeadsAdapter;
import com.example.iqos.databinding.ActivityBookAppointmentBinding;
import com.example.iqos.databinding.ActivityLeadsDetailBinding;

import java.util.ArrayList;

public class ActivityBookAppointment extends AppCompatActivity {


    ActivityBookAppointmentBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);



        appointmentsRecycler();


    }

    private void appointmentsRecycler() {

        ArrayList<String> appointments = new ArrayList<>();

        appointments.add("");
        appointments.add("");
        appointments.add("");

        AppointmentAdapter appointmentAdapter;
        mBinding.rvAppointments.setLayoutManager(new LinearLayoutManager(ActivityBookAppointment.this, LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new AppointmentAdapter(ActivityBookAppointment.this, appointments);
        mBinding.rvAppointments.setAdapter(appointmentAdapter);
    }


}