package com.example.iqos;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iqos.AppointmentsModule.ActivityBookAppointment;
import com.example.iqos.HomeFragmentModule.HomeFragment;
import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.SalesModule.PerformanceActivity;
import com.example.iqos.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ActivityMainBinding mBinding;
    SharedPreferences mSharedPreferences;

    @Override
    public void onBackPressed() {

//        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        LinearLayout llHome =mBinding.navView.getHeaderView(0).findViewById(R.id.llHome);
        LinearLayout llInventory =mBinding.navView.getHeaderView(0).findViewById(R.id.llInventory);
        LinearLayout llMyPerformance =mBinding.navView.getHeaderView(0).findViewById(R.id.llMyPerformance);
        LinearLayout llAppointment =mBinding.navView.getHeaderView(0).findViewById(R.id.llAppointment);
        LinearLayout llLeads =mBinding.navView.getHeaderView(0).findViewById(R.id.llLeads);

        if(mSharedPreferences.getString(Constants.HYPER_CARE,"").equalsIgnoreCase("1")){
             llMyPerformance.setVisibility(View.GONE);
            llInventory.setVisibility(View.GONE);
            llAppointment.setVisibility(View.GONE);
        }else {
            llHome.setVisibility(View.VISIBLE);
            llMyPerformance.setVisibility(View.VISIBLE);
            llInventory.setVisibility(View.VISIBLE);
            llAppointment.setVisibility(View.VISIBLE);
        }
        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new HomeFragment());

            }
        });
        llMyPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PerformanceActivity.class);
                startActivity(intent);

            }
        });
        llInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,InventoryActivity.class);
                startActivity(intent);
            }
        });

        llAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ActivityBookAppointment.class);
                startActivity(intent);


            }
        });
        llLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ActivityLeads.class);
                startActivity(intent);



            }
        });

            if(mSharedPreferences.getString(Constants.EMAIL,"") !=null){
                TextView textView =mBinding.navView.getHeaderView(0).findViewById(R.id.tvCity);
            textView .setText(""+mSharedPreferences.getString(Constants.EMAIL,""));
            }

            if(mSharedPreferences.getString(Constants.USER_NAME,"") !=null){
                TextView textView =mBinding.navView.getHeaderView(0).findViewById(R.id.tvName);
            textView .setText(""+mSharedPreferences.getString(Constants.USER_NAME,""));
            }

        mBinding.navView.setNavigationItemSelectedListener(this);

        mBinding.appBarMain.ivDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mBinding.drawerLayout.isDrawerOpen(GravityCompat.START))
                    mBinding.drawerLayout.openDrawer(GravityCompat.START);
                else mBinding.drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        loadFragment(new HomeFragment());


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;

    }



    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();


            return true;
        }

        return false;
    }


}