package com.raspberyl.mynews.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.raspberyl.mynews.R;
import com.raspberyl.mynews.fragments.FragmentBusiness;
import com.raspberyl.mynews.fragments.FragmentMostPopular;
import com.raspberyl.mynews.fragments.FragmentTopStories;
import com.raspberyl.mynews.utils.SharedPreferencesUtils;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private String mStringTest;

    public static final String SHARED_PREFERENCES_LAST_SAVED_DATE = "SHARED_PREFERENCES_LAST_SAVED_DATE";


    public static final String BUNDLED_EXTRA = "BUNDLED_EXTRA";
    public static final String SEARCH_ID = "9876";
    public static final String NOTIFICATIONS_ID = "6543";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);

        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureViewPager();
        this.setDefaultNavigationDrawerHighlightSelection();

    }

    // ------------------------------------------------------
    // NavigationDrawer default/match tab highlight selection
    // ------------------------------------------------------
    public void setDefaultNavigationDrawerHighlightSelection() {

        mNavigationView.setCheckedItem(R.id.activity_main_drawer_tab_1);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                switch (position) {
                    case 0:
                        mNavigationView.setCheckedItem(R.id.activity_main_drawer_tab_1);
                        break;

                    case 1:
                        mNavigationView.setCheckedItem(R.id.activity_main_drawer_tab_2);
                        break;

                    case 2:
                        mNavigationView.setCheckedItem(R.id.activity_main_drawer_tab_3);

                        break;

                    default:

                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    // ------------------------------------------
    // Init TabLayout, ViewPager & add Fragments
    // ------------------------------------------
    public void configureViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Required to keep tabs in memory (otherwise, 1st one will be destroyed to free memory when 3rd is called)
        mViewPager.setOffscreenPageLimit(2);

        // Fragments (tabs)
        mViewPagerAdapter.AddFragment(new FragmentTopStories(), this.getResources().getString(R.string.tab_name_1));
        mViewPagerAdapter.AddFragment(new FragmentMostPopular(), this.getResources().getString(R.string.tab_name_2));
        mViewPagerAdapter.AddFragment(new FragmentBusiness(), this.getResources().getString(R.string.tab_name_3));

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setElevation(0);

    }

    // ------------------------
    // NavigationDrawer config
    // ------------------------

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){

            case R.id.activity_main_drawer_tab_1:
                TabLayout.Tab tab0 = mTabLayout.getTabAt(0);
                tab0.select();
                break;

            case R.id.activity_main_drawer_tab_2 :
                TabLayout.Tab tab1 = mTabLayout.getTabAt(1);
                tab1.select();

                break;

            case R.id.activity_main_drawer_tab_3:
                TabLayout.Tab tab2 = mTabLayout.getTabAt(2);
                tab2.select();
                break;

            case R.id.activity_main_drawer_search:
                onSearchSelected();
                break;

            case R.id.activity_main_drawer_notifications:
                onNotificationsSelected();
                break;

            case R.id.activity_main_drawer_help:
                onHelpSelected();
                break;

            case R.id.activity_main_drawer_about:
                onAboutSelected();
                break;

            default:
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.mNavigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    // ------------------------
    // Toolbar init & config
    // ------------------------

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
                this.onSearchSelected();
                return true;
            // Start NotificationsActivity from the「Notification」item
            case R.id.toolbar_notifications:
                this.onNotificationsSelected();
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

    // ------------------------
    // onOptionSelected methods
    // ------------------------

    // Will start SearchActivity with Intent and Extra ID
    public void onSearchSelected() {
        Intent mIntentSearch = new Intent(this, SearchActivity.class);
        mIntentSearch.putExtra(BUNDLED_EXTRA, SEARCH_ID);
        this.startActivity(mIntentSearch);
    }

    public void onNotificationsSelected() {
        Intent mIntentNotifications = new Intent(this, SearchActivity.class);
        mIntentNotifications.putExtra(BUNDLED_EXTRA, NOTIFICATIONS_ID);
        this.startActivity(mIntentNotifications);
    }

    public void onHelpSelected() {
        // Build an AlertDialog for the Help section
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog_Style);
        // Set Title and Message content
        builder.setTitle(R.string.toolbar_string_help);
        builder.setMessage(getText(R.string.alertdialog_content_help_string));
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog_Style);
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



