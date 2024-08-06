package com.example.iqos.LeadsModule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.iqos.AppointmentsModule.AppointmentBookingDetailActivity;
import com.example.iqos.Constants;
import com.example.iqos.R;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityLeadsDetailBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLeadsDetail extends AppCompatActivity {

    public static Activity ivityLeads;
    ActivityLeadsDetailBinding mBinding;
    SharedPreferences mSharedPreferences;
    String lead_id = "";
    String tvCallStatus = "Select";
    Dialog dialog;
    ArrayList<String> leadStatus = new ArrayList<>();
    ArrayList<String> call_outcomes = new ArrayList<>();
    ArrayList<String> call2_outcomes = new ArrayList<>();
    ArrayList<String> call3_outcomes = new ArrayList<>();
    ArrayList<String> call4_outcomes = new ArrayList<>();
    String enableCallOutcome = "false";
    String lead_status = "";
    String opening_msg = "";
    String call1 = "";
    String call1_outcome = "";
    String call2 = "";
    String call2_outcome = "";
    String call3 = "";
    String call4 = "";
    String call3_outcome = "";
    String call4_outcome = "";
    String email_message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeadsDetailBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        ivityLeads = this;
        lead_id = getIntent().getStringExtra("KEY_LEAD_ID");
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        enableCallOutcome = "false";


        leadStatus.add("Cold");
        leadStatus.add("connected");
        leadStatus.add("scheduled");
        leadStatus.add("dead");
        call_outcomes.clear();
        call_outcomes.add("Select");
        call_outcomes.add("scheduled");
        call_outcomes.add("not-interested");
        call_outcomes.add("not-verified");
        call_outcomes.add("not-connect");
        call_outcomes.add("call back later");
        call2_outcomes.clear();

        call2_outcomes.add("Select");
        call2_outcomes.add("scheduled");
        call2_outcomes.add("not-interested");
        call2_outcomes.add("not-verified");
        call2_outcomes.add("not-connect");
        call2_outcomes.add("call back later");
        call3_outcomes.clear();

        call3_outcomes.add("Select");
        call3_outcomes.add("scheduled");
        call3_outcomes.add("not-interested");
        call3_outcomes.add("not-verified");
        call3_outcomes.add("not-connect");
        call3_outcomes.add("call back later");
        call4_outcomes.clear();

        call4_outcomes.add("Select");
        call4_outcomes.add("scheduled");
        call4_outcomes.add("not-interested");
        call4_outcomes.add("not-verified");
        call4_outcomes.add("not-connect");
        call4_outcomes.add("call back later");


        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBinding.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.tvEdit.getText().toString().equalsIgnoreCase("Edit")) {
                    mBinding.tvfirstName.setEnabled(true);
                    mBinding.tvlastName.setEnabled(true);
                    mBinding.tvCOmpany.setEnabled(true);
                    mBinding.tvDesignation.setEnabled(true);
                    mBinding.tvAge.setEnabled(true);
                    mBinding.tvPhoneNumber.setEnabled(true);
                    mBinding.tvEmail.setEnabled(true);
                    mBinding.tvEdit.setText("Update");
                } else {

                    mBinding.tvfirstName.setEnabled(false);
                    mBinding.tvlastName.setEnabled(false);
                    mBinding.tvCOmpany.setEnabled(false);
                    mBinding.tvDesignation.setEnabled(false);
                    mBinding.tvAge.setEnabled(false);
                    mBinding.tvPhoneNumber.setEnabled(false);
                    mBinding.tvEmail.setEnabled(false);
                    mBinding.tvEdit.setText("Edit");
                    updateLead(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                            mBinding.tvfirstName.getText().toString(),
                            mBinding.tvlastName.getText().toString(),
                            mBinding.tvCOmpany.getText().toString(),
                            mBinding.tvDesignation.getText().toString(),
                            mBinding.tvAge.getText().toString(),
                            mBinding.tvPhoneNumber.getText().toString(),
                            mBinding.tvEmail.getText().toString());
                }
            }
        });


        getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id);

        mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id);

            }
        });
    }

    private void setLeadStatusSpinner(String status) {


        ArrayList<String> newLeads = new ArrayList<>();
        for (int i = 0; i < leadStatus.size(); i++) {
            if (status.equalsIgnoreCase(leadStatus.get(i))) {
                newLeads.add(0, "" + leadStatus.get(i));
            } else {
                newLeads.add("" + leadStatus.get(i));

            }


        }
        Log.e("TGED", "lead_status-> " + lead_status);
        mBinding.spinnerLeadStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                lead_status = newLeads.get(position);
                Log.e("TGED", "lead_status-> " + lead_status);
                Log.e("TGED", "status-> " + status);
                if (!leadStatus.toString().equalsIgnoreCase("Select")) {

                    if (!status.equalsIgnoreCase("" + lead_status)) {
                        updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                                lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);
                    }
                }

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
        mBinding.spinnerLeadStatus.setAdapter(ad);
    }

    private void setCall2StatusSpinner(String status) {


        ArrayList<String> callOutcomes = new ArrayList<>();
        for (int i = 0; i < call2_outcomes.size(); i++) {
            if (call2_outcomes.get(i).toString().equalsIgnoreCase(status)) {
                callOutcomes.add(0, "" + call2_outcomes.get(i));
            } else {
                callOutcomes.add("" + call2_outcomes.get(i));

            }


        }


        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                callOutcomes);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mBinding.spinnerCall2Status.setAdapter(ad);
        mBinding.spinnerCall2Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                call2_outcome = callOutcomes.get(position);
                Log.e("TGED", "HERE IS THE STATUS-> " + status);
                Log.e("TGED", "call2_outcome-> " + call2_outcome);
                if (!status.equalsIgnoreCase("" + call2_outcome)) {
                    Toast.makeText(ActivityLeadsDetail.this, "Call2 Outcome", Toast.LENGTH_SHORT).show();
                    updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                            lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setCall3StatusSpinner(String status) {

        Log.e("TGED", "CALL3outcome" + status);
        ArrayList<String> callOutcomes = new ArrayList<>();
        for (int i = 0; i < call3_outcomes.size(); i++) {
            if (call3_outcomes.get(i).toString().equalsIgnoreCase(status)) {
                callOutcomes.add(0, "" + call3_outcomes.get(i));
            } else {
                callOutcomes.add("" + call3_outcomes.get(i));

            }


        }


        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                callOutcomes);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mBinding.spinnerCall3Status.setAdapter(ad);
        mBinding.spinnerCall3Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                call3_outcome = callOutcomes.get(position);
                Log.e("TGED", "HERE IS THE STATUS-> " + status);
                Log.e("TGED", "call3_outcome-> " + call3_outcome);
                if (!status.equalsIgnoreCase("" + call3_outcome)) {
                    Toast.makeText(ActivityLeadsDetail.this, "Call3 Outcome", Toast.LENGTH_SHORT).show();
                    updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                            lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setCall4StatusSpinner(String status) {

        Log.e("TGED", "CALL3outcome" + status);
        ArrayList<String> callOutcomes = new ArrayList<>();
        for (int i = 0; i < call4_outcomes.size(); i++) {
            if (call4_outcomes.get(i).toString().equalsIgnoreCase(status)) {
                callOutcomes.add(0, "" + call4_outcomes.get(i));
            } else {
                callOutcomes.add("" + call4_outcomes.get(i));

            }


        }


        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                callOutcomes);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mBinding.spinnerCall4Status.setAdapter(ad);
        mBinding.spinnerCall4Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                call4_outcome = callOutcomes.get(position);
                Log.e("TGED", "HERE IS THE STATUS-> " + status);
                Log.e("TGED", "call3_outcome-> " + call4_outcome);
                if (!status.equalsIgnoreCase("" + call4_outcome)) {
                    Toast.makeText(ActivityLeadsDetail.this, "Call3 Outcome", Toast.LENGTH_SHORT).show();
                    updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                            lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setCallStatusSpinner(String status) {


        ArrayList<String> callOutcomes = new ArrayList<>();
        for (int i = 0; i < call_outcomes.size(); i++) {
            if (call_outcomes.get(i).toString().equalsIgnoreCase(status)) {
                callOutcomes.add(0, "" + call_outcomes.get(i));
            } else {
                callOutcomes.add("" + call_outcomes.get(i));

            }


        }


        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                callOutcomes);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mBinding.spinnerCallStatus.setAdapter(ad);
        mBinding.spinnerCallStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                call1_outcome = callOutcomes.get(position);

                if (!status.equalsIgnoreCase("" + call1_outcome)) {
                    Toast.makeText(ActivityLeadsDetail.this, "Call1 Outcome", Toast.LENGTH_SHORT).show();
                    updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                            lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    public void showEmailDialog(String title, String message, String number, String email) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_text_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnRefresh = dialog.findViewById(R.id.btnDone);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        EditText tvMessage = dialog.findViewById(R.id.tvMessage);

        tvTitle.setText("" + title);
        tvMessage.setText("" + Html.fromHtml(message));

        ImageView ivBack = dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                email_message = currentDate;

                Uri emailUri = Uri.parse("mailto:" + email);

                // Create an Intent with the ACTION_VIEW action and the mailto: Uri
                Intent emailIntent = new Intent(Intent.ACTION_VIEW, emailUri);

                // Check if there's an app that can handle the email Intent
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                } else {
                    startActivity(emailIntent);
                    Toast.makeText(ActivityLeadsDetail.this, "ERROE", Toast.LENGTH_SHORT).show();                    // You can show a toast or display a dialog to inform the user
                }
                updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);


            }
        });


        dialog.show();
    }

    public void showCDialog(String title, String message, String number, String email) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_text_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnRefresh = dialog.findViewById(R.id.btnDone);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        EditText tvMessage = dialog.findViewById(R.id.tvMessage);

        tvTitle.setText("" + title);
        tvMessage.setText("" + Html.fromHtml(message));

        ImageView ivBack = dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                opening_msg = currentDate;
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "" + number);
                smsIntent.putExtra("sms_body", "" + tvMessage.getText().toString());
                startActivity(smsIntent);

                updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                        lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);


            }
        });


        dialog.show();
    }


    public void getLeadsDetails(String token, String lead_id) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityLeadsDetail.this).create(ApiService.class);
        Call<Model.LeadDetailsModel> call = apiService.getLeadsDetails("application/json", token, lead_id);
        call.enqueue(new Callback<Model.LeadDetailsModel>() {
            @Override
            public void onResponse(Call<Model.LeadDetailsModel> call, Response<Model.LeadDetailsModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.LeadDetailsModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.swipeRefresh.setRefreshing(false);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);


                                    if (keyModel.getData().getLead().getFirstName() != null) {
                                        mBinding.tvfirstName.setText("" + keyModel.getData().getLead().getFirstName());
                                    }
//                                    if (keyModel.getData().getLead().getLastName() != null) {
//                                        mBinding.tvlastName.setText("" + keyModel.getData().getLead().getLastName());
//                                    }

                                    if (keyModel.getData().getLead().getCompany() != null) {
                                        mBinding.tvCOmpany.setText("" + keyModel.getData().getLead().getCompany());
                                    }
                                    if (keyModel.getData().getLead().getDesignation() != null) {
                                        mBinding.tvDesignation.setText("" + keyModel.getData().getLead().getDesignation());
                                    }
                                    if (keyModel.getData().getLead().getAgeGroup() != null) {
                                        mBinding.tvAge.setText("" + keyModel.getData().getLead().getAgeGroup());
                                    }

                                    if (keyModel.getData().getLead().getEmail() != null) {
                                        mBinding.tvEmail.setText("" + keyModel.getData().getLead().getEmail());
                                    }
                                    if (keyModel.getData().getLead().getNumber() != null) {
                                        mBinding.tvPhoneNumber.setText("" + keyModel.getData().getLead().getNumber());
                                    }


                                    mBinding.tvBookAppoint.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLeadsDetail.this);
                                            builder.setTitle("Confirmation"); // Set the dialog title
                                            builder.setMessage("Are you sure you want to continue booking an appointment?"); // Set the dialog message

                                            // Set up the buttons
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(ActivityLeadsDetail.this, AppointmentBookingDetailActivity.class);
                                                    intent.putExtra("lead_id", "" + keyModel.getData().getLead().getId());
                                                    intent.putExtra("name", "" + keyModel.getData().getLead().getFirstName() + " " + keyModel.getData().getLead().getLastName());
                                                    startActivity(intent);
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
                                    call1 = keyModel.getData().getLead().getCall1();
                                    call1_outcome = keyModel.getData().getLead().getCall1Outcome();
                                    call2 = keyModel.getData().getLead().getCall2();
                                    call2_outcome = keyModel.getData().getLead().getCall2Outcome();
                                    call3 = keyModel.getData().getLead().getCall3();
                                    call3_outcome = keyModel.getData().getLead().getCall3Outcome();
                                    call4 = keyModel.getData().getLead().getCall4();
                                    call4_outcome = keyModel.getData().getLead().getCall4Outcome();


                                    if (keyModel.getData().getLead().getLeadStatus() != null) {
                                        lead_status = keyModel.getData().getLead().getLeadStatus();
                                    }


                                    if (keyModel.getData().getLead().getOpeningMsg() != null
                                            && !keyModel.getData().getLead().getOpeningMsg().isEmpty()) {
                                        opening_msg = keyModel.getData().getLead().getOpeningMsg();

                                        mBinding.btnEmail.setEnabled(true);
                                        mBinding.tvCall.setEnabled(true);
                                        mBinding.llCallStatus.setEnabled(true);
                                        mBinding.llCall2Status.setEnabled(true);
                                        mBinding.llCall3Status.setEnabled(true);
                                        mBinding.llCall4Status.setEnabled(true);
                                        mBinding.spinnerCall4Status.setEnabled(true);
                                        mBinding.spinnerCall3Status.setEnabled(true);
                                        mBinding.spinnerCall2Status.setEnabled(true);
                                        mBinding.spinnerCallStatus.setEnabled(true);
                                    } else {
                                        mBinding.btnEmail.setEnabled(false);
                                        mBinding.tvCall.setEnabled(false);
                                        mBinding.llCallStatus.setEnabled(false);
                                        mBinding.llCall2Status.setEnabled(false);
                                        mBinding.llCall3Status.setEnabled(false);
                                        mBinding.llCall4Status.setEnabled(false);
                                        mBinding.spinnerCall4Status.setEnabled(false);
                                        mBinding.spinnerCall3Status.setEnabled(false);
                                        mBinding.spinnerCall2Status.setEnabled(false);
                                        mBinding.spinnerCallStatus.setEnabled(false);
                                    }

                                    if (keyModel.getData().getLead().getEmail_message() != null && !keyModel.getData().getLead().getEmail_message().isEmpty()) {
                                        email_message = keyModel.getData().getLead().getEmail_message();

                                    }
                                    mBinding.btnEmail.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLeadsDetail.this);
                                            builder.setTitle("Confirmation"); // Set the dialog title
                                            builder.setMessage("Are you sure you want to continue sending Email?"); // Set the dialog message

                                            // Set up the buttons
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    showEmailDialog("Email", "", keyModel.getData().getLead().getNumber(), keyModel.getData().getLead().getEmail());
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


                                    mBinding.tvMessage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLeadsDetail.this);
                                            builder.setTitle("Confirmation"); // Set the dialog title
                                            builder.setMessage("Are you sure you want to continue sending Message?"); // Set the dialog message

                                            // Set up the buttons
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    showCDialog("Open Message", keyModel.getData().getConfiguration().getOpen_message(), keyModel.getData().getLead().getNumber(), keyModel.getData().getLead().getEmail());
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


