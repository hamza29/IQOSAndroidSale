package com.example.iqos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.SalesModule.ActivitySales;

import java.util.ArrayList;
import java.util.List;

public class ConsumablesAdapter extends RecyclerView.Adapter<ConsumablesAdapter.ViewHolder> {
    List<ActivitySales.Consumable> items = new ArrayList<>();
    private Activity context;


    public ConsumablesAdapter(Activity context, List<ActivitySales.Consumable> leads) {
        this.context = context;
        this.items = leads;

    }

    @Override
    public ConsumablesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consumables, parent, false);
        return new ConsumablesAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(ConsumablesAdapter.ViewHolder holder, int position) {

        if (items.get(position).getAssigned_at() != null) {
            holder.tvTime.setText("" + items.get(position).getAssigned_at());
        }
        if (items.get(position).getType() != null) {
            holder.tvName.setText("" + items.get(position).getType());
        }
        if (items.get(position).getQuantity() != null) {
            holder.tvQuantity.setText("" + items.get(position).getQuantity());
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvName, tvQuantity, tvTime;


        public ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvQuantity = itemView.findViewById(R.id.tvQuant);

            tvTime = itemView.findViewById(R.id.tvTime);


        }
    }
}