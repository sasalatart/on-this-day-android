package com.salatart.onthisday.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.salatart.onthisday.Adapters.EpisodesPagerAdapter;
import com.salatart.onthisday.Fragments.EpisodesFragment;
import com.salatart.onthisday.Models.EpisodesQuery;
import com.salatart.onthisday.R;

import java.text.DateFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.salatart.onthisday.Utils.Constants.DAY_KEY;
import static com.salatart.onthisday.Utils.Constants.MONTH_KEY;

public class EpisodesActivity extends AppCompatActivity {

    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;

    private int mDay;
    private int mMonth;

    public static Intent getIntent(Context context, int day, int month) {
        Intent intent = new Intent(context, EpisodesActivity.class);
        intent.putExtra(DAY_KEY, day);
        intent.putExtra(MONTH_KEY, month);
        return intent;
    }

    public void extractFromIntent() {
        Intent intent = getIntent();
        mDay = intent.getIntExtra(DAY_KEY, 1);
        mMonth = intent.getIntExtra(MONTH_KEY, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        ButterKnife.bind(this);

        extractFromIntent();
        setActionBar();
        setViewPager();
    }

    public void setActionBar() {
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) {
            return;
        }

        actionBar.setHomeButtonEnabled(true);

        String title = new DateFormatSymbols().getMonths()[mMonth - 1] + " " + mDay;
        actionBar.setTitle(title);
    }

    public void setViewPager() {
        EpisodesFragment eventsFragment = EpisodesFragment.build(new EpisodesQuery("events", mDay, mMonth));
        EpisodesFragment birthsFragment = EpisodesFragment.build(new EpisodesQuery("births", mDay, mMonth));
        EpisodesFragment deathsFragment = EpisodesFragment.build(new EpisodesQuery("deaths", mDay, mMonth));

        EpisodesPagerAdapter pagerAdapter = new EpisodesPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(eventsFragment, "events");
        pagerAdapter.addFragment(birthsFragment, "births");
        pagerAdapter.addFragment(deathsFragment, "deaths");

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
