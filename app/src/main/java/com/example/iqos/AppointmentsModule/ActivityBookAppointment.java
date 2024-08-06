package com.example.iqos.AppointmentsModule;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.iqos.Constants;
import com.example.iqos.R;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.databinding.ActivityBookAppointmentBinding;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBookAppointment extends AppCompatActivity {


    ActivityBookAppointmentBinding mBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        mBinding.rvAppointments.setVisibility(View.VISIBLE);

        mBinding.tvNormalAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.tvNormalAppointments.setBackground(getDrawable(R.drawable.rounded_top_blue));
                mBinding.tvEcomAppointments.setBackground(getDrawable(R.drawable.rounded_top_grey));
                mBinding.rvAppointments.setVisibility(View.VISIBLE);
                mBinding.rvEcomAppointments.setVisibility(View.GONE);
                getAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));
            }
        });
        mBinding.tvEcomAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBinding.tvEcomAppointments.setBackground(getDrawable(R.drawable.rounded_top_blue));
                mBinding.tvNormalAppointments.setBackground(getDrawable(R.drawable.rounded_top_grey));
                mBinding.rvAppointments.setVisibility(View.GONE);
                mBinding.rvEcomAppointments.setVisibility(View.VISIBLE);

                getEcomAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));

            }
        });


        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBookAppointment.this);
//                builder.setTitle("Confirmation"); // Set the dialog title
//                builder.setMessage("Are you sure you want to exit? Your data will be lost!"); // Set the dialog message
//
//                // Set up the buttons
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
                finish();
