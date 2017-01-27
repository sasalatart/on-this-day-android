package com.salatart.onthisday.Utils;

import com.salatart.onthisday.Models.Episode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sasalatart on 10/23/16.
 */

public class ParserUtils {
    public static Episode[] episodesFromJSONArray(String response, String episodesType) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(episodesType);

        Episode[] episodes = new Episode[jsonArray.length()];

        for (int i = 0; i < episodes.length; i++) {
            JSONObject episode = jsonArray.getJSONObject(i);

            int year = Integer.parseInt(episode.getString("year"));
            boolean bce = Boolean.parseBoolean(episode.getString("bce"));
            String text = episode.getString("text");

            episodes[i] = new Episode(year, bce, text);
        }

        return episodes;
    }
}
