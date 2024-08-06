package com.example.iqos.MeetingModule;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iqos.Constants;
import com.example.iqos.GPSTracker;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.SalesModule.ActivityNoSales;
import com.example.iqos.SalesModule.ActivitySales;
import com.example.iqos.WebViewActivity;
import com.example.iqos.databinding.ActivityAppointmentMeetingCheckListBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAppointmentMeetingCheckList extends AppCompatActivity {

    public static Activity meetingcehck;
    ActivityAppointmentMeetingCheckListBinding mBinding;
    String starting_latitude;
    String starting_date;
    String ending_latitude;
    String ending_date;
    String meeting_outcome;
    SharedPreferences mSharedPreferences;
    String appointment_id;
    String q4Answer = "";
    String q3Answer = "";
    String q2Answer = "";
    String q1Answer = "", isSale;
    String package_id, package_name;
    String a1, a2, a3, a4, type, name, multisale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAppointmentMeetingCheckListBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        meetingcehck = this;
        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            starting_latitude = gpsTracker.getLatitude() + "," + gpsTracker.getLongitude();

        }
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = formatter.format(todayDate);
        starting_date = todayString;
        appointment_id = getIntent().getStringExtra("appointment_id");
        isSale = getIntent().getStringExtra("isSale");
        String name = getIntent().getStringExtra("name");

        package_id = getIntent().getStringExtra("package_id");
        package_name = getIntent().getStringExtra("package_name");
        a1 = getIntent().getStringExtra("a1");
        a2 = getIntent().getStringExtra("a2");
        a3 = getIntent().getStringExtra("a3");
        a4 = getIntent().getStringExtra("a4");
        type = getIntent().getStringExtra("type");
        multisale = getIntent().getStringExtra("multisale");
        meeting_outcome = getIntent().getStringExtra("meeting_outcome");
        mBinding.tvLeadName.setText("Lead Name: " + name);
        updateMeetingChecklist(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""),
                appointment_id,
                starting_date,
                starting_latitude,
                mBinding.tvQuestion1.getText().toString(), "",
                mBinding.tvQuestion2.getText().toString(), "",
                mBinding.tvQuestion3.getText().toString(), "",
                mBinding.tvQuestion4.getText().toString(), "",
                "", "", "", "", "", "", "", "");


        mBinding.tvSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meeting_outcome = "sale";

                if (mBinding.rbQ1a1.isChecked()) {
                    q1Answer = mBinding.rbQ1a1.getText().toString();
                } else if (mBinding.rbQ1a2.isChecked()) {
                    q1Answer = mBinding.rbQ1a2.getText().toString();
                } else if (mBinding.rbQ1a3.isChecked()) {
                    q1Answer = mBinding.rbQ1a3.getText().toString();
                } else if (mBinding.rbQ1a4.isChecked()) {
                    q1Answer = mBinding.rbQ1a4.getText().toString();
                }

                if (mBinding.rbQ2a1.isChecked()) {
                    q2Answer = mBinding.rbQ2a1Et.getText().toString();
                } else if (mBinding.rbQ2a2.isChecked()) {
                    q2Answer = mBinding.rbQ2a2Et.getText().toString();
                }
                q3Answer = mBinding.etQ3a.getText().toString();
                q4Answer = mBinding.etbrand.getText().toString();


                Intent intent = new Intent(ActivityAppointmentMeetingCheckList.this, ActivityPackages.class);

                if (Objects.equals(isSale, "sale")) {
                    intent = new Intent(ActivityAppointmentMeetingCheckList.this, ActivitySales.class);

                    intent.putExtra("id", package_id);
                    intent.putExtra("name", package_name);
                    intent.putExtra("type", "sales");
                    intent.putExtra("multisale", "" + multisale);

                }
                intent.putExtra("app_id", "" + appointment_id);

                intent.putExtra("appointment_id", "" + appointment_id);
                intent.putExtra("a1", "" + q1Answer);
                intent.putExtra("a2", "" + q2Answer);
                intent.putExtra("a3", "" + q3Answer);
                intent.putExtra("a4", "" + q4Answer);
                intent.putExtra("meeting_outcome", "" + meeting_outcome);
                startActivity(intent);
