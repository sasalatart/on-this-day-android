package com.salatart.onthisday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public final static String SEARCH_MESSAGE = "com.example.salatart.SEARCH_MESSAGE";

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
        String day = Integer.toString(dayPicker.getValue());
        String month = Integer.toString(monthPicker.getValue());
        String type = searchForSpinner.getSelectedItem().toString();

        String url = "";
        try {
            url = Util.getProperty("url", getApplicationContext());
            url += "/episodes?day=" + day + "&month=" + month + "&type=" + type;
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, EpisodesActivity.class);
        intent.putExtra(SEARCH_MESSAGE, url);
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
