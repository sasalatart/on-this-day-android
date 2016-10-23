package com.salatart.onthisday.Utils;

import com.salatart.onthisday.Models.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sasalatart on 10/23/16.
 */

public class ParserUtils {
    public static Episode[] episodesFromJSONArray(String response) throws JSONException {
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
}
