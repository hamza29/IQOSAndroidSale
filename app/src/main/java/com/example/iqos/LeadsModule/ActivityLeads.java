package com.example.iqos.LeadsModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.iqos.Constants;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityLeadsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLeads extends AppCompatActivity {

    ActivityLeadsBinding mBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeadsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));

        //LeadsRecycler();
mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));

    }
});
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));

    }

    private void LeadsRecycler(List<Model.Lead> leads) {


        LeadsAdapter leadsAdapter;
        mBinding.rvLeads.setLayoutManager(new LinearLayoutManager(ActivityLeads.this, LinearLayoutManager.VERTICAL, false));
        leadsAdapter = new LeadsAdapter(ActivityLeads.this, leads);
        mBinding.rvLeads.setAdapter(leadsAdapter);
    }


    public void getLeads(String token) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityLeads.this).create(ApiService.class);
        Call<Model.GetLeadsModel> call = apiService.getLeads("application/json",token,"");
        call.enqueue(new Callback<Model.GetLeadsModel>() {
            @Override
            public void onResponse(Call<Model.GetLeadsModel> call, Response<Model.GetLeadsModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.GetLeadsModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipeRefresh.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);

                                    if (keyModel.getData().getLeads().size() >0){
                                        LeadsRecycler(keyModel.getData().getLeads());
                                    }



                                }

                            } else {
                                Toast.makeText(ActivityLeads.this, "Error", Toast.LENGTH_SHORT).show();
                                mBinding.progress.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipeRefresh.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            mBinding.progress.setVisibility(View.GONE);
                            Toast.makeText(ActivityLeads.this, "key model null", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.GetLeadsModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipeRefresh.setRefreshing(false);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityLeads.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }



}