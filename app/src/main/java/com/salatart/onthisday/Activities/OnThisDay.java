package com.salatart.onthisday.Activities;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by sasalatart on 1/26/17.
 */

public class OnThisDay extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
