package com.example.hw1_androidcourse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.hw1_androidcourse.R;
import com.example.hw1_androidcourse.fragments.ListFragment;
import com.example.hw1_androidcourse.fragments.MapFragment;

public class RecordTableActivity extends AppCompatActivity {


    private FrameLayout main_FRAME_list;
    private FrameLayout main_FRAME_map;

    private ListFragment listFragment;
    private MapFragment mapFragment;

    private AppCompatButton menu_BTN_newGame;

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

        menu_BTN_newGame.setOnClickListener(v -> {
            moveToMainActivity();
        });
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list);
        main_FRAME_map = findViewById(R.id.main_FRAME_map);
        menu_BTN_newGame = findViewById(R.id.menu_BTN_newGame);
    }
}