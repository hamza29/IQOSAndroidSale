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
import com.example.iqos.databinding.ActivityHyperCareLeadsDetailBinding;
import com.example.iqos.databinding.ActivityLeadsDetailBinding;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

public class ActivityHyperCareLeadsDetail extends AppCompatActivity {

    ActivityHyperCareLeadsDetailBinding mBinding;
    SharedPreferences mSharedPreferences;

    String lead_id = "";
    String lead_status="";

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHyperCareLeadsDetailBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
         lead_id = getIntent().getStringExtra("KEY_LEAD_ID");
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);

        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });










        getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

        mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

            }
        });
    }


    public void showD1Dialog(  String message , String number ){
      Dialog  dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_one_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnRefresh =   dialog.findViewById(R.id.btnDone);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage =   dialog.findViewById(R.id.tvMessage);

//        tvTitle.setText(""+ message);
        tvMessage.setText(""+message );

        ImageView ivBack =   dialog.findViewById(R.id.ivBack);


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


                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", ""+number);
                smsIntent.putExtra("sms_body",""+ tvMessage.getText().toString());
                startActivity(smsIntent);




                updateLeadD1(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id,
                        lead_status,currentDate);


            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();
    }
    public void showD10Dialog(  String message , String number ){
        Dialog   dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_ten_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnRefresh =   dialog.findViewById(R.id.btnDone);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage =   dialog.findViewById(R.id.tvMessage);

//        tvTitle.setText(""+ message);
        tvMessage.setText(""+ message );

        ImageView ivBack =   dialog.findViewById(R.id.ivBack);


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


                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", ""+number);
                smsIntent.putExtra("sms_body",""+ tvMessage.getText().toString());
                startActivity(smsIntent);




                updateLeadD10(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id,
                        lead_status,currentDate);


            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();
    }
    public void showD3Dialog(  String message , String number ){
        Dialog  dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_three_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnCall =   dialog.findViewById(R.id.btnCall);
        Button btnMessage =   dialog.findViewById(R.id.btnMessage);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage =   dialog.findViewById(R.id.tvMessage);

//        tvTitle.setText(""+ message);
        tvMessage.setText(""+message);

        ImageView ivBack =   dialog.findViewById(R.id.ivBack);


         ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();




            }
        });







        btnMessage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String message = "Hi "+ mBinding.tvfirstName.getText().toString()+", this is your sales rep "+ mSharedPreferences.getString(Constants.USER_NAME,"")+", \n\n I was calling to ask if you require any assistance with your ILUMA Prime.I\n understand you may be busy & were unable to attend the call.\n\n You can reach me through call or message at any time.\n\n Or you can contact our helpline 0800-04767 or email us at info@iqos.com.pk for\n any product queries or complaints.\n\n This message is for registered adult users only who have independently registered for\n the Device Care Program. Please do not circulate this message further.";

                showD3MessageDialog(message,number);


//                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
//
//
//                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//                smsIntent.setType("vnd.android-dir/mms-sms");
//                smsIntent.putExtra("address", ""+number);
//                smsIntent.putExtra("sms_body",""+ tvMessage.getText().toString());
//                startActivity(smsIntent);
//
//
//
//
//                updateLeadD3Message(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id,
//                        lead_status,currentDate);


            }
        });     btnCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ number));
                startActivity(intent);

                updateLeadD3Call(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id,
                        lead_status,currentDate);


            }
        });
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);


        dialog.show();
    }
    public void showD3MessageDialog(  String message , String number ){
        Dialog  dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_three_message_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
         Button btnMessage =   dialog.findViewById(R.id.btnMessage);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage =   dialog.findViewById(R.id.tvMessage);

//        tvTitle.setText(""+ message);
        tvMessage.setText(""+ message);

        ImageView ivBack =   dialog.findViewById(R.id.ivBack);


         ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();




            }
        });







        btnMessage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {






                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", ""+number);
                smsIntent.putExtra("sms_body",""+ tvMessage.getText().toString());
                startActivity(smsIntent);




                updateLeadD3Message(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id,
                        lead_status,currentDate);


            }
        });


        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);


        dialog.show();
    }
    public void showD7MessageDialog(  String message , String number ){
        Dialog      dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_seven_message_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
         Button btnMessage =   dialog.findViewById(R.id.btnMessage);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage =   dialog.findViewById(R.id.tvMessage);

//        tvTitle.setText(""+ message);
        tvMessage.setText(""+message);

        ImageView ivBack =   dialog.findViewById(R.id.ivBack);


         ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();




            }
        });







        btnMessage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {






                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", ""+number);
                smsIntent.putExtra("sms_body",""+ tvMessage.getText().toString());
                startActivity(smsIntent);




                updateLeadD7Message(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id,
                        lead_status,currentDate);


            }
        });


        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);


        dialog.show();
    }
    public void showD7Dialog(  String message , String number ){
        Dialog       dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_seven_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnCall =   dialog.findViewById(R.id.btnCall);
        Button btnMessage =   dialog.findViewById(R.id.btnMessage);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage =   dialog.findViewById(R.id.tvMessage);
        TextView tvBookAppointment =   dialog.findViewById(R.id.tvBookAppointment);

        tvBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivityHyperCareLeadsDetail.this, AppointmentBookingDetailHyperActivity.class);
                intent.putExtra("lead_id", "" + lead_id);
                intent.putExtra("name", "" + mBinding.tvfirstName.getText().toString().trim());
                startActivity(intent);


            }
        });
