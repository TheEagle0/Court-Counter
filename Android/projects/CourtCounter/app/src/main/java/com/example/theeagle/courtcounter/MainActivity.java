package com.example.theeagle.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int scoreA;
    private int scoreB;
    TextView scoreViewA,scoreViewB;
    Button buttonAdd3A, buttonAdd2A, buttonFreeThrowA, buttonAdd3B, buttonAdd2B, buttonFreeThrowB, rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviews();
    }

    private void initviews() {
        buttonAdd3A = findViewById(R.id.three);
        buttonAdd2A = findViewById(R.id.two);
        buttonFreeThrowA = findViewById(R.id.free_throw);
        buttonAdd3B = findViewById(R.id.three_2);
        buttonAdd2B = findViewById(R.id.two_2);
        buttonFreeThrowB = findViewById(R.id.free_throw_2);
        rest = findViewById(R.id.reset_btn);
        buttonAdd3A.setOnClickListener(this);
        buttonAdd2A.setOnClickListener(this);
        buttonFreeThrowA.setOnClickListener(this);
        buttonAdd3B.setOnClickListener(this);
        buttonAdd2B.setOnClickListener(this);
        buttonFreeThrowB.setOnClickListener(this);
        rest.setOnClickListener(this);
    }


    /**
     * Displays the given score for Team A.
     */
    public void displayForTeamA(int score) {
        scoreViewA = findViewById(R.id.score_tv);
        scoreViewA.setText(String.valueOf(score));
    }

    private void addThreeA() {
        scoreA = scoreA + 3;
        displayForTeamA(scoreA);
    }

    private void addTwoA() {
        scoreA = scoreA + 2;
        displayForTeamA(scoreA);
    }

    private void freeThrowA() {
        scoreA = scoreA + 1;
        displayForTeamA(scoreA);

    }

    public void displayForTeamB(int score2) {
        scoreViewB = findViewById(R.id.score_tv_2);
        scoreViewB.setText(String.valueOf(score2));
    }

    private void addThreeB() {
        scoreB = scoreB + 3;
        displayForTeamB(scoreB);
    }

    private void addTwoB() {
        scoreB = scoreB + 2;
        displayForTeamB(scoreB);
    }

    private void freeThrowB() {
        scoreB = scoreB + 1;
        displayForTeamB(scoreB);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.three:
                addThreeA();
                break;
            case R.id.two:
                addTwoA();
                break;
            case R.id.free_throw:
                freeThrowA();
                break;
            case R.id.three_2:
                addThreeB();
                break;
            case R.id.two_2:
                addTwoB();
                break;
            case R.id.free_throw_2:
                freeThrowB();
                break;
            case R.id.reset_btn:
                restScore();
                break;
        }
    }

    private void restScore() {
        scoreA=0;
        scoreB =0;
        displayForTeamA(scoreA);
        displayForTeamB(scoreB);
    }
}
