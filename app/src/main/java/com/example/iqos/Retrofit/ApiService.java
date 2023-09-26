package com.example.iqos.Retrofit;


import com.example.iqos.MeetingModule.ActivityPackages;
import com.example.iqos.SalesModule.ActivitySales;
import com.example.iqos.SalesModule.PerformanceActivity;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login")
    @FormUrlEncoded
    Call<Model.LoginModel> login(@Field("email") String full_name,
                                 @Field("password") String password,
                                 @Field("device_token") String device_token
    );
    @FormUrlEncoded
    @POST("qoach/leads")
    Call<Model.GetLeadsModel> getLeads(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("type") String type

    );

    @FormUrlEncoded
    @POST("qoach/leads/detail")
    Call<Model.LeadDetailsModel> getLeadsDetails(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id
    );


    @GET("qoach/appointments")
    Call<Model.GetAppointmentModel> getAppointment(
            @Header("Accept") String accept,
            @Header("Authorization") String token);


    @FormUrlEncoded
    @POST("qoach/appointments/book")
    Call<Model.GenerealModel> bookAppointment(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id,
            @Field("appointment_status") String appointment_status,
            @Field("appointment_at") String appointment_at,
            @Field("appointment_location") String appointment_location,
            @Field("lead_status") String lead_status
    );




    @POST("qoach/lead/sale-information")
    Call<ActivitySales.UpdateSaleModel> updateSale(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Body RequestBody body


    );


////
    @POST("qoach/lead/update-sale-info")
    Call<ActivitySales.UpdateSaleModel> updateinfoSale(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Body RequestBody body


    );

     @POST("qoach/lead/pre-checklist")
    Call<Model.GenerealModel> updatePreMeetingChecklist(

            @Header("Authorization") String token,
            @Body RequestBody body


    );

     @POST("qoach/lead/meeting")
    Call<Model.GenerealModel> updateMeetingChecklist(

            @Header("Authorization") String token,
            @Body RequestBody body


    );


    @FormUrlEncoded
    @POST("qoach/appointments/detail")
    Call<Model.AppointmentDetailsModel> appointmentDetails(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id
    );


    ///// Request Product Image ////
    @POST("qoach/lead/update")
    Call<Model.GenerealModel> updateLead(
            @Header("Authorization") String auth,
            @Body RequestBody body


    );

    ///// Request Product Image ////
    @POST("qoach/lead/ic-update")
    Call<Model.GenerealModel> ic_update(
            @Header("Authorization") String auth,
            @Body RequestBody body


    );


    ///// Request Product Image ////
    @GET("qoach/discounts")
    Call<ActivitySales.DiscountsModel> getdiscounts(
            @Header("Authorization") String auth,
            @Header("Accept") String accept

    );


    ///// Request Product Image ////
    @FormUrlEncoded
    @POST("qoach/consumables")
    Call<ActivitySales.ConsumeableModel> getConsumeables(
            @Header("Authorization") String auth,
//            @Header("Accept") String accept
            @Field("type") String type

    );

    @GET("qoach/inventories")
    Call<ActivitySales.GetInventoriesModel> getInventories(
            @Header("Accept") String accept,
            @Header("Authorization") String token

    );


    @POST("qoach/lead/performance")
    Call<PerformanceActivity.GetPerformanceModel> getPerformance(
            @Header("Accept") String accept,
            @Header("Authorization") String token

    );

    @POST("qoach/lead/get-packages")
    Call<ActivityPackages.PackagesModel> getpackages(
            @Header("Accept") String accept,
            @Header("Authorization") String token

    );
    @FormUrlEncoded
    @POST("qoach/lead/get-package-detail")
    Call<ActivitySales.PackageDetail> getpackageDetails(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id


    );
}
