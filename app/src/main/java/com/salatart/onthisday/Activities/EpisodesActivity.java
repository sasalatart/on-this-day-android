package com.salatart.onthisday.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.salatart.onthisday.Adapters.EpisodesAdapter;
import com.salatart.onthisday.Models.Episode;
import com.salatart.onthisday.R;
import com.salatart.onthisday.Utils.Util;

import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class EpisodesActivity extends AppCompatActivity {

    public final static String DAY_MESSAGE = "com.example.salatart.DAY_MESSAGE";
    public final static String MONTH_MESSAGE = "com.example.salatart.MONTH_MESSAGE";
    public final static String TYPE_MESSAGE = "com.example.salatart.TYPE_MESSAGE";

    @BindView(R.id.loading_episodes) com.wang.avi.AVLoadingIndicatorView mSpinner;

    private String mQuery;
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
        buildQuery();
        retrieveEpisodes();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    public void extractFromIntent() {
        Intent intent = getIntent();
        mDay = intent.getIntExtra(DAY_MESSAGE, 1);
        mMonth = intent.getIntExtra(MONTH_MESSAGE, 1);
        mType = intent.getStringExtra(TYPE_MESSAGE);
    }

    public void setTextViews() {
        TextView typeTV = (TextView) findViewById(R.id.label_search_type);
        typeTV.setText(mType + "s");
        TextView dateTV = (TextView) findViewById(R.id.label_search_date);
        dateTV.setText(new DateFormatSymbols().getMonths()[mMonth - 1] + " " + mDay);
    }

    public void buildQuery() {

        try {
            mQuery = Util.getProperty("url", getApplicationContext()) + "/episodes";
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse(mQuery).newBuilder();
        urlBuilder.addQueryParameter("day", mDay + "");
        urlBuilder.addQueryParameter("month", mMonth + "");
        urlBuilder.addQueryParameter("type", mType);
        mQuery = urlBuilder.build().toString();
    }

    public void retrieveEpisodes() {
        mSpinner.show();
        Request request = new Request.Builder().url(mQuery).build();
        MainActivity.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    final Episode[] episodes = Episode.fromJSON(response.body().string());
                    EpisodesActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EpisodesAdapter episodesAdapter = new EpisodesAdapter(EpisodesActivity.this, episodes);
                            ListView listView = (ListView) findViewById(R.id.list_view_episodes);
                            listView.setAdapter(episodesAdapter);
                            mSpinner.hide();
                        }
                    });
                } catch (JSONException e) {
                    Log.e("ERROR", e.getMessage(), e);
                    mSpinner.hide();
                }
            }

            @Override
            public void onFailure(Call call, IOException exception) {
                Log.e("ERROR", "Failed to connect to API");
                mSpinner.hide();
            }
        });
    }
}
