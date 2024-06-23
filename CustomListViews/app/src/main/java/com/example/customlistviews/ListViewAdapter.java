package com.example.customlistviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListViewAdapter extends BaseAdapter {
    Context context;
    String[] name;
    int[] image;

    LayoutInflater layoutInflater;

    public ListViewAdapter(Context context, String[] name, int[] image) {
        this.context = context;
        this.name = name;
        this.image = image;
    }

    @Override
    public int getCount() {
        return name.length;
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
        View listItem = convertView;

        if (listItem == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItem = layoutInflater.inflate(R.layout.list_view, null);
        }

        ImageView imageView = listItem.findViewById(R.id.imageView);
        TextView textView = listItem.findViewById(R.id.textView);

        imageView.setImageResource(image[position]);
        textView.setText(name[position]);

        return listItem;
    }
}
