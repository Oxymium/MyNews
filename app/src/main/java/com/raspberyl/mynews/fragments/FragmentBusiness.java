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
import com.raspberyl.mynews.controller.DocsAdapter;
import com.raspberyl.mynews.model.Docs;
import com.raspberyl.mynews.model.ResponseWrapper;
import com.raspberyl.mynews.utils.DividerItemDecoration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class FragmentBusiness extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private DocsAdapter mDocsAdapter;
    private List<Docs> mBusinessList;

    private int BUSINESS_ANSWER_CODE;

    private static final String BUSINESS_TAB_SEARCH = "source:(\"The New York Times\")" + " AND" + " news_desk:(\"Business\")";

    public FragmentBusiness() {

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
        Call<ResponseWrapper> call = apiService.loadBusiness(ApiKey.NYT_API_KEY,  BUSINESS_TAB_SEARCH, getContext().getString(R.string.newest_sort_order));
        call.enqueue(new Callback<com.raspberyl.mynews.model.ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                if(response.isSuccessful()) {

                BUSINESS_ANSWER_CODE = response.code();
                mBusinessList = response.body().getResponse().getDocs();


                mDocsAdapter = new DocsAdapter(mBusinessList, getContext());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mDocsAdapter);


                // Add horizontal divider to the Recyclerview

                DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), R.drawable.custom_divider);
                mRecyclerView.addItemDecoration(mDividerItemDecoration);

                }else{

                    Toast.makeText(getContext(), "Error" + response.code(), Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

                Log.e(TAG, t.toString());
                System.out.println("ERROR 2");

            }
        });

    }

}
