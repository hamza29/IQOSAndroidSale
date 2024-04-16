package com.example.iqos.SalesModule;

import static com.example.iqos.MeetingModule.ActivityAppointmentMeetingCheckList.meetingcehck;
import static com.example.iqos.MeetingModule.ActivityPackages.packagesActivity;
import static com.example.iqos.MeetingModule.ActivityVerification.verificationAct;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.iqos.Constants;
import com.example.iqos.GPSTracker;
 import com.example.iqos.Retrofit.ApiClient;
import com.example.iqos.Retrofit.ApiService;
import com.example.iqos.Retrofit.Model;
import com.example.iqos.databinding.ActivityBookAppointmentBinding;
import com.example.iqos.databinding.ActivitySalesBinding;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySales extends AppCompatActivity {


    ActivitySalesBinding mBinding;
    SharedPreferences mSharedPreferences;
    ArrayList<Uri> imagesUriArrayList = new ArrayList<>();
    String newDeviceSrNo;
    String terqQuantity;
    String terqid;
    String amberQuantity;
    String amberid;
    String price="0";
    String amberprice="0";
    String terqprice="0";
    String package_id;
    String payment_method;
    String appointment_id;
    String      ending_latitude;
    String a1,a2,a3,a4,meeting_outcome, type, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySalesBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        appointment_id = getIntent().getStringExtra("app_id");
         mSharedPreferences = getSharedPreferences(Constants.PREFRENCES, Context.MODE_PRIVATE);
         Intent intent = getIntent();

        package_id=intent.getStringExtra("id");
        a1 =intent.getStringExtra("a1");
        a2 =intent.getStringExtra("a2");
        a3 =intent.getStringExtra("a3");
        a4 =intent.getStringExtra("a4");
        type =intent.getStringExtra("type");
        meeting_outcome =intent.getStringExtra("meeting_outcome");

         getPackageDetail(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),package_id);

         List<String> payment_method = new ArrayList<>();
         payment_method.add("cash");
         payment_method.add("card");
         payment_method.add("Bank Transfer");
         setPaymentMethodSpinner(payment_method);


            mBinding.tvName.setText(intent.getStringExtra("name"));

            if(intent.getStringExtra("name") !=null) {
                if (intent.getStringExtra("name").equalsIgnoreCase("Package C")) {
                    mBinding.llEmail.setVisibility(View.VISIBLE);
                } else {
                    if (intent.getStringExtra("name").equalsIgnoreCase("Spring")) {
                        mBinding.cbHyperCare.setVisibility(View.GONE);
                        mBinding.cbaccountRegistered.setVisibility(View.GONE);
                        mBinding.cbcustomerDeviceLinked.setVisibility(View.GONE);
                    }else{
                        mBinding.cbHyperCare.setVisibility(View.VISIBLE);
                        mBinding.cbaccountRegistered.setVisibility(View.VISIBLE);
                        mBinding.cbcustomerDeviceLinked.setVisibility(View.VISIBLE);
                    }
                    mBinding.llEmail.setVisibility(View.GONE);

                }
            }


        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBinding.ivTakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnableRuntimePermission();

            }
        });

    }






    public void getInventories(String token, String pack ) {
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivitySales.this).create(ApiService.class);
        Call<GetInventoriesModel> call = apiService.getInventories("application/json",token );
        call.enqueue(new Callback<GetInventoriesModel>() {
            @Override
            public void onResponse(Call<GetInventoriesModel> call, Response<GetInventoriesModel> response) {
                ///// Progress Dialog  Dismiss////////////
                final GetInventoriesModel keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {


                                List<Inventory> ambers= keyModel.getData().getInventories();

                                ambers.add(new Inventory( "-1" ," "," ","","","","",""," "," ","" ));

                                 setNewIQosSerialNumberSpinner(keyModel.getData().getInventories(), pack);

                                mBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.e("TGED","id-> "+ newDeviceSrNo);
                                        Log.e("TGEeeeeD","payment_method---> "+ payment_method);

                                        Toast.makeText(ActivitySales.this, payment_method, Toast.LENGTH_SHORT).show();
                                        if(type.equalsIgnoreCase("sales")){
                                            newDeviceSrNo = mBinding.etSerialNumber.getText().toString();
                                        }
                                        if(!newDeviceSrNo.isEmpty()){
                                        updateSale(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),
                                                appointment_id,newDeviceSrNo,package_id,amberid,terqid,mBinding.etEmail.getText().toString(), mBinding.etAmount.getText().toString(),payment_method);
                                    }else {
                                            if(type.equalsIgnoreCase("sales")){
                                                Toast.makeText(ActivitySales.this, "Please type Serial Number", Toast.LENGTH_SHORT).show();

                                            }else{
                                                Toast.makeText(ActivitySales.this, "Please select device", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ActivitySales.this, "No Device Found", Toast.LENGTH_SHORT).show();
 
                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                             Toast.makeText(ActivitySales.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<GetInventoriesModel> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                         Toast.makeText(ActivitySales.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
    public void getPackageDetail(String token, String id ) {
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ApiService apiService = ApiClient.getClient(ActivitySales.this).create(ApiService.class);
        Call<PackageDetail> call = apiService.getpackageDetails("application/json",token ,id);
        call.enqueue(new Callback<PackageDetail>() {
            @Override
            public void onResponse(Call<PackageDetail> call, Response<PackageDetail> response) {
                ///// Progress Dialog  Dismiss////////////
                final PackageDetail keyModel = response.body();
                if (keyModel != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (keyModel.getStatus().equals("1")) {

                                  price=keyModel.getData().getPackage().getPrice();
                                mBinding.etAmount.setText(keyModel.getData().getPackage().getPrice());
                                if(type.equalsIgnoreCase("sales")){
                                    mBinding.newIqosSerialNumberSpinner.setVisibility(View.GONE);
                                    mBinding.etSerialNumber.setVisibility(View.VISIBLE);
                                    mBinding.cbcustomerDeviceLinked.setVisibility(View.GONE);
                                    mBinding.cbHyperCare.setVisibility(View.GONE);
                                    mBinding.cbaccountRegistered.setVisibility(View.GONE);
                                }
                                    getInventories(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),"");

                                List<Amber> ambers= keyModel.getData().getPackage().getAmber();
                                ambers.add(new Amber("-1","0","0"));
                                setAmberSpinner(ambers);



                                List<Turqouise> turqouise= keyModel.getData().getPackage().getTurqouise();

                                turqouise.add(new Turqouise("-1","0","0"));


                             setTerqSpinner(turqouise);
                            } else {
                                Toast.makeText(ActivitySales.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                             Toast.makeText(ActivitySales.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<PackageDetail> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                         Toast.makeText(ActivitySales.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    private void setNewIQosSerialNumberSpinner(List<Inventory> inventories, String pack){


        ArrayList<String > serials = new ArrayList<>();

            for (int i =0;i< inventories.size();i++){
                if(inventories.get(i).getId().equalsIgnoreCase("-1")){
                    serials.add("Select");

                }else{
                    serials.add(inventories.get(i).getSrNo());

                }



        }
       Collections.reverse(serials);
       Collections.reverse(inventories);

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                serials);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mBinding.newIqosSerialNumberSpinner.setAdapter(ad);
        mBinding.newIqosSerialNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(!serials.get(position).equalsIgnoreCase("Select")){

if(inventories.get(position).getSrNo().equalsIgnoreCase(serials.get(position)))
                         newDeviceSrNo = inventories.get(position).getId();
                         mBinding.tvColor.setText(inventories.get(position).getColor());



            }else{
                    newDeviceSrNo="";
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }





    private void setPaymentMethodSpinner(List<String> payment_list){





        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                payment_list);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mBinding.spinnerPaymentMethod.setAdapter(ad);
        mBinding.spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

               payment_method = payment_list.get(position);



            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }



     private void setTerqSpinner(List<Turqouise> turqouise){

         ArrayList<String > serials = new ArrayList<>();

         for (int i =0;i< turqouise.size();i++){
             if(turqouise.get(i).getId().equalsIgnoreCase("-1")){
                 serials.add("Select" );

             }else{
                 serials.add(turqouise.get(i).getQuantity());

             }


         }
         Collections.reverse(serials);
         Collections.reverse(turqouise);
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                serials);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mBinding.spTurq.setAdapter(ad);
        mBinding.spTurq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
Log.e("TGED","SERIALS-> "+serials.get(position) );
if(!serials.get(position).equalsIgnoreCase("Select")){

        if(serials.get(position).equalsIgnoreCase(turqouise.get(position).getQuantity())) {
            terqQuantity = turqouise.get(position).getQuantity();
            terqid = turqouise.get(position).getId();
            terqprice = turqouise.get(position).getPrice();
            if(type.equalsIgnoreCase("sales")) {
                amberprice = "0";
                terqprice = "0";
                mBinding.etAmount.setText("0");

            }else{
                if(!amberprice.equalsIgnoreCase("0")){
                    int totl= Integer.parseInt(price.split("\\.")[0]) + Integer.parseInt(terqprice) + Integer.parseInt(amberprice);
                    mBinding.etAmount.setText(""+totl );

                }else {
                    int totl= Integer.parseInt(price.split("\\.")[0]) + Integer.parseInt(terqprice);
                    mBinding.etAmount.setText(""+totl );

                }
            }

        }


            }
                    else{
                        terqprice =  "0";

                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }
    private void setAmberSpinner(List<Amber> amber){


        ArrayList<String > serials = new ArrayList<>();

        for (int i =0;i< amber.size();i++){

            if(amber.get(i).getId().equalsIgnoreCase("-1")){
                serials.add("Select" );

            }else{
                serials.add(amber.get(i).getQuantity());

            }

        }
        Collections.reverse(serials);
        Collections.reverse(amber);
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                serials);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        mBinding.spAmber.setAdapter(ad);
        mBinding.spAmber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.e("TGED","serials-> "+ serials.size());
                Log.e("TGED","amber-> "+ amber.size());


    if(!serials.get(position).equalsIgnoreCase("Select")){
        Log.e("TGED","AMBER-> "+  amber.get(position).getQuantity());
        Log.e("TGED","QUANTITY-> "+ serials.get(position));

        for (int  i =0 ;i< serials.size();i++){
            if (serials.get(i).equalsIgnoreCase(
                    amber.get(position).getQuantity())) {
                Log.e("TGED","AMBER-> "+  amber.get(position).getQuantity());
                Log.e("TGED","QUANTITY-> "+ serials.get(i));
                amberQuantity = amber.get(position).getQuantity();
                amberid = amber.get(position).getId();
                amberprice = amber.get(position).getPrice();
                if(type.equalsIgnoreCase("sales")) {
                    amberprice = "0";
                    terqprice = "0";
                    mBinding.etAmount.setText("0");
                }else{
                    if(!terqprice.equalsIgnoreCase("0")){
                        int totl=   Integer.parseInt(price.split("\\.")[0]) + Integer.parseInt(amberprice)+ Integer.parseInt(terqprice);
                        mBinding.etAmount.setText(""+ totl);
                    }else{

                        int totl= Integer.parseInt(price.split("\\.")[0]) + Integer.parseInt(amberprice);
                        mBinding.etAmount.setText(""+ totl);
                    }
                }



            }
        }




    }
else{
    amberprice =   "0";

}

//}












            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    public class Data {

        @SerializedName("inventories")
        @Expose
        private List<Inventory> inventories;

        public List<Inventory> getInventories() {
            return inventories;
        }

        public void setInventories(List<Inventory> inventories) {
            this.inventories = inventories;
        }

    }
    public class GetInventoriesModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private Data data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }
    public class Inventory {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("qoach_id")
        @Expose
        private String qoachId;
        @SerializedName("lead_id")
        @Expose
        private String leadId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("sr_no")
        @Expose
        private String srNo;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;  @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("assigned_at")
        @Expose
        private String assigned_at;

        public Inventory() {
        }

        public Inventory(String id, String qoachId, String leadId, String userId, String name, String color, String srNo, String createdAt, String updatedAt, String price, String assigned_at) {
            this.id = id;
            this.qoachId = qoachId;
            this.leadId = leadId;
            this.userId = userId;
            this.name = name;
            this.color = color;
            this.srNo = srNo;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.price = price;
            this.assigned_at = assigned_at;
        }

        public String getAssigned_at() {
            return assigned_at;
        }

        public void setAssigned_at(String assigned_at) {
            this.assigned_at = assigned_at;
        }

        public String getId() {
            return id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQoachId() {
            return qoachId;
        }

        public void setQoachId(String qoachId) {
            this.qoachId = qoachId;
        }

        public String getLeadId() {
            return leadId;
        }

        public void setLeadId(String leadId) {
            this.leadId = leadId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSrNo() {
            return srNo;
        }

        public void setSrNo(String srNo) {
            this.srNo = srNo;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
    public class LData {

        @SerializedName("pdf_url")
        @Expose
        private String pdfUrl;

        public String getPdfUrl() {
            return pdfUrl;
        }

        public void setPdfUrl(String pdfUrl) {
            this.pdfUrl = pdfUrl;
        }

    }
    public class UpdateSaleModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private LData data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public LData getData() {
            return data;
        }

        public void setData(LData data) {
            this.data = data;
        }

    }







    public class DData {

        @SerializedName("discounts")
        @Expose
        private List<Discount> discounts;

        public List<Discount> getDiscounts() {
            return discounts;
        }

        public void setDiscounts(List<Discount> discounts) {
            this.discounts = discounts;
        }

    }
    public class Discount {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("percentage")
        @Expose
        private String percentage;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
    public class DiscountsModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private DData data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DData getData() {
            return data;
        }

        public void setData(DData data) {
            this.data = data;
        }

    }
    public class Consumable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("assigned_at")
        @Expose
        private String assigned_at;

        public String getAssigned_at() {
            return assigned_at;
        }

        public void setAssigned_at(String assigned_at) {
            this.assigned_at = assigned_at;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
    public class ConsumeableModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private CData data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public CData getData() {
            return data;
        }

        public void setData(CData data) {
            this.data = data;
        }

    }
    public class CData {

        @SerializedName("consumable")
        @Expose
        private List<Consumable> consumable;

        public List<Consumable> getConsumable() {
            return consumable;
        }

        public void setConsumable(List<Consumable> consumable) {
            this.consumable = consumable;
        }

    }
    public void EnableRuntimePermission() {

        Dexter.withActivity(ActivitySales.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_MEDIA_IMAGES
                ).withListener(new MultiplePermissionsListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        isStoragePermissionGranted();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void isStoragePermissionGranted() {
        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setCamera(true)
                .setMaxCount(1)
                .startAlbumWithOnActivityResult(1);
//        takePhotoFromCamera();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
       if(data.getParcelableArrayListExtra(FishBun.INTENT_PATH) !=null) {
           imagesUriArrayList = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);

           if(imagesUriArrayList.size()>0){
           Glide.with(this).load(getRealPathFromURI(imagesUriArrayList.get(0)))
                   .into(mBinding.ivTakeImage);
           mBinding.ivTakeImage.setVisibility(View.VISIBLE);
           Log.e("TGED", "getRealPathFromURI=> " + getRealPathFromURI(imagesUriArrayList.get(0)));
       }
       }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(ActivitySales.this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }



    public Uri getImageUri(Activity inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public class Amber {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("price")
        @Expose
        private String price;

        public Amber() {
        }

        public Amber(String id, String quantity, String price) {
            this.id = id;
            this.quantity = quantity;
            this.price = price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

    }
    public class PData {

        @SerializedName("package")
        @Expose
        private Package _package;

        public Package getPackage() {
            return _package;
        }

        public void setPackage(Package _package) {
            this._package = _package;
        }

    }
    public class Package {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("amber")
        @Expose
        private List<Amber> amber;
        @SerializedName("turqouise")
        @Expose
        private List<Turqouise> turqouise;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<Amber> getAmber() {
            return amber;
        }

        public void setAmber(List<Amber> amber) {
            this.amber = amber;
        }

        public List<Turqouise> getTurqouise() {
            return turqouise;
        }

        public void setTurqouise(List<Turqouise> turqouise) {
            this.turqouise = turqouise;
        }

    }
    public class PackageDetail {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private PData data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public PData getData() {
            return data;
        }

        public void setData(PData data) {
            this.data = data;
        }

    }
    public class Turqouise {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("quantity")
        @Expose
        private String quantity;

        @SerializedName("price")
        @Expose
        private String price;

        public Turqouise() {
        }

        public Turqouise(String id, String quantity, String price) {
            this.id = id;
            this.quantity = quantity;
            this.price = price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

    }
    public void updateSale(String token,String id, String sku_id,
                           String package_id, String amber_id,
                           String turqouise_id, String email, String total_amount,String payment_method   ) {
        ApiService apiService = ApiClient.getClient(ActivitySales.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
        builder.addFormDataPart("package_id", package_id);
        builder.addFormDataPart("sku_id", sku_id);

        if(amber_id !=null) {
            builder.addFormDataPart("amber_id", amber_id);
        }
        if(turqouise_id !=null) {
            builder.addFormDataPart("turqouise_id", turqouise_id);
        }
        if(total_amount !=null) {
            builder.addFormDataPart("total_payment", total_amount);
        }

        if(email !=null) {
            builder.addFormDataPart("email", email);
        }


        if(payment_method !=null) {
            builder.addFormDataPart("payment_method", payment_method);
        }



        if( mBinding.cbEcommerce  !=null) {
            if( mBinding.cbEcommerce.isChecked()){
                builder.addFormDataPart("customer_requested", "1");

            }else{
                builder.addFormDataPart("customer_requested", "0");

            }


        }
        if( mBinding.cbRequested  !=null) {
            if( mBinding.cbRequested.isChecked()){
                builder.addFormDataPart("customer_requested_account", "1");

            }else{
                builder.addFormDataPart("customer_requested_account", "0");

            }


        }

        if( mBinding.cbHyperCare  !=null) {
            if( mBinding.cbHyperCare.isChecked()){
                builder.addFormDataPart("customer_hyper_care", "1");

            }else{
                builder.addFormDataPart("customer_hyper_care", "0");

            }

        }





        if( mBinding.cbcustomerDeviceLinked  !=null) {
            if( mBinding.cbcustomerDeviceLinked.isChecked()){
                builder.addFormDataPart("customer_device_linked", "1");

            }else{
                builder.addFormDataPart("customer_device_linked", "0");

            }


        }
        if( mBinding.cbaccountRegistered  !=null) {
            if( mBinding.cbaccountRegistered.isChecked()){
                builder.addFormDataPart("customer_account_registered", "1");

            }else{
                builder.addFormDataPart("customer_account_registered", "0");

            }


        }
        if (imagesUriArrayList != null) {
            if (imagesUriArrayList.size() > 0) {
                for (int i = 0;i  < imagesUriArrayList.size();i++){
                    File file = new File(getRealPathFromURI(imagesUriArrayList.get(i)));
                    builder.addFormDataPart("image",
                            file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));

                }

            }
        }

        RequestBody requestBody = builder.build();

        Call<UpdateSaleModel> call = apiService.updateinfoSale("application/json",token, requestBody);

        call.enqueue(new Callback<UpdateSaleModel>() {
            @Override
            public void onResponse(Call<UpdateSaleModel> call, Response<UpdateSaleModel> response) {
                final UpdateSaleModel listofhome = response.body();
                if (listofhome != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                            if (listofhome.getStatus().equals("1")) {
//                                getLeadsDetails(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),lead_id);






                 GPSTracker gpsTracker = new GPSTracker(ActivitySales.this);
                 if (gpsTracker.getIsGPSTrackingEnabled())
                 {
                      ending_latitude = gpsTracker.getLatitude()+ "," + gpsTracker.getLongitude();
                 }



                 Date todayDate = Calendar.getInstance().getTime();
                 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 String todayString = formatter.format(todayDate);
             String    ending_date =todayString;

                 updateMeetingChecklist(mSharedPreferences.getString(Constants.BAREAR_TOKEN,""),
                         appointment_id,
                        a1,a2,a3,a4, ending_date,ending_latitude,meeting_outcome);








                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivitySales.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<UpdateSaleModel> call, Throwable t) {


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(ActivitySales.this, "Failure", Toast.LENGTH_SHORT).show();

                    }

                });


            }
        });
    }



    public void updateMeetingChecklist(String token,String id ,
                                       String answer1 , 
                                       String answer2 ,  
                                       String answer3 , 
                                       String answer4 ,
                                       String end_meeting,
                                       String end_meeting_lat_lng,
                                       String meeting_outcome) {
        ApiService apiService = ApiClient.getClient(ActivitySales.this).create(ApiService.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("id", id);
      
        if(answer1 !=null) {
            builder.addFormDataPart("answer1", answer1);
        }
         if(answer2 !=null) {
            builder.addFormDataPart("answer2", answer2);
        }
         if(answer3 !=null) {
            builder.addFormDataPart("answer3", answer3);
        }

         if(answer4 !=null) {
            builder.addFormDataPart("answer4", answer4);
        }

        if(end_meeting !=null) {
            builder.addFormDataPart("end_meeting", end_meeting);
        }
        if(end_meeting_lat_lng !=null) {
            builder.addFormDataPart("end_meeting_lat_lng", end_meeting_lat_lng);
        }
        if(meeting_outcome !=null) {
            builder.addFormDataPart("meeting_outcome", meeting_outcome);
        }

        RequestBody requestBody = builder.build();

        Call<Model.GenerealModel> call = apiService.updateMeetingChecklist(token, requestBody);

        call.enqueue(new Callback<Model.GenerealModel>() {
            @Override
            public void onResponse(Call<Model.GenerealModel> call, Response<Model.GenerealModel> response) {
                final Model.GenerealModel listofhome = response.body();
                if (listofhome != null) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                            if (listofhome.getStatus().equals("1")) {


                                Toast.makeText(ActivitySales.this, listofhome.getMessage(), Toast.LENGTH_SHORT).show();
                                if(!type.equalsIgnoreCase("sales")){
                                    Intent intent = new Intent(ActivitySales.this, ActivityAfterSale.class);
                                    intent.putExtra("appointment_id", id);
                                    startActivity(intent);
                                    finish();
                                }


                                if(   packagesActivity!=null)
                                {
                                    packagesActivity.finish();
                                }



                                if(   meetingcehck!=null)
                                {
                                    meetingcehck.finish();
                                }



                                if(   verificationAct!=null)
                                {
                                    verificationAct.finish();
                                }

                                finish();

                            } else {
                                Toast.makeText(ActivitySales.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ActivitySales.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<Model.GenerealModel> call, Throwable t) {


                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }

                });


            }
        });
    }

}