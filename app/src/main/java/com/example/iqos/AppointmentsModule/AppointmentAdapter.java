package com.example.iqos.AppointmentsModule;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.LeadsModule.ActivityLeadsDetail;
import com.example.iqos.LeadsModule.LeadsAdapter;
import com.example.iqos.MeetingModule.ActivityAppointmentMeetingCheckList;
import com.example.iqos.MeetingModule.ActivityPreMeetingCheckList;
import com.example.iqos.MeetingModule.BookAppointmentDetailActivity;
import com.example.iqos.R;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private Activity context;



    ArrayList<String> items = new ArrayList<>();



    public AppointmentAdapter(Activity context, ArrayList<String> leads) {
        this.context = context;
        this.items = leads;

    }

    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointments, parent, false);
        return new AppointmentAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(AppointmentAdapter.ViewHolder holder, int position) {


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookAppointmentDetailActivity.class);
                context.startActivity(intent);
            }
        });
     holder.tvStartAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityAppointmentMeetingCheckList.class);
                context.startActivity(intent);
            }
        });

        holder.tvPreMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityPreMeetingCheckList.class);
                context.startActivity(intent);
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


        TextView name,tvPreMeeting,tvStartAppointment;
        LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            linearLayout = itemView.findViewById(R.id.llMainAppointment);
            tvStartAppointment = itemView.findViewById(R.id.tvStartAppointment);
            tvPreMeeting = itemView.findViewById(R.id.tvPreMeeting);


        }
    }
}