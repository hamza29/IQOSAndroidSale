package com.example.iqos.SalesModule;

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

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.ViewHolder> {
    private Activity context;



    ArrayList<String> items = new ArrayList<>();



    public FeedBackAdapter(Activity context, ArrayList<String> leads) {
        this.context = context;
        this.items = leads;

    }

    @Override
    public FeedBackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new FeedBackAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(FeedBackAdapter.ViewHolder holder, int position) {

        String item = items.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityLeadsDetail.class);
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



        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);


        }
    }
}