package com.raspberyl.mynews.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.raspberyl.mynews.API.ApiClient;
import com.raspberyl.mynews.API.ApiInterface;
import com.raspberyl.mynews.API.ApiKey;
import com.raspberyl.mynews.R;
import com.raspberyl.mynews.controller.ArticleAdapter;
import com.raspberyl.mynews.model.Article;
import com.raspberyl.mynews.model.ArticleWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class FragmentMostPopular extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private ArticleAdapter mArticleAdapter;
    private List<Article> mMostPopularList;

    private int MOSTPOPULAR_ANSWER_CODE;

    public FragmentMostPopular() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.article_recyclerview, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview_layout);


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArticleWrapper> call = apiService.loadMostPopular(ApiKey.NYT_API_KEY);
        call.enqueue(new Callback<ArticleWrapper>() {
            @Override
            public void onResponse(Call<ArticleWrapper> call, Response<ArticleWrapper> response) {

                MOSTPOPULAR_ANSWER_CODE = response.code();
                mMostPopularList = response.body().getResults();

                // Json output into console
                Log.v("「Most popular」response", (Integer.toString(MOSTPOPULAR_ANSWER_CODE)));
                Log.w("Full「Most popular」json", new GsonBuilder().setPrettyPrinting().create().toJson(response));


                mArticleAdapter = new ArticleAdapter(mMostPopularList, getContext());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mArticleAdapter);


                // Add horizontal divider to the Recyclerview

                //DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                //mRecyclerView.getContext(),
                //DividerItemDecoration.VERTICAL);

                //mRecyclerView.addItemDecoration(mDividerItemDecoration); */


            }

            @Override
            public void onFailure(Call<com.raspberyl.mynews.model.ArticleWrapper> call, Throwable t) {

                Log.e(TAG, t.toString());

            }
        });

    }
}
