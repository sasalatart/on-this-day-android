package com.salatart.onthisday.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.salatart.onthisday.R;
import com.salatart.onthisday.Utils.DateUtils;
import com.salatart.onthisday.Utils.TransitionUtils;
import com.shawnlin.numberpicker.NumberPicker;

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

        TransitionUtils.setExplodeTransition(this);

        setTitle(getResources().getString(R.string.date_selection));
        setPickers();
        setCurrentDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_search:
                searchEpisodes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setPickers() {
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

    public void searchEpisodes() {
        int day = mDayPicker.getValue();
        int month = mMonthPicker.getValue();

        Intent intent = EpisodesActivity.getIntent(MainActivity.this, day, month);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
