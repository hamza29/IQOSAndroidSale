package com.example.iqos.SalesModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.LeadsModule.LeadsAdapter;
import com.example.iqos.databinding.ActivityNoSalesBinding;
import com.example.iqos.databinding.ActivitySalesBinding;

import java.util.ArrayList;

public class ActivityNoSales extends AppCompatActivity {

    ActivityNoSalesBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityNoSalesBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        feedbackrecycler();

        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void feedbackrecycler() {

        ArrayList<String> leads = new ArrayList<>();

        leads.add("");
        leads.add("");
        leads.add("");

        FeedBackAdapter feedBackAdapter;
        mBinding.rvFeedack.setLayoutManager(new LinearLayoutManager(ActivityNoSales.this, LinearLayoutManager.VERTICAL, false));
        feedBackAdapter = new FeedBackAdapter(ActivityNoSales.this, leads);
        mBinding.rvFeedack.setAdapter(feedBackAdapter);
    }


}