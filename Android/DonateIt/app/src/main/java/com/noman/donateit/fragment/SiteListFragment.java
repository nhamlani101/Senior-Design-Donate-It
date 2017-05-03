package com.noman.donateit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.noman.donateit.R;
import com.noman.donateit.adapters.SiteListAdapter;
import com.noman.donateit.data.SiteDataHolder;
import com.noman.donateit.data.SiteModel;

import java.util.ArrayList;

/**
 * Created by noman on 3/30/17.
 */

public class SiteListFragment extends android.support.v4.app.Fragment {

    private ListView mListView;
    private SiteListAdapter mAdapter;
    private ArrayList<SiteModel> siteList = new ArrayList<>();
    private int filter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_list, container, false);
        filter = getArguments().getInt("Filter");
        for (SiteModel site : SiteDataHolder.getInstance().getData()) {
            if (site.getCategories().contains(new Integer(filter))) {
                siteList.add(site);
            }
        }

        mListView = (ListView) rootview.findViewById(R.id.SiteListView);
        mAdapter = new SiteListAdapter(siteList, getContext());
        mListView.setAdapter(mAdapter);

        return rootview;
    }
}
