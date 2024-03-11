package com.example.iqos.AppointmentsModule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.Constants;
import com.example.iqos.LeadsModule.ActivityAppointmentHyperMeeting;
import com.example.iqos.MeetingModule.ActivityAppointmentMeetingCheckList;
import com.example.iqos.MeetingModule.ActivityPreMeetingCheckList;
import com.example.iqos.MeetingModule.ActivityVerification;
import com.example.iqos.MeetingModule.BookAppointmentDetailActivity;
import com.example.iqos.R;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private Activity context;

    SharedPreferences mSharedPreferences;


    List<ActivityBookAppointment.Appointment> items = new ArrayList<>();

    public AppointmentAdapter(Activity context, List<ActivityBookAppointment.Appointment> leads) {
        this.context = context;
        this.items = leads;
        mSharedPreferences =context.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);

    }

    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointments, parent, false);
        return new AppointmentAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(AppointmentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(items.get(position).getAppointmentType().equalsIgnoreCase("hc1")||
                items.get(position).getAppointmentType().equalsIgnoreCase("hc2")
                ||items.get(position).getAppointmentType().equalsIgnoreCase("hc3")
                ||items.get(position).getAppointmentType().equalsIgnoreCase("hc4")
                ||items.get(position).getAppointmentType().equalsIgnoreCase("hc5")||
                items.get(position).getAppointmentType().equalsIgnoreCase("hc6")){
            holder.message.setVisibility(View.GONE);
            holder.tvPreMeeting.setVisibility(View.GONE);
            holder.tvEdit.setVisibility(View.GONE);
         }
        else if(items.get(position).getAppointmentType().equalsIgnoreCase("lead")){
            holder.tvEdit.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.VISIBLE);
            holder.tvPreMeeting.setVisibility(View.VISIBLE);
            if(items.get(position).getMeetingComplete() !=null ){
                if(items.get(position).getMeetingComplete().equalsIgnoreCase("1")){
                    holder.tvStartAppointment.setVisibility(View.GONE);
                }else{
                    holder.tvStartAppointment.setVisibility(View.VISIBLE);
                }
            }
            if(items.get(position).getPreMeeting() !=null ){
                if(items.get(position).getPreMeeting().equalsIgnoreCase("1")){
                    holder.tvPreMeeting.setVisibility(View.GONE);
                }else{
                    holder.tvPreMeeting.setVisibility(View.VISIBLE);
                }
            }

        }


     holder.tvStartAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.get(position).getAppointmentType().equalsIgnoreCase("hc1")||
                        items.get(position).getAppointmentType().equalsIgnoreCase("hc2")||
                        items.get(position).getAppointmentType().equalsIgnoreCase("hc3")||
                        items.get(position).getAppointmentType().equalsIgnoreCase("hc4")||
                        items.get(position).getAppointmentType().equalsIgnoreCase("hc5")||
                        items.get(position).getAppointmentType().equalsIgnoreCase("hc6")) {



                    Intent intent = new Intent(context, ActivityAppointmentHyperMeeting.class);
                    intent.putExtra("appointment_id",""+ items.get(position).getId());
                    intent.putExtra("type",""+ items.get(position).getAppointmentType());
                    intent.putExtra("name",""+ items.get(position).getFirstName());

                    context.startActivity(intent);




                }   else if(items.get(position).getAppointmentType().equalsIgnoreCase("lead")){
                    if(items.get(position).getPreMeeting().equalsIgnoreCase("1")){
                        Intent intent = new Intent(context, ActivityVerification.class);
                        intent.putExtra("appointment_id",""+ items.get(position).getId());
                        intent.putExtra("name",""+ items.get(position).getFirstName()+" ");
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context, "Please fill Pre Meeting Checklist first", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        holder.tvPreMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityPreMeetingCheckList.class);
                intent.putExtra("appointment_id",""+ items.get(position).getId());
                intent.putExtra("name",""+ items.get(position).getFirstName());
                context.startActivity(intent);
            }
            });

        holder.message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", ""+items.get(position).getNumber());
                    smsIntent.putExtra("sms_body",""+ items.get(position).getAppointmentMessage());
                    context.startActivity(smsIntent);

                }
            });






                if(items.get(position).getId() !=null){

                    if(items.get(position).getAppointmentType().equalsIgnoreCase("hc1") ) {
                        holder.tvId.setText("Appointment # "+ items.get(position).getId()+" "+
                                items.get(position).getFirstName()+" |  Device Care 1 | Day 7" );
                    } else if(items.get(position).getAppointmentType().equalsIgnoreCase("hc2") ) {
                        holder.tvId.setText("Appointment # "+ items.get(position).getId()+" "+
                                items.get(position).getFirstName()+" | Device Care 1 | Day 14" );
                    }else if(items.get(position).getAppointmentType().equalsIgnoreCase("hc3") ) {
                        holder.tvId.setText("Appointment # "+ items.get(position).getId()+" "+
                                items.get(position).getFirstName()+" | Device Care 2 | Day 21" );
                    }else if(items.get(position).getAppointmentType().equalsIgnoreCase("hc4") ) {
                        holder.tvId.setText("Appointment # "+ items.get(position).getId()+" "+
                                items.get(position).getFirstName()+" | Device Care 2 | Day 30" );
                    }else if(items.get(position).getAppointmentType().equalsIgnoreCase("hc5") ) {
                        holder.tvId.setText("Appointment # "+ items.get(position).getId()+" "+
                                items.get(position).getFirstName()+" | Device Care 3 | Day 60" );
                    }else if(items.get(position).getAppointmentType().equalsIgnoreCase("hc6") ) {
                        holder.tvId.setText("Appointment # "+ items.get(position).getId()+" "+
                                items.get(position).getFirstName()+" | Device Care 3 | Day 90" );
                    }else if(items.get(position).getAppointmentType().equalsIgnoreCase("lead") ){
                        holder.tvId.setText("Appointment # "+ items.get(position).getId()+" "+ items.get(position).getFirstName()+"" );
                    }

                        }




            if(items.get(position).getFirstName() !=null    ){
                holder.tvName.setText( items.get(position).getFirstName()+" " );
                    }


            if(items.get(position).getAppointmentDate() !=null){
                holder.tvDate.setText( items.get(position).getAppointmentDate() +"");
                    }


            if(items.get(position).getAppointmentDate() !=null){
                holder.tvDate.setText( items.get(position).getAppointmentDate() +"");
                    }

            if(items.get(position).getAppointmentLocation() !=null){
                holder.tvLocation.setText( items.get(position).getAppointmentLocation() +"");
                    }

            if(items.get(position).getAppointmentTime() !=null){
                holder.tvTime.setText( items.get(position).getAppointmentTime() +"");
                    }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.get(position).getAppointmentType().equalsIgnoreCase("lead")) {

                Intent intent = new Intent(context, BookAppointmentDetailActivity.class);
                intent.putExtra("appointment_id",""+ items.get(position).getId());

                context.startActivity(intent);
                }
            }
        });


                 holder.tvEdit.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View view) {
                                                          Intent intent = new Intent(context, BookAppointmentDetailActivity.class);
                                                          intent.putExtra("appointment_id",""+ items.get(position).getId());
                                                          context.startActivity(intent);
                                                      }
                                                  }
                 );


