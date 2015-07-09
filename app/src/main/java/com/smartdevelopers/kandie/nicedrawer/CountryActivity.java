package com.smartdevelopers.kandie.nicedrawer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartdevelopers.kandie.nicedrawer.model.CountryManager;

/**
 * Created by 4331 on 09/07/2015.
 */
public class CountryActivity extends Fragment {
    private RecyclerView mRecyclerView;
    private CountryAdapter mAdapter;
    public static final String TAG = "nation";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_country,container,false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.nation);

        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new CountryAdapter(CountryManager.getInstance().getCountries(),R.layout.row_country,getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }
    }

