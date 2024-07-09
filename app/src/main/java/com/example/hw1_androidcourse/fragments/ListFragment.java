package com.example.hw1_androidcourse.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw1_androidcourse.R;
import com.example.hw1_androidcourse.adapters.RecordAdapter;
import com.example.hw1_androidcourse.utilities.DataManager;

public class ListFragment extends Fragment {

    private RecyclerView main_LST_records;
    private final MapFragment mapFragment;

    public ListFragment(MapFragment mapFragment) {
        this.mapFragment = mapFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(v);
        initViews();
        return v;
    }

    private void initViews() {
        DataManager dataManager = new DataManager(getContext());
        RecordAdapter recordAdapter = new RecordAdapter(dataManager.getRecords(), dataManager.getLats(), dataManager.getLons(), mapFragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_records.setLayoutManager(linearLayoutManager);
        main_LST_records.setAdapter(recordAdapter);
    }


    private void findViews(View v) {
        main_LST_records = v.findViewById(R.id.main_LST_records);
    }
}