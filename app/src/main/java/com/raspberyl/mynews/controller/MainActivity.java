package com.raspberyl.mynews.controller;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.raspberyl.mynews.R;
import com.raspberyl.mynews.fragments.FragmentBusiness;
import com.raspberyl.mynews.fragments.FragmentMostPopular;
import com.raspberyl.mynews.fragments.FragmentTopStories;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    public static final String BUNDLED_EXTRA = "BUNDLED_EXTRA";
    public static final String SEARCH_ID = "9876";
    public static final String NOTIFICATIONS_ID = "6543";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureToolbar();

        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Required to keep tabs in memory (otherwise, 1st one will be destroyed to free memory when 3rd is called)
        mViewPager.setOffscreenPageLimit(2);

        // Fragments (tabs)
        mViewPagerAdapter.AddFragment(new FragmentTopStories(), this.getResources().getString(R.string.tab_name_1));
        mViewPagerAdapter.AddFragment(new FragmentMostPopular(), this.getResources().getString(R.string.tab_name_2));
        mViewPagerAdapter.AddFragment(new FragmentBusiness(), this.getResources().getString(R.string.tab_name_3));

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        // Remove shadow from action bar
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setElevation(0);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu and add to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        mToolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Menu item actions
        switch (item.getItemId()) {
            // Start SearchActivity from the「Search」item
            case R.id.toolbar_search:
                Intent mIntentSearch = new Intent(this, SearchActivity.class);
                mIntentSearch.putExtra(BUNDLED_EXTRA, SEARCH_ID);
                this.startActivity(mIntentSearch);
                return true;
            // Start NotificationsActivity from the「Notification」item
            case R.id.toolbar_notifications:
                Intent mIntentNotifications = new Intent(this, SearchActivity.class);
                mIntentNotifications.putExtra(BUNDLED_EXTRA, NOTIFICATIONS_ID);
                this.startActivity(mIntentNotifications);
                return true;
            // Start onHelpSelected() from the「Help」item
            case R.id.toolbar_help:
                onHelpSelected();
                return true;
            // Start onAboutSelected() from the「About」item
            case R.id.toolbar_about:
                onAboutSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onHelpSelected() {
        // Build an AlertDialog for the Help section
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Set Title and Message content
        builder.setTitle(R.string.toolbar_string_help);
        builder.setMessage(getText(R.string.alertdialog_content_about_string));
        // Neutral button
        builder.setNeutralButton(R.string.alertdialog_button_neutral, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void onAboutSelected() {
        // Build an AlertDialog for the Help section
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Set Title and Message content
        builder.setTitle(R.string.toolbar_string_about);
        builder.setMessage(getText(R.string.alertdialog_content_about_string));
        // Neutral button
        builder.setNeutralButton(R.string.alertdialog_button_neutral, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

}



