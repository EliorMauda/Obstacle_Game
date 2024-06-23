package com.example.realestateapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.realestateapp.R;
import com.example.realestateapp.domain.ItemDomain;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    private TextView titleTxt, addressTxt, bedTxt, bathTxt, wifiTxt, descriptionTxt , priceTxt;
    private ItemDomain item;
    private ImageView pic;

    DecimalFormat formatter = new DecimalFormat("###,###,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        setVariable();
    }

    private void setVariable() {
        item = (ItemDomain) getIntent().getSerializableExtra("object");

        titleTxt.setText(item.getTitle());
        addressTxt.setText(item.getAddress());
        bedTxt.setText(item.getBed() + " bed");
        bathTxt.setText(item.getBath() + " bath");
        descriptionTxt.setText(item.getDescription());
        priceTxt.setText("$" + formatter.format(item.getPrice()));

        if (item.isWifi())
            wifiTxt.setText("Wifi");
        else
            wifiTxt.setText("No-wifi");

        int drawableResourceId = getResources().getIdentifier(item.getPic(), "drawable", getPackageName());

        Glide.with(this)
                .load(drawableResourceId)
                .into(pic);
    }

    private void initView() {
        titleTxt = findViewById(R.id.titleTxt);
        addressTxt = findViewById(R.id.addressTxt);
        bedTxt = findViewById(R.id.bedTxt);
        bathTxt = findViewById(R.id.bathTxt);
        wifiTxt = findViewById(R.id.wifiTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        priceTxt = findViewById(R.id.priceTxt);
        pic = findViewById(R.id.pic);
    }
}