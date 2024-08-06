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

import com.example.iqos.Constants;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.databinding.ActivityHyperleadsBinding;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHyperCareLeads extends AppCompatActivity {

    ActivityHyperleadsBinding mBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHyperleadsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));

        //LeadsRecycler();
        mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));

            }
        });
        mBinding.tvLeads.setText("Device Care");
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));

    }

    private void LeadsRecycler(List<HyperModel> leads) {


        HyperCareMainLeadsAdapter leadsAdapter;
        mBinding.rvLeads.setLayoutManager(new LinearLayoutManager(ActivityHyperCareLeads.this, LinearLayoutManager.VERTICAL, false));
        leadsAdapter = new HyperCareMainLeadsAdapter(ActivityHyperCareLeads.this, leads, mBinding.progress);
        mBinding.rvLeads.setAdapter(leadsAdapter);
    }


    public void getLeads(String token) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityHyperCareLeads.this).create(ApiService.class);
        Call<DeviceCareLeadsModel> call = apiService.getHyperLeads("application/json", token);
        call.enqueue(new Callback<DeviceCareLeadsModel>() {
            @Override
            public void onResponse(Call<DeviceCareLeadsModel> call, Response<DeviceCareLeadsModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final DeviceCareLeadsModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipeRefresh.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);
                                    ArrayList<HyperModel> hyperModels = new ArrayList<HyperModel>();
                                    hyperModels.add(new HyperModel("Day1", keyModel.getData().getDay1()));
                                    hyperModels.add(new HyperModel("Day3", keyModel.getData().getDay3()));
                                    hyperModels.add(new HyperModel("Day7", keyModel.getData().getDay7()));
                                    hyperModels.add(new HyperModel("Day10", keyModel.getData().getDay10()));
                                    hyperModels.add(new HyperModel("Day14", keyModel.getData().getDay14()));


                                    LeadsRecycler(hyperModels);


                                }

                            } else {
                                Toast.makeText(ActivityHyperCareLeads.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                mBinding.progress.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipeRefresh.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            mBinding.progress.setVisibility(View.GONE);
                            Toast.makeText(ActivityHyperCareLeads.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<DeviceCareLeadsModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipeRefresh.setRefreshing(false);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityHyperCareLeads.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public class Data {

        @SerializedName("day_1")
        @Expose
        private List<Day1> day1;
        @SerializedName("day_3")
        @Expose
        private List<Day1> day3;
        @SerializedName("day_7")
        @Expose
        private List<Day1> day7;
        @SerializedName("day_10")
        @Expose
        private List<Day1> day10;
        @SerializedName("day_14")
        @Expose
        private List<Day1> day14;

        public List<Day1> getDay1() {
            return day1;
        }

        public void setDay1(List<Day1> day1) {
            this.day1 = day1;
        }

        public List<Day1> getDay3() {
            return day3;
        }

        public void setDay3(List<Day1> day3) {
            this.day3 = day3;
        }

        public List<Day1> getDay7() {
            return day7;
        }

        public void setDay7(List<Day1> day7) {
            this.day7 = day7;
        }

        public List<Day1> getDay10() {
            return day10;
        }

        public void setDay10(List<Day1> day10) {
            this.day10 = day10;
        }

        public List<Day1> getDay14() {
            return day14;
        }

        public void setDay14(List<Day1> day14) {
            this.day14 = day14;
        }

    }

    public class Day1 {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("lead_status")
        @Expose
        private String leadStatus;
        @SerializedName("opening_msg")
        @Expose
        private String openingMsg;
        @SerializedName("call1")
        @Expose
        private String call1;
        @SerializedName("call1_outcome")
        @Expose
        private String call1Outcome;
        @SerializedName("call2")
        @Expose
        private String call2;
        @SerializedName("call2_outcome")
        @Expose
        private String call2Outcome;
        @SerializedName("call3")
        @Expose
        private String call3;
        @SerializedName("call3_outcome")
        @Expose
        private String call3Outcome;
        @SerializedName("call4")
        @Expose
        private String call4;
        @SerializedName("call4_outcome")
        @Expose
        private String call4Outcome;
        @SerializedName("appointment_at")
        @Expose
        private String appointmentAt;
        @SerializedName("assigned_at")
        @Expose
        private String assignedAt;
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
        @SerializedName("number")
        @Expose
        private String number;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
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

        public String getLeadStatus() {
            return leadStatus;
        }

        public void setLeadStatus(String leadStatus) {
            this.leadStatus = leadStatus;
        }

        public String getOpeningMsg() {
            return openingMsg;
        }

        public void setOpeningMsg(String openingMsg) {
            this.openingMsg = openingMsg;
        }

        public String getCall1() {
            return call1;
        }

        public void setCall1(String call1) {
            this.call1 = call1;
        }

        public String getCall1Outcome() {
            return call1Outcome;
        }

        public void setCall1Outcome(String call1Outcome) {
            this.call1Outcome = call1Outcome;
        }

        public String getCall2() {
            return call2;
        }

        public void setCall2(String call2) {
            this.call2 = call2;
        }

        public String getCall2Outcome() {
            return call2Outcome;
        }

        public void setCall2Outcome(String call2Outcome) {
            this.call2Outcome = call2Outcome;
        }

        public String getCall3() {
            return call3;
        }

        public void setCall3(String call3) {
            this.call3 = call3;
        }

        public String getCall3Outcome() {
            return call3Outcome;
        }

        public void setCall3Outcome(String call3Outcome) {
            this.call3Outcome = call3Outcome;
        }

        public String getCall4() {
            return call4;
        }

        public void setCall4(String call4) {
            this.call4 = call4;
        }

        public String getCall4Outcome() {
            return call4Outcome;
        }

        public void setCall4Outcome(String call4Outcome) {
            this.call4Outcome = call4Outcome;
        }

        public String getAppointmentAt() {
            return appointmentAt;
        }

        public void setAppointmentAt(String appointmentAt) {
            this.appointmentAt = appointmentAt;
        }

        public String getAssignedAt() {
            return assignedAt;
        }

        public void setAssignedAt(String assignedAt) {
            this.assignedAt = assignedAt;
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

    }

    public class DeviceCareLeadsModel {

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


}