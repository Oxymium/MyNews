package com.raspberyl.mynews.controller;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
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

public class SearchActivity extends AppCompatActivity {

    private String mBundledValue;

    private Toolbar mToolbar;
    private Switch mNotificationsSwitch;
    private View mHorizontalDivider;

    private EditText mEditText_queries,
                     mEditText_beginDate,
                     mEditText_endDate;
    private Button mSearchButton;

    private String mSearchTerms;

    private String querySearchBox1, querySearchBox2, querySearchBox3, querySearchBox4, querySearchBox5, querySearchBox6;

    private String mfquery;

    private List<Docs> mSearchResults;

    private int SEARCH_ANSWER_CODE;

    private DocsAdapter mDocsAdapter;

    private Calendar mCalendar;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        configureToolbarSearch();

        emptyQueryFieldValues();
        getQueriesField();

        emptyAllCheckboxesValues();

        initializeOnClickBeginDateListener();
        initializeOnClickEndDateListener();

        //getAllSearchValues();

        startSearch();


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
        // Hides the horizontal divider
        mHorizontalDivider = findViewById(R.id.horizontal_divider_search);
        mHorizontalDivider.setVisibility(INVISIBLE);


    }

    private void getQueriesField() {
        mEditText_queries = findViewById(R.id.search_editText);
        mEditText_queries.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                mfquery = mEditText_queries.getText().toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

    }

    private void emptyQueryFieldValues() {
        mfquery = "";

    }

    // --------------------------------
    // CheckBoxes - Listeners & values
    // --------------------------------

    public void emptyAllCheckboxesValues () {
        // Initialize all checkboxes with empty values
        querySearchBox1 = "";
        querySearchBox2 = "";
        querySearchBox3 = "";
        querySearchBox4 = "";
        querySearchBox5 = "";
        querySearchBox6 = "";
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
                    querySearchBox1 = "";

                    break;

            case R.id.search_box2:
                if (checked) {
                    querySearchBox2 = "news_desk:(\"business\")";
                }
                else
                    querySearchBox2 = "";

                    break;

            case R.id.search_box3:
                if (checked) {
                    querySearchBox3 = "news_desk:(\"entrepreneurs\")";
                }

                else
                    querySearchBox3 = "";

                    break;

            case R.id.search_box4:
                if (checked) {
                    querySearchBox4 = "news_desk:(\"politics\")";
                }

                else
                    querySearchBox4 = "";

                    break;

            case R.id.search_box5:
                if (checked) {
                    querySearchBox5 = "news_desk:(\"sports\")";
                }
                else
                    querySearchBox5 = "";

                    break;

            case R.id.search_box6:
                if (checked) {
                    querySearchBox6 = "news_desk:(\"travel\")";
                }
                else
                    querySearchBox6 = "";
                    break;
        }
    }

    // -----------------------------------------------------------
    // BEGIN DATE : Text View Listener + DatePicker + Label update
    // -----------------------------------------------------------

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

    private void updateBeginDateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEditText_beginDate.setText(sdf.format(mCalendar.getTime()));
    }

    // ---------------------------------------------------------
    // END DATE : TextView Listener + DatePicker + Label update
    // ---------------------------------------------------------

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

    private void updateEndDateLabel() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        mEditText_endDate.setText(sdf.format(mCalendar.getTime()));
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

    private void notifyEmptyQueryfieldError() {

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

    // ------------------------------
    // Init Search form Search Button
    // ------------------------------

    private void startSearch() {

        mSearchButton = findViewById(R.id.button_search);
        mSearchButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        // Check that at least 1 Checkbox is checked, notify if none are

                        if (querySearchBox1.isEmpty() && querySearchBox2.isEmpty() && querySearchBox3.isEmpty() && querySearchBox4.isEmpty() && querySearchBox5.isEmpty() && querySearchBox6.isEmpty()
                                || mfquery.isEmpty()) {

                            if (querySearchBox1.isEmpty() && querySearchBox2.isEmpty() && querySearchBox3.isEmpty() && querySearchBox4.isEmpty() && querySearchBox5.isEmpty() && querySearchBox6.isEmpty()) {

                                notifyEmptyCheckboxesError();
                            }

                            if (mfquery.isEmpty()) {

                                notifyEmptyQueryfieldError();
                            }

                        }else{

                            searchQuery();
                        }


                    }
                });

    }

    // --------
    // API Call
    // --------

    private void searchQuery() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseWrapper> call = apiService.loadSearch(ApiKey.NYT_API_KEY, mfquery, this.getString(R.string.newest_sort_order), "20171214"   , "20180303");
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

                    }

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




