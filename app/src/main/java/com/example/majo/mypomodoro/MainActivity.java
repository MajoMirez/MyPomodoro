package com.example.majo.mypomodoro;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.lang.reflect.Array;
import java.text.CollationElementIterator;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {


    //TODO: agregar contador de pomos completos
    TextView TimerText, InfoText;
    ImageButton PlayButton, StopButton, NextButton;
    CountDownTimer waitTimer;
    private static final String FORMAT = "%02d:%02d";
    int Wmin, Bmin, Lmin, CPomos;
    int pomoCount=0;
    int completedCycles=0;
    boolean working=false;
    int[] CArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimerText=(TextView)findViewById(R.id.textCountdown);
        PlayButton=(ImageButton)findViewById(R.id.buttonPlay);
        StopButton =(ImageButton) findViewById(R.id.textStop);
        NextButton =(ImageButton) findViewById(R.id.textNext);
        InfoText =(TextView)findViewById(R.id.textInfo);
        displaySharedPreferences();
        StopButton.setVisibility(View.GONE);
        NextButton.setVisibility(View.GONE);
        setActivityBackgroundColor(CArray[0]);
        TimerText.setText(Integer.toString(Wmin)+":00");
    }


    //TODO: de-hardcode colors
    public void displaySharedPreferences(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Wmin=prefs.getInt("work_time",25);
        Bmin=prefs.getInt("break_time",5);
        Lmin=prefs.getInt("long_time",15);
        CPomos=prefs.getInt("pomo_amount",4);
        Log.d(TAG,"Wmin: "+Wmin+"\nBmin: "+Bmin+"\nLmin :"+Lmin+"\nCPomos: "+CPomos);
        CArray= new int[4];
        CArray[0]=0xff86d37d; // main
        CArray[1]=0xfff72d31; // working
        CArray[2]=0xffffd559; // break
        CArray[3]=0xff86b0ff; // long
    }


    public void onClick(final View view){
        if (view.getId()==R.id.buttonConfig){
            Intent intent = new Intent(MainActivity.this, Preferences.class);
            startActivity(intent);
        }

        if(view.getId()==R.id.buttonPlay) {
            displaySharedPreferences();
            PlayButton.setVisibility(view.GONE);
            StopButton.setVisibility(view.VISIBLE);
            NextButton.setVisibility(view.VISIBLE);
            setActivityBackgroundColor(CArray[1]);
            startTimer(view, Wmin, true); //WORKING TIMER
        }

        if (view.getId()==R.id.textNext){
            selectNextTimer(view);
        }

        if (view.getId()==R.id.textStop) {
            if (waitTimer!=null) {
                waitTimer.cancel();
                waitTimer = null;
            }

            restartTimer(view);
            pomoCount=0;
            InfoText.setText(getString(R.string.info)+"\nCiclos completados: "+completedCycles);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if (Wmin != prefs.getInt("work_time", 0) && prefs.getInt("work_time",0)!=0) {
            this.recreate();
        }
    }


    public void restartTimer(final View view) { //interfaz
        setActivityBackgroundColor(CArray[0]);
        TimerText.setText(Wmin+":00");
        PlayButton.setVisibility(view.VISIBLE);
        StopButton.setVisibility(view.GONE);
        NextButton.setVisibility(view.GONE);
        sendNotification(view,0);
    }


    public void startTimer(final View view, int min, boolean type){
        if (waitTimer!=null){
            waitTimer.cancel();
            waitTimer=null;
        }
        waitTimer = new CountDownTimer(min*60000, 1000) {
            public void onTick(long millisUntilFinished) {
                String marcador=String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                TimerText.setText(marcador);
            }

            public void onFinish() {
                selectNextTimer(view);
            }
        }.start();
        working=type; //true:working, false: break/long break
        InfoText.setText(escriba());
        sendNotification(view,min);
    }


    public void selectNextTimer(View view){
        if(working){
            pomoCount+=1;
            if(pomoCount<CPomos) {
                setActivityBackgroundColor(CArray[2]);
                startTimer(view, Bmin,false);
            }else{
                completedCycles+=1;
                setActivityBackgroundColor(CArray[3]);
                startTimer(view, Lmin,false);
                pomoCount=0;
            }

            Log.d(TAG,"completed pomos: "+pomoCount+"\ncompleted cycles: "+completedCycles);
        }else{
            setActivityBackgroundColor(CArray[1]);
            startTimer(view, Wmin, true);
        }
    }


    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }


    public String escriba(){
        String p1,p2;
        if(working){
            p1="Trabajo!!";
        }else{
            if(pomoCount<CPomos){
                p1="Descanso (8)";
            }else{
                p1="Coffe Break <3";
            }
        }
        p2="\nPomodoros completados: "+pomoCount+"/"+CPomos+"\nCiclos Completados: "+completedCycles;
        return p1+p2;
    }


    public void sendNotification(View view, int min) {

        Intent i = new Intent(MainActivity.this, TimerService.class);
        i.putExtra("WORKIN", Boolean.toString(working));
        i.putExtra("MIN",Integer.toString(min));
        this.startService(i);

    }

}
