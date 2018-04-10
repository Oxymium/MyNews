package com.raspberyl.mynews.controller;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;

import com.raspberyl.mynews.R;
import com.raspberyl.mynews.fragments.FragmentBusiness;
import com.raspberyl.mynews.fragments.FragmentMostPopular;
import com.raspberyl.mynews.fragments.FragmentTopStories;


public class MainActivity extends AppCompatActivity {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Required to keep tabs "alive" (otherwise, 1st one will be destroyed to free memory when 3rd is called)
        mViewPager.setOffscreenPageLimit(2);

        // Fragments (tabs)
        mViewPagerAdapter.AddFragment(new FragmentTopStories(), "Top Stories");
        mViewPagerAdapter.AddFragment(new FragmentMostPopular(), "Most Popular");
        mViewPagerAdapter.AddFragment(new FragmentBusiness(), "Business");

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        // Remove shadow from action bar
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setElevation(0);




        // Ensures NYT_API_KEY isn't empty
        /* if (NYT_API_KEY.isEmpty()) {

            Toast.makeText(this, "Please provide valid NYT API KEY", Toast.LENGTH_LONG);

        } else {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseWrapper> call = apiService.loadArticles(NYT_API_KEY);
            call.enqueue(new Callback<com.raspberyl.mynews.model.ResponseWrapper>() {
                @Override
                public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                    CALL_RESPONSE_CODE = response.code();

                    List<Article> mArticleList = response.body().getResults();

                    // Json output into console
                    Log.w("Full json", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    Log.v("Response code", (Integer.toString(CALL_RESPONSE_CODE)));

                    // RecyclerView
                    mRecyclerView = findViewById(R.id.recycler_view);

                    mArticleAdapter = new ArticleAdapter(mArticleList, getBaseContext());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mArticleAdapter);

                    // Add horizontal divider to the Recyclerview

                    DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                            mRecyclerView.getContext(),
                            DividerItemDecoration.VERTICAL);

                    mRecyclerView.addItemDecoration(mDividerItemDecoration);



                }

                @Override
                public void onFailure(Call<com.raspberyl.mynews.model.ResponseWrapper> call, Throwable t) {

                    Log.e(TAG, t.toString());

                }
            });
        } */
    }
}


