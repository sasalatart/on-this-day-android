package com.salatart.onthisday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class EpisodesActivity extends AppCompatActivity {

    private String query;
    private String day;
    private String month;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        extractFromIntent();
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
        day = intent.getStringExtra(MainActivity.DAY_MESSAGE);
        month = intent.getStringExtra(MainActivity.MONTH_MESSAGE);
        type = intent.getStringExtra(MainActivity.TYPE_MESSAGE);
    }

    public void buildQuery() {

        try {
            query = Util.getProperty("url", getApplicationContext()) + "/episodes";
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse(query).newBuilder();
        urlBuilder.addQueryParameter("day", day);
        urlBuilder.addQueryParameter("month", month);
        urlBuilder.addQueryParameter("type", type);
        query = urlBuilder.build().toString();
    }

    public void retrieveEpisodes() {
        Request request = new Request.Builder().url(query).build();

        MainActivity.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    JSONArray jsonResponse = new JSONArray(response.body().string());

                    for (int i = 0; i < jsonResponse.length(); i++) {
                        Log.i("INFO", jsonResponse.getJSONObject(i).toString());
                    }

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
