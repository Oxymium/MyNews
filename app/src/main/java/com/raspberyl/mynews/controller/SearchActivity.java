package com.raspberyl.mynews.controller;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.raspberyl.mynews.API.ApiClient;
import com.raspberyl.mynews.API.ApiInterface;
import com.raspberyl.mynews.API.ApiKey;
import com.raspberyl.mynews.R;
import com.raspberyl.mynews.model.Docs;
import com.raspberyl.mynews.model.ResponseWrapper;
import com.raspberyl.mynews.utils.DividerItemDecoration;
import com.raspberyl.mynews.utils.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static com.raspberyl.mynews.controller.MainActivity.BUNDLED_EXTRA;
import static com.raspberyl.mynews.controller.MainActivity.NOTIFICATIONS_ID;
import static com.raspberyl.mynews.controller.MainActivity.SEARCH_ID;
import static java.lang.Boolean.FALSE;
import static java.util.Calendar.DAY_OF_YEAR;

public class SearchActivity extends AppCompatActivity {

    private ActionBar mActionBar;

    private String mBundledValue;

    private Toolbar mToolbar;
    private Switch mNotificationsSwitch;
    private View mHorizontalDivider;

    private TextView mTextView_begin_label, mTextView_end_label;

    private CheckBox mCheckBox1, mCheckBox2, mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6;
    private boolean mCheckBoxChecked1, mCheckBoxChecked2, mCheckBoxChecked3, mCheckBoxChecked4, mCheckBoxChecked5, mCheckBoxChecked6;
    private boolean mSwitchChecked;

    private EditText mEditText_queries,
                     mEditText_beginDate,
                     mEditText_endDate;
    private Button mSearchButton;



    private List<String> queriesChecked;

    private String mSearchTerms;

    // ------------------------
    // Queries for the API Call
    // ------------------------
    private String mBeginDate, mEndDate;
    private String querySearchBox1, querySearchBox2, querySearchBox3, querySearchBox4, querySearchBox5, querySearchBox6;
    private String mQueryTextInput;
    private String mQueryTextTrimmed;
    private String mBundledFQuery;

    private List<Docs> mSearchResults;
    private List<Docs> mNoticationsSearchResults;

    private int SEARCH_ANSWER_CODE;

    private DocsAdapter mDocsAdapter;

    private Calendar mCalendar;

    // SharedPreferences saved values (notification)

    private final static String SHARED_PREFERENCES_SAVED_QUERY_TEXT_FIELD = "SHARED_PREFERENCES_SAVED_QUERY_TEXT_FIELD";

    private final static String SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_1 = "SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_1";
    private final static String SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_2 = "SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_2";
    private final static String SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_3 = "SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_3";
    private final static String SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_4 = "SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_4";
    private final static String SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_5 = "SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_5";
    private final static String SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_6 = "SHARED_PREFERENCES_SAVED_CHECKBOXES_STATE_6";

    private final static String SHARED_PREFERENCES_SAVED_SWITCH_STATE = "SHARED_PREFERENCES_SAVED_SWITCH_STATE";

    public static final String SHARED_PREFERENCES_SAVED_DATE = "SHARED_PREFERENCES_LAST_SAVED_DATE";

    // Used to retrieve notification terms
    public static final String SAVED_QUERY_NOTIFICATION = "SAVED_QUERY_NOTIFICATION";
    public static final String SAVED_CATEGORY_NOTIFICATION = "SAVED_CATEGORY_NOTIFICATION";
    public static final String SAVED_LAST_DOC_DATE = "SAVED_LAST_DOC_DATE";


