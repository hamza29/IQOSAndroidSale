package com.example.iqos;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;

import com.example.iqos.HomeFragmentModule.HomeFragment;
import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);



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