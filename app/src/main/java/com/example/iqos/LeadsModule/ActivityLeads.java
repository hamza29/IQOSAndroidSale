package com.example.iqos.LeadsModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.iqos.databinding.ActivityLeadsBinding;
import com.example.iqos.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class ActivityLeads extends AppCompatActivity {

    ActivityLeadsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeadsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        LeadsRecycler();

        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void LeadsRecycler() {

        ArrayList<String> leads = new ArrayList<>();

        leads.add("");
        leads.add("");
        leads.add("");

        LeadsAdapter leadsAdapter;
        mBinding.rvLeads.setLayoutManager(new LinearLayoutManager(ActivityLeads.this, LinearLayoutManager.VERTICAL, false));
        leadsAdapter = new LeadsAdapter(ActivityLeads.this, leads);
        mBinding.rvLeads.setAdapter(leadsAdapter);
    }

}