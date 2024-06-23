package com.example.customlistviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    String[] primeMinisterName = {"Ariel Sharon" , "Benjamin Netanyahu" , "David Ben Gurion" , "Ehud Olmert" ,
            "Levy Eshkol" , "Menachem Begin" , "Moshe Sharett" , "Shimon Peres" , "Yitzhak rabin" , "Yitzhak Shamir"};
    int[] primeMinisterImage = {R.drawable.arielsharon , R.drawable.benjaminnetanyahu , R.drawable.davidbengurion ,
            R.drawable.ehudolmert , R.drawable.levyeshkol , R.drawable.menachembegin , R.drawable.moshesharett ,
            R.drawable.shimonperes , R.drawable.yitzhakrabin , R.drawable.yitzhakshamir};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        ListViewAdapter listViewAdapter = new ListViewAdapter(MainActivity.this , primeMinisterName , primeMinisterImage);
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "I'm " + primeMinisterName[position], Toast.LENGTH_SHORT).show();
            }
        });


    }
}