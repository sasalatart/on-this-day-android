package com.salatart.onthisday.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.salatart.onthisday.Adapters.EpisodesAdapter;
import com.salatart.onthisday.Listeners.IndexRequestListener;
import com.salatart.onthisday.Models.Episode;
import com.salatart.onthisday.Models.EpisodesQuery;
import com.salatart.onthisday.R;
import com.salatart.onthisday.Utils.EpisodesUtils;
import com.salatart.onthisday.Utils.Routes;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

import static com.salatart.onthisday.Utils.Constants.EPISODES_QUERY_KEY;

/**
 * Created by sasalatart on 1/30/17.
 */

public class EpisodesFragment extends Fragment {
    @BindView(R.id.loading_episodes) AVLoadingIndicatorView mSpinner;

    private EpisodesQuery mEpisodesQuery;
    private ArrayList<Episode> mEpisodes;
    private EpisodesAdapter mEpisodesAdapter;

    public EpisodesFragment() {
        mEpisodes = new ArrayList<>();
    }

    public static EpisodesFragment build(EpisodesQuery episodesQuery) {
        EpisodesFragment fragment = new EpisodesFragment();

        Bundle args = new Bundle();
        args.putParcelable(EPISODES_QUERY_KEY, episodesQuery);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_episodes, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        retrieveArguments();
        setRecyclerView();
        retrieveEpisodes();
    }

    public void retrieveArguments() {
        Bundle arguments = getArguments();
        mEpisodesQuery = arguments.getParcelable(EPISODES_QUERY_KEY);
    }

    public void setRecyclerView() {
        mEpisodesAdapter = new EpisodesAdapter(getActivity(), mEpisodes);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_episodes);
        recyclerView.setAdapter(mEpisodesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void retrieveEpisodes() {
        mSpinner.show();
        Request request = Routes.episodes(mEpisodesQuery);
        EpisodesUtils.RetrieveEpisodes(request, mEpisodesQuery.getEpisodesType(), new IndexRequestListener<Episode>() {
            @Override
            public void OnSuccess(final ArrayList<Episode> episodes) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEpisodes.addAll(episodes);
                        mEpisodesAdapter.notifyDataSetChanged();
                        mSpinner.hide();
                    }
                });
            }

            @Override
            public void OnFailure(final String message) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                        mSpinner.hide();
                    }
                });
            }
        });
    }
}
