package com.salatart.onthisday.Activities;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.salatart.onthisday.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sasalatart on 1/26/17.
 */

public class OnThisDay extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(getResources().getString(R.string.app_name))
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
