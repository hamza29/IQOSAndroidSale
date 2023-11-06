package com.example.iqos.SalesModule;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iqos.Constants;
 import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.databinding.ActivityLeadsBinding;
import com.example.iqos.databinding.ActivityPerformanceBinding;
import com.example.iqos.databinding.ActivitySalesBinding;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerformanceActivity extends AppCompatActivity {


    ActivityPerformanceBinding mBinding;

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPerformanceBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
         mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        getPerformance(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
    }
    public void getPerformance(String token ) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(PerformanceActivity.this).create(ApiService.class);
        Call<GetPerformanceModel> call = apiService.getPerformance("application/json",token );
        call.enqueue(new Callback<GetPerformanceModel>() {
            @Override
            public void onResponse(Call<GetPerformanceModel> call, Response<GetPerformanceModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final GetPerformanceModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {


                                if(keyModel.getData().getAssigned_leads()!=null)
                                {
                                    mBinding.tvAssignedLeads.setText(""+keyModel.getData().getAssigned_leads());
                                }                 else{
                                    mBinding.tvAssignedLeads.setText("N/A" );

                                }


                                if(keyModel.getData().getMeeting_success_rate()!=null)
                                {
                                    mBinding.tvMeetingSuccessRate.setText(""+keyModel.getData().getMeeting_success_rate());
                                }                 else{
                                    mBinding.tvMeetingSuccessRate.setText("N/A" );

                                }


                                if(keyModel.getData().getCompleted_leads()!=null)
                                {
                                    mBinding.tvCompletedLeads.setText(""+keyModel.getData().getCompleted_leads());
                                }                 else{
                                    mBinding.tvCompletedLeads.setText("N/A" );

                                }

                                if(keyModel.getData().getAverage_meeting_time()!=null)
                                {
                                    mBinding.tvDuration.setText(""+keyModel.getData().getAverage_meeting_time());
                                }                 else{
                                    mBinding.tvDuration.setText("N/A" );

                                }



                                if(keyModel.getData().getBooked_appointments()!=null)
                                {
                                    mBinding.tvBookedLeads.setText(""+keyModel.getData().getBooked_appointments());
                                }                 else{
                                    mBinding.tvBookedLeads.setText("N/A" );

                                }

                                if(keyModel.getData().getDevices_sold()!=null)
                                {
                                    mBinding.tvDeviceSold.setText(""+keyModel.getData().getDevices_sold());
                                }                 else{
                                    mBinding.tvDeviceSold.setText("N/A" );

                                }
                                if(keyModel.getData().getConusmables_sold()!=null)
                                {
                                    mBinding.tvConsumablesSold.setText(""+keyModel.getData().getConusmables_sold());
                                }                 else{
                                    mBinding.tvConsumablesSold.setText("N/A" );

                                }





                            } else {
                                Toast.makeText(PerformanceActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(PerformanceActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<GetPerformanceModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(PerformanceActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public class Data {

        @SerializedName("assigned_leads")
        @Expose
         public String assigned_leads;
        @SerializedName("average_meeting_time")
        @Expose
         public String average_meeting_time;
        @SerializedName("meeting_success_rate")
        @Expose
         public String meeting_success_rate;
        @SerializedName("conusmables_sold")
        @Expose
         public String conusmables_sold;
        @SerializedName("devices_sold")
        @Expose
         public String devices_sold;
        @SerializedName("booked_appointments")
        @Expose
         public String booked_appointments;
        @SerializedName("completed_leads")
        @Expose
         public String completed_leads;

        public String getAssigned_leads() {
            return assigned_leads;
        }

        public void setAssigned_leads(String assigned_leads) {
            this.assigned_leads = assigned_leads;
        }

        public String getAverage_meeting_time() {
            return average_meeting_time;
        }

        public void setAverage_meeting_time(String average_meeting_time) {
            this.average_meeting_time = average_meeting_time;
        }

        public String getMeeting_success_rate() {
            return meeting_success_rate;
        }

        public void setMeeting_success_rate(String meeting_success_rate) {
            this.meeting_success_rate = meeting_success_rate;
        }

        public String getConusmables_sold() {
            return conusmables_sold;
        }

        public void setConusmables_sold(String conusmables_sold) {
            this.conusmables_sold = conusmables_sold;
        }

        public String getDevices_sold() {
            return devices_sold;
        }

        public void setDevices_sold(String devices_sold) {
            this.devices_sold = devices_sold;
        }

        public String getBooked_appointments() {
            return booked_appointments;
        }

        public void setBooked_appointments(String booked_appointments) {
            this.booked_appointments = booked_appointments;
        }

        public String getCompleted_leads() {
            return completed_leads;
        }

        public void setCompleted_leads(String completed_leads) {
            this.completed_leads = completed_leads;
        }
    }
    public class GetPerformanceModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private  Data data;

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

        public  Data getData() {
            return data;
        }

        public void setData( Data data) {
            this.data = data;
        }

    }

}