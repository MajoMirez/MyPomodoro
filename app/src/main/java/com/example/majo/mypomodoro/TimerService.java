package com.example.majo.mypomodoro;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class TimerService extends IntentService {

    public TimerService() {
        super("TimerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service Started");
        String minute, hours;

        boolean working = Boolean.parseBoolean(intent.getStringExtra("WORKIN"));
        int min = Integer.parseInt(intent.getStringExtra("MIN"));

        Calendar c = Calendar.getInstance();
        int ihours = c.get(Calendar.HOUR_OF_DAY);
        int iminutes = c.get(Calendar.MINUTE) + min;

        if (iminutes > 59) {
            ihours += 1;
            iminutes = (iminutes - 60);
            if (ihours > 23) {
                ihours = 0;
            }
        }
        if (iminutes < 10) {
            minute = "0" + Integer.toString(iminutes);
        } else {
            minute = Integer.toString(iminutes);
        }
        if (ihours < 10) {
            hours = "0" + Integer.toString(ihours);
        } else {
            hours = Integer.toString(ihours);
        }

        Log.d(TAG, "hora de termino: " + hours + ":" + minute);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, 0);
        mBuilder.setContentIntent(pendingIntent);

        if (working) {
            mBuilder.setSmallIcon(R.drawable.clock_fast);
            mBuilder.setContentTitle("Tiempo de Trabajo");
        } else {
            mBuilder.setSmallIcon(R.drawable.coffee);
            mBuilder.setContentTitle("Tiempo de descanso");
        }
        mBuilder.setContentText("hora de termino: " + hours + ":" + minute);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());

        if (min == 0) {
            mNotificationManager.cancelAll();
        }


    }

}