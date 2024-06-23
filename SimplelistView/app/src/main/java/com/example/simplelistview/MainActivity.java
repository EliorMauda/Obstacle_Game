package com.example.simplelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        String[] colorsNames = {
                "white",
                "black",
                "brown",
                "blue",
                "purple",
                "pink"
        };

        //Adapter - ArrayAdapter
        ArrayAdapter adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                colorsNames
        );

        listView.setAdapter(adapter);
    }
}