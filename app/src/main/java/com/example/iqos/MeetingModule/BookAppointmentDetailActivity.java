package com.example.iqos.MeetingModule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iqos.AppointmentsModule.AppointmentAdapter;
import com.example.iqos.databinding.ActivityBookAppointmentBinding;
import com.example.iqos.databinding.ActivityBookAppointmentDetailBinding;

import java.util.ArrayList;

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



}