    private final static String DEFAULT_DATE = "1700-01-01T01:01:01+0000";
    private String mSearchLastDocDate;
    private String mSearchCurrentDocDate;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        this.configureToolbarSearch();


    }


    // -----------------------------------------------------------------
    // Config Toolbar, and organize layout depending of receiving intent
    // -----------------------------------------------------------------

    private void configureToolbarSearch() {
        //Get the toolbar (Serialise)
        mToolbar = findViewById(R.id.toolbar_searchactivity);
        //Set the toolbar
        setSupportActionBar(mToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar mActionBar = getSupportActionBar();
        // Enable back button
        mActionBar.setDisplayHomeAsUpEnabled(true);
        // Fetch intent to determine origin
        mBundledValue = getIntent().getStringExtra(BUNDLED_EXTRA);

        switch (mBundledValue) {
            case SEARCH_ID:
                // Change ActionBar title to match SEARCH, and hide UI elements from NOTIFICATIONS
                mActionBar.setTitle(R.string.search_title);
                displaySearchInterfaceElements();
                // Reset all search values to default
                emptyQueryFieldValues();
                nullifyBeginDate();
                nullifyEndDate();
                emptyAllCheckboxesValues();
                // Attach listeners to DateField & TextQueries
                initializeOnClickBeginDateListener();
                initializeOnClickEndDateListener();
                fetchQueriesFromField();
                // Search
                startSearchFromSearchButton();


                break;
            case NOTIFICATIONS_ID:
                // Change ActionBar title to match NOTIFICATIONS, and hide UI elements from SEARCH
                mActionBar.setTitle(R.string.notifications_title);
                displayNotificationsInterfaceElements();
                // Reset
                emptyQueryFieldValues();
                emptyAllCheckboxesValues();
                fetchQueriesFromField();
                //
                setNotificationsSwitchListener();


                break;
        }

    }

    // ---------------------------------
    // Display/Hide UI elements methods
    // ---------------------------------

    // Hides Notifications elements
    private void displaySearchInterfaceElements() {
        hideNotificationsHorizontalDivider();
        hideNotificationsSwitch();
    }

    // Hides Search elements
    private void displayNotificationsInterfaceElements() {
        hideSearchBeginDateLabel();
        hideSearchBeginDatePicker();
        hideSearchEndDateLabel();
        hideSearchEndDatePicker();
        hideSearchButton();
    }

    // Hides Text label: "Begin date"
    private void hideSearchBeginDateLabel() {
        mTextView_begin_label = findViewById(R.id.begin_date_textview_label);
        mTextView_begin_label.setVisibility(GONE);
    }

    // Hides TextView: "Begin date"
    private void hideSearchBeginDatePicker() {
        mEditText_beginDate = findViewById(R.id.begin_date_edittext);
        mEditText_beginDate.setVisibility(GONE);
    }

    // Hides Text label: "End date"
    private void hideSearchEndDateLabel() {
        mTextView_end_label = findViewById(R.id.end_date_textview_label);
        mTextView_end_label.setVisibility(GONE);
    }

    // Hides TextView: "End date"
    private void hideSearchEndDatePicker() {
        mEditText_endDate = findViewById(R.id.end_date_edittext);
        mEditText_endDate.setVisibility(GONE);
    }

    // Hides View that acts as an HorizontalDivider
    private void hideNotificationsHorizontalDivider() {
        mHorizontalDivider = findViewById(R.id.horizontal_divider_search);
        mHorizontalDivider.setVisibility(GONE);
    }

    // Hides the Search button
    private void hideSearchButton() {
        mSearchButton = findViewById(R.id.button_search);
        mSearchButton.setVisibility(GONE);
    }
    // Hides the Notifications switch
    private void hideNotificationsSwitch() {
        mNotificationsSwitch = findViewById(R.id.notification_switch);
        mNotificationsSwitch.setVisibility(GONE); }

    // -----------------------------------------------------------------
    // Query TextView input
    // -----------------------------------------------------------------

    // Fetch Text from the query field
    private void fetchQueriesFromField() {
        mEditText_queries = findViewById(R.id.search_editText);
        mEditText_queries.addTextChangedListener(new TextWatcher()
        {
            // Each time a character is input, it will update the String variable
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                mQueryTextInput = mEditText_queries.getText().toString();
                // Remove unnecessary spaces
                mQueryTextTrimmed = mQueryTextInput.trim().replaceAll(" +", " ");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

    }

    // Initialize text variables with empty values
    private void emptyQueryFieldValues() {
        mQueryTextInput = "";
        mQueryTextTrimmed = "";

    }

    // --------------------------------
    // CheckBoxes - Listeners & values
    // --------------------------------

    // Initialize Checkboxes to UNCHECKED position, and set their associated variables with empty values
    public void emptyAllCheckboxesValues () {
        mCheckBox1 = findViewById(R.id.search_box1);
        mCheckBox2 = findViewById(R.id.search_box2);
        mCheckBox3 = findViewById(R.id.search_box3);
        mCheckBox4 = findViewById(R.id.search_box4);
        mCheckBox5 = findViewById(R.id.search_box5);
        mCheckBox6 = findViewById(R.id.search_box6);

        mCheckBox1.setChecked(false);
        mCheckBox2.setChecked(false);
        mCheckBox3.setChecked(false);
        mCheckBox4.setChecked(false);
        mCheckBox5.setChecked(false);
        mCheckBox6.setChecked(false);

        querySearchBox1 = "";
        querySearchBox2 = "";
        querySearchBox3 = "";
        querySearchBox4 = "";
        querySearchBox5 = "";
        querySearchBox6 = "";
    }

    // Listener on Checkboxes to update category values if checked. Reset value to empty if unchecked
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.search_box1:
                if (checked) {
                     querySearchBox1 = this.getString(R.string.search_box1);
                     mCheckBoxChecked1 = true;
                }
                else
                    querySearchBox1 = "";
                    mCheckBoxChecked1 = false;


                break;

            case R.id.search_box2:
                if (checked) {
                    querySearchBox2 = this.getString(R.string.search_box2);
                    mCheckBoxChecked2 = true;
                }
                else
                    querySearchBox2 = "";
                    mCheckBoxChecked2 = false;

                    break;

            case R.id.search_box3:
                if (checked) {
                    querySearchBox3 = this.getString(R.string.search_box3);
                    mCheckBoxChecked3 = true;
                }

                else
                    querySearchBox3 = "";
                    mCheckBoxChecked3 = false;

                    break;

            case R.id.search_box4:
                if (checked) {
                    querySearchBox4 = this.getString(R.string.search_box4);
                    mCheckBoxChecked4 = true;
                }

                else
                    querySearchBox4 = "";
                    mCheckBoxChecked4 = false;

                    break;

            case R.id.search_box5:
                if (checked) {
                    querySearchBox5 = this.getString(R.string.search_box5);
                    mCheckBoxChecked5 = true;
                }
                else
                    querySearchBox5 = "";
                    mCheckBoxChecked5 = false;

                break;

            case R.id.search_box6:
                if (checked) {
                    querySearchBox6 = this.getString(R.string.search_box6);
                    mCheckBoxChecked6 = true;
                }
                else
                    querySearchBox6 = "";
                    mCheckBoxChecked6 = false;

                break;
        }
    }


    // -----------------------------------------------------------
    // BEGIN DATE : Text View Listener + DatePicker + Label update
    // -----------------------------------------------------------

    // Attach listener to TextView that calls a DatePickerDialog when clicked on
    private void initializeOnClickBeginDateListener() {
        mEditText_beginDate = findViewById(R.id.begin_date_edittext);
        mCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateBeginDateLabel();
            }

        };

        mEditText_beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SearchActivity.this, date, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    // Update text field value with the chosen Date, date displayed should be: dd/MM/yyyy, for example 10/03/2018 for 10th March 2018
    private void updateBeginDateLabel() {
        String mFormat = "dd/MM/yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(mFormat, Locale.US);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

        mEditText_beginDate.setText(sdf.format(mCalendar.getTime()));
        mBeginDate = sdf2.format(mCalendar.getTime());
    }

    // Sets date value to null
    private void nullifyBeginDate() {
        mBeginDate = null;
    }

    // ---------------------------------------------------------
    // END DATE : TextView Listener + DatePicker + Label update
    // ---------------------------------------------------------

    // Attach listener to TextView that calls a DatePickerDialog when clicked on
    private void initializeOnClickEndDateListener() {

        mEditText_endDate = findViewById(R.id.end_date_edittext);
        mCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndDateLabel();
            }

        };

        mEditText_endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(SearchActivity.this, date, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    // Update text field value with the chosen Date, date displayed should be: dd/MM/yyyy, for example 10/03/2018 for 10th March 2018
    private void updateEndDateLabel() {
        String dateFormat = "MM/dd/yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

        mEditText_endDate.setText(sdf.format(mCalendar.getTime()));
        mEndDate = sdf2.format(mCalendar.getTime());

    }

    // Sets date value to null
    private void nullifyEndDate() {
        mEndDate = null;
    }


    // ------------------------------
    // Bundled values to pass to Call
    // ------------------------------

    // Fetch all queries from the TextField and reformat them in a proper String to later pass as a valid call argument
    private void fetchBundledStringFQueries() {
        String mFQueryReformated = "news_desk:(";
        List<String> news_desk = new ArrayList<>();
        news_desk.add(querySearchBox1);
        news_desk.add(querySearchBox2);
        news_desk.add(querySearchBox3);
        news_desk.add(querySearchBox4);
        news_desk.add(querySearchBox5);
        news_desk.add(querySearchBox6);

        for(int i = 0;  i < news_desk.size()-1; i++){
            mFQueryReformated = mFQueryReformated.concat('"'+news_desk.get(i)+'"'+" ");
        }
        mBundledFQuery = mFQueryReformated.concat('"'+news_desk.get(news_desk.size()-1)+'"'+")");
    }



    // ------------------------------
    // Init Search form Search Button
    // ------------------------------

    // Verify is all requirements are met before starting a query
    private boolean checkQueryRequirements() {
        /* Returns TRUE = ALL REQUIREMENTS MET
           Returns FALSE = IF THE 2 REQUIREMENTS AREN'T MET
           1st requirement: - At least 1 CheckBox must be checked (has not an empty String value)
           2st requirement: - Text query must NOT be empty  */
        if (querySearchBox1.isEmpty() && querySearchBox2.isEmpty() && querySearchBox3.isEmpty() && querySearchBox4.isEmpty() && querySearchBox5.isEmpty() && querySearchBox6.isEmpty()
                || mQueryTextInput.isEmpty()) {

            if (querySearchBox1.isEmpty() && querySearchBox2.isEmpty() && querySearchBox3.isEmpty() && querySearchBox4.isEmpty() && querySearchBox5.isEmpty() && querySearchBox6.isEmpty()) {
                return false;
            }

            if (mQueryTextInput.isEmpty()) {
                return false;
            }

        }else{

            return true;
        }
        return true;
    }

    // Call Error messages for each specified failed requirement
    private void callErrorMessages() {

        if (querySearchBox1.isEmpty() && querySearchBox2.isEmpty() && querySearchBox3.isEmpty() && querySearchBox4.isEmpty() && querySearchBox5.isEmpty() && querySearchBox6.isEmpty()
                || mQueryTextInput.isEmpty()) {
            // Empty Checkbox failed requirement
            if (querySearchBox1.isEmpty() && querySearchBox2.isEmpty() && querySearchBox3.isEmpty() && querySearchBox4.isEmpty() && querySearchBox5.isEmpty() && querySearchBox6.isEmpty()) {
                notifyEmptyCheckboxesError();
            }
            // Empty Query field failed requirement
            if (mQueryTextInput.isEmpty()) {

                notifyEmptyQueryFieldError();
            }
        }
    }


    // Sets a listener to the Search button and initiate an API Call if requirements are met
    private void startSearchFromSearchButton() {

        mSearchButton = findViewById(R.id.button_search);
        mSearchButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        /*1st requirement: - At least 1 CheckBox must be checked (has not an empty String value)
                          2st requirement: - Text query must NOT be empty */
                        if (checkQueryRequirements())
                            startSearchApiCall();
                        else
                            callErrorMessages();
                    }
                });

    }

    // Sets a listener to the Notifications switch. Resets to default if missing requirements.
    private void setNotificationsSwitchListener() {

        mNotificationsSwitch = findViewById(R.id.notification_switch);
        mNotificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (checkQueryRequirements()) {
                            mNotificationsSwitch.setChecked(true);
                            // Turn value to true (for SP save)
                            mSwitchChecked = true;
                            // Saves VALUES to call later to compare with Alarm
                            saveNotficationsFields();
                            // Calls API
                            searchNotificationsApiCall();
                            // START ALARM
                            setDailyAlarmNotificationOn();


                        }

                        // Reset Notifications Switch to OFF state if minimum requirements are not met
                        // & Reset
                        else {
                            callErrorMessages();
                            mNotificationsSwitch.setChecked(false);
                        }
                    }

                    else {
                        mNotificationsSwitch.setChecked(false);
                            emptyAllNotificationsFields();
                            setDailyAlarmNotificationOff();
                    }

                }
            });
        }

        private void emptyAllNotificationsFields() {
            // 1 - Clear Text field
             mEditText_queries.getText().clear();
             // 2 - Set Text value to empty
            emptyQueryFieldValues();
              // 3 - Reset all Checkboxes to UNCHECKED and empty their values
               emptyAllCheckboxesValues();
           }


        private void saveNotficationsFields() {
            // 1 - Save Query
            SharedPreferencesUtils.saveString(getBaseContext(), SAVED_QUERY_NOTIFICATION, mQueryTextInput);
            // 2 - Save FQuery
            SharedPreferencesUtils.saveString(getBaseContext(), SAVED_CATEGORY_NOTIFICATION, mBundledFQuery);
        }



    // --------
    // API Call
    // --------

    private void startSearchApiCall() {

        fetchBundledStringFQueries();

        System.out.println(mBundledFQuery);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseWrapper> call = apiService.loadSearch(ApiKey.NYT_API_KEY, mQueryTextTrimmed, mBundledFQuery, this.getString(R.string.newest_sort_order), mBeginDate, mEndDate);
        call.enqueue(new Callback<com.raspberyl.mynews.model.ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                if(response.isSuccessful()) {

                    mSearchResults = response.body().getResponse().getDocs();

                    // If List is empty, then display Error message
                    if (mSearchResults.isEmpty()) {
                        notifyEmptySearchResultsError();
                    }

                    else {

                    setContentView(R.layout.article_recyclerview);
                    mRecyclerView = findViewById(R.id.recyclerview_layout);
                    mDocsAdapter = new DocsAdapter(mSearchResults, SearchActivity.this);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mRecyclerView.setAdapter(mDocsAdapter);
                    // Horizontal custom Divider
                    DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), R.drawable.custom_divider);
                    mRecyclerView.addItemDecoration(mDividerItemDecoration);

                    }

                }else{

                System.out.println("ERROR 1");
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

                Log.e(TAG, t.toString());
                System.out.println("ERROR 2");

            }
        });

    }

    // ---------------------------
    // API Notification Call error
    // ---------------------------


    private void searchNotificationsApiCall() {

        fetchBundledStringFQueries();

        System.out.println(mBundledFQuery);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseWrapper> call = apiService.loadSearch(ApiKey.NYT_API_KEY, mQueryTextTrimmed, mBundledFQuery, this.getString(R.string.newest_sort_order), null, null);
        call.enqueue(new Callback<com.raspberyl.mynews.model.ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                if(response.isSuccessful()) {


                    mNoticationsSearchResults = response.body().getResponse().getDocs();

                    // If List is empty, then display Error message
                    if (mNoticationsSearchResults.isEmpty()) {
                        notifyEmptyNotificationsSearchResultsError();
                        // Will still add a value to the date to compare later.
                        mSearchCurrentDocDate = DEFAULT_DATE;
                    }

                    else {

                        mSearchCurrentDocDate = mNoticationsSearchResults.get(0).getPub_date();
                    }

                    // Saves the current published date, value should be either DEFAULT_PAST_DATE, or a new one
                    SharedPreferencesUtils.saveString(SearchActivity.this, SHARED_PREFERENCES_SAVED_DATE, mSearchCurrentDocDate);


                    }else{

                    System.out.println("ERROR 1");
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

                Log.e(TAG, t.toString());
                System.out.println("ERROR 2");

            }
        });

    }

    // ------------------------------------------------
    // AlarmManager
    // ------------------------------------------------

    private void setDailyAlarmNotificationOn() {
        Calendar mAlarmCalendar = Calendar.getInstance();
        mAlarmCalendar.set(Calendar.HOUR_OF_DAY, 7); // For 7 AM
        mAlarmCalendar.set(Calendar.MINUTE, 0);
        mAlarmCalendar.set(Calendar.SECOND, 0);

        //Check if calendar is set in the past
        if (mAlarmCalendar.getTimeInMillis() < System.currentTimeMillis()) {
            //Add one day to the calendar (or whatever repeat interval you would like)
            mAlarmCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        Intent mIntent = new Intent(this, NotifyService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, mIntent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, mAlarmCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(SearchActivity.this, "Start Alarm", Toast.LENGTH_LONG).show();
    }

    private void setDailyAlarmNotificationOff() {

        Intent mIntent = new Intent(this, NotifyService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, mIntent, 0);

        alarmManager.cancel(pendingIntent);


    }


    // ------------------------------------------------
    // AlertDialog Errors
    // ------------------------------------------------

    private void notifyEmptyCheckboxesError() {

        // Build an AlertDialog for the Help section
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this, R.style.AlertDialog_Style);
        // Set Title and Message content
        builder.setTitle(R.string.alertdialog_title_error_search);
        builder.setMessage(getText(R.string.alertdialog_content_empty_checkboxes_search));
        // Neutral button
        builder.setNeutralButton(R.string.alertdialog_button_neutral, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void notifyEmptyQueryFieldError() {

        // Build an AlertDialog for the Help section
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this, R.style.AlertDialog_Style);
        // Set Title and Message content
        builder.setTitle(R.string.alertdialog_title_error_search);
        builder.setMessage(getText(R.string.alertdialog_content_empty_query_search));
        // Neutral button
        builder.setNeutralButton(R.string.alertdialog_button_neutral, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void notifyEmptySearchResultsError() {

        // Build an AlertDialog for the Help section
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this, R.style.AlertDialog_Style);
        // Set Title and Message content
        builder.setTitle(R.string.alertdialog_title_error_search);
        builder.setMessage(getText(R.string.alertdialog_content_empty_results_search));
        // Neutral button
        builder.setNeutralButton(R.string.alertdialog_button_neutral, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void notifyEmptyNotificationsSearchResultsError() {

        // Build an AlertDialog for the Help section
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this, R.style.AlertDialog_Style);
        // Set Title and Message content
        builder.setTitle(R.string.alertdialog_title_error_search);
        builder.setMessage(getText(R.string.alertdialog_content_empty_results_notifications_search));
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




