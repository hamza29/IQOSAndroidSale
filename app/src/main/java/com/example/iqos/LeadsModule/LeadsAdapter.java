package com.example.iqos.LeadsModule;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.R;
import com.example.iqos.Retrofit.Model;

import java.util.ArrayList;
import java.util.List;

public class LeadsAdapter  extends RecyclerView.Adapter<LeadsAdapter.ViewHolder> {
    private Activity context;



    List<Model.Lead> items;



    public LeadsAdapter(Activity context, List<Model.Lead> leads) {
        this.context = context;
        this.items = leads;

    }

    @Override
    public LeadsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leads, parent, false);
        return new LeadsAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(LeadsAdapter.ViewHolder holder, int position) {

        Model.Lead item = items.get(position);
        if (item.getFirstName() != null  && item.getLastName() != null) {
            holder.name.setText(""+item.getFirstName().toString()+" "+item.getLastName().toString());
        }

        if (item.getLeadStatus() != null ) {
            holder.name.setText(""+item.getLeadStatus().toString());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityLeadsDetail.class);
                intent.putExtra("KEY_LEAD_ID",item.getId().toString());
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


        TextView name;
        TextView tvStatus;



        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);


        }
    }
}