package com.example.majo.mypomodoro;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import static android.content.ContentValues.TAG;


public class TimerService extends IntentService {

    public TimerService() {
        super("TimerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service Started");
        String time2 = intent.getStringExtra("TIME");
        int time = Integer.parseInt(time2);
        String type = intent.getStringExtra("TYPE");

        Log.d(TAG, Integer.toString(time));
        Log.d(TAG, type);
    }

}
