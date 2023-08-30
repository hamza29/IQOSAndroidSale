package com.example.iqos.LeadsModule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iqos.AppointmentsModule.AppointmentBookingDetailActivity;
import com.example.iqos.Constants;
import com.example.iqos.R;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityLeadsDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLeadsDetail extends AppCompatActivity {

    ActivityLeadsDetailBinding mBinding;
    SharedPreferences mSharedPreferences;

    String lead_id = "";

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeadsDetailBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        lead_id = getIntent().getStringExtra("KEY_LEAD_ID");
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);

        getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);



        mBinding.tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityLeadsDetail.this, ActivitySales.class);
//                startActivity(intent);
                showCDialog("Open Message","");
            }
        });


        mBinding.tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityLeadsDetail.this, ActivitySales.class);
//                startActivity(intent);
                showCDialog("Email","");
            }
        });



        mBinding.tvBookAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLeadsDetail.this, AppointmentBookingDetailActivity.class);
                startActivity(intent);
            }
        });

        mBinding.tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCallDialog();
            }
        });




    }
    public void showCDialog(String title, String message ){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_text_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnRefresh =   dialog.findViewById(R.id.btnDone);
        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage =   dialog.findViewById(R.id.tvMessage);

        tvTitle.setText(""+ title);
        tvMessage.setText(""+ message);

        ImageView ivBack =   dialog.findViewById(R.id.ivBack);


         ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();

            }
        });







        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    dialog.dismiss();

            }
        });


        dialog.show();
    }
    public void showCallDialog( ){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_call_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnYes =   dialog.findViewById(R.id.btnYes);
        Button btnNo =   dialog.findViewById(R.id.btnNo);






        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    dialog.dismiss();

            }
        });



        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    dialog.dismiss();

            }
        });


        dialog.show();
    }


    public void getLeadsDetails(String token, String lead_id) {
        mBinding.progress.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivityLeadsDetail.this).create(ApiService.class);
        Call<Model.LeadDetailsModel> call = apiService.getLeadsDetails("application/json",token, lead_id);
        call.enqueue(new Callback<Model.LeadDetailsModel>() {
            @Override
            public void onResponse(Call<Model.LeadDetailsModel> call, Response<Model.LeadDetailsModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final Model.LeadDetailsModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {
                                if (keyModel.getData() != null) {

                                    mBinding.progress.setVisibility(View.GONE);




                                }

                            } else {
                                Toast.makeText(ActivityLeadsDetail.this, "Error", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ActivityLeadsDetail.this, "key model null", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ActivityLeadsDetail.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }




}