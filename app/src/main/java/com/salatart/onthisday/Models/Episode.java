package com.salatart.onthisday.Models;

/**
 * Created by sasalatart on 8/17/16.
 */
public class Episode {
    private int mYear;
    private boolean mBCE;
    private String mDescription;

    public Episode(int year, boolean bce, String description) {
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
