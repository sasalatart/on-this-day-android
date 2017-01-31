package com.salatart.onthisday.Utils;

import com.salatart.onthisday.Models.EpisodesQuery;

import okhttp3.Request;

/**
 * Created by sasalatart on 10/23/16.
 */

public class Routes {
    private static String DOMAIN = "https://onthisday.salatart.com";

    public static Request episodes(EpisodesQuery query) {
        return new Request.Builder().url(query.build(DOMAIN)).build();
    }
}
