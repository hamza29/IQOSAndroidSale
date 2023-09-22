package com.example.iqos.AppointmentsModule;

import static com.example.iqos.LeadsModule.ActivityLeadsDetail.ivityLeads;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iqos.Constants;
import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.MeetingModule.BookAppointmentDetailActivity;
import com.example.iqos.R;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityBookAppointmentBinding;
import com.example.iqos.databinding.ActivityBookAppointmentDetailBinding;
import com.example.iqos.databinding.ActivityBookingAppointmentBinding;
import com.google.android.gms.common.internal.Objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentBookingDetailActivity extends AppCompatActivity {


    ActivityBookingAppointmentBinding mBinding;
    SharedPreferences mSharedPreferences;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    int year;
    int month;
    int dayOfMonth;
    ArrayList<String> AppointStatus = new ArrayList<>();

    String appoint_status="Scheduled";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBookingAppointmentBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
Intent intent = getIntent();

        AppointStatus.add("Select");
        AppointStatus.add("Scheduled");
        AppointStatus.add("Done");
; mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         mBinding.tvName.setText(""+ intent.getStringExtra("name"));

        mBinding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentBookingDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                mBinding.tvDate.setText(""+ selectedDate);
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(AppointmentBookingDetailActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String selectedTime = hourOfDay + ":" + minute+":00";
                                mBinding.tvTime.setText(""+ selectedTime);
                            }
                        }, hour, minute, false);

                timePickerDialog.show();
            }
        });

mBinding.confirmApp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(!mBinding.tvTime.getText().toString().isEmpty() && !mBinding.tvDate.getText().toString().isEmpty()
                && !mBinding.tvName.getText().toString().isEmpty() && !mBinding.tvLocation.getText().toString().isEmpty()) {
//          if(!appoint_status.equalsIgnoreCase("Select")) {
              boookApp(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""),
                      intent.getStringExtra("lead_id"),
                      appoint_status,
                      mBinding.tvDate.getText().toString() + " " + mBinding.tvTime.getText().toString(),
                      mBinding.tvLocation.getText().toString());
//          }else{
//              Toast.makeText(AppointmentBookingDetailActivity.this, "Please select status", Toast.LENGTH_SHORT).show();
//            }
        }
        else{
            Toast.makeText(AppointmentBookingDetailActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }}
});
//        setAppointStatusSpinner();

    }
    private void setAppointStatusSpinner( ){


        mBinding.spinnerAppointStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                appoint_status = AppointStatus.get(position) ;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                AppointStatus);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mBinding.spinnerAppointStatus.setAdapter(ad);
    }

    public void boookApp(String token,String id,String status,String app_at,String location) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(AppointmentBookingDetailActivity.this).create(ApiService.class);
        Call<Model.GenerealModel> call = apiService.bookAppointment("application/json",token,id,status,app_at,location, "scheduled");
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

                                    Toast.makeText(AppointmentBookingDetailActivity.this, "Appointment Book Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                if(ivityLeads !=null){
                                    ivityLeads.finish();
                                }




                            } else {
                                Toast.makeText(AppointmentBookingDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AppointmentBookingDetailActivity.this, "key model null", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AppointmentBookingDetailActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

//    public void boookAppDetails(String token,String id) {
//        mBinding.progress.setVisibility(View.VISIBLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        ApiService apiService = ApiClient.getClient(AppointmentBookingDetailActivity.this).create(ApiService.class);
//        Call<Model.BookAppointmentDetailsModel> call = apiService.bookAppointmentDetails("application/json",token,id);
//        call.enqueue(new Callback<Model.BookAppointmentDetailsModel>() {
//            @Override
//            public void onResponse(Call<Model.BookAppointmentDetailsModel> call, Response<Model.BookAppointmentDetailsModel> response) {
//                ///// Progress Dialog  Dismiss////////////
//                final Model.BookAppointmentDetailsModel keyModel = response.body();
//                if (keyModel != null) {
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                            if (keyModel.getStatus().equals("1")) {
//                                if (keyModel.getData() != null) {
//                                    mBinding.progress.setVisibility(View.GONE);
//                                    Toast.makeText(AppointmentBookingDetailActivity.this, "Book app details  load Successfully", Toast.LENGTH_SHORT).show();
//                                }
//
//                            } else {
//                                Toast.makeText(AppointmentBookingDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                                mBinding.progress.setVisibility(View.GONE);
//
//                            }
//                        }
//                    });
//                } else {
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                            mBinding.progress.setVisibility(View.GONE);
//                            Toast.makeText(AppointmentBookingDetailActivity.this, "key model null", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Model.BookAppointmentDetailsModel> call, Throwable t) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                        mBinding.progress.setVisibility(View.GONE);
//                        Toast.makeText(AppointmentBookingDetailActivity.this, "failure", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//            }
//        });
//    }

}