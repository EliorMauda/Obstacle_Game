package com.example.realestateapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.realestateapp.R;
import com.example.realestateapp.adapter.ItemAdapter;
import com.example.realestateapp.domain.ItemDomain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterPopular, adapterNew;
    private RecyclerView recyclerViewPopular, recyclerViewNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<ItemDomain> itemsArrayList = new ArrayList<>();
        itemsArrayList.add(new ItemDomain("House with a great view", "San Francisco, CA 94110", "This 2 bed /1 bath home boasts an enormous, \n" +
                " open-living plan , accented by striking \n" +
                "architectural features and high-end finishes.\n" +
                "Feel inspired by open sight lines that\n" +
                "embrace the outdoors, crowned by stunning\n" +
                "coffered ceilings. ", 2, 1, 841456, "pic1", true));
        itemsArrayList.add(new ItemDomain("House with a great view", "San Francisco, CA 94110", "This 2 bed /1 bath home boasts an enormous, \n" +
                " open-living plan , accented by striking \n" +
                "architectural features and high-end finishes.\n" +
                "Feel inspired by open sight lines that\n" +
                "embrace the outdoors, crowned by stunning\n" +
                "coffered ceilings. ", 3, 1, 654987, "pic2", false));
        itemsArrayList.add(new ItemDomain("House with a great view", "San Francisco, CA 94110", "This 2 bed /1 bath home boasts an enormous, \n" +
                " open-living plan , accented by striking \n" +
                "architectural features and high-end finishes.\n" +
                "Feel inspired by open sight lines that\n" +
                "embrace the outdoors, crowned by stunning\n" +
                "coffered ceilings. ", 3, 1, 841456, "pic1", true));


        recyclerViewPopular = findViewById(R.id.viewPopular);
        recyclerViewNew = findViewById(R.id.viewNew);

        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterPopular = new ItemAdapter(itemsArrayList);
        adapterNew = new ItemAdapter(itemsArrayList);

        recyclerViewPopular.setAdapter(adapterPopular);
        recyclerViewNew.setAdapter(adapterNew);

    }
}