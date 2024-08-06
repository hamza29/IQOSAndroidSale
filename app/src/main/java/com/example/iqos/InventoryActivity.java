package com.example.iqos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.SalesModule.ActivitySales;
import com.example.iqos.databinding.ActivityInventoryBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryActivity extends AppCompatActivity {


    ActivityInventoryBinding mBinding;
    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityInventoryBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBinding.tvConsumables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBinding.tvConsumables.setBackground(getDrawable(R.drawable.rounded_top_blue));
                mBinding.tvDevices.setBackground(getDrawable(R.drawable.rounded_top_grey));
                mBinding.rvConsumables.setVisibility(View.VISIBLE);
                mBinding.rvDevices.setVisibility(View.GONE);

                getConsumables(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));

            }
        });
        mBinding.tvDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.tvDevices.setBackground(getDrawable(R.drawable.rounded_top_blue));
                mBinding.tvConsumables.setBackground(getDrawable(R.drawable.rounded_top_grey));
                mBinding.rvConsumables.setVisibility(View.GONE);
                mBinding.rvDevices.setVisibility(View.VISIBLE);
                getInventories(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));


            }
        });

        getInventories(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));


    }

    private void consumableItem(List<ActivitySales.Consumable> consumables) {


        ConsumablesAdapter appointmentAdapter;
        mBinding.rvConsumables.setLayoutManager(new LinearLayoutManager(InventoryActivity.this, LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new ConsumablesAdapter(InventoryActivity.this, consumables);
        mBinding.rvConsumables.setAdapter(appointmentAdapter);
    }


    private void devicesItem(List<ActivitySales.Inventory> devices) {


        DevicesAdapter appointmentAdapter;
        mBinding.rvDevices.setLayoutManager(new LinearLayoutManager(InventoryActivity.this, LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new DevicesAdapter(InventoryActivity.this, devices);
        mBinding.rvDevices.setAdapter(appointmentAdapter);
    }

    public void getInventories(String token) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(InventoryActivity.this).create(ApiService.class);
        Call<ActivitySales.GetInventoriesModel> call = apiService.getInventories("application/json", token);
        call.enqueue(new Callback<ActivitySales.GetInventoriesModel>() {
            @Override
            public void onResponse(Call<ActivitySales.GetInventoriesModel> call, Response<ActivitySales.GetInventoriesModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final ActivitySales.GetInventoriesModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                devicesItem(keyModel.getData().getInventories());

                            } else {
                                Toast.makeText(InventoryActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(InventoryActivity.this, "key model null", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ActivitySales.GetInventoriesModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(InventoryActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public void getConsumables(String token) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(InventoryActivity.this).create(ApiService.class);
        Call<ActivitySales.ConsumeableModel> call = apiService.getConsumeables(token, "");
        call.enqueue(new Callback<ActivitySales.ConsumeableModel>() {
            @Override
            public void onResponse(Call<ActivitySales.ConsumeableModel> call, Response<ActivitySales.ConsumeableModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final ActivitySales.ConsumeableModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {

                                consumableItem(keyModel.getData().getConsumable());


                            } else {
                                Toast.makeText(InventoryActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(InventoryActivity.this, "key model null", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ActivitySales.ConsumeableModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(InventoryActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }


}