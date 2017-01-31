package com.salatart.onthisday.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.salatart.onthisday.Fragments.EpisodesFragment;

import java.util.ArrayList;

/**
 * Created by sasalatart on 1/30/17.
 */

public class EpisodesPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<EpisodesFragment> mFragments = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();

    public EpisodesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void addFragment(EpisodesFragment episodesFragment, String title) {
        mFragments.add(episodesFragment);
        mTitles.add(title);
    }
}
