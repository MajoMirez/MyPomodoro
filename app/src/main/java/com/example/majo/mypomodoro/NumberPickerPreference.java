package com.example.majo.mypomodoro;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import com.example.majo.mypomodoro.Util.MyPreferences;

/*  TODO: Numberpicker generico que pille en internet, hasta ahora solo muestra el Numberpicker
 *  y puedo setear algunos valores, pero no guarda los valores en sharedpreferences
 */

public class NumberPickerPreference extends DialogPreference {

    private int Minute = 25;
    private NumberPicker np= null;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
        setSummary(String.valueOf(MyPreferences.getValueTest(getContext())));
    }

    @Override
    protected View onCreateDialogView() {
        np = new NumberPicker(getContext());
        return (np);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        np.setMaxValue(60);
        np.setMinValue(1);
        np.setValue(MyPreferences.getValueTest(getContext()));
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
            Minute = np.getValue();
            setSummary(String.valueOf(Minute));
            MyPreferences.setValueTest(getContext(), Minute);
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