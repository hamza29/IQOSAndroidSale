package com.example.iqos.LeadsModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iqos.AppointmentsModule.ActivityBookAppointment;
import com.example.iqos.AppointmentsModule.AppointmentBookingDetailActivity;
import com.example.iqos.MeetingModule.ActivityPreMeetingCheckList;
import com.example.iqos.MeetingModule.BookAppointmentDetailActivity;
import com.example.iqos.R;
import com.example.iqos.SalesModule.ActivitySales;
import com.example.iqos.databinding.ActivityBookingAppointmentBinding;
import com.example.iqos.databinding.ActivityLeadsBinding;
import com.example.iqos.databinding.ActivityLeadsDetailBinding;

import java.util.ArrayList;

public class ActivityLeadsDetail extends AppCompatActivity {

    ActivityLeadsDetailBinding mBinding;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeadsDetailBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);



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



}