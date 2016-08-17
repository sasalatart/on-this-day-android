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

    public void searchEpisodes(View view) {
        Intent intent = new Intent(this, EpisodesActivity.class);
        intent.putExtra(DAY_MESSAGE, dayPicker.getValue());
        intent.putExtra(MONTH_MESSAGE, monthPicker.getValue());
        intent.putExtra(TYPE_MESSAGE, searchForSpinner.getSelectedItem().toString());
        startActivity(intent);
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
