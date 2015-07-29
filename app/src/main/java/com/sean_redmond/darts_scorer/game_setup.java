package com.sean_redmond.darts_scorer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class game_setup extends ActionBarActivity{

    private Spinner numLegsSpinner, startingScoreSpinner, timePerShotSpinner, penaltyForTimeoutSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);

        startingPointsSpinner();
        timePerShotSpinner();
        penaltyForTimeoutSpinner();
        legsToPlaySpinner();

    }

    // add items into spinner dynamically
    public void legsToPlaySpinner(){

        numLegsSpinner = (Spinner) findViewById(R.id.legsPerGame);

        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        numLegsSpinner.setAdapter(dataAdapter);

    }

    public void startingPointsSpinner() {

        startingScoreSpinner = (Spinner) findViewById(R.id.startingScore);
        List<String> list = new ArrayList<String>();
        list.add("101");
        list.add("301");
        list.add("501");
        list.add("701");
        list.add("901");
        list.add("1001");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        startingScoreSpinner.setAdapter(dataAdapter);
    }

    public void timePerShotSpinner(){

        timePerShotSpinner = (Spinner) findViewById(R.id.timePerShot);
        List<String> list = new ArrayList<String>();
        list.add("10");
        list.add("15");
        list.add("20");
        list.add("25");
        list.add("30");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        timePerShotSpinner.setAdapter(dataAdapter);

    }

    public void penaltyForTimeoutSpinner(){

        penaltyForTimeoutSpinner = (Spinner)findViewById(R.id.penaltyForTimeout);
        List<String> list = new ArrayList<String>();
        list.add("10");
        list.add("20");
        list.add("30");
        list.add("40");
        list.add("50");
        list.add("60");
        list.add("70");
        list.add("80");
        list.add("90");
        list.add("100");
        list.add("150");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        penaltyForTimeoutSpinner.setAdapter(dataAdapter);

    }


    public void onClickStartGame(View view){//this method creates intent which allows to switch to other activity

        int skipSavedScores = 1;//this variable used in play_game onResume to skip saved pref values if play new game

        numLegsSpinner = (Spinner) findViewById(R.id.legsPerGame);
        startingScoreSpinner = (Spinner) findViewById(R.id.startingScore);
        timePerShotSpinner = (Spinner) findViewById(R.id.timePerShot);
        penaltyForTimeoutSpinner = (Spinner) findViewById(R.id.penaltyForTimeout);


        int legsToPlay = Integer.parseInt(String.valueOf(numLegsSpinner.getSelectedItem()));
        int scoreToStartOn = Integer.parseInt(String.valueOf(startingScoreSpinner.getSelectedItem()));
        int clockStartTime = Integer.parseInt(String.valueOf(timePerShotSpinner.getSelectedItem()));
        int penaltyForTimeUp = Integer.parseInt(String.valueOf(penaltyForTimeoutSpinner.getSelectedItem()));

        Intent i = new Intent(getApplicationContext(), play_game.class);
        i.putExtra("legsToPlay", legsToPlay );
        i.putExtra("scoreStartFrom", scoreToStartOn);
        i.putExtra("timerStartFrom", clockStartTime);
        i.putExtra("timeUpPenalty", penaltyForTimeUp);

        i.putExtra("ignoreOnResumeValues", skipSavedScores);

        startActivity(i);
        finish();
    }


    public void onClickContinueLast(View v){

        numLegsSpinner = (Spinner) findViewById(R.id.legsPerGame);
        startingScoreSpinner = (Spinner) findViewById(R.id.startingScore);
        timePerShotSpinner = (Spinner) findViewById(R.id.timePerShot);
        penaltyForTimeoutSpinner = (Spinner) findViewById(R.id.penaltyForTimeout);


        int legsToPlay = Integer.parseInt(String.valueOf(numLegsSpinner.getSelectedItem()));
        int scoreToStartOn = Integer.parseInt(String.valueOf(startingScoreSpinner.getSelectedItem()));
        int clockStartTime = Integer.parseInt(String.valueOf(timePerShotSpinner.getSelectedItem()));
        int penaltyForTimeUp = Integer.parseInt(String.valueOf(penaltyForTimeoutSpinner.getSelectedItem()));

        Intent i = new Intent(getApplicationContext(), play_game.class);
        i.putExtra("legsToPlay", legsToPlay );
        i.putExtra("scoreStartFrom", scoreToStartOn);
        i.putExtra("timerStartFrom", clockStartTime);
        i.putExtra("timeUpPenalty", penaltyForTimeUp);


        startActivity(i);
        finish();

     }
}