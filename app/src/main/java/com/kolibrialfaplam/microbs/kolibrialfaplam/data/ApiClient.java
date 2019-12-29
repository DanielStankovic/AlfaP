package com.kolibrialfaplam.microbs.kolibrialfaplam.data;

import com.kolibrialfaplam.microbs.kolibrialfaplam.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    //MBS test address
  //  public final static String SERVICE_ADDRESS = "http://89.216.113.44:8222/";


    //Alfa Plam production
    public final static String SERVICE_ADDRESS = "http://212.200.43.123:8092/";

    private final static String SERVICE_URI = SERVICE_ADDRESS + "KolibriT.svc/";

    public static Retrofit retrofit;

    public static Retrofit getApiClient(){

        //Okhttp client
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .connectTimeout(30, TimeUnit.SECONDS) //time for connecting to server and start to receive data
                .readTimeout(30, TimeUnit.SECONDS)     //time that passes between two bytes that are received from the server
                .writeTimeout(15, TimeUnit.SECONDS); //time that passes between two bytes that are sent to the server






        if(BuildConfig.DEBUG) {
            //Interceptor - used to see the data being sent and received
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        if(retrofit == null){

            retrofit = new Retrofit.Builder().baseUrl(SERVICE_URI).addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build()).build();
        }

        return retrofit;

    }
}
