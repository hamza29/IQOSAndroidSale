package com.example.iqos.HomeFragmentModule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iqos.AppointmentsModule.ActivityBookAppointment;
import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.LeadsModule.ActivityLeadsDetail;
import com.example.iqos.LoginModule.ActivityLogin;
import com.example.iqos.MainActivity;
import com.example.iqos.R;
import com.example.iqos.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {


    FragmentHomeBinding mBinding;

    HomeFragment activity;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        activity = this;

        mBinding.rlLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityLeads.class);
                 startActivity(intent);


            }
        });mBinding.rlAppoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), ActivityBookAppointment.class);
                        startActivity(intent);



            }
        });


        return view;


    }
}