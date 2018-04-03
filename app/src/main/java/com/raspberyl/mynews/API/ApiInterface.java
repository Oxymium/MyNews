package com.raspberyl.mynews.API;

import com.raspberyl.mynews.model.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("svc/topstories/v2/home.json")
    Call<ResponseWrapper> loadArticles(@Query("api-key") String apiKey);

}

