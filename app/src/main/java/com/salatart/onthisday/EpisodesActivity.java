package com.salatart.onthisday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormatSymbols;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class EpisodesActivity extends AppCompatActivity {

    private String mQuery;
    private int mDay;
    private int mMonth;
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
        mDay = intent.getIntExtra(MainActivity.DAY_MESSAGE, 1);
        mMonth = intent.getIntExtra(MainActivity.MONTH_MESSAGE, 1);
        mType = intent.getStringExtra(MainActivity.TYPE_MESSAGE);
    }

    public void setTextViews() {
        TextView typeTV = (TextView) findViewById(R.id.searchType);
        typeTV.setText(mType + "s");
        TextView dateTV = (TextView) findViewById(R.id.searchDate);
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
                            ListView listView = (ListView) findViewById(R.id.episodeList);
                            listView.setAdapter(episodesAdapter);
                        }
                    });
                } catch (JSONException e) {
                    Log.e("ERROR", e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call call, IOException exception) {
                Log.e("ERROR", "Failed to connect to API");
            }
        });
    }
}
