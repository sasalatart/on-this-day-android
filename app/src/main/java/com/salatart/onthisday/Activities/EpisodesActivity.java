package com.salatart.onthisday.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.salatart.onthisday.Adapters.EpisodesAdapter;
import com.salatart.onthisday.Listeners.IndexRequestListener;
import com.salatart.onthisday.Models.Episode;
import com.salatart.onthisday.R;
import com.salatart.onthisday.Utils.EpisodesUtils;
import com.salatart.onthisday.Utils.Routes;

import java.text.DateFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class EpisodesActivity extends AppCompatActivity {

    public final static String DAY_MESSAGE = "com.example.salatart.DAY_MESSAGE";
    public final static String MONTH_MESSAGE = "com.example.salatart.MONTH_MESSAGE";
    public final static String TYPE_MESSAGE = "com.example.salatart.TYPE_MESSAGE";

    @BindView(R.id.label_search_type) TextView mLabelType;
    @BindView(R.id.label_search_date) TextView mLabelDate;
    @BindView(R.id.loading_episodes) com.wang.avi.AVLoadingIndicatorView mSpinner;

    private int mDay;
    private int mMonth;
    private String mType;

    public static Intent getIntent(Context context, int day, int month, String type) {
        Intent intent = new Intent(context, EpisodesActivity.class);
        intent.putExtra(DAY_MESSAGE, day);
        intent.putExtra(MONTH_MESSAGE, month);
        intent.putExtra(TYPE_MESSAGE, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        extractFromIntent();
        setTextViews();
        retrieveEpisodes();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        return true;
    }

    public void extractFromIntent() {
        Intent intent = getIntent();
        mDay = intent.getIntExtra(DAY_MESSAGE, 1);
        mMonth = intent.getIntExtra(MONTH_MESSAGE, 1);
        mType = intent.getStringExtra(TYPE_MESSAGE);
    }

    public void setTextViews() {
        mLabelType.setText(mType + "s");
        mLabelDate.setText(new DateFormatSymbols().getMonths()[mMonth - 1] + " " + mDay);
    }

    public void retrieveEpisodes() {
        mSpinner.show();
        Request request = Routes.episodes(mDay, mMonth, mType);
        EpisodesUtils.RetrieveEpisodes(EpisodesActivity.this, request, mSpinner, new IndexRequestListener<Episode>() {
            @Override
            public void OnSuccess(final Episode[] episodes) {
                EpisodesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EpisodesAdapter episodesAdapter = new EpisodesAdapter(EpisodesActivity.this, episodes);
                        ListView listView = (ListView) findViewById(R.id.list_view_episodes);
                        listView.setAdapter(episodesAdapter);
                        mSpinner.hide();
                    }
                });
            }
        });
    }
}
