package com.example.hw1_androidcourse.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hw1_androidcourse.models.RecordChart;

import java.util.ArrayList;
import java.util.Arrays;

public class DataManager {

    private SharedPreferences sharedPreferences;
    private ArrayList<String> scoresList;
    private ArrayList<String> latsList;
    private ArrayList<String> lonsList;


    public void getScoresAndLocation() {
        String scores = sharedPreferences.getString("scores", "");
        String lats = sharedPreferences.getString("lat", "");
        String lons = sharedPreferences.getString("lon", "");

        if (!scores.isEmpty()) { // that is enough that only one string is not empty
            scoresList = new ArrayList<>(Arrays.asList(scores.split(",")));
            latsList = new ArrayList<>(Arrays.asList(lats.split(",")));
            lonsList = new ArrayList<>(Arrays.asList(lons.split(",")));
//            // Sort the list as integers
//            scoresList.sort((a, b) -> Integer.compare(Integer.parseInt(b), Integer.parseInt(a)));
        } else {
            scoresList = new ArrayList<>();
            latsList = new ArrayList<>();
            lonsList = new ArrayList<>();
        }
    }


    public DataManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
        getScoresAndLocation();
    }

    public ArrayList<RecordChart> getRecords() {
        ArrayList<RecordChart> records = new ArrayList<>();

        for (String score : scoresList) {
            records.add(new RecordChart().setScore(Integer.parseInt(score)));
        }

        return records;
    }

    public ArrayList<String> getLats() {
        return latsList;
    }

    public ArrayList<String> getLons() {
        return lonsList;
    }
}
