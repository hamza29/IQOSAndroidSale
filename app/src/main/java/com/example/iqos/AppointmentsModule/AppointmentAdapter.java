package com.example.iqos.AppointmentsModule;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.LeadsModule.ActivityLeadsDetail;
import com.example.iqos.LeadsModule.LeadsAdapter;
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

        String item = items.get(position);



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearData() {
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView name;



        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);


        }
    }
}