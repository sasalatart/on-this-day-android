package com.salatart.onthisday.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.salatart.onthisday.Models.Episode;
import com.salatart.onthisday.R;

import java.util.ArrayList;

/**
 * Created by sasalatart on 8/17/16.
 */
public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Episode> mEpisdoes;

    public EpisodesAdapter(Context context, ArrayList<Episode> episodes) {
        mInflater = LayoutInflater.from(context);
        mEpisdoes = episodes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.episode_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Episode episode = mEpisdoes.get(position);
        holder.setData(episode);
    }

    @Override
    public int getItemCount() {
        return mEpisdoes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mYearView;
        private TextView mDescriptionView;

        ViewHolder(View itemView) {
            super(itemView);

            mYearView = (ImageView) itemView.findViewById(R.id.image_view_year);
            mDescriptionView = (TextView) itemView.findViewById(R.id.text_view_description);
        }

        void setData(Episode episode) {
            String year = episode.getYearString();
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .fontSize(48)
                    .withBorder(8)
                    .endConfig()
                    .buildRect(year, ColorGenerator.MATERIAL.getColor(year));

            mYearView.setImageDrawable(drawable);
            mDescriptionView.setText(episode.getDescription());
        }
    }
}
