package com.example.iqos.MeetingModule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.LeadsModule.ActivityLeads;
import com.example.iqos.R;
import com.example.iqos.SalesModule.ActivitySales;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    List<ActivityPackages.Package> items = new ArrayList<>();
    String app_id;
    String a1;
    String a2;
    String a3;
    String a4;
    String type;
    String multisale;
    String meeting_outcome, name;
    private Activity context;

    public PackageAdapter(Activity context, List<ActivityPackages.Package> leads, String app_id, String a1,
                          String a2,
                          String a3,
                          String a4,
                          String meeting_outcome, String type, String multisale, String name) {
        this.context = context;
        this.items = leads;
//        this.meeting_outcome = meeting_outcome;
        this.a1 = a1;
        this.a4 = a4;
        this.a2 = a2;
        this.a3 = a3;
        this.multisale = multisale;
        this.meeting_outcome = meeting_outcome;
        this.app_id = app_id;
        this.type = type;
        this.name = name;

    }

    @Override
    public PackageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_packages, parent, false);
        return new PackageAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(PackageAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (items.get(position).getName() != null) {
            holder.tvPackageName.setText("" + items.get(position).getName());
        }
        holder.rlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(items.get(position).getName(), "Spring Package")){
                    Intent intent = new Intent(context, ActivityAppointmentMeetingCheckList.class);
                    intent.putExtra("appointment_id", "" + app_id);
                    intent.putExtra("isSale", "" + "sale");
                    intent.putExtra("name", name);
                    intent.putExtra("app_id", "" + app_id);
                    intent.putExtra("package_id", "" + items.get(position).getId());
                    intent.putExtra("package_name", items.get(position).getName() + "");
                    intent.putExtra("a1", "" + a1);
                    intent.putExtra("a2", "" + a2);
                    intent.putExtra("a3", "" + a3);
                    intent.putExtra("a4", "" + a4);
                    intent.putExtra("meeting_outcome", "" + meeting_outcome);
                    intent.putExtra("type", "" + type);
                    intent.putExtra("multisale", "" + multisale);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, ActivitySales.class);
                    Log.e("TGED", "app_id=> " + app_id);
                    intent.putExtra("app_id", "" + app_id);
                    intent.putExtra("id", "" + items.get(position).getId());
                    intent.putExtra("name", items.get(position).getName() + "");
                    intent.putExtra("a1", "" + a1);
                    intent.putExtra("a2", "" + a2);
                    intent.putExtra("a3", "" + a3);
                    intent.putExtra("a4", "" + a4);
                    intent.putExtra("meeting_outcome", "" + meeting_outcome);
                    intent.putExtra("type", "" + type);
                    intent.putExtra("multisale", "" + multisale);
                    context.startActivity(intent);
                }
//        context.finish();
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


        TextView tvPackageName;
        RelativeLayout rlLayout;

        public ViewHolder(View itemView) {
            super(itemView);


            tvPackageName = itemView.findViewById(R.id.tvPackageName);
            rlLayout = itemView.findViewById(R.id.rlLayout);


        }
    }
}