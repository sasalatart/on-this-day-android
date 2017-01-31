package com.salatart.onthisday.Models;

import android.os.Parcel;
import android.os.Parcelable;

import okhttp3.HttpUrl;

/**
 * Created by sasalatart on 1/30/17.
 */

public class EpisodesQuery implements Parcelable {
    public static final Creator<EpisodesQuery> CREATOR = new Creator<EpisodesQuery>() {
        @Override
        public EpisodesQuery createFromParcel(Parcel in) {
            return new EpisodesQuery(in);
        }

        @Override
        public EpisodesQuery[] newArray(int size) {
            return new EpisodesQuery[size];
        }
    };

    private String mEpisodesType;
    private int mDay;
    private int mMonth;

    public EpisodesQuery(String episodesType, int day, int month) {
        this.mEpisodesType = episodesType;
        this.mDay = day;
        this.mMonth = month;
    }

    private EpisodesQuery(Parcel in) {
        mEpisodesType = in.readString();
        mDay = in.readInt();
        mMonth = in.readInt();
    }

    public String build(String domain) {
        String query = domain + "/" + mEpisodesType;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(query).newBuilder();
        urlBuilder.addQueryParameter("day", mDay + "");
        urlBuilder.addQueryParameter("month", mMonth + "");
        return urlBuilder.build().toString();
    }

    public String getEpisodesType() {
        return mEpisodesType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEpisodesType);
        dest.writeInt(mDay);
        dest.writeInt(mMonth);
    }
}
