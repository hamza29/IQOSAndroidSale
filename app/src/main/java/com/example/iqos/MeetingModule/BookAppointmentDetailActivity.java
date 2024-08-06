package com.example.iqos.MeetingModule;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iqos.Constants;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityBookAppointmentDetailBinding;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentDetailActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;


    ActivityBookAppointmentDetailBinding mBinding;
    ArrayList<String> AppointStatus = new ArrayList<>();

    String appoint_status = "Scheduled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBookAppointmentDetailBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);

//        AppointStatus.add("Select");
//        AppointStatus.add("Scheduled");
//        AppointStatus.add("Done");
        Intent intent = getIntent();
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBinding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookAppointmentDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                mBinding.tvDate.setText("" + selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();
            }
        });

        mBinding.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(BookAppointmentDetailActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String selectedTime = hourOfDay + ":" + minute + ":00";
                                mBinding.tvTime.setText("" + selectedTime);
                            }
                        }, hour, minute, false);

                timePickerDialog.show();
            }
        });


        appointmentDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), intent.getStringExtra("appointment_id"));


    }

    private void setAppointStatusSpinner(String status) {


        ArrayList<String> newLeads = new ArrayList<>();
        for (int i = 0; i < AppointStatus.size(); i++) {
            if (status.equalsIgnoreCase(AppointStatus.get(i))) {
                newLeads.add(0, "" + AppointStatus.get(i));
            } else {
                newLeads.add("" + AppointStatus.get(i));

            }


        }
        mBinding.spinnerAppointStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                appoint_status = newLeads.get(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                newLeads);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mBinding.spinnerAppointStatus.setAdapter(ad);
    }

    public void appointmentDetails(String token, String id) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(BookAppointmentDetailActivity.this).create(ApiService.class);
        Call<Model.AppointmentDetailsModel> call = apiService.appointmentDetails("application/json", token,
                id);
        call.enqueue(new Callback<Model.AppointmentDetailsModel>() {
            @Override
            public void onResponse(Call<Model.AppointmentDetailsModel> call, Response<Model.AppointmentDetailsModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.AppointmentDetailsModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);
                                    mBinding.tvAppointment.setText("Appointment # " + keyModel.getData().getAppoinment().getId());

                                    if (keyModel.getData().getAppoinment().getFirstName() != null) {
                                        mBinding.tvName.setText("" + keyModel.getData().getAppoinment().getFirstName());
                                    }
                                    if (keyModel.getData().getAppoinment().getAppointmentLocation() != null) {
                                        mBinding.tvLocation.setText("" + keyModel.getData().getAppoinment().getAppointmentLocation());
                                    }
                                    if (keyModel.getData().getAppoinment().getAppointmentDate() != null) {
                                        mBinding.tvDate.setText("" + keyModel.getData().getAppoinment().getAppointmentDate());
                                    }
                                    if (keyModel.getData().getAppoinment().getAppointmentTime() != null) {
                                        mBinding.tvTime.setText("" + keyModel.getData().getAppoinment().getAppointmentTime());
                                    }
                                    mBinding.startAppoint.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
//                                            if(!appoint_status.equalsIgnoreCase("select")){
                                            bookApp(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""),
                                                    id,
                                                    appoint_status,
                                                    mBinding.tvDate.getText().toString() + " " + mBinding.tvTime.getText().toString(),
                                                    mBinding.tvLocation.getText().toString());
//                                        }else{
//                                                Toast.makeText(BookAppointmentDetailActivity.this, "Please select appointment status", Toast.LENGTH_SHORT).show();
//                                            }
                                        }
                                    });

                                }
//                                if(keyModel.getData().getAppoinment().getAppointmentStatus() == null) {
//                                    setAppointStatusSpinner("Select");
//                                }else{
//                                    setAppointStatusSpinner(keyModel.getData().getAppoinment().getAppointmentStatus());
//
//                                }
                            } else {
                                Toast.makeText(BookAppointmentDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                mBinding.progress.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            mBinding.progress.setVisibility(View.GONE);
                            Toast.makeText(BookAppointmentDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.AppointmentDetailsModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(BookAppointmentDetailActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public void bookApp(String token, String id, String status, String app_at, String location) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(BookAppointmentDetailActivity.this).create(ApiService.class);
        Call<Model.GenerealModel> call = apiService.bookAppointment("application/json", token, id, status, app_at, location, "scheduled");
        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.GenerealModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {


                                mBinding.progress.setVisibility(View.GONE);

                                Toast.makeText(BookAppointmentDetailActivity.this, "Appointment Book Successfully", Toast.LENGTH_SHORT).show();
                                finish();


                            } else {
                                Toast.makeText(BookAppointmentDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                mBinding.progress.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            mBinding.progress.setVisibility(View.GONE);
                            Toast.makeText(BookAppointmentDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(BookAppointmentDetailActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }


}