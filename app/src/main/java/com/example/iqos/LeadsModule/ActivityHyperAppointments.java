package com.example.iqos.LeadsModule;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.iqos.AppointmentsModule.AppointmentAdapter;
import com.example.iqos.Constants;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityBookAppointmentBinding;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHyperAppointments extends AppCompatActivity {


    ActivityBookAppointmentBinding mBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                         finish();

             }
        });

        mBinding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.swipe.setRefreshing(true);
                getAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));

            }
        });

        //  appointmentsRecycler();

        getAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAppointment(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));

    }

    private void appointmentsRecycler(List<Appointment> appointments) {



        AppointmentHyperAdapter appointmentAdapter;
        mBinding.rvAppointments.setLayoutManager(new LinearLayoutManager(ActivityHyperAppointments.this, LinearLayoutManager.VERTICAL, false));
        appointmentAdapter = new AppointmentHyperAdapter(ActivityHyperAppointments.this, appointments);
        mBinding.rvAppointments.setAdapter(appointmentAdapter);


    }

    public void getAppointment(String token) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityHyperAppointments.this).create(ApiService.class);
        Call<HyperAppointmentsModel> call = apiService.getHyperAppointments("application/json",token);
        call.enqueue(new Callback<HyperAppointmentsModel>() {
            @Override
            public void onResponse(Call<HyperAppointmentsModel> call, Response<HyperAppointmentsModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final HyperAppointmentsModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipe.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);

                                    if (keyModel.getData().getAppointments().size() >0){
                                        appointmentsRecycler(keyModel.getData().getAppointments())   ;
                                    }
                                    mBinding.rvAppointments.setVisibility(View.VISIBLE);



                                }

                            } else {
                                mBinding.rvAppointments.setVisibility(View.GONE);

                                appointmentsRecycler(new ArrayList<>())   ;

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
                            Toast.makeText(ActivityHyperAppointments.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<HyperAppointmentsModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipe.setRefreshing(false);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityHyperAppointments.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
    public class Appointment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("lead_id")
        @Expose
        private Integer leadId;
        @SerializedName("day1_message_at")
        @Expose
        private String day1MessageAt;
        @SerializedName("day1_call_at")
        @Expose
        private Object day1CallAt;
        @SerializedName("day3_message_at")
        @Expose
        private String day3MessageAt;
        @SerializedName("day3_call_at")
        @Expose
        private String day3CallAt;
        @SerializedName("day7_message_at")
        @Expose
        private String day7MessageAt;
        @SerializedName("day7_call_at")
        @Expose
        private String day7CallAt;
        @SerializedName("day10_message_at")
        @Expose
        private String day10MessageAt;
        @SerializedName("day10_call_at")
        @Expose
        private Object day10CallAt;
        @SerializedName("day14_message_at")
        @Expose
        private Object day14MessageAt;
        @SerializedName("day14_call_at")
        @Expose
        private String day14CallAt;
        @SerializedName("appointment_at")
        @Expose
        private String appointmentAt;
        @SerializedName("appointment_location")
        @Expose
        private String appointmentLocation;
        @SerializedName("appointment_status")
        @Expose
        private String appointmentStatus;
        @SerializedName("start_meeting")
        @Expose
        private String startMeeting;
        @SerializedName("start_meeting_lat_lng")
        @Expose
        private String startMeetingLatLng;
        @SerializedName("end_meeting")
        @Expose
        private String endMeeting;
        @SerializedName("end_meeting_lat_lng")
        @Expose
        private String endMeetingLatLng;
        @SerializedName("question1")
        @Expose
        private String question1;
        @SerializedName("answer1")
        @Expose
        private String answer1;
        @SerializedName("question2")
        @Expose
        private String question2;
        @SerializedName("answer2")
        @Expose
        private String answer2;
        @SerializedName("question3")
        @Expose
        private String question3;
        @SerializedName("answer3")
        @Expose
        private String answer3;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("last_action")
        @Expose
        private LastAction lastAction;
        @SerializedName("is_appointment")
        @Expose
        private Integer isAppointment;
        @SerializedName("meeting_complete")
        @Expose
        private Integer meetingComplete;
        @SerializedName("lead")
        @Expose
        private Lead lead;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getLeadId() {
            return leadId;
        }

        public void setLeadId(Integer leadId) {
            this.leadId = leadId;
        }

        public String getDay1MessageAt() {
            return day1MessageAt;
        }

        public void setDay1MessageAt(String day1MessageAt) {
            this.day1MessageAt = day1MessageAt;
        }

        public Object getDay1CallAt() {
            return day1CallAt;
        }

        public void setDay1CallAt(Object day1CallAt) {
            this.day1CallAt = day1CallAt;
        }

        public String getDay3MessageAt() {
            return day3MessageAt;
        }

        public void setDay3MessageAt(String day3MessageAt) {
            this.day3MessageAt = day3MessageAt;
        }

        public String getDay3CallAt() {
            return day3CallAt;
        }

        public void setDay3CallAt(String day3CallAt) {
            this.day3CallAt = day3CallAt;
        }

        public String getDay7MessageAt() {
            return day7MessageAt;
        }

        public void setDay7MessageAt(String day7MessageAt) {
            this.day7MessageAt = day7MessageAt;
        }

        public String getDay7CallAt() {
            return day7CallAt;
        }

        public void setDay7CallAt(String day7CallAt) {
            this.day7CallAt = day7CallAt;
        }

        public String getDay10MessageAt() {
            return day10MessageAt;
        }

        public void setDay10MessageAt(String day10MessageAt) {
            this.day10MessageAt = day10MessageAt;
        }

        public Object getDay10CallAt() {
            return day10CallAt;
        }

        public void setDay10CallAt(Object day10CallAt) {
            this.day10CallAt = day10CallAt;
        }

        public Object getDay14MessageAt() {
            return day14MessageAt;
        }

        public void setDay14MessageAt(Object day14MessageAt) {
            this.day14MessageAt = day14MessageAt;
        }

        public String getDay14CallAt() {
            return day14CallAt;
        }

        public void setDay14CallAt(String day14CallAt) {
            this.day14CallAt = day14CallAt;
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

        public String getAppointmentStatus() {
            return appointmentStatus;
        }

        public void setAppointmentStatus(String appointmentStatus) {
            this.appointmentStatus = appointmentStatus;
        }

        public String getStartMeeting() {
            return startMeeting;
        }

        public void setStartMeeting(String startMeeting) {
            this.startMeeting = startMeeting;
        }

        public String getStartMeetingLatLng() {
            return startMeetingLatLng;
        }

        public void setStartMeetingLatLng(String startMeetingLatLng) {
            this.startMeetingLatLng = startMeetingLatLng;
        }

        public String getEndMeeting() {
            return endMeeting;
        }

        public void setEndMeeting(String endMeeting) {
            this.endMeeting = endMeeting;
        }

        public String getEndMeetingLatLng() {
            return endMeetingLatLng;
        }

        public void setEndMeetingLatLng(String endMeetingLatLng) {
            this.endMeetingLatLng = endMeetingLatLng;
        }

        public String getQuestion1() {
            return question1;
        }

        public void setQuestion1(String question1) {
            this.question1 = question1;
        }

        public String getAnswer1() {
            return answer1;
        }

        public void setAnswer1(String answer1) {
            this.answer1 = answer1;
        }

        public String getQuestion2() {
            return question2;
        }

        public void setQuestion2(String question2) {
            this.question2 = question2;
        }

        public String getAnswer2() {
            return answer2;
        }

        public void setAnswer2(String answer2) {
            this.answer2 = answer2;
        }

        public String getQuestion3() {
            return question3;
        }

        public void setQuestion3(String question3) {
            this.question3 = question3;
        }

        public String getAnswer3() {
            return answer3;
        }

        public void setAnswer3(String answer3) {
            this.answer3 = answer3;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public LastAction getLastAction() {
            return lastAction;
        }

        public void setLastAction(LastAction lastAction) {
            this.lastAction = lastAction;
        }

        public Integer getIsAppointment() {
            return isAppointment;
        }

        public void setIsAppointment(Integer isAppointment) {
            this.isAppointment = isAppointment;
        }

        public Integer getMeetingComplete() {
            return meetingComplete;
        }

        public void setMeetingComplete(Integer meetingComplete) {
            this.meetingComplete = meetingComplete;
        }

        public Lead getLead() {
            return lead;
        }

        public void setLead(Lead lead) {
            this.lead = lead;
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
    public class HyperAppointmentsModel {

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
    public class LastAction__1 {

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
    public class Lead {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("last_action")
        @Expose
        private LastAction__1 lastAction;
        @SerializedName("next_action")
        @Expose
        private NextAction nextAction;
        @SerializedName("existing_device_url")
        @Expose
        private String existingDeviceUrl;
        @SerializedName("is_appointment")
        @Expose
        private Integer isAppointment;
        @SerializedName("pre_meeting")
        @Expose
        private Integer preMeeting;
        @SerializedName("meeting_complete")
        @Expose
        private Integer meetingComplete;
        @SerializedName("appointment_message")
        @Expose
        private String appointmentMessage;

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

        public LastAction__1 getLastAction() {
            return lastAction;
        }

        public void setLastAction(LastAction__1 lastAction) {
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

        public Integer getIsAppointment() {
            return isAppointment;
        }

        public void setIsAppointment(Integer isAppointment) {
            this.isAppointment = isAppointment;
        }

        public Integer getPreMeeting() {
            return preMeeting;
        }

        public void setPreMeeting(Integer preMeeting) {
            this.preMeeting = preMeeting;
        }

        public Integer getMeetingComplete() {
            return meetingComplete;
        }

        public void setMeetingComplete(Integer meetingComplete) {
            this.meetingComplete = meetingComplete;
        }

        public String getAppointmentMessage() {
            return appointmentMessage;
        }

        public void setAppointmentMessage(String appointmentMessage) {
            this.appointmentMessage = appointmentMessage;
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
}