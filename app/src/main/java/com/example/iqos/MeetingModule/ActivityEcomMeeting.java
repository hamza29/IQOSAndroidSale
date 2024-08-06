package com.example.iqos.MeetingModule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iqos.Constants;
import com.example.iqos.GPSTracker;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityEcomMeetingBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityEcomMeeting extends AppCompatActivity {

    ActivityEcomMeetingBinding mBinding;
    String tvCoachTime = "";
    String tvConsumerTime = "";
    String tvSpaceTime = "";
    String tvToolsTime = "";
    SharedPreferences mSharedPreferences;
    String appointment_id = "";
    String starting_latitude;
    String starting_date;
    String ending_latitude;
    String ending_date;
    String cbCustomer;
    String cbDeviceLinking;
    String cbEccomerce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityEcomMeetingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        Intent intent = getIntent();
        appointment_id = intent.getStringExtra("appointment_id");
        String name = getIntent().getStringExtra("name");
        mBinding.tvLeadName.setText("Lead Name: " + name);


        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            starting_latitude = gpsTracker.getLatitude() + "," + gpsTracker.getLongitude();

        }
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = formatter.format(todayDate);
        starting_date = todayString;


        updateStartEcommMeeting(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""),
                appointment_id,
                starting_date,
                starting_latitude, "", "", "", "", ""
        );


        mBinding.tvDone.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {

                                                   GPSTracker gpsTracker = new GPSTracker(ActivityEcomMeeting.this);
                                                   if (gpsTracker.getIsGPSTrackingEnabled()) {
                                                       ending_latitude = gpsTracker.getLatitude() + "," + gpsTracker.getLongitude();

                                                   }
                                                   Date todayDate = Calendar.getInstance().getTime();
                                                   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                   String todayString = formatter.format(todayDate);
                                                   ending_date = todayString;
                                                   if (mBinding.cbCustomer.isChecked()) {
                                                       cbCustomer = "YES";
                                                   } else {
                                                       cbCustomer = "NO";

                                                   }
                                                   if (mBinding.cbDeviceLinking.isChecked()) {
                                                       cbDeviceLinking = "YES";
                                                   } else {
                                                       cbDeviceLinking = "NO";

                                                   }
                                                   if (mBinding.cbEcoomerRegisteration.isChecked()) {
                                                       cbEccomerce = "YES";
                                                   } else {
                                                       cbEccomerce = "NO";

                                                   }

                                                   updateEcommMeeting(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""),
                                                           appointment_id,
                                                           starting_date,
                                                           starting_latitude, cbCustomer, cbDeviceLinking, cbEccomerce, ending_date, ending_latitude
                                                   );

                                               }
                                           }
        );

    }

    public void updateEcommMeeting(String token,
                                   String id,
                                   String start_meeting,
                                   String start_meeting_lat_lng,
                                   String customer_registration,
                                   String device_linking,
                                   String ecommerce_registration,
                                   String end_meeting,
                                   String end_meeting_lat_lng) {
        ApiService apiService = ApiClient.getClient(ActivityEcomMeeting.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        if (start_meeting != null) {
            builder.addFormDataPart("start_meeting", start_meeting);
        }
        if (start_meeting_lat_lng != null) {
            builder.addFormDataPart("start_meeting_lat_lng", start_meeting_lat_lng);
        }
        if (customer_registration != null) {
            builder.addFormDataPart("customer_registration", customer_registration);
        }
        if (device_linking != null) {
            builder.addFormDataPart("device_linking", device_linking);
        }
        if (ecommerce_registration != null) {
            builder.addFormDataPart("ecommerce_registration", ecommerce_registration);
        }
        if (end_meeting != null) {
            builder.addFormDataPart("end_meeting", end_meeting);
        }
        if (end_meeting_lat_lng != null) {
            builder.addFormDataPart("end_meeting_lat_lng", end_meeting_lat_lng);
        }

        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateEcomMeeting(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                            if (listofhome.getStatus().equals("1")) {


                                Toast.makeText(ActivityEcomMeeting.this, "Meeting End", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(ActivityEcomMeeting.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityEcomMeeting.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }


    public void updateStartEcommMeeting(String token,
                                        String id,
                                        String start_meeting,
                                        String start_meeting_lat_lng,
                                        String customer_registration,
                                        String device_linking,
                                        String ecommerce_registration,
                                        String end_meeting,
                                        String end_meeting_lat_lng) {
        ApiService apiService = ApiClient.getClient(ActivityEcomMeeting.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        if (start_meeting != null) {
            builder.addFormDataPart("start_meeting", tvCoachTime);
        }
        if (start_meeting_lat_lng != null) {
            builder.addFormDataPart("start_meeting_lat_lng", tvConsumerTime);
        }
        if (customer_registration != null) {
            builder.addFormDataPart("customer_registration", tvToolsTime);
        }
        if (device_linking != null) {
            builder.addFormDataPart("device_linking", tvSpaceTime);
        }
        if (ecommerce_registration != null) {
            builder.addFormDataPart("ecommerce_registration", tvSpaceTime);
        }
        if (end_meeting != null) {
            builder.addFormDataPart("end_meeting", tvSpaceTime);
        }
        if (end_meeting_lat_lng != null) {
            builder.addFormDataPart("end_meeting_lat_lng", tvSpaceTime);
        }

        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateEcomMeeting(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                            if (listofhome.getStatus().equals("1")) {


                                Toast.makeText(ActivityEcomMeeting.this, "Meeting End", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ActivityEcomMeeting.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityEcomMeeting.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }


}