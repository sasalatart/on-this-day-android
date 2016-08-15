package com.salatart.onthisday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {

    private NumberPicker dayPicker;
    private NumberPicker monthPicker;
    private Spinner searchForSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPickers();
        setSearchForSpinner();
    }

    private void setPickers() {
        dayPicker = (NumberPicker) findViewById(R.id.dayPicker);
        monthPicker = (NumberPicker) findViewById(R.id.monthPicker);

        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
    }

    private void setSearchForSpinner() {
        searchForSpinner = (Spinner) findViewById(R.id.searchForSpinner);
        ArrayAdapter<CharSequence> searchForAdapter = ArrayAdapter.createFromResource(this,
                R.array.episodes_array, android.R.layout.simple_spinner_item);
        searchForAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchForSpinner.setAdapter(searchForAdapter);
    }
}