//        tvTitle.setText(""+ message);
        tvMessage.setText(""+message);

        ImageView ivBack =   dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();




            }
        });








        btnMessage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String message = "Hi "+ mBinding.tvfirstName.getText().toString()+", \n\n Its been a week since you purchased your IQOS device.\n\n If you are facing any challenges, please reach out to me without hesitation.\n\n" +
                        "Alternatively you can also contact our helpline 0800-04767 or email us at info@iqos.com.pk for any product queries or complaints.\n\n This message is for registered adult users only who have independently registered for\n the Device Care Program. Please do not circulate this message further.";

                showD7MessageDialog(message,number);


//                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
//
//
//                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//                smsIntent.setType("vnd.android-dir/mms-sms");
//                smsIntent.putExtra("address", ""+number);
//                smsIntent.putExtra("sms_body",""+ tvMessage.getText().toString());
//                startActivity(smsIntent);
//
//
//
//
//                updateLeadD3Message(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id,
//                        lead_status,currentDate);


            }
        });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ number));
                startActivity(intent);

                updateLeadD7Call(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id,
                        lead_status,currentDate);


            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();
    }
    public void showD14Dialog(  String message , String number ){
        Dialog   dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_fourteen_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnCall =   dialog.findViewById(R.id.btnCall);

        EditText tvMessage =   dialog.findViewById(R.id.tvMessage);

        tvMessage.setText(""+message);

        ImageView ivBack =   dialog.findViewById(R.id.ivBack);
        TextView tvBookAppointment =   dialog.findViewById(R.id.tvBookAppointment);

        tvBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivityHyperCareLeadsDetail.this, AppointmentBookingDetailHyperActivity.class);
                intent.putExtra("lead_id", "" + lead_id);
                intent.putExtra("name", "" + mBinding.tvfirstName.getText().toString().trim());
                startActivity(intent);

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();




            }
        });




        btnCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {


                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ number));
                startActivity(intent);

                updateLeadD14Call(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id,
                        lead_status,currentDate);


            }
        });
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);


        dialog.show();
    }


    public void getLeadsDetails(String token, String lead_id) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityHyperCareLeadsDetail.this).create(ApiService.class);
        Call<DeviceCareModel> call = apiService.getHyperLeadsDetails("application/json",token, lead_id);
        call.enqueue(new Callback<DeviceCareModel>() {
            @Override
            public void onResponse(Call<DeviceCareModel> call, Response<DeviceCareModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final DeviceCareModel keyModel = response.body();
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



//                                    if (keyModel.getData().getLead().getLeadStatus() != null) {
//                                        lead_status = keyModel.getData().getLead().getLeadStatus();
                                   mBinding.tvStatus.setText(""+ lead_status);
                                   mBinding.tvLastAction.setText(""+ keyModel.getData().getLead().getLastAction().getType());
//                                    }

                                    mBinding.tvD1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHyperCareLeadsDetail.this);
                                            builder.setTitle("Confirmation"); // Set the dialog title
                                            builder.setMessage("Are you sure you want to continue starting D1?"); // Set the dialog message

                                            // Set up the buttons
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    String message = "Hi "+ keyModel.getData().getLead().getFirstName()+ ",\n\n Welcome to th Device Care Program. I'm your sales rep "+ mSharedPreferences.getString(Constants.USER_NAME,"")+". You can reach out to me any time during the next 2 weeks if you have any questions about your device.\n\n You can also contact our helpline 0800-04767 or email us at info@iqos.com.pk.\n\n This message is for registered adult users only who have independently registered for the Device Care Program. Please do not circulate this message further.";


                                                    showD1Dialog( message, keyModel.getData().getLead().getNumber() );
                                                }
                                            });

                                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                 dialog.dismiss();  }
                                            });

                                            // Create and show the AlertDialog
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                        }
                                    });
                                    mBinding.tvD3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHyperCareLeadsDetail.this);
                                            builder.setTitle("Confirmation"); // Set the dialog title
                                            builder.setMessage("Are you sure you want to continue starting D3?"); // Set the dialog message

                                            // Set up the buttons
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    String message = "Hi "+ keyModel.getData().getLead().getFirstName()+", this is your sales rep "+ mSharedPreferences.getString(Constants.USER_NAME,"")+", I hope you are having a great day.\n\n Its been a few days since you purchased your ILUMA Prime. I'm calling since you registered for the device care program to address any queries you may have about your device.\n\n Resolve issues if any using GTPs\n\n Is there anything else I can help you with today?\n\n I understand this device is new to you and I want to remind you that you can call or\n message me anytime if you have a question or require customer care support.\n\n Alternatively, you can contact our helpline 0800-04767 or email us at info@iqos.com.pk for any product queries or complaints.";


                                                    showD3Dialog( message, keyModel.getData().getLead().getNumber() );
                                                }
                                            });

                                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                 dialog.dismiss();  }
                                            });

                                            // Create and show the AlertDialog
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                        }
                                    });
                                    mBinding.tvD7.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHyperCareLeadsDetail.this);
                                            builder.setTitle("Confirmation"); // Set the dialog title
                                            builder.setMessage("Are you sure you want to continue starting D7?"); // Set the dialog message

                                            // Set up the buttons
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    String message = "Hi "+ keyModel.getData().getLead().getFirstName()+", this is your sales rep "+ mSharedPreferences.getString(Constants.USER_NAME,"")+", how are you?\n\n Its been a week since you purchased your IQOS ILUMA Prime device, have faced any challenges with your IQOS device?\n\n";


                                                    showD7Dialog( message, keyModel.getData().getLead().getNumber() );
                                                }
                                            });

                                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                 dialog.dismiss();  }
                                            });

                                            // Create and show the AlertDialog
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                        }
                                    });
                                    mBinding.tvD14.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHyperCareLeadsDetail.this);
                                            builder.setTitle("Confirmation"); // Set the dialog title
                                            builder.setMessage("Are you sure you want to continue starting D14?"); // Set the dialog message

                                            // Set up the buttons
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    String message = "Hi "+ keyModel.getData().getLead().getFirstName()+", this is your sales rep "+ mSharedPreferences.getString(Constants.USER_NAME,"")+", how are you?\n\n Its been two weeks since you purchased your IQOS ILUMA Prime device, have faced any challenges with your IQOS device?\n\n";


                                                    showD14Dialog( message, keyModel.getData().getLead().getNumber() );
                                                }
                                            });

                                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                 dialog.dismiss();  }
                                            });

                                            // Create and show the AlertDialog
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                        }
                                    });
                                    mBinding.tvD10.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHyperCareLeadsDetail.this);
                                            builder.setTitle("Confirmation"); // Set the dialog title
                                            builder.setMessage("Are you sure you want to continue starting D10?"); // Set the dialog message

                                            // Set up the buttons
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    String message = "Hi "+ keyModel.getData().getLead().getFirstName()+",\n\n You are receiving this SMS as you requested to be informed of availability and price of consumables.\n\n " +
                                                            "Genuine Terea is available at shop.terea.com.pk at pack price PKR 500.\n\n This message is intended for registered adult users only for addressing theie query. Please do not forward,disseminate or circulate this message further.";


                                                    showD10Dialog( message, keyModel.getData().getLead().getNumber() );
                                                }
                                            });

                                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                 dialog.dismiss();  }
                                            });

                                            // Create and show the AlertDialog
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                        }
                                    });




                                    }

                            } else {
                                Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<DeviceCareModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBinding.progress.setVisibility(View.GONE);
                        mBinding.swipeRefresh.setRefreshing(false);
                        Toast.makeText(ActivityHyperCareLeadsDetail.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
 
    public void updateLeadD1(String token,String id, String lead_status,
                                         String day1_message_at ) {
        mBinding.progress.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(ActivityHyperCareLeadsDetail.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
 
        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

            builder.addFormDataPart("day1_message_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

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

                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(ActivityHyperCareLeadsDetail.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
    public void updateLeadD10(String token,String id, String lead_status,
                                         String day1_message_at ) {
        mBinding.progress.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(ActivityHyperCareLeadsDetail.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

            builder.addFormDataPart("day10_message_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

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
//                                if(dialog!=null) {
//                                    dialog.dismiss();
//                                }
                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(ActivityHyperCareLeadsDetail.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
  public void updateLeadD3Message(String token,String id, String lead_status,
                                         String day1_message_at ) {
        mBinding.progress.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(ActivityHyperCareLeadsDetail.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

            builder.addFormDataPart("day3_message_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

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
//                                if(dialog!=null) {
//                                    dialog.dismiss();
//                                }
                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(ActivityHyperCareLeadsDetail.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
public void updateLeadD3Call(String token,String id, String lead_status,
                                         String day1_message_at ) {
        mBinding.progress.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(ActivityHyperCareLeadsDetail.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

            builder.addFormDataPart("day3_call_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

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

                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(ActivityHyperCareLeadsDetail.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
public void updateLeadD7Message(String token,String id, String lead_status,
                                         String day1_message_at ) {
        mBinding.progress.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(ActivityHyperCareLeadsDetail.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

            builder.addFormDataPart("day7_message_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

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

                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(ActivityHyperCareLeadsDetail.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
    public void updateLeadD7Call(String token,String id, String lead_status,
                                         String day1_message_at ) {
        mBinding.progress.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(ActivityHyperCareLeadsDetail.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

            builder.addFormDataPart("day7_call_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

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

                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(ActivityHyperCareLeadsDetail.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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




    public void updateLeadD14Call(String token,String id, String lead_status,
                                         String day1_message_at ) {
        mBinding.progress.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(ActivityHyperCareLeadsDetail.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("lead_status", lead_status);

            builder.addFormDataPart("day14_call_at", day1_message_at);


        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateHyperLead(token, requestBody);

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

                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText(ActivityHyperCareLeadsDetail.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityHyperCareLeadsDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHyperCareLeadsDetail.this);
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
                dialog.dismiss();  }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }




    public class Data {

        @SerializedName("lead")
        @Expose
        private Lead lead;
        @SerializedName("device_care")
        @Expose
        private DeviceCare deviceCare;

        public Lead getLead() {
            return lead;
        }

        public void setLead(Lead lead) {
            this.lead = lead;
        }

        public DeviceCare getDeviceCare() {
            return deviceCare;
        }

        public void setDeviceCare(DeviceCare deviceCare) {
            this.deviceCare = deviceCare;
        }

    }

    public class DeviceCare {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("lead_id")
        @Expose
        private Integer leadId;
        @SerializedName("day1_message_at")
        @Expose
        private Object day1MessageAt;
        @SerializedName("day1_call_at")
        @Expose
        private Object day1CallAt;
        @SerializedName("day3_message_at")
        @Expose
        private Object day3MessageAt;
        @SerializedName("day3_call_at")
        @Expose
        private Object day3CallAt;
        @SerializedName("day7_message_at")
        @Expose
        private Object day7MessageAt;
        @SerializedName("day7_call_at")
        @Expose
        private Object day7CallAt;
        @SerializedName("day10_message_at")
        @Expose
        private Object day10MessageAt;
        @SerializedName("day10_call_at")
        @Expose
        private Object day10CallAt;
        @SerializedName("day14_message_at")
        @Expose
        private Object day14MessageAt;
        @SerializedName("day14_call_at")
        @Expose
        private Object day14CallAt;
        @SerializedName("appointment_at")
        @Expose
        private Object appointmentAt;
        @SerializedName("appointment_location")
        @Expose
        private Object appointmentLocation;
        @SerializedName("appointment_status")
        @Expose
        private Object appointmentStatus;
        @SerializedName("start_meeting")
        @Expose
        private Object startMeeting;
        @SerializedName("start_meeting_lat_lng")
        @Expose
        private Object startMeetingLatLng;
        @SerializedName("end_meeting")
        @Expose
        private Object endMeeting;
        @SerializedName("end_meeting_lat_lng")
        @Expose
        private Object endMeetingLatLng;
        @SerializedName("question1")
        @Expose
        private Object question1;
        @SerializedName("answer1")
        @Expose
        private Object answer1;
        @SerializedName("question2")
        @Expose
        private Object question2;
        @SerializedName("answer2")
        @Expose
        private Object answer2;
        @SerializedName("question3")
        @Expose
        private Object question3;
        @SerializedName("answer3")
        @Expose
        private Object answer3;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("last_action")
        @Expose
        private LastAction__1 lastAction;
        @SerializedName("is_appointment")
        @Expose
        private Integer isAppointment;
        @SerializedName("meeting_complete")
        @Expose
        private Integer meetingComplete;

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

        public Object getDay1MessageAt() {
            return day1MessageAt;
        }

        public void setDay1MessageAt(Object day1MessageAt) {
            this.day1MessageAt = day1MessageAt;
        }

        public Object getDay1CallAt() {
            return day1CallAt;
        }

        public void setDay1CallAt(Object day1CallAt) {
            this.day1CallAt = day1CallAt;
        }

        public Object getDay3MessageAt() {
            return day3MessageAt;
        }

        public void setDay3MessageAt(Object day3MessageAt) {
            this.day3MessageAt = day3MessageAt;
        }

        public Object getDay3CallAt() {
            return day3CallAt;
        }

        public void setDay3CallAt(Object day3CallAt) {
            this.day3CallAt = day3CallAt;
        }

        public Object getDay7MessageAt() {
            return day7MessageAt;
        }

        public void setDay7MessageAt(Object day7MessageAt) {
            this.day7MessageAt = day7MessageAt;
        }

        public Object getDay7CallAt() {
            return day7CallAt;
        }

        public void setDay7CallAt(Object day7CallAt) {
            this.day7CallAt = day7CallAt;
        }

        public Object getDay10MessageAt() {
            return day10MessageAt;
        }

        public void setDay10MessageAt(Object day10MessageAt) {
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

        public Object getDay14CallAt() {
            return day14CallAt;
        }

        public void setDay14CallAt(Object day14CallAt) {
            this.day14CallAt = day14CallAt;
        }

        public Object getAppointmentAt() {
            return appointmentAt;
        }

        public void setAppointmentAt(Object appointmentAt) {
            this.appointmentAt = appointmentAt;
        }

        public Object getAppointmentLocation() {
            return appointmentLocation;
        }

        public void setAppointmentLocation(Object appointmentLocation) {
            this.appointmentLocation = appointmentLocation;
        }

        public Object getAppointmentStatus() {
            return appointmentStatus;
        }

        public void setAppointmentStatus(Object appointmentStatus) {
            this.appointmentStatus = appointmentStatus;
        }

        public Object getStartMeeting() {
            return startMeeting;
        }

        public void setStartMeeting(Object startMeeting) {
            this.startMeeting = startMeeting;
        }

        public Object getStartMeetingLatLng() {
            return startMeetingLatLng;
        }

        public void setStartMeetingLatLng(Object startMeetingLatLng) {
            this.startMeetingLatLng = startMeetingLatLng;
        }

        public Object getEndMeeting() {
            return endMeeting;
        }

        public void setEndMeeting(Object endMeeting) {
            this.endMeeting = endMeeting;
        }

        public Object getEndMeetingLatLng() {
            return endMeetingLatLng;
        }

        public void setEndMeetingLatLng(Object endMeetingLatLng) {
            this.endMeetingLatLng = endMeetingLatLng;
        }

        public Object getQuestion1() {
            return question1;
        }

        public void setQuestion1(Object question1) {
            this.question1 = question1;
        }

        public Object getAnswer1() {
            return answer1;
        }

        public void setAnswer1(Object answer1) {
            this.answer1 = answer1;
        }

        public Object getQuestion2() {
            return question2;
        }

        public void setQuestion2(Object question2) {
            this.question2 = question2;
        }

        public Object getAnswer2() {
            return answer2;
        }

        public void setAnswer2(Object answer2) {
            this.answer2 = answer2;
        }

        public Object getQuestion3() {
            return question3;
        }

        public void setQuestion3(Object question3) {
            this.question3 = question3;
        }

        public Object getAnswer3() {
            return answer3;
        }

        public void setAnswer3(Object answer3) {
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

        public LastAction__1 getLastAction() {
            return lastAction;
        }

        public void setLastAction(LastAction__1 lastAction) {
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

    }
    public class DeviceCareModel {

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
        @SerializedName("nic_format")
        @Expose
        private String nicFormat;
        @SerializedName("cig_brand")
        @Expose
        private Object cigBrand;
        @SerializedName("best_time_to_contact")
        @Expose
        private Object bestTimeToContact;
        @SerializedName("day_for_contact")
        @Expose
        private Object dayForContact;
        @SerializedName("lead_status")
        @Expose
        private String leadStatus;
        @SerializedName("last_update")
        @Expose
        private Object lastUpdate;
        @SerializedName("upload_time")
        @Expose
        private String uploadTime;
        @SerializedName("approval_status")
        @Expose
        private String approvalStatus;
        @SerializedName("approval_time")
        @Expose
        private String approvalTime;
        @SerializedName("assigned_by")
        @Expose
        private Integer assignedBy;
        @SerializedName("assigned_at")
        @Expose
        private String assignedAt;
        @SerializedName("qoach_id")
        @Expose
        private Integer qoachId;
        @SerializedName("opening_msg")
        @Expose
        private Object openingMsg;
        @SerializedName("email_message")
        @Expose
        private Object emailMessage;
        @SerializedName("call1")
        @Expose
        private Object call1;
        @SerializedName("call1_outcome")
        @Expose
        private String call1Outcome;
        @SerializedName("call2")
        @Expose
        private Object call2;
        @SerializedName("call2_outcome")
        @Expose
        private String call2Outcome;
        @SerializedName("call3")
        @Expose
        private Object call3;
        @SerializedName("call3_outcome")
        @Expose
        private String call3Outcome;
        @SerializedName("call4")
        @Expose
        private Object call4;
        @SerializedName("call4_outcome")
        @Expose
        private String call4Outcome;
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
        @SerializedName("pre_meeting_checklist_item1")
        @Expose
        private String preMeetingChecklistItem1;
        @SerializedName("pre_meeting_checklist_item2")
        @Expose
        private String preMeetingChecklistItem2;
        @SerializedName("pre_meeting_checklist_item3")
        @Expose
        private String preMeetingChecklistItem3;
        @SerializedName("pre_meeting_checklist_item4")
        @Expose
        private String preMeetingChecklistItem4;
        @SerializedName("pre_meeting_checklist_item5")
        @Expose
        private Object preMeetingChecklistItem5;
        @SerializedName("pre_meeting_checklist_item6")
        @Expose
        private Object preMeetingChecklistItem6;
        @SerializedName("start_meeting")
        @Expose
        private String startMeeting;
        @SerializedName("start_meeting_lat_lng")
        @Expose
        private String startMeetingLatLng;
        @SerializedName("question1")
        @Expose
        private Object question1;
        @SerializedName("answer1")
        @Expose
        private String answer1;
        @SerializedName("question2")
        @Expose
        private Object question2;
        @SerializedName("answer2")
        @Expose
        private String answer2;
        @SerializedName("question3")
        @Expose
        private Object question3;
        @SerializedName("answer3")
        @Expose
        private String answer3;
        @SerializedName("question4")
        @Expose
        private Object question4;
        @SerializedName("answer4")
        @Expose
        private String answer4;
        @SerializedName("question5")
        @Expose
        private Object question5;
        @SerializedName("answer5")
        @Expose
        private Object answer5;
        @SerializedName("video1")
        @Expose
        private Object video1;
        @SerializedName("video2")
        @Expose
        private Object video2;
        @SerializedName("video3")
        @Expose
        private Object video3;
        @SerializedName("i1")
        @Expose
        private Object i1;
        @SerializedName("i2")
        @Expose
        private Object i2;
        @SerializedName("end_meeting")
        @Expose
        private String endMeeting;
        @SerializedName("end_meeting_lat_lng")
        @Expose
        private String endMeetingLatLng;
        @SerializedName("meeting_outcome")
        @Expose
        private String meetingOutcome;
        @SerializedName("sku_id")
        @Expose
        private Integer skuId;
        @SerializedName("consumable_type")
        @Expose
        private Object consumableType;
        @SerializedName("consumable_quantity")
        @Expose
        private Object consumableQuantity;
        @SerializedName("consumable_price")
        @Expose
        private Object consumablePrice;
        @SerializedName("existing_device")
        @Expose
        private Object existingDevice;
        @SerializedName("existing_device_sr_no")
        @Expose
        private Object existingDeviceSrNo;
        @SerializedName("existing_device_image_link")
        @Expose
        private Object existingDeviceImageLink;
        @SerializedName("package_id")
        @Expose
        private Integer packageId;
        @SerializedName("package_amount")
        @Expose
        private Object packageAmount;
        @SerializedName("amber_id")
        @Expose
        private Object amberId;
        @SerializedName("amber_type")
        @Expose
        private Integer amberType;
        @SerializedName("amber_quantity")
        @Expose
        private Integer amberQuantity;
        @SerializedName("amber_amount")
        @Expose
        private Integer amberAmount;
        @SerializedName("turqouise_id")
        @Expose
        private Object turqouiseId;
        @SerializedName("turqouise_type")
        @Expose
        private Integer turqouiseType;
        @SerializedName("turqouise_quantity")
        @Expose
        private Integer turqouiseQuantity;
        @SerializedName("turqouise_amount")
        @Expose
        private Integer turqouiseAmount;
        @SerializedName("discount_offered")
        @Expose
        private Object discountOffered;
        @SerializedName("payment_method")
        @Expose
        private String paymentMethod;
        @SerializedName("total_payment")
        @Expose
        private String totalPayment;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("ic_question1")
        @Expose
        private String icQuestion1;
        @SerializedName("ic_answer1")
        @Expose
        private String icAnswer1;
        @SerializedName("ic_question2")
        @Expose
        private String icQuestion2;
        @SerializedName("ic_answer2")
        @Expose
        private String icAnswer2;
        @SerializedName("ic_question3")
        @Expose
        private Object icQuestion3;
        @SerializedName("ic_answer3")
        @Expose
        private Object icAnswer3;
        @SerializedName("ic_question4")
        @Expose
        private Object icQuestion4;
        @SerializedName("ic_answer4")
        @Expose
        private Object icAnswer4;
        @SerializedName("ic_question5")
        @Expose
        private Object icQuestion5;
        @SerializedName("ic_answer5")
        @Expose
        private Object icAnswer5;
        @SerializedName("ic_question6")
        @Expose
        private Object icQuestion6;
        @SerializedName("ic_answer6")
        @Expose
        private Object icAnswer6;
        @SerializedName("ic_question7")
        @Expose
        private Object icQuestion7;
        @SerializedName("ic_answer7")
        @Expose
        private Object icAnswer7;
        @SerializedName("ic_question8")
        @Expose
        private Object icQuestion8;
        @SerializedName("ic_answer8")
        @Expose
        private Object icAnswer8;
        @SerializedName("is_name_verified")
        @Expose
        private String isNameVerified;
        @SerializedName("is_hav_verified")
        @Expose
        private String isHavVerified;
        @SerializedName("is_smoke_status_verified")
        @Expose
        private String isSmokeStatusVerified;
        @SerializedName("customer_requested")
        @Expose
        private Integer customerRequested;
        @SerializedName("customer_requested_account")
        @Expose
        private Integer customerRequestedAccount;
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
        @SerializedName("device_care")
        @Expose
        private Object deviceCare;

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

        public String getNicFormat() {
            return nicFormat;
        }

        public void setNicFormat(String nicFormat) {
            this.nicFormat = nicFormat;
        }

        public Object getCigBrand() {
            return cigBrand;
        }

        public void setCigBrand(Object cigBrand) {
            this.cigBrand = cigBrand;
        }

        public Object getBestTimeToContact() {
            return bestTimeToContact;
        }

        public void setBestTimeToContact(Object bestTimeToContact) {
            this.bestTimeToContact = bestTimeToContact;
        }

        public Object getDayForContact() {
            return dayForContact;
        }

        public void setDayForContact(Object dayForContact) {
            this.dayForContact = dayForContact;
        }

        public String getLeadStatus() {
            return leadStatus;
        }

        public void setLeadStatus(String leadStatus) {
            this.leadStatus = leadStatus;
        }

        public Object getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(Object lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public String getApprovalTime() {
            return approvalTime;
        }

        public void setApprovalTime(String approvalTime) {
            this.approvalTime = approvalTime;
        }

        public Integer getAssignedBy() {
            return assignedBy;
        }

        public void setAssignedBy(Integer assignedBy) {
            this.assignedBy = assignedBy;
        }

        public String getAssignedAt() {
            return assignedAt;
        }

        public void setAssignedAt(String assignedAt) {
            this.assignedAt = assignedAt;
        }

        public Integer getQoachId() {
            return qoachId;
        }

        public void setQoachId(Integer qoachId) {
            this.qoachId = qoachId;
        }

        public Object getOpeningMsg() {
            return openingMsg;
        }

        public void setOpeningMsg(Object openingMsg) {
            this.openingMsg = openingMsg;
        }

        public Object getEmailMessage() {
            return emailMessage;
        }

        public void setEmailMessage(Object emailMessage) {
            this.emailMessage = emailMessage;
        }

        public Object getCall1() {
            return call1;
        }

        public void setCall1(Object call1) {
            this.call1 = call1;
        }

        public String getCall1Outcome() {
            return call1Outcome;
        }

        public void setCall1Outcome(String call1Outcome) {
            this.call1Outcome = call1Outcome;
        }

        public Object getCall2() {
            return call2;
        }

        public void setCall2(Object call2) {
            this.call2 = call2;
        }

        public String getCall2Outcome() {
            return call2Outcome;
        }

        public void setCall2Outcome(String call2Outcome) {
            this.call2Outcome = call2Outcome;
        }

        public Object getCall3() {
            return call3;
        }

        public void setCall3(Object call3) {
            this.call3 = call3;
        }

        public String getCall3Outcome() {
            return call3Outcome;
        }

        public void setCall3Outcome(String call3Outcome) {
            this.call3Outcome = call3Outcome;
        }

        public Object getCall4() {
            return call4;
        }

        public void setCall4(Object call4) {
            this.call4 = call4;
        }

        public String getCall4Outcome() {
            return call4Outcome;
        }

        public void setCall4Outcome(String call4Outcome) {
            this.call4Outcome = call4Outcome;
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

        public String getPreMeetingChecklistItem1() {
            return preMeetingChecklistItem1;
        }

        public void setPreMeetingChecklistItem1(String preMeetingChecklistItem1) {
            this.preMeetingChecklistItem1 = preMeetingChecklistItem1;
        }

        public String getPreMeetingChecklistItem2() {
            return preMeetingChecklistItem2;
        }

        public void setPreMeetingChecklistItem2(String preMeetingChecklistItem2) {
            this.preMeetingChecklistItem2 = preMeetingChecklistItem2;
        }

        public String getPreMeetingChecklistItem3() {
            return preMeetingChecklistItem3;
        }

        public void setPreMeetingChecklistItem3(String preMeetingChecklistItem3) {
            this.preMeetingChecklistItem3 = preMeetingChecklistItem3;
        }

        public String getPreMeetingChecklistItem4() {
            return preMeetingChecklistItem4;
        }

        public void setPreMeetingChecklistItem4(String preMeetingChecklistItem4) {
            this.preMeetingChecklistItem4 = preMeetingChecklistItem4;
        }

        public Object getPreMeetingChecklistItem5() {
            return preMeetingChecklistItem5;
        }

        public void setPreMeetingChecklistItem5(Object preMeetingChecklistItem5) {
            this.preMeetingChecklistItem5 = preMeetingChecklistItem5;
        }

        public Object getPreMeetingChecklistItem6() {
            return preMeetingChecklistItem6;
        }

        public void setPreMeetingChecklistItem6(Object preMeetingChecklistItem6) {
            this.preMeetingChecklistItem6 = preMeetingChecklistItem6;
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

        public Object getQuestion1() {
            return question1;
        }

        public void setQuestion1(Object question1) {
            this.question1 = question1;
        }

        public String getAnswer1() {
            return answer1;
        }

        public void setAnswer1(String answer1) {
            this.answer1 = answer1;
        }

        public Object getQuestion2() {
            return question2;
        }

        public void setQuestion2(Object question2) {
            this.question2 = question2;
        }

        public String getAnswer2() {
            return answer2;
        }

        public void setAnswer2(String answer2) {
            this.answer2 = answer2;
        }

        public Object getQuestion3() {
            return question3;
        }

        public void setQuestion3(Object question3) {
            this.question3 = question3;
        }

        public String getAnswer3() {
            return answer3;
        }

        public void setAnswer3(String answer3) {
            this.answer3 = answer3;
        }

        public Object getQuestion4() {
            return question4;
        }

        public void setQuestion4(Object question4) {
            this.question4 = question4;
        }

        public String getAnswer4() {
            return answer4;
        }

        public void setAnswer4(String answer4) {
            this.answer4 = answer4;
        }

        public Object getQuestion5() {
            return question5;
        }

        public void setQuestion5(Object question5) {
            this.question5 = question5;
        }

        public Object getAnswer5() {
            return answer5;
        }

        public void setAnswer5(Object answer5) {
            this.answer5 = answer5;
        }

        public Object getVideo1() {
            return video1;
        }

        public void setVideo1(Object video1) {
            this.video1 = video1;
        }

        public Object getVideo2() {
            return video2;
        }

        public void setVideo2(Object video2) {
            this.video2 = video2;
        }

        public Object getVideo3() {
            return video3;
        }

        public void setVideo3(Object video3) {
            this.video3 = video3;
        }

        public Object getI1() {
            return i1;
        }

        public void setI1(Object i1) {
            this.i1 = i1;
        }

        public Object getI2() {
            return i2;
        }

        public void setI2(Object i2) {
            this.i2 = i2;
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

        public String getMeetingOutcome() {
            return meetingOutcome;
        }

        public void setMeetingOutcome(String meetingOutcome) {
            this.meetingOutcome = meetingOutcome;
        }

        public Integer getSkuId() {
            return skuId;
        }

        public void setSkuId(Integer skuId) {
            this.skuId = skuId;
        }

        public Object getConsumableType() {
            return consumableType;
        }

        public void setConsumableType(Object consumableType) {
            this.consumableType = consumableType;
        }

        public Object getConsumableQuantity() {
            return consumableQuantity;
        }

        public void setConsumableQuantity(Object consumableQuantity) {
            this.consumableQuantity = consumableQuantity;
        }

        public Object getConsumablePrice() {
            return consumablePrice;
        }

        public void setConsumablePrice(Object consumablePrice) {
            this.consumablePrice = consumablePrice;
        }

        public Object getExistingDevice() {
            return existingDevice;
        }

        public void setExistingDevice(Object existingDevice) {
            this.existingDevice = existingDevice;
        }

        public Object getExistingDeviceSrNo() {
            return existingDeviceSrNo;
        }

        public void setExistingDeviceSrNo(Object existingDeviceSrNo) {
            this.existingDeviceSrNo = existingDeviceSrNo;
        }

        public Object getExistingDeviceImageLink() {
            return existingDeviceImageLink;
        }

        public void setExistingDeviceImageLink(Object existingDeviceImageLink) {
            this.existingDeviceImageLink = existingDeviceImageLink;
        }

        public Integer getPackageId() {
            return packageId;
        }

        public void setPackageId(Integer packageId) {
            this.packageId = packageId;
        }

        public Object getPackageAmount() {
            return packageAmount;
        }

        public void setPackageAmount(Object packageAmount) {
            this.packageAmount = packageAmount;
        }

        public Object getAmberId() {
            return amberId;
        }

        public void setAmberId(Object amberId) {
            this.amberId = amberId;
        }

        public Integer getAmberType() {
            return amberType;
        }

        public void setAmberType(Integer amberType) {
            this.amberType = amberType;
        }

        public Integer getAmberQuantity() {
            return amberQuantity;
        }

        public void setAmberQuantity(Integer amberQuantity) {
            this.amberQuantity = amberQuantity;
        }

        public Integer getAmberAmount() {
            return amberAmount;
        }

        public void setAmberAmount(Integer amberAmount) {
            this.amberAmount = amberAmount;
        }

        public Object getTurqouiseId() {
            return turqouiseId;
        }

        public void setTurqouiseId(Object turqouiseId) {
            this.turqouiseId = turqouiseId;
        }

        public Integer getTurqouiseType() {
            return turqouiseType;
        }

        public void setTurqouiseType(Integer turqouiseType) {
            this.turqouiseType = turqouiseType;
        }

        public Integer getTurqouiseQuantity() {
            return turqouiseQuantity;
        }

        public void setTurqouiseQuantity(Integer turqouiseQuantity) {
            this.turqouiseQuantity = turqouiseQuantity;
        }

        public Integer getTurqouiseAmount() {
            return turqouiseAmount;
        }

        public void setTurqouiseAmount(Integer turqouiseAmount) {
            this.turqouiseAmount = turqouiseAmount;
        }

        public Object getDiscountOffered() {
            return discountOffered;
        }

        public void setDiscountOffered(Object discountOffered) {
            this.discountOffered = discountOffered;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getTotalPayment() {
            return totalPayment;
        }

        public void setTotalPayment(String totalPayment) {
            this.totalPayment = totalPayment;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getIcQuestion1() {
            return icQuestion1;
        }

        public void setIcQuestion1(String icQuestion1) {
            this.icQuestion1 = icQuestion1;
        }

        public String getIcAnswer1() {
            return icAnswer1;
        }

        public void setIcAnswer1(String icAnswer1) {
            this.icAnswer1 = icAnswer1;
        }

        public String getIcQuestion2() {
            return icQuestion2;
        }

        public void setIcQuestion2(String icQuestion2) {
            this.icQuestion2 = icQuestion2;
        }

        public String getIcAnswer2() {
            return icAnswer2;
        }

        public void setIcAnswer2(String icAnswer2) {
            this.icAnswer2 = icAnswer2;
        }

        public Object getIcQuestion3() {
            return icQuestion3;
        }

        public void setIcQuestion3(Object icQuestion3) {
            this.icQuestion3 = icQuestion3;
        }

        public Object getIcAnswer3() {
            return icAnswer3;
        }

        public void setIcAnswer3(Object icAnswer3) {
            this.icAnswer3 = icAnswer3;
        }

        public Object getIcQuestion4() {
            return icQuestion4;
        }

        public void setIcQuestion4(Object icQuestion4) {
            this.icQuestion4 = icQuestion4;
        }

        public Object getIcAnswer4() {
            return icAnswer4;
        }

        public void setIcAnswer4(Object icAnswer4) {
            this.icAnswer4 = icAnswer4;
        }

        public Object getIcQuestion5() {
            return icQuestion5;
        }

        public void setIcQuestion5(Object icQuestion5) {
            this.icQuestion5 = icQuestion5;
        }

        public Object getIcAnswer5() {
            return icAnswer5;
        }

        public void setIcAnswer5(Object icAnswer5) {
            this.icAnswer5 = icAnswer5;
        }

        public Object getIcQuestion6() {
            return icQuestion6;
        }

        public void setIcQuestion6(Object icQuestion6) {
            this.icQuestion6 = icQuestion6;
        }

        public Object getIcAnswer6() {
            return icAnswer6;
        }

        public void setIcAnswer6(Object icAnswer6) {
            this.icAnswer6 = icAnswer6;
        }

        public Object getIcQuestion7() {
            return icQuestion7;
        }

        public void setIcQuestion7(Object icQuestion7) {
            this.icQuestion7 = icQuestion7;
        }

        public Object getIcAnswer7() {
            return icAnswer7;
        }

        public void setIcAnswer7(Object icAnswer7) {
            this.icAnswer7 = icAnswer7;
        }

        public Object getIcQuestion8() {
            return icQuestion8;
        }

        public void setIcQuestion8(Object icQuestion8) {
            this.icQuestion8 = icQuestion8;
        }

        public Object getIcAnswer8() {
            return icAnswer8;
        }

        public void setIcAnswer8(Object icAnswer8) {
            this.icAnswer8 = icAnswer8;
        }

        public String getIsNameVerified() {
            return isNameVerified;
        }

        public void setIsNameVerified(String isNameVerified) {
            this.isNameVerified = isNameVerified;
        }

        public String getIsHavVerified() {
            return isHavVerified;
        }

        public void setIsHavVerified(String isHavVerified) {
            this.isHavVerified = isHavVerified;
        }

        public String getIsSmokeStatusVerified() {
            return isSmokeStatusVerified;
        }

        public void setIsSmokeStatusVerified(String isSmokeStatusVerified) {
            this.isSmokeStatusVerified = isSmokeStatusVerified;
        }

        public Integer getCustomerRequested() {
            return customerRequested;
        }

        public void setCustomerRequested(Integer customerRequested) {
            this.customerRequested = customerRequested;
        }

        public Integer getCustomerRequestedAccount() {
            return customerRequestedAccount;
        }

        public void setCustomerRequestedAccount(Integer customerRequestedAccount) {
            this.customerRequestedAccount = customerRequestedAccount;
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

        public Object getDeviceCare() {
            return deviceCare;
        }

        public void setDeviceCare(Object deviceCare) {
            this.deviceCare = deviceCare;
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