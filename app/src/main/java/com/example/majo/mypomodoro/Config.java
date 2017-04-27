package com.example.majo.mypomodoro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.app.Activity;
import android.widget.NumberPicker;
import android.widget.TextView;

public class Config extends AppCompatActivity {

    TextView numberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        numberView=(TextView)findViewById(R.id.textView);

        NumberPicker npick= (NumberPicker) findViewById(R.id.nPicker);
        npick.setMaxValue(60);
        npick.setMinValue(1);
        npick.setWrapSelectorWheel(true);
        npick.setOnValueChangedListener( new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberView.setText("Selected number is "+ newVal);
            }
        });
    }
}
