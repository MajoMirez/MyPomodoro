package com.example.majo.mypomodoro.NumberPickerPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ericksson on 09/05/2017.
 */

public class MyPref4_Pomos {

    private static String keyValueTest = "pomo_amount";

    public static void setValueTest(Context context, int n){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(keyValueTest, n);
        editor.apply();
    }

    public static int getValueTest(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(keyValueTest, 4);
    }

}
