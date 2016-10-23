package com.salatart.onthisday.Utils;

import android.app.Activity;

import com.salatart.onthisday.Listeners.IndexRequestListener;
import com.salatart.onthisday.Models.Episode;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sasalatart on 10/23/16.
 */

public class EpisodesUtils {
    public static void RetrieveEpisodes(final Activity activity, Request request, final com.wang.avi.AVLoadingIndicatorView spinner, final IndexRequestListener<Episode> listener) {
        HttpClient.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                HttpClient.onUnsuccessfulRequestWithSpinner(activity, "Error", spinner);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (activity == null) {
                    return;
                }

                if (response.isSuccessful()) {
                    try {
                        listener.OnSuccess(ParserUtils.episodesFromJSONArray(response.body().string()));
                    } catch (JSONException e) {
                        HttpClient.onUnsuccessfulRequestWithSpinner(activity, "Error", spinner);
                    }
                } else {
                    HttpClient.onUnsuccessfulRequestWithSpinner(activity, HttpClient.parseErrorMessage(response), spinner);
                }

                response.body().close();
            }
        });
    }
}
