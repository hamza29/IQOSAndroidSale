package com.example.iqos.LeadsModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLeads extends AppCompatActivity {

    ActivityLeadsBinding mBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeadsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        mBinding.rvLeads.setVisibility(View.VISIBLE);

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



        getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));

        //LeadsRecycler();
            mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(mBinding.rvLeads.getVisibility() == View.VISIBLE) {
                        getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
                    }else{
                        getEcomLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));

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
        if(mBinding.rvLeads.getVisibility() == View.VISIBLE) {
            getLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));
        }else{
            getEcomLeads(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""));

        }
    }

    private void LeadsRecycler(List<Model.Lead> leads) {


        LeadsAdapter leadsAdapter;
        mBinding.rvLeads.setLayoutManager(new LinearLayoutManager(ActivityLeads.this, LinearLayoutManager.VERTICAL, false));
        leadsAdapter = new LeadsAdapter(ActivityLeads.this, leads);
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
                                        LeadsRecycler(keyModel.getData().getLeads());
                                    } else     if (keyModel.getData().getLeads().size() ==0){
                                        mBinding.rvLeads.setVisibility(View.GONE);
                                        LeadsRecycler(new ArrayList<>());
                                    }



                                }

                            } else {
                                mBinding.rvLeads.setVisibility(View.GONE);
                                LeadsRecycler(new ArrayList<>());

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