//                                                if (keyModel.getData().getLead().getOpeningMsg() == null ) {


//                                                    }else{
//                                                    Toast.makeText(ActivityLeadsDetail.this, "You have already submitted open message", Toast.LENGTH_SHORT).show();
//
//                                                }
                                        }
                                    });
                                    mBinding.tvLastAction.setText(keyModel.getData().getLead().getLastAction().getType());
                                    mBinding.tvNextAction.setText(keyModel.getData().getLead().getNextAction().getType());
//


                                    Log.e("TGED", "call3_outcome-> " + call3_outcome);
                                    Log.e("TGED", "call2_outcome-> " + call2_outcome);
                                    Log.e("TGED", "call1_outcome-> " + call1_outcome);


                                    if (call1_outcome == null) {
                                        call1_outcome = "Select";
                                        setCallStatusSpinner(call1_outcome);
                                    } else {
                                        setCallStatusSpinner(call1_outcome);

                                    }
                                    if (call3_outcome == null) {
                                        call3_outcome = "Select";
                                        setCall3StatusSpinner(call3_outcome);
                                    } else {
                                        setCall3StatusSpinner(call3_outcome);

                                    }
                                    if (call4_outcome == null) {
                                        call4_outcome = "Select";
                                        setCall4StatusSpinner(call4_outcome);
                                    } else {
                                        setCall4StatusSpinner(call4_outcome);

                                    }
                                    if (call2_outcome == null) {
                                        call2_outcome = "Select";
                                        setCall2StatusSpinner(call2_outcome);
                                    } else {
                                        setCall2StatusSpinner(call2_outcome);

                                    }


