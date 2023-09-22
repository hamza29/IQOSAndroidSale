package com.example.iqos.MeetingModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.iqos.AppointmentsModule.ActivityBookAppointment;
import com.example.iqos.AppointmentsModule.AppointmentAdapter;
import com.example.iqos.Constants;
 import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityLeadsDetailBinding;
import com.example.iqos.databinding.ActivityPreMeetingCheckListBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPreMeetingCheckList extends AppCompatActivity {

    ActivityPreMeetingCheckListBinding mBinding;
String tvCoachTime="";
String tvConsumerTime="";
String tvSpaceTime="";
String tvToolsTime="";
    SharedPreferences mSharedPreferences;
String appointment_id ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPreMeetingCheckListBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
       Intent intent = getIntent();
        appointment_id = intent.getStringExtra("appointment_id");
        mBinding.llCoach.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(mBinding.tvCoachText.getVisibility() == View.VISIBLE){
          mBinding.tvCoachText.setVisibility(View.GONE);
        }else{
            mBinding.tvCoachText.setVisibility(View.VISIBLE);

        }
    }
});
mBinding.llTasks.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(mBinding.tvConsumerText.getVisibility() == View.VISIBLE){
          mBinding.tvConsumerText.setVisibility(View.GONE);
        }else{
            mBinding.tvConsumerText.setVisibility(View.VISIBLE);

        }
    }
});
mBinding.llTools.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(mBinding.tvtoolsText.getVisibility() == View.VISIBLE){
          mBinding.tvtoolsText.setVisibility(View.GONE);
        }else{
            mBinding.tvtoolsText.setVisibility(View.VISIBLE);

        }
    }
});
mBinding.llSpace.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(mBinding.tvSpaceText.getVisibility() == View.VISIBLE){
          mBinding.tvSpaceText.setVisibility(View.GONE);
        }else{
            mBinding.tvSpaceText.setVisibility(View.VISIBLE);

        }
    }
});
mBinding.cbCoach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String todayString = formatter.format(todayDate);
        if(mBinding.cbCoach.isChecked()){
            tvCoachTime =todayString;
        }else{
            tvCoachTime= "";
        }
    }
});mBinding.cbTools.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String todayString = formatter.format(todayDate);
        if(mBinding.cbTools.isChecked()){
            tvToolsTime =todayString;
        }else{
            tvToolsTime= "";
        }
    }
});mBinding.cbConsumer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String todayString = formatter.format(todayDate);
        if(mBinding.cbConsumer.isChecked()){
            tvConsumerTime =todayString;
        }else{
            tvConsumerTime= "";
        }
    }
});

mBinding.cbSpace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String todayString = formatter.format(todayDate);
        if(mBinding.cbSpace.isChecked()){
            tvSpaceTime =todayString;
        }else{
            tvSpaceTime= "";
        }
    }
});
mBinding.tvDone.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           if(!tvConsumerTime.isEmpty() &&!tvToolsTime.isEmpty() &&!tvSpaceTime.isEmpty() &&!tvCoachTime.isEmpty() ) {
                                               updatePreMeetingChecklist(mSharedPreferences.getString(Constants.BAREAR_TOKEN, "")
                                                       , appointment_id, tvCoachTime,
                                                       tvConsumerTime,
                                                       tvSpaceTime,
                                                       tvToolsTime);
                                           }else{

                                               Toast.makeText(ActivityPreMeetingCheckList.this, "Complete checklist to continue!", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   }
);

    }

    public void updatePreMeetingChecklist(String token,String id, String tvCoachTime ,
    String tvConsumerTime,
    String tvSpaceTime ,
    String tvToolsTime) {
         ApiService apiService = ApiClient.getClient(ActivityPreMeetingCheckList.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        if(tvCoachTime !=null) {
            builder.addFormDataPart("pre_meeting_checklist_item1", tvCoachTime);
        }
        if(tvConsumerTime !=null) {
            builder.addFormDataPart("pre_meeting_checklist_item2", tvConsumerTime);
        }
        if(tvToolsTime !=null) {
            builder.addFormDataPart("pre_meeting_checklist_item3", tvToolsTime);
        }
        if(tvSpaceTime !=null) {
            builder.addFormDataPart("pre_meeting_checklist_item4", tvSpaceTime);
        }

        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updatePreMeetingChecklist(token, requestBody);

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
                                 
 
                                Toast.makeText(ActivityPreMeetingCheckList.this, "Pre meeting checklist done" , Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);
finish();

                            } else {
                                Toast.makeText(ActivityPreMeetingCheckList.this, "Error", Toast.LENGTH_SHORT).show();
 
                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                             Toast.makeText(ActivityPreMeetingCheckList.this, "key model null", Toast.LENGTH_SHORT).show();
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