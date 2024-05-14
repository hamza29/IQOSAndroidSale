package com.example.iqos.Retrofit;


import com.example.iqos.AppointmentsModule.ActivityBookAppointment;
import com.example.iqos.LeadsModule.ActivityHyperAppointments;
import com.example.iqos.LeadsModule.ActivityHyperCareLeads;
import com.example.iqos.LeadsModule.ActivityHyperCareLeadsDetail;
import com.example.iqos.LeadsModule.ActivityLeads;
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
    @POST("qoach/leads/sales")
    Call<Model.GetLeadsModel> getSales(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("type") String type

    );
     @POST("qoach/lead/e-comm")
    Call<ActivityLeads.EcommLeads> getEcomLeads(
            @Header("Accept") String accept,
            @Header("Authorization") String token

    );

     @POST("qoach/lead/device-care/leads")
    Call<ActivityHyperCareLeads.DeviceCareLeadsModel> getHyperLeads(
            @Header("Accept") String accept,
            @Header("Authorization") String token

    );

    @FormUrlEncoded
    @POST("qoach/leads/detail")
    Call<Model.LeadDetailsModel> getLeadsDetails(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id
    );
    @POST("qoach/lead")
    Call<Model.LeadData> createLead(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Body RequestBody body
    );

    @FormUrlEncoded
    @POST("qoach/lead/device-care/show")
    Call<ActivityHyperCareLeadsDetail.DeviceCareModel> getHyperLeadsDetails(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id
    );


    @GET("qoach/appointments")
    Call<ActivityBookAppointment.GetAppointmentModel> getAppointment(
            @Header("Accept") String accept,
            @Header("Authorization") String token);

@GET("qoach/lead/e-comm/appointments")
    Call<ActivityBookAppointment.EcomAppointments> getEcomAppointment(
            @Header("Accept") String accept,
            @Header("Authorization") String token);



    @POST("qoach/lead/device-care/appointments")
    Call<ActivityHyperAppointments.HyperAppointmentsModel> getHyperAppointments(
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
    @FormUrlEncoded
    @POST("qoach/lead/e-comm/appointments/book")
    Call<Model.GenerealModel> bookEcomAppointment(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id,
            @Field("appointment_status") String appointment_status,
            @Field("appointment_at") String appointment_at,
            @Field("appointment_location") String appointment_location
    );



    @FormUrlEncoded
    @POST("qoach/lead/device-care/book-appointment")
    Call<Model.GenerealModel> bookHyperAppointment(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("type") String type,
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
     @POST("qoach/lead/e-comm/meeting")
    Call<Model.GenerealModel> updateEcomMeeting(

            @Header("Authorization") String token,
            @Body RequestBody body


    );

     @POST("qoach/lead/meeting")
    Call<Model.GenerealModel> updateMeetingChecklist(

            @Header("Authorization") String token,
            @Body RequestBody body


    );

     @POST("qoach/lead/device-care/meeting")
    Call<Model.GenerealModel> updateHyperMeetingChecklist(

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
    @POST("qoach/lead/device-care/update")
    Call<Model.GenerealModel> updateHyperLead(
            @Header("Authorization") String auth,
            @Body RequestBody body


    );

    ///// Request Product Image ////
    @POST("qoach/lead/custom-message")
    Call<Model.GenerealModel> custom_message(
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
    @FormUrlEncoded
    @POST("qoach/lead/get-qrcode")
    Call<ActivitySales.GetQRModel> getQRCode(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id,
            @Field("token") String bearer_token
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