//
//
//
//                    }
//                });
//
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                // Create and show the AlertDialog
//                AlertDialog dialog = builder.create();
//                dialog.show();
            }
        });
        mBinding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mBinding.rvAppointments.getVisibility() == View.VISIBLE) {
                    mBinding.swipe.setRefreshing(true);
                    getAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));

                } else {
                    getEcomAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));


                }

            }
        });

        //  appointmentsRecycler();

        getAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mBinding.rvAppointments.getVisibility() == View.VISIBLE) {
            mBinding.swipe.setRefreshing(true);
            getAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));

        } else {
            getEcomAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));


        }
    }

    private void appointmentsRecycler(List<Appointment> appointments) {


        AppointmentAdapter appointmentAdapter;
        mBinding.rvAppointments.setLayoutManager(new LinearLayoutManager(ActivityBookAppointment.this, LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new AppointmentAdapter(ActivityBookAppointment.this, appointments);
        mBinding.rvAppointments.setAdapter(appointmentAdapter);


    }

    private void ecomappointmentsRecycler(List<EcAppointment> appointments) {


        EcomAppointmentAdapter appointmentAdapter;
        mBinding.rvEcomAppointments.setLayoutManager(new LinearLayoutManager(ActivityBookAppointment.this, LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new EcomAppointmentAdapter(ActivityBookAppointment.this, appointments);
        mBinding.rvEcomAppointments.setAdapter(appointmentAdapter);


    }

    public void getEcomAppointment(String token) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityBookAppointment.this).create(ApiService.class);
        Call<EcomAppointments> call = apiService.getEcomAppointment("application/json", token);
        call.enqueue(new Callback<EcomAppointments>() {
            @Override
            public void onResponse(Call<EcomAppointments> call, Response<EcomAppointments> response) {
                ///// Progress Dialog  Dismiss////////////
                final EcomAppointments keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipe.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);

                                    if (keyModel.getData().getAppointments().size() > 0) {
                                        mBinding.rvEcomAppointments.setVisibility(View.VISIBLE);
                                        ecomappointmentsRecycler(keyModel.getData().getAppointments());
                                        ;
                                    } else if (keyModel.getData().getAppointments().size() == 0) {

                                        mBinding.rvEcomAppointments.setVisibility(View.GONE);

                                        ecomappointmentsRecycler(new ArrayList<>());
                                    }


                                } else {
                                    mBinding.rvEcomAppointments.setVisibility(View.GONE);

                                    ecomappointmentsRecycler(new ArrayList<>());
                                }

                            } else {
                                mBinding.rvEcomAppointments.setVisibility(View.GONE);

                                ecomappointmentsRecycler(new ArrayList<>());
                                ;

//                                Toast.makeText(ActivityBookAppointment.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                mBinding.progress.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipe.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            mBinding.progress.setVisibility(View.GONE);
                            Toast.makeText(ActivityBookAppointment.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<EcomAppointments> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipe.setRefreshing(false);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityBookAppointment.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public void getAppointment(String token) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityBookAppointment.this).create(ApiService.class);
        Call<GetAppointmentModel> call = apiService.getAppointment("application/json", token);
        call.enqueue(new Callback<GetAppointmentModel>() {
            @Override
            public void onResponse(Call<GetAppointmentModel> call, Response<GetAppointmentModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final GetAppointmentModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipe.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);

                                    if (keyModel.getData().getAppointments().size() > 0) {
                                        mBinding.rvAppointments.setVisibility(View.VISIBLE);
                                        appointmentsRecycler(keyModel.getData().getAppointments());
                                    } else if (keyModel.getData().getAppointments().size() == 0) {
                                        mBinding.rvAppointments.setVisibility(View.GONE);
                                        appointmentsRecycler(new ArrayList<>());
                                    }


                                }

                            } else {
                                mBinding.rvAppointments.setVisibility(View.GONE);

                                appointmentsRecycler(new ArrayList<>());

//                                Toast.makeText(ActivityBookAppointment.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                mBinding.progress.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipe.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            mBinding.progress.setVisibility(View.GONE);
                            Toast.makeText(ActivityBookAppointment.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<GetAppointmentModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipe.setRefreshing(false);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityBookAppointment.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public class Appointment {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("designation")
        @Expose
        private String designation;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("age_group")
        @Expose
        private String ageGroup;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("appointment_status")
        @Expose
        private String appointmentStatus;
        @SerializedName("appointment_date")
        @Expose
        private String appointmentDate;
        @SerializedName("appointment_time")
        @Expose
        private String appointmentTime;
        @SerializedName("appointment_at")
        @Expose
        private String appointmentAt;
        @SerializedName("appointment_location")
        @Expose
        private String appointmentLocation;
        @SerializedName("last_action")
        @Expose
        private LastAction lastAction;
        @SerializedName("next_action")
        @Expose
        private NextAction nextAction;
        @SerializedName("existing_device_url")
        @Expose
        private String existingDeviceUrl;
        @SerializedName("is_appointment")
        @Expose
        private String isAppointment;
        @SerializedName("pre_meeting")
        @Expose
        private String preMeeting;
        @SerializedName("meeting_complete")
        @Expose
        private String meetingComplete;
        @SerializedName("appointment_message")
        @Expose
        private String appointmentMessage;
        @SerializedName("appointment_type")
        @Expose
        private String appointmentType;

        public String getIsMultiSale() {
            return isMultiSale;
        }

        public void setIsMultiSale(String isMultiSale) {
            this.isMultiSale = isMultiSale;
        }

        @SerializedName("is_multisale")
        @Expose
        private String isMultiSale;
        @SerializedName("is_external")
        @Expose
        private String is_external;

        public String getIs_external() {
            return is_external;
        }

        public void setIs_external(String is_external) {
            this.is_external = is_external;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAppointmentStatus() {
            return appointmentStatus;
        }

        public void setAppointmentStatus(String appointmentStatus) {
            this.appointmentStatus = appointmentStatus;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public String getAppointmentAt() {
            return appointmentAt;
        }

        public void setAppointmentAt(String appointmentAt) {
            this.appointmentAt = appointmentAt;
        }

        public String getAppointmentLocation() {
            return appointmentLocation;
        }

        public void setAppointmentLocation(String appointmentLocation) {
            this.appointmentLocation = appointmentLocation;
        }

        public LastAction getLastAction() {
            return lastAction;
        }

        public void setLastAction(LastAction lastAction) {
            this.lastAction = lastAction;
        }

        public NextAction getNextAction() {
            return nextAction;
        }

        public void setNextAction(NextAction nextAction) {
            this.nextAction = nextAction;
        }

        public String getExistingDeviceUrl() {
            return existingDeviceUrl;
        }

        public void setExistingDeviceUrl(String existingDeviceUrl) {
            this.existingDeviceUrl = existingDeviceUrl;
        }

        public String getIsAppointment() {
            return isAppointment;
        }

        public void setIsAppointment(String isAppointment) {
            this.isAppointment = isAppointment;
        }

        public String getPreMeeting() {
            return preMeeting;
        }

        public void setPreMeeting(String preMeeting) {
            this.preMeeting = preMeeting;
        }

        public String getMeetingComplete() {
            return meetingComplete;
        }

        public void setMeetingComplete(String meetingComplete) {
            this.meetingComplete = meetingComplete;
        }

        public String getAppointmentMessage() {
            return appointmentMessage;
        }

        public void setAppointmentMessage(String appointmentMessage) {
            this.appointmentMessage = appointmentMessage;
        }

        public String getAppointmentType() {
            return appointmentType;
        }

        public void setAppointmentType(String appointmentType) {
            this.appointmentType = appointmentType;
        }

    }

    public class Data {

        @SerializedName("appointments")
        @Expose
        private List<Appointment> appointments;

        public List<Appointment> getAppointments() {
            return appointments;
        }

        public void setAppointments(List<Appointment> appointments) {
            this.appointments = appointments;
        }

    }

    public class GetAppointmentModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private Data data;

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

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }

    public class LastAction {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("time")
        @Expose
        private String time;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }

    public class NextAction {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("time")
        @Expose
        private String time;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }


    public class EcAppointment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("age_group")
        @Expose
        private String ageGroup;
        @SerializedName("appointment_status")
        @Expose
        private String appointmentStatus;
        @SerializedName("appointment_date")
        @Expose
        private String appointmentDate;
        @SerializedName("appointment_time")
        @Expose
        private String appointmentTime;
        @SerializedName("appointment_at")
        @Expose
        private String appointmentAt;
        @SerializedName("appointment_location")
        @Expose
        private String appointmentLocation;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAgeGroup() {
            return ageGroup;
        }

        public void setAgeGroup(String ageGroup) {
            this.ageGroup = ageGroup;
        }

        public String getAppointmentStatus() {
            return appointmentStatus;
        }

        public void setAppointmentStatus(String appointmentStatus) {
            this.appointmentStatus = appointmentStatus;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public String getAppointmentAt() {
            return appointmentAt;
        }

        public void setAppointmentAt(String appointmentAt) {
            this.appointmentAt = appointmentAt;
        }

        public String getAppointmentLocation() {
            return appointmentLocation;
        }

        public void setAppointmentLocation(String appointmentLocation) {
            this.appointmentLocation = appointmentLocation;
        }

    }

    public class EcData {

        @SerializedName("appointments")
        @Expose
        private List<EcAppointment> appointments;

        public List<EcAppointment> getAppointments() {
            return appointments;
        }

        public void setAppointments(List<EcAppointment> appointments) {
            this.appointments = appointments;
        }

    }

    public class EcomAppointments {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private EcData data;

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

        public EcData getData() {
            return data;
        }

        public void setData(EcData data) {
            this.data = data;
        }

    }
}