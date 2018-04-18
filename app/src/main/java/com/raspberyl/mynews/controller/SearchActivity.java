package com.raspberyl.mynews.controller;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.raspberyl.mynews.API.ApiClient;
import com.raspberyl.mynews.API.ApiInterface;
import com.raspberyl.mynews.API.ApiKey;
import com.raspberyl.mynews.R;
import com.raspberyl.mynews.model.Docs;
import com.raspberyl.mynews.model.ResponseWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static com.raspberyl.mynews.controller.MainActivity.BUNDLED_EXTRA;
import static com.raspberyl.mynews.controller.MainActivity.NOTIFICATIONS_ID;
import static com.raspberyl.mynews.controller.MainActivity.SEARCH_ID;

public class SearchActivity extends AppCompatActivity {

    private String mBundledValue;

    private Toolbar mToolbar;
    private Switch mNotificationsSwitch;

    private EditText mEditText;
    private Button mSearchButton;

    private String mSearchTerms;

    private String querySearchBox1, querySearchBox2, querySearchBox3, querySearchBox4, querySearchBox5, querySearchBox6;

    private List<Docs> mSearchResults;

    private int SEARCH_ANSWER_CODE;

    private DocsAdapter mDocsAdapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        configureToolbarSearch();
        launchSearchButton();

        mEditText = findViewById(R.id.search_editText);
        mEditText.getText().toString();


    }


    private void configureToolbarSearch() {
        //Get the toolbar (Serialise)
        mToolbar = findViewById(R.id.toolbar_searchactivity);
        //Set the toolbar
        setSupportActionBar(mToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Fetch intent to determine origin
        mBundledValue = getIntent().getStringExtra(BUNDLED_EXTRA);

        switch (mBundledValue) {
            case SEARCH_ID:
                actionBar.setTitle(R.string.search_title);
                hideNotificationsSwitch();
                break;
            case NOTIFICATIONS_ID:
                actionBar.setTitle(R.string.notifications_title);
                hideSearchButton();
                break;
        }
    }

    private void hideSearchButton() {
        // Remove the Search button
        mSearchButton = findViewById(R.id.button_search);
        mSearchButton.setVisibility(GONE);
    }

    private void hideNotificationsSwitch() {
        // Remove the Notifications switch
        mNotificationsSwitch = findViewById(R.id.notification_switch);
        mNotificationsSwitch.setVisibility(GONE);

    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.search_box1:
                if (checked) {
                     querySearchBox1 = "news_desk:(\"arts\")";
                }
                else

                    break;

            case R.id.search_box2:
                if (checked) {
                    querySearchBox2 = "news_desk:(\"business\")";
                }
                else

                    break;

            case R.id.search_box3:
                if (checked) {
                    querySearchBox3 = "news_desk:(\"entrepreneurs\")";
                }

                else

                    break;

            case R.id.search_box4:
                if (checked) {
                    querySearchBox4 = "news_desk:(\"politics\")";
                }

                else

                    break;

            case R.id.search_box5:
                if (checked) {
                    querySearchBox5 = "news_desk:(\"sports\")";
                }

                else

                    break;

            case R.id.search_box6:
                if (checked) {
                    querySearchBox5 = "news_desk:(\"travel\")";
                }

                else
                    break;
        }
    }

    private void launchSearchButton() {

        mSearchButton = findViewById(R.id.button_search);
        mSearchButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Toast.makeText(getBaseContext(), mEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                        mSearchTerms = mEditText.getText().toString();
                        searchQuery();


                    }
                });

    }

    private void searchQuery() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseWrapper> call = apiService.loadSearch(ApiKey.NYT_API_KEY, "news_desk:(\"sports\")", "newest", "20171214"   , "20180303");
        call.enqueue(new Callback<com.raspberyl.mynews.model.ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                if(response.isSuccessful()) {

                    mSearchResults = response.body().getResponse().getDocs();

                    // Json output into console
                    Log.v("「Search」response", (Integer.toString(SEARCH_ANSWER_CODE)));
                    Log.w("Full「Search」json", new GsonBuilder().setPrettyPrinting().create().toJson(response));


                    setContentView(R.layout.article_recyclerview);
                    mRecyclerView = findViewById(R.id.recyclerview_layout);
                    mDocsAdapter = new DocsAdapter(mSearchResults, SearchActivity.this);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mRecyclerView.setAdapter(mDocsAdapter);


                    // Add horizontal divider to the Recyclerview

                    //DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                            //mRecyclerView.getContext(),
                            //DividerItemDecoration.VERTICAL);

                    //mRecyclerView.addItemDecoration(mDividerItemDecoration);

                }else{
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

                Log.e(TAG, t.toString());

            }
        });

    }


}

