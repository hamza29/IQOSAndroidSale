package com.example.iqos.HomeFragmentModule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iqos.AppointmentsModule.ActivityBookAppointment;
import com.example.iqos.Constants;
import com.example.iqos.InventoryActivity;
import com.example.iqos.LeadsModule.ActivityHyperAppointments;
import com.example.iqos.LeadsModule.ActivityHyperCareLeads;
import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.LeadsModule.ActivityLeadsDetail;
import com.example.iqos.LoginModule.ActivityLogin;
import com.example.iqos.MainActivity;
import com.example.iqos.MeetingModule.ActivityPackages;
import com.example.iqos.R;
import com.example.iqos.SalesModule.PerformanceActivity;
import com.example.iqos.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {


    FragmentHomeBinding mBinding;

    HomeFragment activity;
    SharedPreferences mSharedPreferences;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        mSharedPreferences = getActivity().getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);

        activity = this;
        if(mSharedPreferences.getString(Constants.HYPER_CARE,"").equalsIgnoreCase("1")){
             mBinding.rlAppoints.setVisibility(View.GONE);
            mBinding.rlHyperCare.setVisibility(View.GONE);
            mBinding.rlInvent.setVisibility(View.GONE);
            mBinding.rlPerf.setVisibility(View.GONE);
            mBinding.rlHyperCareAppointment.setVisibility(View.GONE);
        }else{
             mBinding.rlAppoints.setVisibility(View.VISIBLE);
            mBinding.rlHyperCare.setVisibility(View.VISIBLE);
            mBinding.rlInvent.setVisibility(View.VISIBLE);
            mBinding.rlPerf.setVisibility(View.VISIBLE);
            mBinding.rlHyperCareAppointment.setVisibility(View.VISIBLE);
        }

        if(mSharedPreferences.getString(Constants.ROLE,"").equalsIgnoreCase("sales")){
            mBinding.rlAppoints.setVisibility(View.GONE);
            mBinding.rlHyperCare.setVisibility(View.GONE);
            mBinding.rlInvent.setVisibility(View.GONE);
            mBinding.rlPerf.setVisibility(View.VISIBLE);
            mBinding.rlSales.setVisibility(View.VISIBLE);
            mBinding.rlHyperCareAppointment.setVisibility(View.GONE);
        }else{
            mBinding.rlAppoints.setVisibility(View.VISIBLE);
            mBinding.rlHyperCare.setVisibility(View.VISIBLE);
            mBinding.rlInvent.setVisibility(View.VISIBLE);
            mBinding.rlPerf.setVisibility(View.VISIBLE);
            mBinding.rlSales.setVisibility(View.GONE);
            mBinding.rlHyperCareAppointment.setVisibility(View.VISIBLE);
        }

        mBinding.rlLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityLeads.class);
                intent.putExtra("type", "leads");
                startActivity(intent);


            }

        });mBinding.rlSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityLeads.class);
                intent.putExtra("type", "sales");
                startActivity(intent);
            }

        });mBinding.rlAppoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), ActivityBookAppointment.class);
                        startActivity(intent);



            }
        });

        mBinding.rlHyperCareAppointment.setVisibility(View.GONE);

        mBinding.rlHyperCareAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), ActivityHyperAppointments.class);
                        startActivity(intent);



            }
        });
mBinding.rlHyperCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), ActivityHyperCareLeads.class);
                        startActivity(intent);



            }
        });
mBinding.rlInvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), InventoryActivity.class);
                        startActivity(intent);



            }
        });
mBinding.rlPerf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), PerformanceActivity.class);
                        startActivity(intent);



            }
        });


        return view;


    }
}