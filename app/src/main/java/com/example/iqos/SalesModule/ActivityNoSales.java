package com.example.iqos.SalesModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.iqos.Constants;
import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.LeadsModule.LeadsAdapter;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityNoSalesBinding;
import com.example.iqos.databinding.ActivityNoSalesBinding;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityNoSales extends AppCompatActivity {

    ActivityNoSalesBinding mBinding;
    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityNoSalesBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
        String appointment_id = getIntent().getStringExtra("appointment_id");


        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
mBinding.tvComplete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        update_ic(
                mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),
                appointment_id,
                mBinding.tvQuestion1.getText().toString(),
                mBinding.tvAnswer1.getText().toString(),
                mBinding.tvQuestion2.getText().toString(),
                mBinding.tvAnswer2.getText().toString(),
                mBinding.tvQuestion3.getText().toString(),
                mBinding.tvAnswer3.getText().toString(),
                mBinding.tvQuestion4.getText().toString(),
                mBinding.tvAnswer4.getText().toString()
 );
    }
});
    }

    public void update_ic(String token,String id, String ic_question1,
                           String ic_answer1, String ic_question2,
                           String ic_answer2, String ic_question3,
                           String ic_answer3 , String ic_question4, String ic_answer4    ) {
        ApiService apiService = ApiClient.getClient(ActivityNoSales.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
         if(ic_question1 !=null) {
            builder.addFormDataPart("ic_question1", ic_question1);
        }
        if(ic_answer1 !=null) {
            builder.addFormDataPart("ic_answer1", ic_answer1);
        }
        if(ic_question2 !=null) {
            builder.addFormDataPart("ic_question2", ic_question2);
        }
        if(ic_answer2 !=null) {
            builder.addFormDataPart("ic_answer2", ic_answer2);
        }
        if(ic_question3 !=null) {
            builder.addFormDataPart("ic_question3", ic_question3);
        }
        if(ic_answer3 !=null) {
            builder.addFormDataPart("ic_answer3", ic_answer3);
        }

        if(ic_question4 !=null) {
            builder.addFormDataPart("ic_question4", ic_question4);
        } if(ic_answer4 !=null) {
            builder.addFormDataPart("ic_answer4", ic_answer4);
        }

        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.ic_update(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                            if (listofhome.getStatus().equals("1")) {
                                Toast.makeText(ActivityNoSales.this, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);
                                finish();

                            } else {
                                Toast.makeText(ActivityNoSales.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivityNoSales.this, "key model null", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(ActivityNoSales.this, "Failure", Toast.LENGTH_SHORT).show();

                    }

                });


            }
        });
    }
}