package com.example.hw1_androidcourse;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView[] main_IMG_hearts;
    private AppCompatImageView[][] main_IMG_birds;
    private AppCompatImageView[] main_IMG_spaceship;
    private AppCompatImageView main_IMG_leftArrow;
    private AppCompatImageView main_IMG_rightArrow;
    private Handler handler;
    Runnable birdRunnable;
    Runnable gameRunnable;
    private Random random;
    private int currentRowIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        initView();
        startGame();
    }

    private void startGame() {
        handler = new Handler();
        random = new Random();

        gameRunnable = new Runnable() {
            @Override
            public void run() {
                // Generate a random row index
                currentRowIndex = random.nextInt(main_IMG_birds.length);
                moveBird(currentRowIndex);
                handler.postDelayed(this, 2000); // Repeat every 2 seconds
            }
        };
        handler.post(gameRunnable);
    }

    private void moveBird(int currentRowIndex) {
        birdRunnable = new Runnable() {
            int colIndex = 0;

            @Override
            public void run() {
                if (colIndex > 0) {
                    main_IMG_birds[currentRowIndex][colIndex - 1].setVisibility(View.INVISIBLE);
                }
                if (colIndex < main_IMG_birds[0].length) {
                    main_IMG_birds[currentRowIndex][colIndex].setVisibility(View.VISIBLE);
                    if (colIndex == main_IMG_birds[0].length - 1) {
                        collisionDetection(currentRowIndex, colIndex);
                    }
                    colIndex++;
                    handler.postDelayed(this, 1000); // Repeat every 1 second
                } else {
                    handler.removeCallbacks(this);
                }
            }
        };
        handler.post(birdRunnable);
    }

    private void collisionDetection(int currentRowIndex, int currentColIndex) {
        if (((main_IMG_birds[currentRowIndex][currentColIndex].getVisibility() == View.VISIBLE) && (main_IMG_spaceship[currentRowIndex].getVisibility() == View.VISIBLE))) {
            toastAndVibrate("watch out!");
            if (main_IMG_hearts[0].getVisibility() == View.VISIBLE) {
                main_IMG_hearts[0].setVisibility(View.INVISIBLE);
            } else if (main_IMG_hearts[1].getVisibility() == View.VISIBLE) {
                main_IMG_hearts[1].setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(this, "Hearts are over! New game starts!", Toast.LENGTH_LONG).show();
                main_IMG_hearts[0].setVisibility(View.VISIBLE);
                main_IMG_hearts[1].setVisibility(View.VISIBLE);
            }
        }
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
                        findViewById(R.id.main_IMG_bird5)
                },
                {
                        findViewById(R.id.main_IMG_bird6),
                        findViewById(R.id.main_IMG_bird7),
                        findViewById(R.id.main_IMG_bird8),
                        findViewById(R.id.main_IMG_bird9),
                        findViewById(R.id.main_IMG_bird10)

                },
                {
                        findViewById(R.id.main_IMG_bird11),
                        findViewById(R.id.main_IMG_bird12),
                        findViewById(R.id.main_IMG_bird13),
                        findViewById(R.id.main_IMG_bird14),
                        findViewById(R.id.main_IMG_bird15)
                },
                {
                        findViewById(R.id.main_IMG_bird16),
                        findViewById(R.id.main_IMG_bird17),
                        findViewById(R.id.main_IMG_bird18),
                        findViewById(R.id.main_IMG_bird19),
                        findViewById(R.id.main_IMG_bird20)
                },
                {
                        findViewById(R.id.main_IMG_bird21),
                        findViewById(R.id.main_IMG_bird22),
                        findViewById(R.id.main_IMG_bird23),
                        findViewById(R.id.main_IMG_bird24),
                        findViewById(R.id.main_IMG_bird25)
                }

        };

    }
}
