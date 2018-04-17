package com.raspberyl.mynews.controller;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.raspberyl.mynews.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        configureToolbarSearch();





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
        switch(view.getId()) {
            case R.id.search_box1:
                if (checked) {
                    Toast.makeText(getBaseContext(), "Test", Toast.LENGTH_LONG).show();
                }
                // Put some meat on the sandwich
            else
                // Remove the meat
                break;
            case R.id.search_box2:
                if (checked) {}
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.search_box3:
                if (checked) {}
                // Put some meat on the sandwich
            else
                // Remove the meat
                break;
            case R.id.search_box4:
                if (checked) {}
                // Put some meat on the sandwich
            else
                // Remove the meat
                break;
            case R.id.search_box5:
                if (checked) {}
                // Put some meat on the sandwich
            else
                // Remove the meat
                break;
            case R.id.search_box6:
                if (checked) {}
                // Put some meat on the sandwich
            else
                // Remove the meat
                break;
        }
    }
    private void launchSearchButton() {

        mSearchButton = findViewById(R.id.button_search);
        mSearchButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Toast.makeText(getBaseContext(), "Test", Toast.LENGTH_SHORT);
                    }
                });

    }

}

