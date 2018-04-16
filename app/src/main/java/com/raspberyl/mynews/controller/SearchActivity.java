package com.raspberyl.mynews.controller;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.raspberyl.mynews.R;

public class SearchActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.configureToolbar();

    }


    private void configureToolbar() {
        //Get the toolbar (Serialise)
        mToolbar = findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(mToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Search Articles");
    }

}

