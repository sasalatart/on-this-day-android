package com.salatart.onthisday;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;

/**
 * Created by sasalatart on 8/17/16.
 */
public class Episode {
    private int year;
    private int month;
    private int day;
    private boolean bce;
    private String description;

    public Episode(int year, int month, int day, boolean bce, String description) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.bce = bce;
        this.description = description;
    }

    public static Episode[] fromJSON(String response, int day, int month) throws JSONException {
        JSONArray jsonResponse = new JSONArray(response);
        Episode[] episodes = new Episode[jsonResponse.length()];

        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject episode = jsonResponse.getJSONObject(i);

            int year = Integer.parseInt(episode.getString("year"));
            boolean bce = Boolean.parseBoolean(episode.getString("bce"));
            String text = episode.getString("text");

            episodes[i] = new Episode(year, month, day, bce, text);
        }

        return episodes;
    }

    public String getYearString() {
        return year + (bce ? " B.C." : "");
    }

    public String getMonthString() {
        return new DateFormatSymbols().getMonths()[month - 1] + " " + day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getDescription() {
        return description;
    }
}
