package com.example.iqos.MeetingModule;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.R;

import java.util.ArrayList;

public class PreMeetingAdapter extends RecyclerView.Adapter<PreMeetingAdapter.ViewHolder> {
    ArrayList<String> items = new ArrayList<>();
    private Activity context;


    public PreMeetingAdapter(Activity context, ArrayList<String> leads) {
        this.context = context;
        this.items = leads;

    }

    @Override
    public PreMeetingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting, parent, false);
        return new PreMeetingAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(PreMeetingAdapter.ViewHolder holder, int position) {

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
