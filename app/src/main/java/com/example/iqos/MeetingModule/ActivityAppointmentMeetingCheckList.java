package com.example.iqos.MeetingModule;

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
import com.example.iqos.GPSTracker;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.SalesModule.ActivityNoSales;
import com.example.iqos.SalesModule.ActivitySales;
import com.example.iqos.databinding.ActivityAppointmentMeetingCheckListBinding;
import com.example.iqos.databinding.ActivityAppointmentMeetingCheckListBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAppointmentMeetingCheckList extends AppCompatActivity {

    ActivityAppointmentMeetingCheckListBinding mBinding;
    String starting_latitude;
    String starting_date;
    String ending_latitude;
    String ending_date;
    String meeting_outcome;
    SharedPreferences mSharedPreferences;
    String appointment_id;
    String q4Answer  ="";
    String q5Answer  ="";

    String q3Answer  ="";
    String q2Answer  ="";
    String q1Answer  ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAppointmentMeetingCheckListBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            starting_latitude = gpsTracker.getLatitude()+ "," + gpsTracker.getLongitude();

        }
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String todayString = formatter.format(todayDate);
        starting_date =todayString;
         appointment_id = getIntent().getStringExtra("appointment_id");
         mBinding.tvSales.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 meeting_outcome ="sale";
                 Intent intent = new Intent(ActivityAppointmentMeetingCheckList.this, ActivityPackages.class);
              intent.putExtra("appointment_id",""+appointment_id);
                 startActivity(intent);
             }
         });

         mBinding.tvNoSales.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 meeting_outcome ="no sale";
                 Intent intent = new Intent(ActivityAppointmentMeetingCheckList.this, ActivityNoSales.class);
                 intent.putExtra("appointment_id",""+appointment_id);
                 startActivity(intent);
             }
         });


         mBinding.tvComplete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 GPSTracker gpsTracker = new GPSTracker(ActivityAppointmentMeetingCheckList.this);
                 if (gpsTracker.getIsGPSTrackingEnabled())
                 {
                     ending_latitude = gpsTracker.getLatitude()+ "," + gpsTracker.getLongitude();
                 }


                 if(mBinding.rbQ1a1.isChecked()){
                     q1Answer =mBinding.rbQ1a1.getText().toString();
                 }else  if(mBinding.rbQ1a2.isChecked()){
                     q1Answer =mBinding.rbQ1a2.getText().toString();
                 }else  if(mBinding.rbQ1a3.isChecked()){
                     q1Answer =mBinding.rbQ1a3.getText().toString();
                 }else  if(mBinding.rbQ1a4.isChecked()){
                     q1Answer =mBinding.rbQ1a4.getText().toString();
                 }

                 if(mBinding.rbQ2a1.isChecked()){
                     q2Answer =mBinding.rbQ2a1Et.getText().toString();
                 }else  if(mBinding.rbQ2a2.isChecked()){
                     q2Answer =mBinding.rbQ2a2Et.getText().toString();
                 }



                q3Answer =mBinding.etQ3a.getText().toString();




                 q4Answer= mBinding.etbrand.getText().toString();


                 Date todayDate = Calendar.getInstance().getTime();
                 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                 String todayString = formatter.format(todayDate);
                 ending_date =todayString;

                 updateMeetingChecklist(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),
                         appointment_id,
                         starting_date,
                         starting_latitude,
                         mBinding. tvQuestion1.getText().toString(),q1Answer,
                         mBinding. tvQuestion2.getText().toString(),q2Answer,
                         mBinding. tvQuestion3.getText().toString(),q3Answer,
                         mBinding. tvQuestion4.getText().toString(),q4Answer,
                         "","","","","",ending_date,ending_latitude,meeting_outcome);

             }
         });





    }


    public void updateMeetingChecklist(String token,String id, String start_meeting ,
                                          String start_meeting_lat_lng,
                                          String question1 ,
                                          String answer1 ,
                                          String question2 ,
                                          String answer2 ,
                                          String question3 ,
                                          String answer3 ,
                                          String question4 ,
                                          String answer4 ,

                                          String video1 ,
                                          String video2,
                                          String video3,
                                          String i1,
                                          String i2,
                                          String end_meeting,
                                          String end_meeting_lat_lng,
                                          String meeting_outcome) {
        ApiService apiService = ApiClient.getClient(ActivityAppointmentMeetingCheckList.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        if(start_meeting !=null) {
            builder.addFormDataPart("start_meeting", start_meeting);
        }
        if(start_meeting_lat_lng !=null) {
            builder.addFormDataPart("start_meeting_lat_lng", start_meeting_lat_lng);
        }
        if(question1 !=null) {
            builder.addFormDataPart("question1", question1);
        }
        if(answer1 !=null) {
            builder.addFormDataPart("answer1", answer1);
        }
         if(question2 !=null) {
                    builder.addFormDataPart("question2", question2);
                }
         if(answer2 !=null) {
                    builder.addFormDataPart("answer2", answer2);
                }

         if(question3 !=null) {
                    builder.addFormDataPart("question3", question3);
                }
         if(answer3 !=null) {
                    builder.addFormDataPart("answer3", answer3);
                }

         if(question4 !=null) {
                    builder.addFormDataPart("question4", question4);
                }
         if(answer4 !=null) {
                    builder.addFormDataPart("answer4", answer4);
                }


         if(video1 !=null) {
                    builder.addFormDataPart("video1", video1);
                }

         if(video2 !=null) {
                    builder.addFormDataPart("video2", video2);
                }
         if(video3 !=null) {
                    builder.addFormDataPart("video3", video3);
                }
         if(i1 !=null) {
                    builder.addFormDataPart("i1", i1);
                }
         if(i2 !=null) {
                    builder.addFormDataPart("i2", i2);
                }
         if(end_meeting !=null) {
                    builder.addFormDataPart("end_meeting", end_meeting);
                }
         if(end_meeting_lat_lng !=null) {
                    builder.addFormDataPart("end_meeting_lat_lng", end_meeting_lat_lng);
                }
         if(meeting_outcome !=null) {
                    builder.addFormDataPart("meeting_outcome", meeting_outcome);
                }

        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateMeetingChecklist(token, requestBody);

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


                                Toast.makeText(ActivityAppointmentMeetingCheckList.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();

                                finish();
                            } else {
                                Toast.makeText(ActivityAppointmentMeetingCheckList.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityAppointmentMeetingCheckList.this, "key model null", Toast.LENGTH_SHORT).show();
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