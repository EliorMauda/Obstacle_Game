package com.example.hw1_androidcourse.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hw1_androidcourse.R;

public class MenuActivity extends AppCompatActivity {

    private AppCompatButton menu_BUTTON_slow;
    private AppCompatButton menu_BUTTON_fast;
    private AppCompatButton menu_BUTTON_sensorsMode;
    private AppCompatButton menu_BUTTON_recordChart;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findView();
        initView();
    }

    private void initView() {
        menu_BUTTON_sensorsMode.setOnClickListener(v -> {
            moveToMainActivity(1);
        });

        menu_BUTTON_fast.setOnClickListener(v -> {
            moveToMainActivity(2);
        });

        menu_BUTTON_slow.setOnClickListener(v -> {
            moveToMainActivity(3);
        });

        menu_BUTTON_recordChart.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecordTableActivity.class);
            startActivity(intent);
        });
    }

    private void moveToMainActivity(int buttonType) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putInt("mode", buttonType);
        intent.putExtras(b); //Put buttonType to the next Intent
        startActivity(intent);
        finish();
    }


    private void findView() {
        menu_BUTTON_slow = findViewById(R.id.menu_BUTTON_slow);
        menu_BUTTON_fast = findViewById(R.id.menu_BUTTON_fast);
        menu_BUTTON_sensorsMode = findViewById(R.id.menu_BUTTON_sensorsMode);
        menu_BUTTON_recordChart = findViewById(R.id.menu_BUTTON_recordChart);
    }
}