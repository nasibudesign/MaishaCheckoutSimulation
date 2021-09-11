package com.maishapay.myshop.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppFactory {
    private static String url = "https://fakestoreapi.com/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {

        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(60, TimeUnit.SECONDS);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addInterceptor(loggingInterceptor);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient.build())
                    .build();
        }

        return retrofit;
    }
}
