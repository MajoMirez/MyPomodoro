package com.example.majo.mypomodoro;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.CollationElementIterator;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    TextView mTextField, StartText, StopText;
    CountDownTimer waitTimer;
    private static final String FORMAT = "%02d:%02d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextField=(TextView)findViewById(R.id.textCountdown);
        StartText =(TextView)findViewById(R.id.textStart);
        StopText = (TextView)findViewById(R.id.textStop);
        setActivityBackgroundColor(0xff0000f0);
    }

    public void onClick(final View view){

        if(view.getId()==R.id.textStart) {
            StartText.setVisibility(view.GONE);
            StopText.setVisibility(view.VISIBLE);
            setActivityBackgroundColor(0xfff00000);

            waitTimer = new CountDownTimer(1500000, 1000) {
                public void onTick(long millisUntilFinished) {
                    mTextField.setText(""+String.format(FORMAT,
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }

                public void onFinish() {
                    restartTimer(view);
                }

            };
            waitTimer.start();
        }
        if (view.getId()==R.id.buttonConfig){
            Intent intent = new Intent(MainActivity.this, Config.class);
            startActivity(intent);
        }

        if (view.getId()==R.id.buttonStats) {
            Log.d(TAG, "STATS");
        }

        if (view.getId()==R.id.textStop) {
            if (waitTimer!=null) {
                waitTimer.cancel();
                waitTimer = null;
                restartTimer(view);
            }
        }
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

    public void restartTimer(final View view) {
        setActivityBackgroundColor(0xff0000f0);
        mTextField.setText("25:00");
        StartText.setVisibility(view.VISIBLE);
        StopText.setVisibility(view.GONE);
    }
}
