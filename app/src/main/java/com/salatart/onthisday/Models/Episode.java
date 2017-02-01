package com.salatart.onthisday.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sasalatart on 8/17/16.
 */
public class Episode extends RealmObject {
    @PrimaryKey private int mId;

    private int mYear;
    private boolean mBCE;
    private String mDescription;

    public Episode() {
    }

    public Episode(int id, int year, boolean bce, String description) {
        this.mId = id;
        this.mYear = year;
        this.mBCE = bce;
        this.mDescription = description;
    }

    public String getYearString() {
        return mYear + (mBCE ? "BC" : "");
    }

    public String getDescription() {
        return mDescription;
    }
}
