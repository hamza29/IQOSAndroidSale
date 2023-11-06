package com.example.iqos;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.MeetingModule.ActivityAppointmentMeetingCheckList;
import com.example.iqos.MeetingModule.ActivityPreMeetingCheckList;
import com.example.iqos.MeetingModule.BookAppointmentDetailActivity;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.SalesModule.ActivitySales;

import java.util.ArrayList;
import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder> {
    private Activity context;



    List<ActivitySales.Inventory> items = new ArrayList<>();



    public DevicesAdapter(Activity context, List<ActivitySales.Inventory> leads) {
        this.context = context;
        this.items = leads;

    }

    @Override
    public DevicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_devices, parent, false);
        return new DevicesAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(DevicesAdapter.ViewHolder holder, int position) {



        if(items.get(position).getAssigned_at()!=null){
            holder.tvTime.setText(""+items.get(position).getAssigned_at() );
        }
        if(items.get(position).getName()!=null){
            holder.tvDevices.setText(""+/*items.get(position).getName()+" | "+ */items.get(position).getSrNo() );
        }
        if(items.get(position).getColor()!=null){
            holder.tvColor.setText(""+items.get(position).getColor() );
        }






    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvDevices,tvColor,tvTime ;


        public ViewHolder(View itemView) {
            super(itemView);

            tvDevices = itemView.findViewById(R.id.tvDevices);
            tvColor = itemView.findViewById(R.id.tvColor);
            tvTime = itemView.findViewById(R.id.tvTime);



        }
    }
}