package com.salatart.onthisday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

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
    }

    private void setSearchForSpinner() {
        mTypeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.episodes_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(typeAdapter);
    }
}
