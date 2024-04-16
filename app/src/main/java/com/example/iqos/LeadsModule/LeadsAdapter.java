package com.example.iqos.LeadsModule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iqos.Constants;
import com.example.iqos.MeetingModule.ActivityPackages;
import com.example.iqos.R;
import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadsAdapter  extends RecyclerView.Adapter<LeadsAdapter.ViewHolder> {
    private Activity context;


    SharedPreferences mSharedPreferences;

    List<Model.Lead> items;

    String type;



    public LeadsAdapter(Activity context, List<Model.Lead> leads, String type) {
        this.context = context;
        this.items = leads;
        this.type = type;
        mSharedPreferences =context.getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);

    }

    @Override
    public LeadsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leads, parent, false);
        return new LeadsAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(LeadsAdapter.ViewHolder holder, int position) {

        Model.Lead item = items.get(position);
        if (item.getFirstName() != null  ) {
            if(item.getType().equalsIgnoreCase("hc1") ) {
                holder.name.setText("" + item.getFirstName().toString() + " | Device Care 1 | Day 7"  );
            } else if(item.getType().equalsIgnoreCase("hc2") ) {
                holder.name.setText("" + item.getFirstName().toString() + " | Device Care 1 | Day 14"  );
            }else if(item.getType().equalsIgnoreCase("hc3") ) {
                holder.name.setText("" + item.getFirstName().toString() + " | Device Care 2 | Day 21"  );
            }else if(item.getType().equalsIgnoreCase("hc4") ) {
                holder.name.setText("" + item.getFirstName().toString() + " | Device Care 2 | Day 30"  );
            }else if(item.getType().equalsIgnoreCase("hc5") ) {
                holder.name.setText("" + item.getFirstName().toString() + " | Device Care 3 | Day 60"  );
            }else if(item.getType().equalsIgnoreCase("hc6") ) {
                holder.name.setText("" + item.getFirstName().toString() + " | Device Care 3 | Day 90"  );
            }else if(item.getType().equalsIgnoreCase("lead") ) {
                holder.name.setText("" + item.getFirstName().toString()   );
            }
        }
        holder.llSalesText.setVisibility(View.GONE);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSharedPreferences.getString(Constants.ROLE,"").equalsIgnoreCase("sales")){

                    holder.tvAddMessage.setVisibility(View.GONE);
                    if(!Objects.equals(type, "sales")){
                        Intent intent;

                        intent = new Intent(context, ActivityPackages.class);
                        intent.putExtra("appointment_id",""+ items.get(position).getId());
                        intent.putExtra("type","sales");
                        intent.putExtra("name",""+ items.get(position).getFirstName());
                        context.startActivity(intent);

                    }
                }else {
                    holder.tvAddMessage.setVisibility(View.VISIBLE);
                    if(item.getType().equalsIgnoreCase("hc1") ||
                            item.getType().equalsIgnoreCase("hc2")||
                            item.getType().equalsIgnoreCase("hc3")||
                            item.getType().equalsIgnoreCase("hc4")||
                            item.getType().equalsIgnoreCase("hc5")||
                            item.getType().equalsIgnoreCase("hc6")) {
                        Intent intent = new Intent(context, AppointmentBookingDetailHyperActivity.class);
                        intent.putExtra("lead_id", "" + item.getId().toString());
                        intent.putExtra("name", "" + item.getFirstName().toString());
                        intent.putExtra("type", item.getType());
                        context.startActivity(intent);
                    }else{
                        if(item.getIs_appointment().equalsIgnoreCase("0")) {
                            Intent intent = new Intent(context, ActivityLeadsDetail.class);
                            intent.putExtra("type", "lead");

                            intent.putExtra("KEY_LEAD_ID", item.getId().toString());
                            context.startActivity(intent);
                        }else{
                            Toast.makeText(context, "You have already booked an appointment", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
            }
        });

if(item.getType() !=null){
    if(item.getType().equalsIgnoreCase("hc1") || item.getType().equalsIgnoreCase("hc2")|| item.getType().equalsIgnoreCase("hc3")|| item.getType().equalsIgnoreCase("hc4"))
    {
      holder.  llMain.setVisibility(View.GONE);
      holder.  llSub.setVisibility(View.GONE);
    }else
    {
        holder.  llMain.setVisibility(View.VISIBLE);
        holder.  llSub.setVisibility(View.VISIBLE);
    }




}else
{
    holder.  llMain.setVisibility(View.VISIBLE);
    holder.  llSub.setVisibility(View.VISIBLE);
}
        if(mSharedPreferences.getString(Constants.ROLE,"").equalsIgnoreCase("sales")) {
            holder.tvAddMessage.setVisibility(View.GONE);
        }
        if(Objects.equals(type, "sales")){
            holder.tvId.setText(""+ item.getId());

            holder.llMain.setVisibility(View.GONE);
            holder.llSub.setVisibility(View.GONE);
            holder.llSalesText.setVisibility(View.VISIBLE);
            holder.tvDevice.setText(item.getDevice());
            holder.tvTurqouise.setText(item.getTurqoiseQuantity());
            holder.tvAmber.setText(item.getAmberQuantity());
            holder.tvStatusSales.setText(item.getLeadStatus());
        }else{
            if (item.getLeadStatus() != null ) {
                holder.tvStatus.setText(""+item.getLeadStatus().toString());
            }
            holder.tvId.setText(""+ item.getId()+" Date: "+ item.getAssigned_at());

            holder.tvLastAction.setText(item.getLastAction().getType());
            holder.tvNextAction.setText(item.getNextAction().getType());
            holder.tvLastActionOutcome.setText(""+ item.getLastAction().getTime());
            holder.tvNextActionOutcome.setText(""+ item.getNextAction().getTime());
        }
holder.tvAddMessage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {



        showOpenMessage(item.getId()+"");
    }
});




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void showOpenMessage(  String lead_id   ){
        Dialog dialog = new Dialog( context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.day_custom_dialog);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Button btnRefresh =   dialog.findViewById(R.id.btnDone);
//        TextView tvTitle =   dialog.findViewById(R.id.tvTitle);
        EditText tvMessage =   dialog.findViewById(R.id.tvMessage);

        ImageView ivBack =   dialog.findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();




            }
        });







        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {



                updateLeadMEssaage(mSharedPreferences.
                                getString(Constants.BAREAR_TOKEN,""),lead_id,
                        tvMessage.getText().toString());


            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        dialog.show();
    }
    public void updateLeadMEssaage(String token,String id, String message  ) {

        ApiService apiService = ApiClient.getClient( context).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("lead_id", id);
        builder.addFormDataPart("message", message);



        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.custom_message(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    context.    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                            if (listofhome.getStatus().equals("1")) {

//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);

                                Toast.makeText( context, ""+listofhome.getMessage(), Toast.LENGTH_SHORT).show();
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);


                            } else {
                                Toast.makeText( context, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    context.    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            context.     getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                             Toast.makeText( context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                context.    runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        LinearLayout llSalesText;
        TextView name;
        TextView tvStatus;
        TextView tvLastAction;
        TextView tvNextAction;
        TextView tvId;
        TextView tvLastActionOutcome;
        TextView tvDevice;
        TextView tvTurqouise;
        TextView tvAmber;
        TextView tvStatusSales;
        TextView tvNextActionOutcome;
        TextView tvAddMessage;
        LinearLayout llMain;
        LinearLayout llSub;



        public ViewHolder(View itemView) {
            super(itemView);

            tvAddMessage = itemView.findViewById(R.id.tvAddMessage);
            llSub = itemView.findViewById(R.id.llSub);
            llMain = itemView.findViewById(R.id.llMain);
            name = itemView.findViewById(R.id.tvName);
            tvLastAction = itemView.findViewById(R.id.tvLastAction);
            name = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvNextAction = itemView.findViewById(R.id.tvNextAction);
            tvId = itemView.findViewById(R.id.tvId);
            tvDevice = itemView.findViewById(R.id.tvDevice);
            tvTurqouise = itemView.findViewById(R.id.tvTurqouise);
            tvAmber = itemView.findViewById(R.id.tvAmber);
            tvLastActionOutcome = itemView.findViewById(R.id.tvLastActionOutcome);
            tvNextActionOutcome = itemView.findViewById(R.id.tvNextActionOutcome);
            llSalesText = itemView.findViewById(R.id.llSalesText);
            tvStatusSales = itemView.findViewById(R.id.tvStatusSales);

        }
    }
}