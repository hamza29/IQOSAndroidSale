package com.example.iqos.Retrofit;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    //public static String BASE_URL = "https://www.myfitwiz.com/api/";
//    public static String BASE_URL = "http://app.iqoch.com/api/";
    public static String BASE_URL = "https://8cf2-39-46-241-45.ngrok-free.app/api/";
//    public static String BASE_URL = "https://rpm.stagingdesk.net/api/";
//    public static String BASE_URL_IMAGE = "https://rpm.stagingdesk.net/";
    public static String BASE_URL_IMAGE = "https://rpm.stagingdesk.net/";
   // public static String BASE_URL_IMAGE = "https://www.myfitwiz.com/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(Activity activity) {
        if (retrofit == null) {


            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            File httpCacheDirectory = new File(activity.getCacheDir(), "http-cache");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(loggingInterceptor)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }
}
