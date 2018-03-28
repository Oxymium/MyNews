package com.raspberyl.mynews.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.raspberyl.mynews.API.ApiClient;
import com.raspberyl.mynews.API.ApiInterface;
import com.raspberyl.mynews.R;
import com.raspberyl.mynews.model.Article;
import com.raspberyl.mynews.model.ResponseWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // API KEY - Required for the app to work.
    // Is the same for the 3 API 「Top Stories」, 「Most popular」 & 「Article Search」

    private static final String NYT_API_KEY = "7423ec0d6b104a77b3c1cbcf877e8fb9";

    // Response code

    private int CALL_RESPONSE_CODE;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensures NYT_API_KEY isn't empty

        if (NYT_API_KEY.isEmpty()) {

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

                }

                @Override
                public void onFailure(Call<com.raspberyl.mynews.model.ResponseWrapper> call, Throwable t) {

                    Log.e(TAG, t.toString());

                }
            });
        }
    }
}


