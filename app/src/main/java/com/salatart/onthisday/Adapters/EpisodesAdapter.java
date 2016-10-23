package com.salatart.onthisday.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.salatart.onthisday.Models.Episode;
import com.salatart.onthisday.R;

/**
 * Created by sasalatart on 8/17/16.
 */
public class EpisodesAdapter extends ArrayAdapter<Episode> {
    public EpisodesAdapter(Context context, Episode[] episodes) {
        super(context, 0, episodes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Episode episode = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_episode, parent, false);
        }

        TextView tvYear = (TextView) convertView.findViewById(R.id.label_year);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.label_description);

        tvYear.setText(episode.getYearString());
        tvDescription.setText(episode.getDescription());

        return convertView;
    }
}
