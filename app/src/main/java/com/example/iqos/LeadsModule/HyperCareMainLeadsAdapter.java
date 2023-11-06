package com.example.iqos.LeadsModule;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.R;
import com.example.iqos.Retrofit.Model;

import java.util.List;

public class HyperCareMainLeadsAdapter extends RecyclerView.Adapter<HyperCareMainLeadsAdapter.ViewHolder> {
    private Activity context;



    List<HyperModel> items;


    ProgressBar progressBar;
    public HyperCareMainLeadsAdapter(Activity context, List<HyperModel> leads , ProgressBar progressBar) {
        this.context = context;
        this.items = leads;
        this.progressBar = progressBar;

    }

    @Override
    public HyperCareMainLeadsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hyper_leads, parent, false);
        return new HyperCareMainLeadsAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(HyperCareMainLeadsAdapter.ViewHolder holder, int position) {

        HyperModel item = items.get(position);

holder.tvDays.setText(""+ item.getTitle());
        HyperCareLeadsAdapter leadsAdapter;
        holder.tvRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        leadsAdapter = new HyperCareLeadsAdapter(context, items.get(position).getDay1s(),
                item.getTitle(),progressBar);
        holder.tvRecycler.setAdapter(leadsAdapter);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearData() {
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        RecyclerView tvRecycler;
        TextView tvDays;


        public ViewHolder(View itemView) {
            super(itemView);

            tvDays = itemView.findViewById(R.id.tvDays);
            tvRecycler = itemView.findViewById(R.id.tvRecycler);


        }
    }
}