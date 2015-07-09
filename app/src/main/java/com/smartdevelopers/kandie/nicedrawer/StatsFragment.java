package com.smartdevelopers.kandie.nicedrawer;

/**
 * Created by 4331 on 07/07/2015.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poliveira on 11/03/2015.
 */
public class StatsFragment extends Fragment {
    public static final String TAG = "stats";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statsfragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.stats);
        List<String> content = new ArrayList<>();
        content.add("list");
        content.add("list");
        content.add("list");
        content.add("list");
        content.add("list");
        content.add("list");
        content.add("list");
        ListView listView = (ListView) getActivity().findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, content));
    }
}