//                                    Log.e("TGED","LEAD STATUS=> "+ keyModel.getData().getLead().getLeadStatus());
                                    setLeadStatusSpinner(lead_status);


                                    mBinding.tvCall.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLeadsDetail.this);
                                            builder.setTitle("Confirmation"); // Set the dialog title
                                            builder.setMessage("Are you sure you want to continue calling the Lead?"); // Set the dialog message

                                            // Set up the buttons
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {


                                                    if (keyModel.getData().getLead().getLastAction().getType().equalsIgnoreCase("N/A")) {
                                                        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                                        call1 = currentDate;
                                                        mBinding.tvCall.setEnabled(true);


                                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                                        intent.setData(Uri.parse("tel:" + keyModel.getData().getLead().getNumber()));
                                                        startActivity(intent);
                                                        updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                                                                lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);

                                                    } else if (keyModel.getData().getLead().getNextAction().getType().equalsIgnoreCase("call 1")) {

                                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                                        intent.setData(Uri.parse("tel:" + keyModel.getData().getLead().getNumber()));
                                                        startActivity(intent);
                                                        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                                        call1 = currentDate;
                                                        mBinding.tvCall.setEnabled(true);
                                                        DateTimeFormatter formatter = null;
                                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                                            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                                        }
//                                                        if(keyModel.getData().getLead().getNextAction().getTime().equalsIgnoreCase("N/A"))
                                                        String date = keyModel.getData().getLead().getNextAction().getTime();

                                                        LocalDateTime dateTime = null;
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            dateTime = LocalDateTime.parse(date, formatter);
                                                        }
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            if (dateTime.toLocalDate().equals(LocalDate.now())) {
                                                                updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                                                                        lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);

                                                            }

                                                        }

                                                    } else if (keyModel.getData().getLead().getNextAction().getType().equalsIgnoreCase("call 2")) {

                                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                                        intent.setData(Uri.parse("tel:" + keyModel.getData().getLead().getNumber()));
                                                        startActivity(intent);
                                                        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                                        call2 = currentDate;
                                                        mBinding.tvCall.setEnabled(true);
                                                        DateTimeFormatter formatter = null;
                                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                                            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                                        }
                                                        String date = keyModel.getData().getLead().getNextAction().getTime();

                                                        LocalDateTime dateTime = null;
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            dateTime = LocalDateTime.parse(date, formatter);
                                                        }
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            if (dateTime.toLocalDate().equals(LocalDate.now())) {
                                                                updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                                                                        lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);

                                                            }

                                                        }

                                                    } else if (keyModel.getData().getLead().getNextAction().getType().equalsIgnoreCase("call 3")) {

                                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                                        intent.setData(Uri.parse("tel:" + keyModel.getData().getLead().getNumber()));
                                                        startActivity(intent);
                                                        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                                        call3 = currentDate;
                                                        mBinding.tvCall.setEnabled(true);


                                                        updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                                                                lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);
                                                        DateTimeFormatter formatter = null;
                                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                                            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                                        }
                                                        String date = keyModel.getData().getLead().getNextAction().getTime();

                                                        LocalDateTime dateTime = null;
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            dateTime = LocalDateTime.parse(date, formatter);
                                                        }
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            if (dateTime.toLocalDate().equals(LocalDate.now())) {
                                                                updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                                                                        lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);

                                                            }

                                                        }
                                                    } else if (keyModel.getData().getLead().getNextAction().getType().equalsIgnoreCase("call 4")) {

                                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                                        intent.setData(Uri.parse("tel:" + keyModel.getData().getLead().getNumber()));
                                                        startActivity(intent);
                                                        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                                        call4 = currentDate;
                                                        mBinding.tvCall.setEnabled(true);


                                                        DateTimeFormatter formatter = null;
                                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                                            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                                        }
                                                        String date = keyModel.getData().getLead().getNextAction().getTime();

                                                        LocalDateTime dateTime = null;
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            dateTime = LocalDateTime.parse(date, formatter);
                                                        }
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            if (dateTime.toLocalDate().equals(LocalDate.now())) {
                                                                updateLeadAndCallsStatus(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id,
                                                                        lead_status, opening_msg, call1, call1_outcome, call2, call2_outcome, call3, call3_outcome, call4, call4_outcome, email_message);

                                                            }

                                                        }
                                                    } else if (keyModel.getData().getLead().getNextAction().getType().equalsIgnoreCase("appointed")) {
                                                        Toast.makeText(ActivityLeadsDetail.this, "You have completed", Toast.LENGTH_SHORT).show();
                                                        mBinding.tvCall.setEnabled(false);
                                                    }


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

                            } else {
                                Toast.makeText(ActivityLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                mBinding.progress.setVisibility(View.GONE);

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            mBinding.swipeRefresh.setRefreshing(false);
                            mBinding.progress.setVisibility(View.GONE);
                            Toast.makeText(ActivityLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Model.LeadDetailsModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        mBinding.swipeRefresh.setRefreshing(false);
                        Toast.makeText(ActivityLeadsDetail.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    public void updateLeadAndCallsStatus(String token, String id, String lead_status,
                                         String opening_msg, String call1,
                                         String call1_outcome, String call2,
                                         String call2_outcome, String call3,
                                         String call3_outcome, String call4, String call4_outcome,
                                         String email_message) {
        mBinding.progress.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(ActivityLeadsDetail.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);
        builder.addFormDataPart("opening_msg", opening_msg);
        if (call1 != null) {
            builder.addFormDataPart("call1", call1);
        }
        if (call1_outcome != null) {
            builder.addFormDataPart("call1_outcome", call1_outcome);
        }
        if (call2 != null) {
            builder.addFormDataPart("call2", call2);
        }
        if (call2_outcome != null) {
            builder.addFormDataPart("call2_outcome", call2_outcome);
        }
        if (call3 != null) {
            builder.addFormDataPart("call3", call3);
        }
        if (call3_outcome != null) {
            builder.addFormDataPart("call3_outcome", call3_outcome);
        }
        if (call4 != null) {
            builder.addFormDataPart("call4", call4);
        }
        if (call4_outcome != null) {
            builder.addFormDataPart("call4_outcome", call4_outcome);
        }
        if (email_message != null) {
            builder.addFormDataPart("email_message", email_message);
        }
        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            mBinding.progress.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id);

                                Toast.makeText(ActivityLeadsDetail.this, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(ActivityLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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

    public void updateLead(String token, String id, String fname,
                           String lname, String tvCOmpany,
                           String tvDesignation, String tvAge,
                           String tvPhoneNumber, String tvEmail) {
        mBinding.progress.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(ActivityLeadsDetail.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        if (fname != null) {
            builder.addFormDataPart("f_name", fname);
        }
        if (lname != null) {
            builder.addFormDataPart("l_name", lname);
        }
        if (tvCOmpany != null) {
            builder.addFormDataPart("company", tvCOmpany);
        }
        if (tvDesignation != null) {
            builder.addFormDataPart("designation", tvDesignation);
        }
        if (tvAge != null) {
            builder.addFormDataPart("age", tvAge);
        }
        if (tvPhoneNumber != null) {
            builder.addFormDataPart("phone_no", tvPhoneNumber);
        }
        if (tvEmail != null) {
            builder.addFormDataPart("email", tvEmail);
        }
        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateLead(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            mBinding.progress.setVisibility(View.GONE);

                            if (listofhome.getStatus().equals("1")) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN, ""), lead_id);

                                Toast.makeText(ActivityLeadsDetail.this, "" + listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(ActivityLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLeadsDetail.this);
        builder.setTitle("Confirmation"); // Set the dialog title
        builder.setMessage("Are you sure you have updated the Lead Status?"); // Set the dialog message

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