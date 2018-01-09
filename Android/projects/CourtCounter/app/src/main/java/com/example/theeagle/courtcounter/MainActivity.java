package com.example.theeagle.courtcounter;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int scoreA;
    private int scoreB;
    TextView scoreViewA, scoreViewB;
    Button buttonAdd6A, buttonAdd3A, buttonAdd2A, buttonKickA, buttonAdd6B, buttonAdd3B, buttonAdd2B, buttonKickB, rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviews();

    }

    private void initviews() {
        buttonAdd6A = findViewById(R.id.six);
        buttonAdd3A = findViewById(R.id.three);
        buttonAdd2A = findViewById(R.id.tackle);
        buttonKickA = findViewById(R.id.kick);
        buttonAdd6B = findViewById(R.id.six_2);
        buttonAdd3B = findViewById(R.id.three_2);
        buttonAdd2B = findViewById(R.id.tackle_2);
        buttonKickB = findViewById(R.id.kick_2);
        rest = findViewById(R.id.reset_btn);
        buttonAdd6A.setOnClickListener(this);
        buttonAdd3A.setOnClickListener(this);
        buttonAdd2A.setOnClickListener(this);
        buttonKickA.setOnClickListener(this);
        buttonAdd6B.setOnClickListener(this);
        buttonAdd3B.setOnClickListener(this);
        buttonAdd2B.setOnClickListener(this);
        buttonKickB.setOnClickListener(this);
        rest.setOnClickListener(this);
    }


    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        scoreViewA = findViewById(R.id.score_tv);
        scoreViewA.setText(String.valueOf(score));
    }

    private void addSixA() {
        scoreA = scoreA + 6;
        displayForTeamA(scoreA);
    }

    private void addThreeA() {
        scoreA = scoreA + 3;
        displayForTeamA(scoreA);
    }

    private void addTwoA() {
        scoreA = scoreA + 2;
        displayForTeamA(scoreA);
    }

    private void kickA() {
        scoreA = scoreA + 1;
        displayForTeamA(scoreA);

    }

    public void displayForTeamB(int score2) {
        scoreViewB = findViewById(R.id.score_tv_2);
        scoreViewB.setText(String.valueOf(score2));
    }

    private void addSixB() {
        scoreB = scoreB + 6;
        displayForTeamB(scoreB);
    }

    private void addThreeB() {
        scoreB = scoreB + 3;
        displayForTeamB(scoreB);
    }

    private void addTwoB() {
        scoreB = scoreB + 2;
        displayForTeamB(scoreB);
    }

    private void kickB() {
        scoreB = scoreB + 1;
        displayForTeamB(scoreB);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.six:
                addSixA();
                break;
            case R.id.three:
                addThreeA();
                break;
            case R.id.tackle:
                addTwoA();
                break;
            case R.id.kick:
                kickA();
                break;
            case R.id.six_2:
                addSixB();
                break;
            case R.id.three_2:
                addThreeB();
                break;
            case R.id.tackle_2:
                addTwoB();
                break;
            case R.id.kick_2:
                kickB();
                break;
            case R.id.reset_btn:
                restScore();
                break;
        }
    }

    private void restScore() {
        scoreA = 0;
        scoreB = 0;
        displayForTeamA(scoreA);
        displayForTeamB(scoreB);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("scoreA", scoreA);
        outState.putInt("scoreB", scoreB);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        scoreA = savedInstanceState.getInt("scoreA");
        scoreB = savedInstanceState.getInt("scoreB");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "haram", Toast.LENGTH_SHORT).show();
            scoreViewB.setText(scoreB);
            scoreViewA.setText(scoreA);

        } else {
            Toast.makeText(this, "haram2", Toast.LENGTH_SHORT).show();
            scoreViewB.setText(scoreB);
            scoreViewA.setText(scoreA);

        }
    }
}
