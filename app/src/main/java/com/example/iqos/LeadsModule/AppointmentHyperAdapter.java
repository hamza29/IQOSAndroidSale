package com.example.iqos.LeadsModule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.MeetingModule.ActivityPreMeetingCheckList;
import com.example.iqos.MeetingModule.ActivityVerification;
import com.example.iqos.MeetingModule.BookAppointmentDetailActivity;
import com.example.iqos.R;
import com.example.iqos.Retrofit.Model;

import java.util.ArrayList;
import java.util.List;

public class AppointmentHyperAdapter extends RecyclerView.Adapter<AppointmentHyperAdapter.ViewHolder> {
    private Activity context;



    List<ActivityHyperAppointments.Appointment> items = new ArrayList<>();

    public AppointmentHyperAdapter(Activity context, List<ActivityHyperAppointments.Appointment> leads) {
        this.context = context;
        this.items = leads;

    }

    @Override
    public AppointmentHyperAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hyper_appointments, parent, false);
        return new AppointmentHyperAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(AppointmentHyperAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
/*

        if(items.get(position).getMeeting_complete() !=null ){
            if(items.get(position).getMeeting_complete().equalsIgnoreCase("1")){
                holder.tvStartAppointment.setVisibility(View.GONE);
            }else{
                holder.tvStartAppointment.setVisibility(View.VISIBLE);
            }
        }
        if(items.get(position).getPre_meeting() !=null ){
            if(items.get(position).getPre_meeting().equalsIgnoreCase("1")){
                holder.tvPreMeeting.setVisibility(View.GONE);
            }else{
                holder.tvPreMeeting.setVisibility(View.VISIBLE);
            }
        }
*/

    /* holder.tvStartAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.get(position).getPre_meeting().equalsIgnoreCase("1")){
                Intent intent = new Intent(context, ActivityVerification.class);
                intent.putExtra("appointment_id",""+ items.get(position).getId());
                intent.putExtra("name",""+ items.get(position).getFirstName()+" ");
                context.startActivity(intent);
            }else{
                    Toast.makeText(context, "Please fill Pre Meeting Checklist first", Toast.LENGTH_SHORT).show();
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
                    smsIntent.putExtra("sms_body",""+ items.get(position).getMessage());
                    context.startActivity(smsIntent);

                }
            });
*/
                if(items.get(position).getId() !=null){
                    holder.tvId.setText("Appointment # "+ items.get(position).getId());
                        }

                /*if(items.get(position).getDay1CallAt()!=null){
                    holder.tvDay.setText("Device Care 1");

                }

               else if(items.get(position).getDay3CallAt()!=null){
                    holder.tvDay.setText("Device Care 3");

                }
               else if(items.get(position).getDay7CallAt()!=null){
                    holder.tvDay.setText("Device Care 7");

                }else if(items.get(position).getDay10CallAt()!=null){
                    holder.tvDay.setText("Device Care 10");

                }

              else  if(items.get(position).getDay14CallAt()!=null){
                    holder.tvDay.setText("Device Care 14");

                }
*/


//holder.tvDay.setText(""+ items.get(position).get);


            if(items.get(position).getLead().getFirstName() !=null    ){
                holder.tvName.setText( items.get(position).getLead().getFirstName()+" " );
                    }


            if(items.get(position).getAppointmentAt() !=null){
                holder.tvDate.setText( items.get(position).getAppointmentAt().split(" ")[0].toString() +"");
                    }
            if(items.get(position).getAppointmentAt() !=null){
                holder.tvTime.setText( items.get(position).getAppointmentAt().split(" ")[1].toString() +"");
                    }



            if(items.get(position).getAppointmentLocation() !=null){
                holder.tvLocation.setText( items.get(position).getAppointmentLocation() +"");
                    }


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, ActivityAppointmentHyperMeeting.class);
                intent.putExtra("appointment_id",""+ items.get(position).getLead().getId());

                context.startActivity(intent);
            }
        });


        holder.tvEdit.setVisibility(View.GONE);
                 holder.tvEdit.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View view) {
                                                          Intent intent = new Intent(context, BookAppointmentDetailActivity.class);
                                                          intent.putExtra("appointment_id",""+ items.get(position).getId());
                                                          context.startActivity(intent);
                                                      }
                                                  }
                 );






    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearData() {
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvName,tvPreMeeting,message,tvDay,tvStartAppointment,tvId,tvTime,tvEdit,tvDate,tvLocation;
        LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.tvMessage);
            tvName = itemView.findViewById(R.id.tvName);
            linearLayout = itemView.findViewById(R.id.llMainAppointment);
            tvStartAppointment = itemView.findViewById(R.id.tvStartAppointment);
            tvPreMeeting = itemView.findViewById(R.id.tvPreMeeting);
            tvId = itemView.findViewById(R.id.tvId);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDay = itemView.findViewById(R.id.tvDay);


        }
    }
}