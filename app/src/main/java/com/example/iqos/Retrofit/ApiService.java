package com.example.iqos.Retrofit;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @GET("login")
    @FormUrlEncoded
    Call<Model.LoginModel> login(@Field("email") String full_name,
                                 @Field("password") String password,
                                 @Field("device_token") String device_token
    );

    @GET("qoach/leads")
    Call<Model.GetLeadsModel> getLeads(
            @Header("Accept") String accept,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("qoach/lead/detail")
    Call<Model.LeadDetailsModel> getLeadsDetails(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id
    );


    @GET("qoach/appoinments")
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
            @Field("appointment_location") String appointment_location
    );


    @FormUrlEncoded
    @POST("qoach/appointments/update")
    Call<Model.GenerealModel> updateAppointment(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id,
            @Field("appointment_status") String appointment_status,
            @Field("appointment_at") String appointment_at,
            @Field("appointment_location") String appointment_location
    );


    @FormUrlEncoded
    @POST("qoach/appointments/detail")
    Call<Model.BookAppointmentDetailsModel> bookAppointmentDetails(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Field("id") String id
    );



  /*  @POST("auth/logout")
    Call<Model.GeneralModel> logout(
            @Header("Authorization") String token
            , @Header("Accept") String accept

    );

    @POST("auth/resend-otp")
    @FormUrlEncoded
    Call<Model.ResendOtpModel> resendOTp(  // cd
                                           @Field("phone") String phone
            , @Header("Accept") String accept
    );


    @POST("auth/verify-otp")
    @FormUrlEncoded
    Call<Model.LoginModel> verifyOtp(@Field("otp") String otp,
                                     @Field("id") String id
            , @Header("Accept") String accept

    );*/

}
