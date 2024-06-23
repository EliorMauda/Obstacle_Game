package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        DataModel[] myListData = new DataModel[]{
                new DataModel("Ariel Sharon" , R.drawable.arielsharon),
                new DataModel("Benjamin Netanyahu" , R.drawable.benjaminnetanyahu),
                new DataModel("David Ben Gurion" , R.drawable.davidbengurion),
                new DataModel("Menachem Begin" , R.drawable.menachembegin)
        };

        MyAdapter myAdapter = new MyAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

    }
}