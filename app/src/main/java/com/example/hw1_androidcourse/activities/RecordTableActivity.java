package com.example.hw1_androidcourse.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1_androidcourse.R;
import com.example.hw1_androidcourse.fragments.ListFragment;
import com.example.hw1_androidcourse.fragments.MapFragment;

public class RecordTableActivity extends AppCompatActivity {


    private FrameLayout main_FRAME_list;
    private FrameLayout main_FRAME_map;

    private ListFragment listFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_table);

        findViews();
        initViews();
    }

    private void initViews() {
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map,mapFragment).commit();
        listFragment = new ListFragment(mapFragment);
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list,listFragment).commit();
    }

    private void findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list);
        main_FRAME_map = findViewById(R.id.main_FRAME_map);
    }
}