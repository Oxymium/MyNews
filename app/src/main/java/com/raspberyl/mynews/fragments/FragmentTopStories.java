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
import com.raspberyl.mynews.model.ResponseWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class FragmentTopStories extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private ArticleAdapter mArticleAdapter;
    private List<Article> mTopStoriesList;

    private int TOPSTORIES_ANSWER_CODE;

    public FragmentTopStories() {

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
        Call<ResponseWrapper> call = apiService.loadArticles(ApiKey.NYT_API_KEY);
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                if (response.isSuccessful()) {

                    TOPSTORIES_ANSWER_CODE = response.code();
                    mTopStoriesList = response.body().getResults();
                    // Json output into console
                    Log.v("「Top Stories」response", (Integer.toString(TOPSTORIES_ANSWER_CODE)));
                    Log.w("Full「Top Stories」json", new GsonBuilder().setPrettyPrinting().create().toJson(response));

                    mArticleAdapter = new ArticleAdapter(mTopStoriesList, getContext());
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mArticleAdapter);


                    // Add horizontal divider to the Recyclerview

                    //DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                    //mRecyclerView.getContext(),
                    //DividerItemDecoration.VERTICAL);

                    //mRecyclerView.addItemDecoration(mDividerItemDecoration);


                } else {

                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onFailure(Call<com.raspberyl.mynews.model.ResponseWrapper> call, Throwable t) {

                Log.e(TAG, t.toString());

            }
        });


    }
}
