package com.raspberyl.mynews.controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.raspberyl.mynews.API.ApiClient;
import com.raspberyl.mynews.API.ApiInterface;
import com.raspberyl.mynews.API.ApiKey;
import com.raspberyl.mynews.R;
import com.raspberyl.mynews.model.Docs;
import com.raspberyl.mynews.model.ResponseWrapper;
import com.raspberyl.mynews.utils.SharedPreferencesUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.raspberyl.mynews.controller.SearchActivity.SHARED_PREFERENCES_SAVED_DATE;


public class NotifyService extends Service {

    private String CHANNEL_ID = "my_channel_01";
    private CharSequence name = "my_channel";
    private String Description = "This is my channel";

    private String mFetchedQueries;
    private String mFetchedCategories;
    private static final String DEFAULT_STRING_VALUE = "";

    private String mPreviousDocPublishedDate;
    private String mCurrentDocPublishedDate;
    private final static String DEFAULT_DATE_VALUE = "1700-01-01T01:01:01+0000";



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        // Fetch last date from SharedPreferences
        mPreviousDocPublishedDate = SharedPreferencesUtils.loadString(getBaseContext(), SHARED_PREFERENCES_SAVED_DATE, DEFAULT_STRING_VALUE);
        // Initiate a search
        callApi();

        // Will try 10sec later to compare
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // Test newly queried Date versus previous one
                if (!mCurrentDocPublishedDate.equals(mPreviousDocPublishedDate)) {
                    // If different, send a notification to the user
                    sendNotification();
                    // Updated current date into previous one (to reuse)
                    SharedPreferencesUtils.saveString(getBaseContext(), SHARED_PREFERENCES_SAVED_DATE, mCurrentDocPublishedDate); }

                else {
                    // Test
                    sendNotification();
                }
            }
        }, 10000);





    }

    // Initiate search with the notification's values
    public void callApi() {

        mFetchedQueries = SharedPreferencesUtils.loadString(getBaseContext(), SearchActivity.SAVED_QUERY_NOTIFICATION, DEFAULT_STRING_VALUE);
        mFetchedCategories = SharedPreferencesUtils.loadString(getBaseContext(), SearchActivity.SAVED_CATEGORY_NOTIFICATION, DEFAULT_STRING_VALUE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseWrapper> call = apiService.loadSearch(ApiKey.NYT_API_KEY, mFetchedQueries, mFetchedCategories, this.getString(R.string.newest_sort_order), null, null);
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                if(response.isSuccessful()) {

                    // Retrieve first Doc published_date
                    mCurrentDocPublishedDate = response.body().getResponse().getDocs().get(0).getPub_date();

                    // If this value is empty (does not exist), then add a default value
                    if (mCurrentDocPublishedDate.isEmpty()) {
                       mCurrentDocPublishedDate = DEFAULT_DATE_VALUE;
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


    public void sendNotification() {

        int NOTIFICATION_ID = 234;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Notifications need a channel from SDK >= 26 (Oreo)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            // Build channel
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.mn_navigation_drawer_logo)
                .setContentTitle("Newly article published")
                .setContentText("Test message");

        Intent resultIntent = new Intent(this.getApplicationContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
