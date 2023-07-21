package com.example.myheroes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private Api myApi;

    private RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(Api.class);
    }
    // tránh viec instance bị tạo lại
    public static synchronized  RetrofitClient getInstance(){
        if (instance == null){
            instance = new RetrofitClient();
        }
            return instance;
    }
    // get Api được tạo
    public Api getMyApi(){
        return myApi;
    }
}
