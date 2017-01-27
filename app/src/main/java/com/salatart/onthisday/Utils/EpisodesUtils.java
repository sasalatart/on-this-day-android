package com.salatart.onthisday.Utils;

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
    public static void RetrieveEpisodes(Request request, final String episodesType, final IndexRequestListener<Episode> listener) {
        HttpClient.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.OnFailure(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        listener.OnSuccess(ParserUtils.episodesFromJSONArray(response.body().string(), episodesType));
                    } catch (JSONException e) {
                        listener.OnFailure(e.toString());
                    }
                } else {
                    listener.OnFailure(HttpClient.parseErrorMessage(response));
                }

                response.body().close();
            }
        });
    }
}
