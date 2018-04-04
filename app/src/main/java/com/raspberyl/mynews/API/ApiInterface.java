package com.raspberyl.mynews.API;

import com.raspberyl.mynews.model.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    // TopStories
    @GET("svc/topstories/v2/home.json")
    Call<ResponseWrapper> loadArticles(@Query("api-key") String apiKey);

    // MostPopular
    @GET("svc/mostpopular/v2/mostshared/all-sections/7.json")
    Call<ResponseWrapper> loadMostPopular(@Query("api-key") String apiKey);

}

