package com.salatart.onthisday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    public final static String DAY_MESSAGE = "com.example.salatart.DAY_MESSAGE";
    public final static String MONTH_MESSAGE = "com.example.salatart.MONTH_MESSAGE";
    public final static String TYPE_MESSAGE = "com.example.salatart.TYPE_MESSAGE";
    public static OkHttpClient okHttpClient = new OkHttpClient();

    private NumberPicker mDayPicker;
    private NumberPicker mMonthPicker;
    private Spinner mTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPickers();
        setCurrentDate();
        setSearchForSpinner();
    }

    public void searchEpisodes(View view) {
        Intent intent = new Intent(this, EpisodesActivity.class);
        intent.putExtra(DAY_MESSAGE, mDayPicker.getValue());
        intent.putExtra(MONTH_MESSAGE, mMonthPicker.getValue());
        intent.putExtra(TYPE_MESSAGE, mTypeSpinner.getSelectedItem().toString());
        startActivity(intent);
    }

    private void setPickers() {
        mDayPicker = (NumberPicker) findViewById(R.id.dayPicker);
        mMonthPicker = (NumberPicker) findViewById(R.id.monthPicker);

        mDayPicker.setMinValue(1);
        mDayPicker.setMaxValue(31);

        mMonthPicker.setMinValue(1);
        mMonthPicker.setMaxValue(12);

        mDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                checkDateValidity(newVal, mMonthPicker.getValue(), oldVal, picker);
            }
        });

        mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                checkDateValidity(mDayPicker.getValue(), newVal, oldVal, picker);
            }
        });
    }

    private void checkDateValidity(int day, int month, int oldVal, NumberPicker picker) {
        String date = day + "/" + month;

        if (!Util.isDateValid(date)) {
            picker.setValue(oldVal);
            String text = new DateFormatSymbols().getMonths()[month - 1] + " " + day + " is not a valid date.";
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private void setCurrentDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        mDayPicker.setValue(currentDay);
        mMonthPicker.setValue(currentMonth);
    }

    private void setSearchForSpinner() {
        mTypeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.episodes_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(typeAdapter);
    }
}
