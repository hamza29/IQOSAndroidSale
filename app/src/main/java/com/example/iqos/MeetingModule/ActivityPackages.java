package com.example.iqos.MeetingModule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.iqos.Constants;
import com.example.iqos.DevicesAdapter;
import com.example.iqos.GPSTracker;
 import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.SalesModule.ActivityNoSales;
import com.example.iqos.SalesModule.ActivitySales;
import com.example.iqos.databinding.ActivityAppointmentMeetingCheckListBinding;
import com.example.iqos.databinding.ActivityPackagesBinding;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPackages extends AppCompatActivity {

    ActivityPackagesBinding mBinding;

    SharedPreferences mSharedPreferences;
    String appointment_id,a1,a2,a3,a4,meeting_outcome, type="lead";

    public static Activity  packagesActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPackagesBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        packagesActivity = this;
        Intent intent = getIntent();
        appointment_id =intent.getStringExtra("appointment_id");
        a1 =intent.getStringExtra("a1");
        a2 =intent.getStringExtra("a2");
        a3 =intent.getStringExtra("a3");
        a4 =intent.getStringExtra("a4");
        type =intent.getStringExtra("type");
        meeting_outcome =intent.getStringExtra("meeting_outcome");

        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        getPackages(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));



    }



    private void devicesItem(List<Package> devices) {



        PackageAdapter appointmentAdapter;
        mBinding.rvPackages.setLayoutManager(new LinearLayoutManager(ActivityPackages.this, LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new PackageAdapter(ActivityPackages.this, devices,appointment_id,a1 ,
        a2,
        a3,
        a4,
        meeting_outcome,type);
        mBinding.rvPackages.setAdapter(appointmentAdapter);
    }
    public void getPackages(String token ) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityPackages.this).create(ApiService.class);
        Call<PackagesModel> call = apiService.getpackages("application/json",token );
        call.enqueue(new Callback<PackagesModel>() {
            @Override
            public void onResponse(Call<PackagesModel> call, Response<PackagesModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final PackagesModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                devicesItem(keyModel.getData().getPackages());

                            } else {
                                Toast.makeText(ActivityPackages.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityPackages.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<PackagesModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(ActivityPackages.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }



    public class Data {

        @SerializedName("packages")
        @Expose
        private List<Package> packages;

        public List<Package> getPackages() {
            return packages;
        }

        public void setPackages(List<Package> packages) {
            this.packages = packages;
        }

    }
    public class Package {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
    public class PackagesModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private Data data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }

}