//                 finish();
            }
        });

        mBinding.tvNoSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meeting_outcome = "no sale";

                if (mBinding.rbQ1a1.isChecked()) {
                    q1Answer = mBinding.rbQ1a1.getText().toString();
                } else if (mBinding.rbQ1a2.isChecked()) {
                    q1Answer = mBinding.rbQ1a2.getText().toString();
                } else if (mBinding.rbQ1a3.isChecked()) {
                    q1Answer = mBinding.rbQ1a3.getText().toString();
                } else if (mBinding.rbQ1a4.isChecked()) {
                    q1Answer = mBinding.rbQ1a4.getText().toString();
                }

                if (mBinding.rbQ2a1.isChecked()) {
                    q2Answer = mBinding.rbQ2a1Et.getText().toString();
                } else if (mBinding.rbQ2a2.isChecked()) {
                    q2Answer = mBinding.rbQ2a2Et.getText().toString();
                }
                q3Answer = mBinding.etQ3a.getText().toString();
                q4Answer = mBinding.etbrand.getText().toString();


                Intent intent = new Intent(ActivityAppointmentMeetingCheckList.this, ActivityNoSales.class);
                intent.putExtra("appointment_id", "" + appointment_id);
                intent.putExtra("a1", "" + q1Answer);
                intent.putExtra("a2", "" + q2Answer);
                intent.putExtra("a3", "" + q3Answer);
                intent.putExtra("a4", "" + q4Answer);
                intent.putExtra("meeting_outcome", "" + meeting_outcome);
                startActivity(intent);
                finish();
            }
        });
        //
        mBinding.v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAppointmentMeetingCheckList.this, WebViewActivity.class);
                intent.putExtra("video_link", "https://iqoch.com/videos/B265970_IQOS_ILUMA_Premium_HD1080_.mp4");
                startActivity(intent);
            }
        });
        mBinding.v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityAppointmentMeetingCheckList.this, WebViewActivity.class);
                intent.putExtra("video_link", "https://iqoch.com/videos/B265970_ILUMA_Premium_Super_Restricted_How_to_Charging_video_744_A_en_GB_83_HD1080_25p_AE001.mp4");
                startActivity(intent);
            }
        });
        mBinding.tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GPSTracker gpsTracker = new GPSTracker(ActivityAppointmentMeetingCheckList.this);
                if (gpsTracker.getIsGPSTrackingEnabled()) {
                    ending_latitude = gpsTracker.getLatitude() + "," + gpsTracker.getLongitude();
                }


                if (mBinding.rbQ1a1.isChecked()) {
                    q1Answer = mBinding.rbQ1a1.getText().toString();
                } else if (mBinding.rbQ1a2.isChecked()) {
                    q1Answer = mBinding.rbQ1a2.getText().toString();
                } else if (mBinding.rbQ1a3.isChecked()) {
                    q1Answer = mBinding.rbQ1a3.getText().toString();
                } else if (mBinding.rbQ1a4.isChecked()) {
                    q1Answer = mBinding.rbQ1a4.getText().toString();
                }

                if (mBinding.rbQ2a1.isChecked()) {
                    q2Answer = mBinding.rbQ2a1Et.getText().toString();
                } else if (mBinding.rbQ2a2.isChecked()) {
                    q2Answer = mBinding.rbQ2a2Et.getText().toString();
                }


                q3Answer = mBinding.etQ3a.getText().toString();


                q4Answer = mBinding.etbrand.getText().toString();


                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String todayString = formatter.format(todayDate);
                ending_date = todayString;

                updateMeetingChecklist(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""),
                        appointment_id,
                        starting_date,
                        starting_latitude,
                        mBinding.tvQuestion1.getText().toString(), q1Answer,
                        mBinding.tvQuestion2.getText().toString(), q2Answer,
                        mBinding.tvQuestion3.getText().toString(), q3Answer,
                        mBinding.tvQuestion4.getText().toString(), q4Answer,
                        "", "", "", "", "", ending_date, ending_latitude, meeting_outcome);

            }
        });

        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAppointmentMeetingCheckList.this);
                builder.setTitle("Confirmation"); // Set the dialog title
                builder.setMessage("Are you sure you want to exit? Your data will be lost!"); // Set the dialog message

                // Set up the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();


                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }


    public void updateMeetingChecklist(String token, String id, String start_meeting,
                                       String start_meeting_lat_lng,
                                       String question1,
                                       String answer1,
                                       String question2,
                                       String answer2,
                                       String question3,
                                       String answer3,
                                       String question4,
                                       String answer4,

                                       String video1,
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
        if (start_meeting != null) {
            builder.addFormDataPart("start_meeting", start_meeting);
        }
        if (start_meeting_lat_lng != null) {
            builder.addFormDataPart("start_meeting_lat_lng", start_meeting_lat_lng);
        }
        if (question1 != null) {
            builder.addFormDataPart("question1", question1);
        }
        if (answer1 != null) {
            builder.addFormDataPart("answer1", answer1);
        }
        if (question2 != null) {
            builder.addFormDataPart("question2", question2);
        }
        if (answer2 != null) {
            builder.addFormDataPart("answer2", answer2);
        }

        if (question3 != null) {
            builder.addFormDataPart("question3", question3);
        }
        if (answer3 != null) {
            builder.addFormDataPart("answer3", answer3);
        }

        if (question4 != null) {
            builder.addFormDataPart("question4", question4);
        }
        if (answer4 != null) {
            builder.addFormDataPart("answer4", answer4);
        }


        if (video1 != null) {
            builder.addFormDataPart("video1", video1);
        }

        if (video2 != null) {
            builder.addFormDataPart("video2", video2);
        }
        if (video3 != null) {
            builder.addFormDataPart("video3", video3);
        }
        if (i1 != null) {
            builder.addFormDataPart("i1", i1);
        }
        if (i2 != null) {
            builder.addFormDataPart("i2", i2);
        }
        if (end_meeting != null) {
            builder.addFormDataPart("end_meeting", end_meeting);
        }
        if (end_meeting_lat_lng != null) {
            builder.addFormDataPart("end_meeting_lat_lng", end_meeting_lat_lng);
        }
        if (meeting_outcome != null) {
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


                                Toast.makeText(ActivityAppointmentMeetingCheckList.this, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();

//                                finish();
                            } else {
                                Toast.makeText(ActivityAppointmentMeetingCheckList.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityAppointmentMeetingCheckList.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAppointmentMeetingCheckList.this);
        builder.setTitle("Confirmation"); // Set the dialog title
        builder.setMessage("Are you sure you want to exit? Your data will be lost!"); // Set the dialog message

        // Set up the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();


            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}