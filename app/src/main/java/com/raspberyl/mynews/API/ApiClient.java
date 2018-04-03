package com.raspberyl.mynews.API;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// Retrofit API

public class ApiClient {

    public static final String  BASE_URL = "https://api.nytimes.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }



}
