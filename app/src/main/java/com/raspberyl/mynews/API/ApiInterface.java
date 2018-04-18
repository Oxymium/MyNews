package com.raspberyl.mynews.API;

import com.raspberyl.mynews.model.ArticleWrapper;
import com.raspberyl.mynews.model.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    // TopStories
    @GET("svc/topstories/v2/home.json")
    Call<ArticleWrapper> loadTopStories(@Query("api-key") String apiKey);

    // MostPopular
    @GET("svc/mostpopular/v2/mostshared/all-sections/7.json")
    Call<ArticleWrapper> loadMostPopular(@Query("api-key") String apiKey);

    // Business Tab
    @GET("svc/search/v2/articlesearch.json")
    Call<ResponseWrapper> loadBusiness(@Query("api-key") String apiKey, @Query("fq") String fQuery, @Query("sort") String sort);

    // Search
    @GET("svc/search/v2/articlesearch.json")
    Call<ResponseWrapper> loadSearch(@Query("api-key") String apiKey,
                                     @Query("fq") String fQuery,
                                     @Query("sort") String sort,
                                     @Query("begin_date") String beginDate,
                                     @Query("end_date") String endDate);

}

