package com.example.iqos.LeadsModule;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        if (item.getFirstName() != null  ) {
            holder.name.setText(""+item.getFirstName().toString()+" ");
        }

        if (item.getLeadStatus() != null ) {
            holder.tvStatus.setText(""+item.getLeadStatus().toString());
        }
        holder.tvId.setText(""+ item.getId()+" Date: "+ item.getAssigned_at());


//        if(item.getCall1()== null && item.getCall2()== null && item.getCall3()== null && item.getCall4()== null){
//            holder.tvLastAction.setText("N/A");
//            holder.tvNextAction.setText("N/A");
//            holder.tvLastActionOutcome.setText("N/A");
//            holder.tvNextActionOutcome.setText("N/A");
//        }
//        else if (item.getCall1()!= null&& item.getCall2()== null&& item.getCall3()== null){
//            holder.tvLastAction.setText("Call 1");
//            holder.tvNextAction.setText("N/A");
//            holder.tvLastActionOutcome.setText(""+ item.getCall1());
//            holder.tvNextActionOutcome.setText("N/A");
//        }  else if (item.getCall1()!= null && item.getCall2()!= null&& item.getCall3()== null){
//            holder.tvLastAction.setText("Call 1");
//            holder.tvNextAction.setText("Call 2");
//            holder.tvLastActionOutcome.setText(""+ item.getCall1());
//            holder.tvNextActionOutcome.setText(""+ item.getCall2());
//        }else if (item.getCall1()!= null && item.getCall2()!= null && item.getCall3()!= null){
//            holder.tvLastAction.setText("Call 2");
//            holder.tvNextAction.setText("Call 3");
//            holder.tvLastActionOutcome.setText(""+ item.getCall2());
//            holder.tvNextActionOutcome.setText(""+ item.getCall3());
//        }
        holder.tvLastAction.setText(item.getLastAction().getType());
        holder.tvNextAction.setText(item.getNextAction().getType());
        holder.tvLastActionOutcome.setText(""+ item.getLastAction().getTime());
        holder.tvNextActionOutcome.setText(""+ item.getNextAction().getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getIs_appointment().equalsIgnoreCase("0")) {
                    Intent intent = new Intent(context, ActivityLeadsDetail.class);
                    intent.putExtra("KEY_LEAD_ID", item.getId().toString());
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "You have already booked an appointment", Toast.LENGTH_SHORT).show();
                }
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
        TextView tvLastAction;
        TextView tvNextAction;
        TextView tvId;
        TextView tvLastActionOutcome;
        TextView tvNextActionOutcome;



        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            tvLastAction = itemView.findViewById(R.id.tvLastAction);
            name = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvNextAction = itemView.findViewById(R.id.tvNextAction);
            tvId = itemView.findViewById(R.id.tvId);
            tvLastActionOutcome = itemView.findViewById(R.id.tvLastActionOutcome);
            tvNextActionOutcome = itemView.findViewById(R.id.tvNextActionOutcome);


        }
    }
}