package com.example.iqos.LeadsModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.iqos.Constants;
import com.example.iqos.R;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityLeadsBinding;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLeads extends AppCompatActivity {

    ActivityLeadsBinding mBinding;
    SharedPreferences mSharedPreferences;
    String first_name;
    String last_name;
    String age;
    String email;
    String phone;
    String designation;
    String organization;
    String city;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeadsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        mBinding.rvLeads.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        mBinding.tvNormalLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.tvNormalLeads.setBackground(getDrawable(R.drawable.rounded_top_blue));
                mBinding.tvEcomLeads.setBackground(getDrawable(R.drawable.rounded_top_grey));
                mBinding.rvLeads.setVisibility(View.VISIBLE);
                mBinding.rvEcomLeads.setVisibility(View.GONE);
                getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
            }
        });  mBinding.tvEcomLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBinding.tvEcomLeads.setBackground(getDrawable(R.drawable.rounded_top_blue));
                mBinding.tvNormalLeads.setBackground(getDrawable(R.drawable.rounded_top_grey));
                mBinding.rvLeads.setVisibility(View.GONE);
                mBinding.rvEcomLeads.setVisibility(View.VISIBLE);

                getEcomLeads (mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));

            }
        });

        if(!type.isEmpty() && type.equals("sales")){
            getSales(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
            mBinding.fabAddLead.setVisibility(View.GONE);
            mBinding.tvEcomLeads.setVisibility(View.GONE);
            mBinding.tvLeads.setText("Sales");
            mBinding.tvNormalLeads.setText("My Sales");
        }else{
            getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
            mBinding.tvEcomLeads.setVisibility(View.VISIBLE);
            mBinding.tvLeads.setText("Leads");
            mBinding.tvNormalLeads.setText("My Leads");
            if(mSharedPreferences.getString(Constants.ROLE,"").equalsIgnoreCase("sales")){
                mBinding.tvEcomLeads.setVisibility(View.GONE);
                mBinding.fabAddLead.setVisibility(View.VISIBLE);
                mBinding.rvLeads.setVisibility(View.VISIBLE);
            }else {
                mBinding.fabAddLead.setVisibility(View.GONE);
                mBinding.tvEcomLeads.setVisibility(View.VISIBLE);
            }

        }

        mBinding.fabAddLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.swipeRefresh.setEnabled(false);
                mBinding.svForm.setVisibility(View.VISIBLE);
                mBinding.fabAddLead.setVisibility(View.GONE);
            }
        }); mBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                first_name = mBinding.etFirstName.getText().toString();
                last_name = mBinding.etLastName.getText().toString();
                email = mBinding.etLeadEmail.getText().toString();
                phone = mBinding.etLeadPhone.getText().toString();
                designation = mBinding.etDesignation.getText().toString();
                organization = mBinding.etOrganization.getText().toString();
                age = mBinding.etAge.getText().toString();
                city = mBinding.etCity.getText().toString();
                if(first_name.isEmpty()){
                    mBinding.etFirstName.requestFocus();
                    mBinding.etFirstName.setBackground(getDrawable(R.drawable.rounded_corner_red));
                } else{
                    mBinding.etFirstName.setBackground(getDrawable(R.drawable.rounded_corner_meeting));
                } 
                if(last_name.isEmpty()){
                    mBinding.etLastName.requestFocus();
                    mBinding.etLastName.setBackground(getDrawable(R.drawable.rounded_corner_red));
                } else{
                    mBinding.etLastName.setBackground(getDrawable(R.drawable.rounded_corner_meeting));
                } 
                if(email.isEmpty()){
                    mBinding.etLeadEmail.requestFocus();
                    mBinding.etLeadEmail.setBackground(getDrawable(R.drawable.rounded_corner_red));
                } else{
                    mBinding.etLeadPhone.setBackground(getDrawable(R.drawable.rounded_corner_meeting));
                }
                if(phone.isEmpty()){
                    mBinding.etLeadPhone.requestFocus();
                    mBinding.etLeadPhone.setBackground(getDrawable(R.drawable.rounded_corner_red));
                } else{
                    mBinding.etLeadPhone.setBackground(getDrawable(R.drawable.rounded_corner_meeting));

                } 
                if(designation.isEmpty()){
                    mBinding.etDesignation.requestFocus();
                    mBinding.etDesignation.setBackground(getDrawable(R.drawable.rounded_corner_red));
                } else{
                    mBinding.etDesignation.setBackground(getDrawable(R.drawable.rounded_corner_meeting));

                } 
                if(organization.isEmpty()){
                    mBinding.etOrganization.requestFocus();
                    mBinding.etOrganization.setBackground(getDrawable(R.drawable.rounded_corner_red));
                } else{
                    mBinding.etOrganization.setBackground(getDrawable(R.drawable.rounded_corner_meeting));

                } 
                if(age.isEmpty()){
                    mBinding.etAge.requestFocus();
                    mBinding.etAge.setBackground(getDrawable(R.drawable.rounded_corner_red));
                } else{
                    mBinding.etAge.setBackground(getDrawable(R.drawable.rounded_corner_meeting));

                } 
                if(city.isEmpty()){
                    mBinding.etCity.requestFocus();
                    mBinding.etCity.setBackground(getDrawable(R.drawable.rounded_corner_red));
                } else{
                    mBinding.etCity.setBackground(getDrawable(R.drawable.rounded_corner_meeting));

                }

                if(!phone.isEmpty() && !first_name.isEmpty() && !city.isEmpty() && !email.isEmpty() && !last_name.isEmpty() && !designation.isEmpty() && !organization.isEmpty() && !city.isEmpty() && !age.isEmpty() ){
                    createLead(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
                }
            }
        });
        Log.e("visibilityleads1", ""+(mBinding.rvLeads.getVisibility()==View.VISIBLE));



        //LeadsRecycler();
            mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Log.e("sales", type);
                    Log.e("visibilityleads", ""+(mBinding.rvLeads.getVisibility()==View.VISIBLE));

                    if(!type.isEmpty() && type.equals("sales")){
                        getSales(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
                    }else{
                        if (mBinding.rvLeads.getVisibility() == View.VISIBLE) {
                            getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));
                        } else {
                            getEcomLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));
                        }
                    }

                }
            });

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
        if(!type.isEmpty() && type.equals("sales")){
            getSales(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
        }else{
            if (mBinding.rvLeads.getVisibility() == View.VISIBLE) {
                getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));
            } else {
                getEcomLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));
            }
        }
    }

    private void LeadsRecycler(List<Model.Lead> leads,String leadType) {


        LeadsAdapter leadsAdapter;
        mBinding.rvLeads.setLayoutManager(new LinearLayoutManager(ActivityLeads.this, LinearLayoutManager.VERTICAL, false));
        leadsAdapter = new LeadsAdapter(ActivityLeads.this, leads, leadType);
        mBinding.rvLeads.setAdapter(leadsAdapter);
    }
    private void LeadsEcomRecycler(List<Lead> leads) {


        EcomLeadsAdapter leadsAdapter;
        mBinding.rvEcomLeads.setLayoutManager(new LinearLayoutManager(ActivityLeads.this, LinearLayoutManager.VERTICAL, false));
        leadsAdapter = new EcomLeadsAdapter(ActivityLeads.this, leads);
        mBinding.rvEcomLeads.setAdapter(leadsAdapter);
    }


    public void getLeads(String token) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityLeads.this).create(ApiService.class);
        Call<Model.GetLeadsModel> call = apiService.getLeads("application/json",token,"");
        call.enqueue(new Callback<Model.GetLeadsModel>() {
            @Override
            public void onResponse(Call<Model.GetLeadsModel> call, Response<Model.GetLeadsModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.GetLeadsModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipeRefresh.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);

                                    if (keyModel.getData().getLeads().size() >0){
                                        mBinding.rvLeads.setVisibility(View.VISIBLE);
                                        LeadsRecycler(keyModel.getData().getLeads(), "leads");
                                    } else     if (keyModel.getData().getLeads().size() ==0){
                                        mBinding.rvLeads.setVisibility(View.GONE);
                                        LeadsRecycler(new ArrayList<>(), "leads");
                                    }



                                }

                            } else {
                                mBinding.rvLeads.setVisibility(View.GONE);
                                LeadsRecycler(new ArrayList<>(), "leads");

                                //                                Toast.makeText(ActivityLeads.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityLeads.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.GetLeadsModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipeRefresh.setRefreshing(false);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityLeads.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
    public void getSales(String token) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityLeads.this).create(ApiService.class);
        Call<Model.GetLeadsModel> call = apiService.getSales("application/json",token,"");
        call.enqueue(new Callback<Model.GetLeadsModel>() {
            @Override
            public void onResponse(Call<Model.GetLeadsModel> call, Response<Model.GetLeadsModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.GetLeadsModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipeRefresh.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);

                                    if (keyModel.getData().getLeads().size() >0){
                                        mBinding.rvLeads.setVisibility(View.VISIBLE);
                                        LeadsRecycler(keyModel.getData().getLeads(), "sales");
                                    } else     if (keyModel.getData().getLeads().size() ==0){
                                        mBinding.rvLeads.setVisibility(View.GONE);
                                        LeadsRecycler(new ArrayList<>(), "sales");
                                    }



                                }

                            } else {
                                mBinding.rvLeads.setVisibility(View.GONE);
                                LeadsRecycler(new ArrayList<>(), "sales");

                                //                                Toast.makeText(ActivityLeads.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityLeads.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.GetLeadsModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipeRefresh.setRefreshing(false);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityLeads.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
    public void getEcomLeads(String token) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityLeads.this).create(ApiService.class);
        Call<EcommLeads> call = apiService.getEcomLeads("application/json",token );
        call.enqueue(new Callback<EcommLeads>() {
            @Override
            public void onResponse(Call<EcommLeads> call, Response<EcommLeads> response) {
                ///// Progress Dialog  Dismiss////////////
                final EcommLeads keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipeRefresh.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);

                                    if (keyModel.getData().getLeads().size() >0){
                                        mBinding.rvEcomLeads.setVisibility(View.VISIBLE);
                                        LeadsEcomRecycler(keyModel.getData().getLeads());
                                    } else     if (keyModel.getData().getLeads().size() ==0){
                                        mBinding.rvEcomLeads.setVisibility(View.GONE);
                                        LeadsEcomRecycler(new ArrayList<>());
                                    }



                                }else{
                                    mBinding.progress.setVisibility(View.GONE);
                                    mBinding.rvEcomLeads.setVisibility(View.GONE);
                                    LeadsEcomRecycler(new ArrayList<>());
                                }

                            } else {
                                mBinding.rvEcomLeads.setVisibility(View.GONE);
                                LeadsEcomRecycler(new ArrayList<>());

                                //                                Toast.makeText(ActivityLeads.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityLeads.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<EcommLeads> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipeRefresh.setRefreshing(false);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityLeads.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public void createLead(String token) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityLeads.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("first_name", first_name);
        builder.addFormDataPart("last_name", last_name);
        builder.addFormDataPart("email", email);
        builder.addFormDataPart("phone", phone);
        builder.addFormDataPart("designation", designation);
        builder.addFormDataPart("organization", organization);
        builder.addFormDataPart("age_group", age);
        builder.addFormDataPart("city", city);
        RequestBody requestBody = builder.build();
        Call<Model.LeadData> call = apiService.createLead("application/json",token, requestBody);
        call.enqueue(new Callback<Model.LeadData>() {
            @Override
            public void onResponse(Call<Model.LeadData> call, Response<Model.LeadData> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.LeadData keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (keyModel.getStatus().equals("1")) {
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                mBinding.swipeRefresh.setEnabled(true);
                                mBinding.svForm.setVisibility(View.GONE);
                                mBinding.fabAddLead.setVisibility(View.VISIBLE);
                                mBinding.progress.setVisibility(View.GONE);
                                Toast.makeText(ActivityLeads.this, "Lead Saved Successfully", Toast.LENGTH_SHORT).show();
                                if(!type.isEmpty() && type.equals("sales")){
                                    getSales(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
                                }else {
                                    getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""));
                                }
                            }else{
                                mBinding.swipeRefresh.setRefreshing(false);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if(keyModel.getMessage().contains("email")){
                                    mBinding.etLeadEmail.requestFocus();
                                    mBinding.etLeadEmail.setBackground(getDrawable(R.drawable.rounded_corner_red));
                                }
                                if(keyModel.getMessage().contains("phone")){
                                    mBinding.etLeadPhone.requestFocus();
                                    mBinding.etLeadPhone.setBackground(getDrawable(R.drawable.rounded_corner_red));
                                }
                                mBinding.progress.setVisibility(View.GONE);
                                Toast.makeText(ActivityLeads.this, keyModel.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(ActivityLeads.this, keyModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.LeadData> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.swipeRefresh.setRefreshing(false);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        Toast.makeText(ActivityLeads.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
    public class Data {

        @SerializedName("leads")
        @Expose
        private List<Lead> leads;

        public List<Lead> getLeads() {
            return leads;
        }

        public void setLeads(List<Lead> leads) {
            this.leads = leads;
        }

    }
    public class EcommLeads {

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
        @SerializedName("ecommerce_registration")
        @Expose
        private String ecommerceRegistration;
        @SerializedName("appointment_at")
        @Expose
        private String appointmentAt;
        @SerializedName("assigned_at")
        @Expose
        private String assignedAt;

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

        public String getEcommerceRegistration() {
            return ecommerceRegistration;
        }

        public void setEcommerceRegistration(String ecommerceRegistration) {
            this.ecommerceRegistration = ecommerceRegistration;
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

    }


}