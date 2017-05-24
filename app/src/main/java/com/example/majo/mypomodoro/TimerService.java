package com.example.majo.mypomodoro;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.ContentValues.TAG;


public class TimerService extends IntentService {

    public TimerService() {
        super("TimerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service Started");

        String key1 = intent.getStringExtra("WORKIN");
        boolean working = Boolean.parseBoolean(key1);

        //Log.d(TAG, "test: "+key1);



        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.androidauthority.com/"));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, 0);
        mBuilder.setContentIntent(pendingIntent);

        if(working) {
            mBuilder.setSmallIcon(R.drawable.ic_play_arrow_black_24dp);
            mBuilder.setContentTitle("Working Time");
            mBuilder.setContentText("work work");
        }
        else {
            mBuilder.setSmallIcon(R.drawable.ic_stop_black_24dp);
            mBuilder.setContentTitle("Rest Time");
            mBuilder.setContentText("z z z");
        }
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());






    }

}
