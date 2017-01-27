package com.salatart.onthisday.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.salatart.onthisday.Adapters.EpisodesAdapter;
import com.salatart.onthisday.Listeners.IndexRequestListener;
import com.salatart.onthisday.Models.Episode;
import com.salatart.onthisday.R;
import com.salatart.onthisday.Utils.EpisodesUtils;
import com.salatart.onthisday.Utils.Routes;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DateFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class EpisodesActivity extends AppCompatActivity {

    public final static String DAY_MESSAGE = "com.example.salatart.DAY_MESSAGE";
    public final static String MONTH_MESSAGE = "com.example.salatart.MONTH_MESSAGE";
    public final static String TYPE_MESSAGE = "com.example.salatart.TYPE_MESSAGE";

    @BindView(R.id.loading_episodes) AVLoadingIndicatorView mSpinner;

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

        String title = mType + ": " + new DateFormatSymbols().getMonths()[mMonth - 1] + " " + mDay;
        setTitle(title);

        retrieveEpisodes();
    }

    public void extractFromIntent() {
        Intent intent = getIntent();
        mDay = intent.getIntExtra(DAY_MESSAGE, 1);
        mMonth = intent.getIntExtra(MONTH_MESSAGE, 1);
        mType = intent.getStringExtra(TYPE_MESSAGE);
    }

    public void retrieveEpisodes() {
        mSpinner.show();
        Request request = Routes.episodes(mDay, mMonth, mType);
        EpisodesUtils.RetrieveEpisodes(request, mType, new IndexRequestListener<Episode>() {
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

            @Override
            public void OnFailure(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EpisodesActivity.this, message, Toast.LENGTH_LONG).show();
                        mSpinner.hide();
                    }
                });
            }
        });
    }
}
