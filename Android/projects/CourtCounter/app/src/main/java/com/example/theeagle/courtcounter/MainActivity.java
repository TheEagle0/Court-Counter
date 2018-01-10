package com.example.theeagle.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    /**
     * Initialize the Views
     */

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
        listeners();
    }

    /**
     * Adding onClickListeners to the Views
     */

    private void listeners() {
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

    /**
     * Add six points to the team A
     */
    private void addSixA() {
        scoreA = scoreA + 6;
        displayForTeamA(scoreA);
    }

    /**
     * Add three points to the team A
     */
    private void addThreeA() {
        scoreA = scoreA + 3;
        displayForTeamA(scoreA);
    }

    /**
     * Add two points to the team A
     */
    private void addTwoA() {
        scoreA = scoreA + 2;
        displayForTeamA(scoreA);
    }

    /**
     * Add one point to the team A
     */
    private void kickA() {
        scoreA = scoreA + 1;
        displayForTeamA(scoreA);

    }

    /**
     * Displays the given score for Team B.
     */
    public void displayForTeamB(int score2) {
        scoreViewB = findViewById(R.id.score_tv_2);
        scoreViewB.setText(String.valueOf(score2));
    }

    /**
     * Add six to points the team B
     */
    private void addSixB() {
        scoreB = scoreB + 6;
        displayForTeamB(scoreB);
    }

    /**
     * Add three points to the team B
     */
    private void addThreeB() {
        scoreB = scoreB + 3;
        displayForTeamB(scoreB);
    }

    /**
     * Add two points to the team B
     */
    private void addTwoB() {
        scoreB = scoreB + 2;
        displayForTeamB(scoreB);
    }

    /**
     * Add one point to the team B
     */
    private void kickB() {
        scoreB = scoreB + 1;
        displayForTeamB(scoreB);

    }

    /**
     * Adding Actions To the Views
     */
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

    /**
     * Resetting the Score
     */
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
    protected void onResume() {
        super.onResume();
        displayForTeamA(scoreA);
        displayForTeamB(scoreB);
    }
}
