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

import com.example.iqos.Constants;
import com.example.iqos.GPSTracker;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.SalesModule.ActivityNoSales;
import com.example.iqos.SalesModule.ActivitySales;
import com.example.iqos.databinding.ActivityAppointmentMeetingCheckListBinding;
import com.example.iqos.databinding.ActivityVerificationBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityVerification extends AppCompatActivity {

    ActivityVerificationBinding mBinding;
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
    String  name;
    public  static Activity verificationAct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityVerificationBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        verificationAct= this;

        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);

         appointment_id = getIntent().getStringExtra("appointment_id");
          name = getIntent().getStringExtra("name");
      mBinding.tvName.setText("1. Name Verified ("+ name+")");

         mBinding.tvDone.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
String cbName="";
String cbHav="";
String cbSmoker="";
             if(mBinding.cbName.isChecked()){
                 cbName ="1";
             }else{
                 cbName ="0";

             }
    if(mBinding.cbHav.isChecked()){
        cbHav ="1";
             }else{
        cbHav ="0";

             }
    if(mBinding.cbSmokerStatus.isChecked()){
                 cbSmoker ="1";
             }else{
        cbSmoker ="0";

             }
        if(cbName.equalsIgnoreCase("1") && cbHav.equalsIgnoreCase("1") && cbSmoker.equalsIgnoreCase("1")) {
            updateLead(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""),
                    appointment_id, cbName, cbHav, cbSmoker);

        }else{
            Toast.makeText(ActivityVerification.this, "Please verify all to proceed forward!", Toast.LENGTH_SHORT).show();
        }

                     }
         });





    }


    public void updateLead(String token,String id, String  is_name_verified, String  is_hav_verified, String  is_smoke_status_verified) {
        ApiService apiService = ApiClient.getClient(ActivityVerification.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        if(is_name_verified !=null) {
            builder.addFormDataPart("is_name_verified", is_name_verified);
        }
        if(is_hav_verified !=null) {
            builder.addFormDataPart("is_hav_verified", is_hav_verified);
        }
        if(is_smoke_status_verified !=null) {
            builder.addFormDataPart("is_smoke_status_verified", is_smoke_status_verified);
        }

        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateLead(token, requestBody);

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


                                Toast.makeText(ActivityVerification.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ActivityVerification.this, ActivityAppointmentMeetingCheckList.class);
                                intent.putExtra("appointment_id",""+ appointment_id);
                                intent.putExtra("name",""+ name);
                                  startActivity(intent);
//                                finish();
                            } else {
                                Toast.makeText(ActivityVerification.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityVerification.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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