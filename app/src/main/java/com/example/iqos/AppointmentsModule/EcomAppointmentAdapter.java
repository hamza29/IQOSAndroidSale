package com.example.iqos.AppointmentsModule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.MeetingModule.ActivityEcomMeeting;
import com.example.iqos.R;

import java.util.ArrayList;
import java.util.List;

public class EcomAppointmentAdapter extends RecyclerView.Adapter<EcomAppointmentAdapter.ViewHolder> {
    List<ActivityBookAppointment.EcAppointment> items = new ArrayList<>();
    private Activity context;

    public EcomAppointmentAdapter(Activity context, List<ActivityBookAppointment.EcAppointment> leads) {
        this.context = context;
        this.items = leads;

    }

    @Override
    public EcomAppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ec_appointments, parent, false);
        return new EcomAppointmentAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(EcomAppointmentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.tvStartAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityEcomMeeting.class);
                intent.putExtra("appointment_id", "" + items.get(position).getId());
                intent.putExtra("name", "" + items.get(position).getFirstName());
                context.startActivity(intent);


            }
        });


        if (items.get(position).getId() != null) {
            holder.tvId.setText("Appointment # " + items.get(position).getId() + " " + items.get(position).getFirstName());
        }


        if (items.get(position).getFirstName() != null) {
            holder.tvName.setText(items.get(position).getFirstName() + " ");
        }


        if (items.get(position).getAppointmentDate() != null) {
            holder.tvDate.setText(items.get(position).getAppointmentDate() + "");
        }


        if (items.get(position).getAppointmentDate() != null) {
            holder.tvDate.setText(items.get(position).getAppointmentDate() + "");
        }

        if (items.get(position).getAppointmentLocation() != null) {
            holder.tvLocation.setText(items.get(position).getAppointmentLocation() + "");
        }

        if (items.get(position).getAppointmentTime() != null) {
            holder.tvTime.setText(items.get(position).getAppointmentTime() + "");
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearData() {
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvName, tvPreMeeting, message, tvStartAppointment, tvId, tvTime, tvEdit, tvDate, tvLocation;
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


        }
    }
}