holder.tvAddMessage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        showOpenMessage( items.get(position).getId());
    }
});



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearData() {
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvName,tvPreMeeting,message,tvStartAppointment,tvId,tvTime,tvEdit,tvDate,tvLocation, tvAddMessage;
        LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.tvMessage);
            tvAddMessage = itemView.findViewById(R.id.tvAddMessage);
            tvName = itemView.findViewById(R.id.tvName);
            linearLayout = itemView.findViewById(R.id.llMainAppointment);
            tvStartAppointment = itemView.findViewById(R.id.tvStartAppointment);
            tvPreMeeting = itemView.findViewById(R.id.tvPreMeeting);
            tvId = itemView.findViewById(R.id.tvId);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvEdit = itemView.findViewById(R.id.tvEdit);


        }
    }

    public void showOpenMessage(  String lead_id   ){
        Dialog dialog = new Dialog( context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_custom_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnRefresh =   dialog.findViewById(R.id.btnDone);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage =   dialog.findViewById(R.id.tvMessage);

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



                updateLeadMEssaage(mSharedPreferences.
                                getString(Constants.BAREAR_TOKEN,""),lead_id,
                        tvMessage.getText().toString());


            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();
    }
    public void updateLeadMEssaage(String token,String id, String message  ) {

        ApiService apiService = ApiClient.getClient( context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("lead_id", id);
        builder.addFormDataPart("message", message);



        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.custom_message(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                            if (listofhome.getStatus().equals("1")) {

//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText( context, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText( context, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    context.    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.     getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText( context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.    runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }


}