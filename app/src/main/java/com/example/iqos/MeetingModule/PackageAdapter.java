package com.example.iqos.MeetingModule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.R;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.SalesModule.ActivitySales;

import java.util.ArrayList;
import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private Activity context;



    List<ActivityPackages.Package> items = new ArrayList<>();


String app_id;
    public PackageAdapter(Activity context, List<ActivityPackages.Package> leads,String app_id) {
        this.context = context;
        this.items = leads;
        this.app_id = app_id;

    }

    @Override
    public PackageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_packages, parent, false);
        return new PackageAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(PackageAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
 if(items.get(position).getName()!=null)
 {
     holder.tvPackageName.setText(""+ items.get(position).getName());
 }
holder.rlLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, ActivitySales.class);
        Log.e("TGED","app_id=> "+ app_id);
        intent.putExtra("app_id",""+ app_id);
        intent.putExtra("id",""+ items.get(position).getId());
        intent.putExtra("name",items.get(position).getName()+"");
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


        TextView tvPackageName ;
 RelativeLayout rlLayout;

        public ViewHolder(View itemView) {
            super(itemView);


            tvPackageName = itemView.findViewById(R.id.tvPackageName);
            rlLayout = itemView.findViewById(R.id.rlLayout);


        }
    }
}