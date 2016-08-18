package com.salatart.onthisday;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;

/**
 * Created by sasalatart on 8/17/16.
 */
public class Episode {
    private int mYear;
    private boolean mBCE;
    private String mDescription;

    public Episode(int year, boolean bce, String description) {
        this.mYear = year;
        this.mBCE = bce;
        this.mDescription = description;
    }

    public static Episode[] fromJSON(String response) throws JSONException {
        JSONArray jsonResponse = new JSONArray(response);
        Episode[] episodes = new Episode[jsonResponse.length()];

        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject episode = jsonResponse.getJSONObject(i);

            int year = Integer.parseInt(episode.getString("year"));
            boolean bce = Boolean.parseBoolean(episode.getString("bce"));
            String text = episode.getString("text");

            episodes[i] = new Episode(year, bce, text);
        }

        return episodes;
    }

    public String getYearString() {
        return mYear + (mBCE ? " B.C." : "");
    }

    public String getDescription() {
        return mDescription;
    }
}
