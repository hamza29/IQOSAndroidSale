package com.example.iqos.SalesModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.iqos.LeadsModule.ActivityLeadsDetail;
import com.example.iqos.databinding.ActivityBookAppointmentBinding;
import com.example.iqos.databinding.ActivitySalesBinding;

public class ActivitySales extends AppCompatActivity {


    ActivitySalesBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySalesBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);


        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySales.this, ActivityNoSales.class);
                startActivity(intent);
            }
        });



    }
}