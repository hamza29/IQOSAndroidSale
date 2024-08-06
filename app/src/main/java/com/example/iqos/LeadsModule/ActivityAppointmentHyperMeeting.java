package com.example.iqos.LeadsModule;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.iqos.databinding.ActivityAppointmentHyperMeetingCheckListBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAppointmentHyperMeeting extends AppCompatActivity {

    ActivityAppointmentHyperMeetingCheckListBinding mBinding;
    String starting_latitude;
    String starting_date;
    String ending_latitude;
    String ending_date;
    String meeting_outcome;
    SharedPreferences mSharedPreferences;
    String appointment_id;
    String q4Answer = "";
    String q5Answer = "";

    String q3Answer = "";
    String q2Answer = "";
    String q1Answer = "";
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding =
                ActivityAppointmentHyperMeetingCheckListBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        GPSTracker gpsTracker = new GPSTracker(this);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            starting_latitude = gpsTracker.getLatitude() + "," + gpsTracker.getLongitude();

        }
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = formatter.format(todayDate);
        starting_date = todayString;
        appointment_id = getIntent().getStringExtra("appointment_id");
        String name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
        mBinding.tvLeadName.setText("Lead Name: " + name);
        updateMeetingFirstChecklist(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""),
                type, appointment_id,
                starting_date,
                starting_latitude,
                mBinding.tvQuestion1.getText().toString(), "",
                mBinding.tvQuestion2.getText().toString(), "",
                mBinding.tvQuestion3.getText().toString(), "",
                "", "", "");


        mBinding.tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GPSTracker gpsTracker = new GPSTracker(ActivityAppointmentHyperMeeting.this);
                if (gpsTracker.getIsGPSTrackingEnabled()) {
                    ending_latitude = gpsTracker.getLatitude() + "," + gpsTracker.getLongitude();
                }


                if (mBinding.rbQ1a1.isChecked()) {
                    q1Answer = mBinding.rbQ1a1.getText().toString();
                } else if (mBinding.rbQ1a2.isChecked()) {
                    q1Answer = mBinding.rbQ1a2.getText().toString();
                }

                q2Answer = mBinding.rbQ2a1Et.getText().toString();


                q3Answer = mBinding.etQ3a.getText().toString();


                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String todayString = formatter.format(todayDate);
                ending_date = todayString;

                updateMeetingChecklist(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""),
                        type, appointment_id,
                        starting_date,
                        starting_latitude,
                        mBinding.tvQuestion1.getText().toString(), q1Answer,
                        mBinding.tvQuestion2.getText().toString(), q2Answer,
                        mBinding.tvQuestion3.getText().toString(), q3Answer,
                        ending_date, ending_latitude, "Completed");

            }
        });

        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAppointmentHyperMeeting.this);
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


    public void updateMeetingChecklist(String token, String type, String id, String start_meeting,
                                       String start_meeting_lat_lng,
                                       String question1,
                                       String answer1,
                                       String question2,
                                       String answer2,
                                       String question3,
                                       String answer3,
                                       String end_meeting,
                                       String end_meeting_lat_lng,
                                       String meeting_outcome) {
        ApiService apiService = ApiClient.getClient(ActivityAppointmentHyperMeeting.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("type", type.trim().toLowerCase().toString());
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

        Call<Model.GenerealModel> call = apiService.updateHyperMeetingChecklist(token, requestBody);

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


                                Toast.makeText(ActivityAppointmentHyperMeeting.this, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();

                                finish();
                            } else {
                                Toast.makeText(ActivityAppointmentHyperMeeting.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityAppointmentHyperMeeting.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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

    public void updateMeetingFirstChecklist(String token, String type, String id,

                                            String start_meeting,
                                            String start_meeting_lat_lng,
                                            String question1,
                                            String answer1,
                                            String question2,
                                            String answer2,
                                            String question3,
                                            String answer3,
                                            String end_meeting,
                                            String end_meeting_lat_lng,
                                            String meeting_outcome) {
        ApiService apiService = ApiClient.getClient(ActivityAppointmentHyperMeeting.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("type", type.trim().toLowerCase().toString());
        if (start_meeting != null) {
            builder.addFormDataPart("start_meeting", start_meeting);
        }
        if (start_meeting_lat_lng != null) {
            builder.addFormDataPart("start_meeting_lat_lng", start_meeting_lat_lng);
        }
        if (question1 != null) {
            builder.addFormDataPart("question1", question1);
        }

        if (question2 != null) {
            builder.addFormDataPart("question2", question2);
        }

        if (question3 != null) {
            builder.addFormDataPart("question3", question3);
        }


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperMeetingChecklist(token, requestBody);

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


                                Toast.makeText(ActivityAppointmentHyperMeeting.this, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();

//                                finish();
                            } else {
                                Toast.makeText(ActivityAppointmentHyperMeeting.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityAppointmentHyperMeeting.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAppointmentHyperMeeting.this);
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