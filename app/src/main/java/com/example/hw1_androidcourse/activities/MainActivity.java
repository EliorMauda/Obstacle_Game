package com.example.hw1_androidcourse.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hw1_androidcourse.Interfaces.MoveCallback;
import com.example.hw1_androidcourse.R;
import com.example.hw1_androidcourse.utilities.MoveDetector;
import com.example.hw1_androidcourse.utilities.SoundPlayer;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView[] main_IMG_hearts;
    private AppCompatImageView[][] main_IMG_birds;
    private AppCompatImageView[][] main_IMG_coins;
    private AppCompatImageView[] main_IMG_spaceship;
    private AppCompatImageView main_IMG_leftArrow;
    private AppCompatImageView main_IMG_rightArrow;
    private MaterialTextView main_score_text;
    public SoundPlayer soundPlayer;
    private Handler handler;
    Runnable birdRunnable;
    Runnable gameRunnable;
    Runnable coinRunnable;
    Runnable scoreRunnable;
    private Random random;
    private int currentRowIndex;
    private long grillRandomNumTime;
    private long birdsMovementRhythm;
    private MoveDetector moveDetector;
    private double latitude, longitude;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        initView();


        checkLocationPermission();


        //startGame();
        //randomCoins();
        //startScoreUpdater();
    }


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLastKnownLocation();
        }
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        startGame();
                        randomCoins();
                        startScoreUpdater();
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    } else {
                        handleLocationUnavailable();
                    }
                })
                .addOnFailureListener(this, e -> {
                    handleLocationUnavailable();
                });
    }

    private void handleLocationUnavailable() {
        Toast.makeText(this, "שירותי המיקום אינם זמינים. הפעל את שירותי המיקום ונסה שוב.", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation();
            } else {
                Toast.makeText(this, "נדרשת הרשאת מיקום להפעלת האפליקציה.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void initMoveDetector() {
        moveDetector = new MoveDetector(this,
                new MoveCallback() {
                    @Override
                    public void moveRightX() {
                        manageRightArrow();
                    }

                    @Override
                    public void moveLeftX() {
                        manageLeftArrow();
                    }
                });
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (soundPlayer != null) {
            soundPlayer.stopSound();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null); // Remove all callbacks and messages
        }
        if (moveDetector != null) {
            moveDetector.stop();
        }
        finish();
    }

    private void startScoreUpdater() {
        scoreRunnable = new Runnable() {
            @Override
            public void run() {
                main_score_text.setText(String.valueOf(Integer.parseInt(main_score_text.getText().toString()) + 10));
                handler.postDelayed(this, 10000); // repeat every 10 seconds
            }
        };
        handler.postDelayed(scoreRunnable, 10000); // starts 10 seconds after the game begins
    }

    private void startGame() {
        handler = new Handler();
        random = new Random();
        if (modeTypeInitialization() == 1) {
            initMoveDetector();
            moveDetector.start();
        }

        gameRunnable = new Runnable() {
            @Override
            public void run() {
                // Generate a random row index
                currentRowIndex = random.nextInt(main_IMG_birds.length);
                if (main_IMG_coins[currentRowIndex][0].getVisibility() == View.INVISIBLE) {
                    moveBirdsAndCoins(currentRowIndex, main_IMG_birds);
                }
                handler.postDelayed(this, grillRandomNumTime); // Repeat every 0.5 seconds
            }
        };
        handler.post(gameRunnable);
    }

    private int modeTypeInitialization() {
        Bundle b = getIntent().getExtras();
        int value = 0;
        if (b != null)
            value = b.getInt("mode");

        switch (value) {
            case 1:
                grillRandomNumTime = 1000L;
                birdsMovementRhythm = 300L;
                main_IMG_leftArrow.setVisibility(View.INVISIBLE);
                main_IMG_rightArrow.setVisibility(View.INVISIBLE);
                break;
            case 2:
                grillRandomNumTime = 300L;
                birdsMovementRhythm = 100L;
                break;
            case 3:
                grillRandomNumTime = 500L;
                birdsMovementRhythm = 200L;
                break;
        }
        return value;
    }


    private void randomCoins() {
        coinRunnable = new Runnable() {
            @Override
            public void run() {
                // Generate a random row index
                currentRowIndex = random.nextInt(main_IMG_coins.length);
                if (main_IMG_birds[currentRowIndex][0].getVisibility() == View.INVISIBLE) {
                    moveBirdsAndCoins(currentRowIndex, main_IMG_coins);
                }
                handler.postDelayed(this, 2000); // Repeat every 2 seconds
            }
        };
        handler.post(coinRunnable);
    }


    private void moveBirdsAndCoins(int currentRowIndex, View[][] view) {
        birdRunnable = new Runnable() {
            int colIndex = 0;

            @Override
            public void run() {
                if (colIndex > 0) {
                    view[currentRowIndex][colIndex - 1].setVisibility(View.INVISIBLE);
                }
                if (colIndex < view[0].length) {
                    view[currentRowIndex][colIndex].setVisibility(View.VISIBLE);
                    if (colIndex == view[0].length - 1) {
                        collisionDetection(currentRowIndex, colIndex, view);
                    }
                    colIndex++;
                    handler.postDelayed(this, birdsMovementRhythm); // Repeat every 0.2 second
                } else {
                    handler.removeCallbacks(this);
                }
            }
        };
        handler.post(birdRunnable);
    }

    private void collisionDetection(int currentRowIndex, int currentColIndex, View[][] view) {
        if (((view[currentRowIndex][currentColIndex].getVisibility() == View.VISIBLE) && (main_IMG_spaceship[currentRowIndex].getVisibility() == View.VISIBLE))) {
            if (view == main_IMG_birds) {
                soundPlayer = new SoundPlayer(this);
                soundPlayer.playSound(R.raw.crashsound);
                toastAndVibrate("watch out!");
                if (main_IMG_hearts[0].getVisibility() == View.VISIBLE) {
                    main_IMG_hearts[0].setVisibility(View.INVISIBLE);
                } else if (main_IMG_hearts[1].getVisibility() == View.VISIBLE) {
                    main_IMG_hearts[1].setVisibility(View.INVISIBLE);
                } else {
                    saveScoreAndLocation(latitude, longitude);
                    Intent intent = new Intent(this, RecordTableActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                main_score_text.setText(String.valueOf(Integer.parseInt(main_score_text.getText().toString()) + 1));
            }
        }
    }

    private void saveScoreAndLocation(double lat, double lon) {
        String newLats = "";
        String newLons = "";
        SharedPreferences sharedPreferences = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String scores = sharedPreferences.getString("scores", "");
        String lats = sharedPreferences.getString("lat", "");
        String lons = sharedPreferences.getString("lon", "");
        int currentScore = Integer.parseInt(main_score_text.getText().toString());

        ArrayList<String> latsList = new ArrayList<>(Arrays.asList(lats.split(",")));
        ArrayList<String> lonsList = new ArrayList<>(Arrays.asList(lons.split(",")));

        String newScores = canInsertToArray(currentScore, scores, latsList, lonsList, lat, lon);
        if (newScores == null) {
            Toast.makeText(this, "Your score is too low to be added to the record table", Toast.LENGTH_SHORT).show();
            // Toast that not in the record chart because the score is low
            return;
        }

        if (latsList.size() == 1) {
            newLats = String.valueOf(lat);
            newLons = String.valueOf(lon);
        } else if (latsList.size() > 1) {
            newLats = String.join(",", latsList);
            newLons = String.join(",", lonsList);
        }


        editor.putString("scores", newScores);
        editor.putString("lat", newLats);
        editor.putString("lon", newLons);
        editor.apply(); // Apply the changes
    }

    private String canInsertToArray(int currentScore, String scores, ArrayList<String> latsList, ArrayList<String> lonsList, double lat, double lon) {
        if (scores.isEmpty()) {
            return String.valueOf(currentScore);
        }

        ArrayList<String> scoresList = new ArrayList<>(Arrays.asList(scores.split(",")));
        scoresList.sort((a, b) -> Integer.compare(Integer.parseInt(b), Integer.parseInt(a)));
        ArrayList<Integer> intScoresList = new ArrayList<>();
        for (String score : scoresList) {
            intScoresList.add(Integer.parseInt(score));
        }

        if (scoresList.size() >= 10) {
            if (currentScore > Collections.min(intScoresList)) {
                int insertIndex = 0;
                for (; insertIndex < intScoresList.size(); insertIndex++) {
                    if (currentScore > intScoresList.get(insertIndex)) {
                        break;
                    }
                }
                scoresList.add(insertIndex, String.valueOf(currentScore));
                latsList.add(insertIndex, String.valueOf(lat));
                lonsList.add(insertIndex, String.valueOf(lon));
                scoresList.remove(10);
                latsList.remove(10);
                lonsList.remove(10);
            } else {
                return null;
            }
        } else {
            int insertIndex = 0;
            for (; insertIndex < intScoresList.size(); insertIndex++) {
                if (currentScore > intScoresList.get(insertIndex)) {
                    break;
                }
            }
            scoresList.add(insertIndex, String.valueOf(currentScore));
            latsList.add(insertIndex, String.valueOf(lat));
            lonsList.add(insertIndex, String.valueOf(lon));
        }

        return String.join(",", scoresList);
    }


    private void toastAndVibrate(String text) {
        vibrate();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private void manageRightArrow() {
        for (int i = 0; i < main_IMG_spaceship.length; i++) {
            if (main_IMG_spaceship[i].getVisibility() == View.VISIBLE) {
                main_IMG_spaceship[i].setVisibility(View.INVISIBLE);
                if (i == main_IMG_spaceship.length - 1) {
                    main_IMG_spaceship[0].setVisibility(View.VISIBLE);
                } else {
                    main_IMG_spaceship[i + 1].setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }

    private void manageLeftArrow() {
        for (int i = 0; i < main_IMG_spaceship.length; i++) {
            if (main_IMG_spaceship[i].getVisibility() == View.VISIBLE) {
                main_IMG_spaceship[i].setVisibility(View.INVISIBLE);
                if (i == 0) {
                    main_IMG_spaceship[main_IMG_spaceship.length - 1].setVisibility(View.VISIBLE);
                } else {
                    main_IMG_spaceship[i - 1].setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }

    private void initView() {
        main_IMG_rightArrow.setOnClickListener(v -> manageRightArrow());
        main_IMG_leftArrow.setOnClickListener(v -> manageLeftArrow());
    }

    private void findView() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        main_score_text = findViewById(R.id.main_score_text);
        main_IMG_rightArrow = findViewById(R.id.main_IMG_rightArrow);
        main_IMG_leftArrow = findViewById(R.id.main_IMG_leftArrow);

        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };

        main_IMG_spaceship = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_spaceship1),
                findViewById(R.id.main_IMG_spaceship2),
                findViewById(R.id.main_IMG_spaceship3),
                findViewById(R.id.main_IMG_spaceship4),
                findViewById(R.id.main_IMG_spaceship5)
        };

        main_IMG_birds = new AppCompatImageView[][]{
                {
                        findViewById(R.id.main_IMG_bird1),
                        findViewById(R.id.main_IMG_bird2),
                        findViewById(R.id.main_IMG_bird3),
                        findViewById(R.id.main_IMG_bird4),
                        findViewById(R.id.main_IMG_bird5),
                        findViewById(R.id.main_IMG_bird6),
                        findViewById(R.id.main_IMG_bird7),
                        findViewById(R.id.main_IMG_bird8),
                        findViewById(R.id.main_IMG_bird9)
                },
                {
                        findViewById(R.id.main_IMG_bird10),
                        findViewById(R.id.main_IMG_bird11),
                        findViewById(R.id.main_IMG_bird12),
                        findViewById(R.id.main_IMG_bird13),
                        findViewById(R.id.main_IMG_bird14),
                        findViewById(R.id.main_IMG_bird15),
                        findViewById(R.id.main_IMG_bird16),
                        findViewById(R.id.main_IMG_bird17),
                        findViewById(R.id.main_IMG_bird18)

                },
                {
                        findViewById(R.id.main_IMG_bird19),
                        findViewById(R.id.main_IMG_bird20),
                        findViewById(R.id.main_IMG_bird21),
                        findViewById(R.id.main_IMG_bird22),
                        findViewById(R.id.main_IMG_bird23),
                        findViewById(R.id.main_IMG_bird24),
                        findViewById(R.id.main_IMG_bird25),
                        findViewById(R.id.main_IMG_bird26),
                        findViewById(R.id.main_IMG_bird27)
                },
                {
                        findViewById(R.id.main_IMG_bird28),
                        findViewById(R.id.main_IMG_bird29),
                        findViewById(R.id.main_IMG_bird30),
                        findViewById(R.id.main_IMG_bird31),
                        findViewById(R.id.main_IMG_bird32),
                        findViewById(R.id.main_IMG_bird33),
                        findViewById(R.id.main_IMG_bird34),
                        findViewById(R.id.main_IMG_bird35),
                        findViewById(R.id.main_IMG_bird36)
                },
                {
                        findViewById(R.id.main_IMG_bird37),
                        findViewById(R.id.main_IMG_bird38),
                        findViewById(R.id.main_IMG_bird39),
                        findViewById(R.id.main_IMG_bird40),
                        findViewById(R.id.main_IMG_bird41),
                        findViewById(R.id.main_IMG_bird42),
                        findViewById(R.id.main_IMG_bird43),
                        findViewById(R.id.main_IMG_bird44),
                        findViewById(R.id.main_IMG_bird45)
                }

        };


        main_IMG_coins = new AppCompatImageView[][]{
                {
                        findViewById(R.id.main_IMG_coin1),
                        findViewById(R.id.main_IMG_coin2),
                        findViewById(R.id.main_IMG_coin3),
                        findViewById(R.id.main_IMG_coin4),
                        findViewById(R.id.main_IMG_coin5),
                        findViewById(R.id.main_IMG_coin6),
                        findViewById(R.id.main_IMG_coin7),
                        findViewById(R.id.main_IMG_coin8),
                        findViewById(R.id.main_IMG_coin9)
                },
                {
                        findViewById(R.id.main_IMG_coin10),
                        findViewById(R.id.main_IMG_coin11),
                        findViewById(R.id.main_IMG_coin12),
                        findViewById(R.id.main_IMG_coin13),
                        findViewById(R.id.main_IMG_coin14),
                        findViewById(R.id.main_IMG_coin15),
                        findViewById(R.id.main_IMG_coin16),
                        findViewById(R.id.main_IMG_coin17),
                        findViewById(R.id.main_IMG_coin18)

                },
                {
                        findViewById(R.id.main_IMG_coin19),
                        findViewById(R.id.main_IMG_coin20),
                        findViewById(R.id.main_IMG_coin21),
                        findViewById(R.id.main_IMG_coin22),
                        findViewById(R.id.main_IMG_coin23),
                        findViewById(R.id.main_IMG_coin24),
                        findViewById(R.id.main_IMG_coin25),
                        findViewById(R.id.main_IMG_coin26),
                        findViewById(R.id.main_IMG_coin27)
                },
                {
                        findViewById(R.id.main_IMG_coin28),
                        findViewById(R.id.main_IMG_coin29),
                        findViewById(R.id.main_IMG_coin30),
                        findViewById(R.id.main_IMG_coin31),
                        findViewById(R.id.main_IMG_coin32),
                        findViewById(R.id.main_IMG_coin33),
                        findViewById(R.id.main_IMG_coin34),
                        findViewById(R.id.main_IMG_coin35),
                        findViewById(R.id.main_IMG_coin36)
                },
                {
                        findViewById(R.id.main_IMG_coin37),
                        findViewById(R.id.main_IMG_coin38),
                        findViewById(R.id.main_IMG_coin39),
                        findViewById(R.id.main_IMG_coin40),
                        findViewById(R.id.main_IMG_coin41),
                        findViewById(R.id.main_IMG_coin42),
                        findViewById(R.id.main_IMG_coin43),
                        findViewById(R.id.main_IMG_coin44),
                        findViewById(R.id.main_IMG_coin45)
                }

        };

    }
}
