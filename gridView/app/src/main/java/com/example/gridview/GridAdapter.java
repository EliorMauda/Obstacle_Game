package com.example.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    Context context;
    String[] PrimeMinisterName;
    int[] PrimeMinisterImage;

    public GridAdapter(Context context, String[] primeMinisterName, int[] primeMinisterImage) {
        this.context = context;
        this.PrimeMinisterName = primeMinisterName;
        this.PrimeMinisterImage = primeMinisterImage;
    }


    @Override
    public int getCount() {
        return PrimeMinisterName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;

        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.grid_item, null);
        }

        ImageView imageView = gridView.findViewById(R.id.grid_image);
        TextView textView = gridView.findViewById(R.id.item_name);

        imageView.setImageResource(PrimeMinisterImage[position]);
        textView.setText(PrimeMinisterName[position]);

        return gridView;
    }
}
