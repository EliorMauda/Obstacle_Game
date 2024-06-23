package com.example.viewpagerandcardview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<ViewPagerItem> viewPagerItemArrayList;
    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewpager);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        int[] images = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
        String[] heading = {"Baked","Grilled","Dessert","Italian","Shakes"};
        String[] desc = {getString(R.string.a_desc),
                getString(R.string.b_desc),
                getString(R.string.c_desc),
                getString(R.string.d_desc)
                ,getString(R.string.e_desc)};

        viewPagerItemArrayList = new ArrayList<>();

        for (int i =0; i< images.length ; i++){

            ViewPagerItem viewPagerItem = new ViewPagerItem(images[i],heading[i],desc[i]);
            viewPagerItemArrayList.add(viewPagerItem);

        }

        VPAdapter vpAdapter = new VPAdapter(viewPagerItemArrayList);
        viewPager2.setAdapter(vpAdapter);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager2.getCurrentItem();
                viewPager2.setCurrentItem(currentItem + 1 , true);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager2.getCurrentItem();
                if(currentItem > 0)
                    viewPager2.setCurrentItem(currentItem - 1 , true);
            }
        });

        viewPager2.setClipToPadding(false);

        viewPager2.setClipChildren(false);

        viewPager2.setOffscreenPageLimit(2);

        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

    }
}