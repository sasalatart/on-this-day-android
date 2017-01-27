package com.salatart.onthisday.Utils;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by sasalatart on 8/27/16.
 */
public class HttpClient {
    private static OkHttpClient instance = null;

    protected HttpClient() {
        // Exists only to defeat instantiation.
    }

    public static OkHttpClient getInstance() {
        if (instance == null) {
            instance = new OkHttpClient.Builder()
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
        }

        return instance;
    }

    public static String parseErrorMessage(Response response) {
        try {
            JSONObject jsonResponse = new JSONObject(response.body().string());
            return jsonResponse.getString("error");
        } catch (IOException | JSONException e) {
            return "Error";
        }
    }
}
