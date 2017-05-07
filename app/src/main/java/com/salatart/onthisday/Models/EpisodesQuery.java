package com.salatart.onthisday.Models;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import okhttp3.HttpUrl;

/**
 * Created by sasalatart on 1/30/17.
 */

public class EpisodesQuery extends RealmObject {
    @PrimaryKey private String mId;
    private int mDay;
    private int mMonth;
    private String mEpisodesType;
    private RealmList<Episode> mEpisodes;

    public EpisodesQuery() {
    }

    private EpisodesQuery(String episodesType, int day, int month) {
        this.mId = episodesType + day + month;
        this.mEpisodesType = episodesType;
        this.mDay = day;
        this.mMonth = month;
        this.mEpisodes = new RealmList<>();
    }

    public static EpisodesQuery find(String id) {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(EpisodesQuery.class)
                .equalTo("mId", id)
                .findFirst();
    }

    public static EpisodesQuery findOrCreateBy(int day, int month, String episodesType) {
        EpisodesQuery episodesQuery = find(episodesType + day + month);

        if (episodesQuery != null) {
            return episodesQuery;
        } else {
            return create(episodesType, day, month);
        }
    }

    private static EpisodesQuery create(String episodesType, int day, int month) {
        EpisodesQuery episodesQuery = new EpisodesQuery(episodesType, day, month);
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(episodesQuery);
        realm.commitTransaction();
        return episodesQuery;
    }

    public String build(String domain) {
        String query = domain + "/" + mEpisodesType;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(query).newBuilder();
        urlBuilder.addQueryParameter("day", mDay + "");
        urlBuilder.addQueryParameter("month", mMonth + "");
        return urlBuilder.build().toString();
    }

    public String getId() {
        return mId;
    }

    public RealmList<Episode> getEpisodes() {
        return mEpisodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        mEpisodes.clear();
        mEpisodes.addAll(episodes);
        realm.commitTransaction();
    }
}
