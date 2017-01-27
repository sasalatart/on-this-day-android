package com.salatart.onthisday.Utils;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by sasalatart on 10/23/16.
 */

public class Routes {
    private static String DOMAIN = "https://onthisday.salatart.com";

    public static Request episodes(int day, int month, String type) {
        String query = DOMAIN + "/" + type;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(query).newBuilder();
        urlBuilder.addQueryParameter("day", day + "");
        urlBuilder.addQueryParameter("month", month + "");
        query = urlBuilder.build().toString();

        return new Request.Builder().url(query).build();
    }
}
