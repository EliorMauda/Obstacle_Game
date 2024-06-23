package com.example.recognizethesong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView numOfQuestions, score;
    ImageView startImage, pauseImage;
    Button ans1, ans2, ans3, ans4;
    MediaPlayer mediaPlayer;
    QuizModel quizModel;
    int current = 0;
    int currentScore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        newQuestion();
    }

    private void initViews() {
        numOfQuestions = findViewById(R.id.numOfQuestions);
        score = findViewById(R.id.score);
        startImage = findViewById(R.id.startImage);
        pauseImage = findViewById(R.id.pauseImage);
        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        ans4 = findViewById(R.id.ans4);
        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        quizModel = new QuizModel();
    }

    private void newQuestion() {
        numOfQuestions.setText("Question " + (current + 1) + "/" + quizModel.correctAnswers.length);
        score.setText("Score: " + currentScore);
        mediaPlayer = MediaPlayer.create(this, quizModel.songs[current]);
        ans1.setText(quizModel.answers[current][0]);
        ans2.setText(quizModel.answers[current][1]);
        ans3.setText(quizModel.answers[current][2]);
        ans4.setText(quizModel.answers[current][3]);
        playStart();
        playStop();
    }


    private void playStart() {
        startImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start(); // Start playing if not already playing
                }
            }
        });
    }

    private void playStop() {
        pauseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop(); // Stop playback if playing
                    mediaPlayer.prepareAsync(); // Prepare the MediaPlayer for the next play
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release the MediaPlayer when the activity is paused
        }
    }


    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;
        if (clickedButton.getText().toString().equals(quizModel.correctAnswers[current])) {
            clickedButton.setBackgroundResource(R.drawable.right_answer_background);
            currentScore++;
            score.setText("Score: " + currentScore);
        } else {
            clickedButton.setBackgroundResource(R.drawable.wrong_answer_background);
        }
        current++;

        // Delay the color change for 2 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clickedButton.setBackgroundResource(R.drawable.intro_btn_background); // Reset button color after 2 seconds
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop(); // Stop playback if playing
                    mediaPlayer.prepareAsync(); // Prepare the MediaPlayer for the next play
                }
                if (current >= quizModel.correctAnswers.length) {
                    animateScoreDrop();
                } else {
                    newQuestion(); // Move to the next question after color change
                }
            }
        }, 1500); // 2000 milliseconds = 2 seconds
    }

    private void animateScoreDrop() {
        final float startScoreY = score.getY();
        final float endScoreY = (findViewById(android.R.id.content)).getHeight() / 2 - score.getHeight() / 2;

        // Make other views invisible
        numOfQuestions.setVisibility(View.INVISIBLE);
        startImage.setVisibility(View.INVISIBLE);
        pauseImage.setVisibility(View.INVISIBLE);
        ans1.setVisibility(View.INVISIBLE);
        ans2.setVisibility(View.INVISIBLE);
        ans3.setVisibility(View.INVISIBLE);
        ans4.setVisibility(View.INVISIBLE);
        score.setTextSize(80);

        score.animate()
                .translationY(endScoreY - startScoreY)
                .setDuration(1000) // Set duration for the animation
                .start();
    }

}
