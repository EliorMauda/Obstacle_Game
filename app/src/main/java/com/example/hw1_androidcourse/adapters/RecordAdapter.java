package com.example.hw1_androidcourse.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw1_androidcourse.R;
import com.example.hw1_androidcourse.fragments.MapFragment;
import com.example.hw1_androidcourse.models.RecordChart;
import com.example.hw1_androidcourse.utilities.DataManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private final ArrayList<RecordChart> records;
    private final ArrayList<String> lats;
    private final ArrayList<String> lons;
    private final MapFragment mapFragment;


    public RecordAdapter(ArrayList<RecordChart> records, ArrayList<String> lats, ArrayList<String> lons, MapFragment mapFragment) {
        this.records = records;
        this.lats = lats;
        this.lons = lons;
        this.mapFragment = mapFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        double lat;
        double lon;
        RecordChart record = getItem(position);
        if (!lats.isEmpty() && !lons.isEmpty()) {
            lat = Double.parseDouble(lats.get(position));
            lon = Double.parseDouble(lons.get(position));
        } else {
            lat = 0;
            lon = 0;
        }

        holder.record_TXT_number.setText(String.valueOf(record.getScore()));
        holder.record_TXT_listNumber.setText(String.valueOf(position + 1));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapFragment != null) {
                    mapFragment.setLat(lat);
                    mapFragment.setLon(lon);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }

    private RecordChart getItem(int position) {
        return records.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView record_TXT_number;
        private MaterialTextView record_TXT_listNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            record_TXT_number = itemView.findViewById(R.id.record_TXT_number);
            record_TXT_listNumber = itemView.findViewById(R.id.record_TXT_listNumber);
        }
    }
}
