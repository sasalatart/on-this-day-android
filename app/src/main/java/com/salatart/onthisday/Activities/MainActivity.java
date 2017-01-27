package com.salatart.onthisday.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;

import com.salatart.onthisday.R;
import com.salatart.onthisday.Utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.picker_day) NumberPicker mDayPicker;
    @BindView(R.id.picker_month) NumberPicker mMonthPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setPickers();
        setCurrentDate();
    }

    private void setPickers() {
        mDayPicker.setMinValue(1);
        mDayPicker.setMaxValue(31);

        mMonthPicker.setMinValue(1);
        mMonthPicker.setMaxValue(12);

        mDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                DateUtils.checkDateValidity(MainActivity.this, newVal, mMonthPicker.getValue(), oldVal, picker);
            }
        });

        mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                DateUtils.checkDateValidity(MainActivity.this, mDayPicker.getValue(), newVal, oldVal, picker);
            }
        });
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

    public void searchEvents(View view) {
        searchEpisodes("events");
    }

    public void searchBirths(View view) {
        searchEpisodes("births");
    }

    public void searchDeaths(View view) {
        searchEpisodes("deaths");
    }

    public void searchEpisodes(String episodesType) {
        startActivity(EpisodesActivity.getIntent(MainActivity.this,
                mDayPicker.getValue(), mMonthPicker.getValue(), episodesType));
    }
}
