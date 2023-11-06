package com.example.iqos.SalesModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.iqos.Constants;
import com.example.iqos.GPSTracker;
import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.LeadsModule.LeadsAdapter;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityNoSalesBinding;
import com.example.iqos.databinding.ActivityNoSalesBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityNoSales extends AppCompatActivity {

    ActivityNoSalesBinding mBinding;
    SharedPreferences mSharedPreferences;

    String a1,a2,a3,a4,meeting_outcome;
    String      ending_latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityNoSalesBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        String appointment_id = getIntent().getStringExtra("appointment_id");
        a1 =getIntent().getStringExtra("a1");
        a2 =getIntent().getStringExtra("a2");
        a3 =getIntent().getStringExtra("a3");
        a4 =getIntent().getStringExtra("a4");
        meeting_outcome =getIntent().getStringExtra("meeting_outcome");

        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
mBinding.tvComplete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        GPSTracker gpsTracker = new GPSTracker(ActivityNoSales.this);
        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            ending_latitude = gpsTracker.getLatitude()+ "," + gpsTracker.getLongitude();
        }



        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = formatter.format(todayDate);
        String    ending_date =todayString;

        updateMeetingChecklist(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),
                appointment_id,
                a1,a2,a3,a4, ending_date,ending_latitude,meeting_outcome);




    }
});
    }
    public void updateMeetingChecklist(String token,String id ,
                                       String answer1 ,
                                       String answer2 ,
                                       String answer3 ,
                                       String answer4 ,
                                       String end_meeting,
                                       String end_meeting_lat_lng,
                                       String meeting_outcome) {
        ApiService apiService = ApiClient.getClient(ActivityNoSales.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);

        if(answer1 !=null) {
            builder.addFormDataPart("answer1", answer1);
        }
        if(answer2 !=null) {
            builder.addFormDataPart("answer2", answer2);
        }
        if(answer3 !=null) {
            builder.addFormDataPart("answer3", answer3);
        }

        if(answer4 !=null) {
            builder.addFormDataPart("answer4", answer4);
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






                                update_ic(
                                        mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),
                                        id,
                                        mBinding.tvQuestion1.getText().toString(),
                                        mBinding.tvAnswer1.getText().toString(),
                                        mBinding.tvQuestion2.getText().toString(),
                                        mBinding.tvAnswer2.getText().toString(),
                                        mBinding.tvQuestion3.getText().toString(),
                                        mBinding.tvAnswer3.getText().toString(),
                                        mBinding.tvQuestion4.getText().toString(),
                                        mBinding.tvAnswer4.getText().toString()
                                );
                                Toast.makeText(ActivityNoSales.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ActivityNoSales.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityNoSales.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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

    public void update_ic(String token,String id, String ic_question1,
                           String ic_answer1, String ic_question2,
                           String ic_answer2, String ic_question3,
                           String ic_answer3 , String ic_question4, String ic_answer4    ) {
        ApiService apiService = ApiClient.getClient(ActivityNoSales.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
         if(ic_question1 !=null) {
            builder.addFormDataPart("ic_question1", ic_question1);
        }
        if(ic_answer1 !=null) {
            builder.addFormDataPart("ic_answer1", ic_answer1);
        }
        if(ic_question2 !=null) {
            builder.addFormDataPart("ic_question2", ic_question2);
        }
        if(ic_answer2 !=null) {
            builder.addFormDataPart("ic_answer2", ic_answer2);
        }
        if(ic_question3 !=null) {
            builder.addFormDataPart("ic_question3", ic_question3);
        }
        if(ic_answer3 !=null) {
            builder.addFormDataPart("ic_answer3", ic_answer3);
        }

        if(ic_question4 !=null) {
            builder.addFormDataPart("ic_question4", ic_question4);
        } if(ic_answer4 !=null) {
            builder.addFormDataPart("ic_answer4", ic_answer4);
        }

        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.ic_update(token, requestBody);

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
                                Toast.makeText(ActivityNoSales.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);
                                finish();

                            } else {
                                Toast.makeText(ActivityNoSales.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityNoSales.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(ActivityNoSales.this, "Failure", Toast.LENGTH_SHORT).show();

                    }

                });


            }
        });
    }
}