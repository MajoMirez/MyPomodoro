package com.example.majo.mypomodoro.NumberPickerPreferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

public class NumberPickerPref2_Break extends DialogPreference {

    private int Minute =5 ;
    private NumberPicker np= null;

    public NumberPickerPref2_Break(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSummary(String.valueOf(MyPref4_Pomos.getValueTest(getContext()))+" pomodoros");
    }

    @Override
    protected View onCreateDialogView() {
        np = new NumberPicker(getContext());
        return (np);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        np.setMaxValue(20);
        np.setMinValue(1);
        np.setValue(MyPref4_Pomos.getValueTest(getContext()));
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            Minute = np.getValue();
            setSummary(String.valueOf(Minute)+" pomodoros");
            MyPref4_Pomos.setValueTest(getContext(), Minute);

        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return (a.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        if (restoreValue) {
            if (defaultValue == null) {
                Minute=33;
            } else {
                Minute=Integer.parseInt(defaultValue.toString());
            }
        } else {
            Minute=Integer.parseInt(defaultValue.toString());
        }
    }

}