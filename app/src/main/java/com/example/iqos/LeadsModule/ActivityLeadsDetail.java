package com.example.iqos.LeadsModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.iqos.AppointmentsModule.ActivityBookAppointment;
import com.example.iqos.MeetingModule.ActivityPreMeetingCheckList;
import com.example.iqos.SalesModule.ActivitySales;
import com.example.iqos.databinding.ActivityLeadsBinding;
import com.example.iqos.databinding.ActivityLeadsDetailBinding;

import java.util.ArrayList;

public class ActivityLeadsDetail extends AppCompatActivity {

    ActivityLeadsDetailBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeadsDetailBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);


        mBinding.tvBookAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLeadsDetail.this, ActivityBookAppointment.class);
                startActivity(intent);
            }
        });

        mBinding.tvPreMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLeadsDetail.this, ActivityPreMeetingCheckList.class);
                startActivity(intent);
            }
        });


        mBinding.tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLeadsDetail.this, ActivitySales.class);
                startActivity(intent);
            }
        });




    }



}