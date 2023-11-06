package com.example.iqos.LeadsModule;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.AppointmentsModule.BookEcommAppointmentActivity;
import com.example.iqos.R;
import com.example.iqos.Retrofit.Model;

import java.util.List;

public class EcomLeadsAdapter extends RecyclerView.Adapter<EcomLeadsAdapter.ViewHolder> {
    private Activity context;



    List<ActivityLeads.Lead> items;



    public EcomLeadsAdapter(Activity context, List<ActivityLeads.Lead> leads) {
        this.context = context;
        this.items = leads;

    }

    @Override
    public EcomLeadsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ecom_leads, parent, false);
        return new EcomLeadsAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(EcomLeadsAdapter.ViewHolder holder, int position) {

        ActivityLeads.Lead item = items.get(position);
        if (item.getEcommerceRegistration() != null  ) {
            holder.tvEcomm.setText("Unregistered" );
        }


        holder.tvEmail.setText(""+ item.getNumber());
        holder.tvId.setText(""+ item.getId()+" ."+ " "+ item.getFirstName());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getAppointmentAt()==null) {
                    Intent intent = new Intent(context, BookEcommAppointmentActivity.class);
                    intent.putExtra("KEY_LEAD_ID", item.getId().toString());
                    intent.putExtra("name", item.getFirstName());
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


    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvEmail;
        TextView tvEcomm;
         TextView tvBookAppointment;
         TextView tvId;



        public ViewHolder(View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvBookAppointment = itemView.findViewById(R.id.tvBookAppointment);
            tvEcomm = itemView.findViewById(R.id.tvEcomm);




        }
    }
}