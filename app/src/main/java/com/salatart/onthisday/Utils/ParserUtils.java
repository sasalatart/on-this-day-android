package com.salatart.onthisday.Utils;

import com.salatart.onthisday.Models.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sasalatart on 10/23/16.
 */

public class ParserUtils {
    public static ArrayList<Episode> episodesFromJSONArray(String response, String episodesType) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(episodesType);

        ArrayList<Episode> episodes = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject episode = jsonArray.getJSONObject(i);

            int id = Integer.parseInt(episode.getString("id"));
            int year = Integer.parseInt(episode.getString("year"));
            boolean bce = Boolean.parseBoolean(episode.getString("bce"));
            String text = episode.getString("text");

            episodes.add(new Episode(id, year, bce, text));
        }

        return episodes;
    }
}
