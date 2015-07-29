package com.sean_redmond.darts_scorer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

public class play_game extends ActionBarActivity {

    TextView scored, remainingP1, player1Legs, remainingP2, player2Legs, textViewTime, currentlyP1Icon, currentlyP2Icon,
            numberOfLegs;
    Button btnStart;
    Button num_0, num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, del_button, enter_button;


    int legsToPlay;//Once this number of legs is reached the game is completed.
    int startingScore;//set instance variable so can use this to set text of start score onCreate and initialise starting score for calc's for both players
    int timePerShot;
    int timeRunOutPenalty;
    int legsPlayed = 0;
    int[] sixtyPlusScores = {0, 0};
    int[] tonPlusScores = {0, 0};
    int[] tonFortyPlusScores = {0, 0};
    int[] tonEightyScores = {0, 0};
    int[] legsWon = {0, 0};
    int[] dartsThrown = {0, 0};
    int[] pointsScored = {0, 0};
    int oneDartAverage[] = {0, 0};
    int threeDartAverage[] = {0, 0};
    boolean ignoreScoreHitIfTimeOut = false;
    int[] scoreLeft;

    SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);//SoundPool object used for sound effects
    CounterClass timer;//class declaration - to initialize in the code.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        btnStart = (Button) findViewById(R.id.btnStart);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        currentlyP1Icon = (TextView) findViewById(R.id.currentlyP1Icon);
        currentlyP2Icon = (TextView) findViewById(R.id.currentlyP2Icon);
        num_1 = (Button) findViewById(R.id.num_1);
        num_2 = (Button) findViewById(R.id.num_2);
        num_3 = (Button) findViewById(R.id.num_3);
        num_4 = (Button) findViewById(R.id.num_4);
        num_5 = (Button) findViewById(R.id.num_5);
        num_6 = (Button) findViewById(R.id.num_6);
        num_7 = (Button) findViewById(R.id.num_7);
        num_8 = (Button) findViewById(R.id.num_8);
        num_9 = (Button) findViewById(R.id.num_9);
        num_0 = (Button) findViewById(R.id.num_0);
        del_button = (Button) findViewById(R.id.del_button);
        enter_button = (Button) findViewById(R.id.enter_button);


        scored = (TextView) findViewById(R.id.scoredTxtBox);
        scored.setText("0");//set initial value for textbox
        player1Legs = (TextView) findViewById(R.id.textView9);
        player1Legs.setText("0");
        player2Legs = (TextView) findViewById(R.id.textView10);
        player2Legs.setText("0");


        currentlyP1Icon.setVisibility(View.VISIBLE);//set top player icon to visible when starting match as this is first player up


        //receive the values from game_setup class

        numberOfLegs = (TextView) findViewById(R.id.legsToPlayDisplay);
        remainingP1 = (TextView) findViewById(R.id.remainingTxtBox);
        remainingP2 = (TextView) findViewById(R.id.textView5);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            legsToPlay = extras.getInt("legsToPlay");
            startingScore = extras.getInt("scoreStartFrom");
            timePerShot = extras.getInt("timerStartFrom");//the textview for this already created for this at the beginning of onCreate
            timeRunOutPenalty = extras.getInt("timeUpPenalty");

            //set text of textfields
            if (legsToPlay == 1) {//will be plural i.e. "legs" if the user decides to play two or more legs
                numberOfLegs.setText("First to " + legsToPlay + " leg");
            } else {
                numberOfLegs.setText("First to " + legsToPlay + " legs");
            }
            remainingP1.setText("" + startingScore);//will display the initial value in the score left display box.
            remainingP2.setText("" + startingScore);
            scoreLeft = new int[]{startingScore, startingScore};
            textViewTime.setText("00:" + timePerShot);


           // final CounterClass timer = new CounterClass(timePerShot * 1000, 1000);//i.e. 3 mins (180 secs) for 180,000

            timer = new CounterClass(timePerShot *1000, 1000);//i.e. 3 mins (180 secs) for 180,000

            //make the start button flash at the start to catch the users attention - this effect draws the users attention to this button which has to be pressed to start the clock (this effect also used to stop clock)
            final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
            animation.setDuration(2000); // every 1000 = 1 sec i.e. every 100 is 1/10 of a second
            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
            animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
            animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
            btnStart.startAnimation(animation);
            enter_button.setEnabled(false);//have to click start button before can enter a score so have disable this button on setup to reflect this.

            btnStart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    timer.start();
                    enter_button.setEnabled(false);
                    v.clearAnimation();//turns off the flashing effect
                    btnStart.setVisibility(View.INVISIBLE);//This start timer button can only be used once at the start of a leg (from then on use enter button to complete a leg).
                }
            });
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("p1scoreLeft", Integer.parseInt(remainingP1.getText().toString()));
        editor.putInt("p2scoreLeft", Integer.parseInt(remainingP2.getText().toString()));
        editor.putInt("p1Legs", Integer.parseInt(player1Legs.getText().toString()));
        editor.putInt("p2Legs", Integer.parseInt(player2Legs.getText().toString()));
        editor.putInt("playerForPointer", player);
        editor.putInt("theStartScore", startingScore);//want to save this so can ensure if return to saved game subsequent legs start from the same opening score e.g 501.
        editor.putInt("timePerThrow", timePerShot);
        editor.apply();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        int exitOnResume = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            exitOnResume = extras.getInt("ignoreOnResumeValues");
        }

        if (exitOnResume == 1) {
            return;//return as don't want to use the shared preference (saved values) if play new game - if new game will start afresh.
        }


        int p1ScoreToGo = sharedPref.getInt("p1scoreLeft", 0);
        remainingP1.setText("" + p1ScoreToGo);
        scoreLeft[0] = p1ScoreToGo;//setting player 1's score equal to the saved value that was retrieved

        int p2ScoreToGo = sharedPref.getInt("p2scoreLeft", 0);
        remainingP2.setText("" + p2ScoreToGo);
        scoreLeft[1] = p2ScoreToGo; //setting player 2's score equal to the saved value that was retrieved

        int p1Legs = sharedPref.getInt("p1Legs", 0);
        player1Legs.setText("" + p1Legs);
        legsWon[0] = p1Legs;

        int p2Legs = sharedPref.getInt("p2Legs", 0);
        player2Legs.setText("" + p2Legs);
        legsWon[1] = p2Legs;

        int beginningScore = sharedPref.getInt("theStartScore", 0);
        startingScore = beginningScore;//setting the instance var startingScore equal to the transferred value - scoreLeft[0] and scoreLeft[1] are set to equal this at end of game

        int timeForThrow = sharedPref.getInt("timePerThrow", 0);//creating variable to equal saved value in onPause method and then setting instance var equal to this value.
        timePerShot = timeForThrow;


        int playerPointerIcon = sharedPref.getInt("playerForPointer", 0);//remember player number so will have the pointer icon correctly positioned
        player = playerPointerIcon;

        if (player == 1) {
            currentlyP2Icon.setVisibility(View.VISIBLE);//will switch the pointer to opponent when entered score as their turn.
            currentlyP1Icon.setVisibility(View.INVISIBLE);
        } else {
            currentlyP1Icon.setVisibility(View.VISIBLE);
            currentlyP2Icon.setVisibility(View.INVISIBLE);
        }
    }


    public void onClickResetScores(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Start New Game?")
                .setMessage("Are you sure you want to start a new game?")
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        for (int x = 0; x <= 1; x++) {
                            scoreLeft[x] = startingScore;
                            legsWon[x] = 0;
                            sixtyPlusScores[x] = 0;
                            tonPlusScores[x] = 0;
                            tonFortyPlusScores[x] = 0;
                            tonEightyScores[x] = 0;
                            legsWon[x] = 0;
                            dartsThrown[x] = 0;
                            pointsScored[x] = 0;
                            oneDartAverage[x] = 0;
                            threeDartAverage[x] = 0;
                        }

                        legsPlayed = 0;
                        remainingP1.setText("" + startingScore);
                        remainingP2.setText("" + startingScore);
                        player1Legs.setText("0");
                        player2Legs.setText("0");

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void matchStats() {//can call this method when required to switch to transfer values and move to the stats screen
        Intent i = new Intent(getApplicationContext(), statistics.class);
        i.putExtra("legsDone", legsPlayed);
        i.putExtra("sixtyPlusScoresP1", sixtyPlusScores[0]);
        i.putExtra("sixtyPlusScoresP2", sixtyPlusScores[1]);
        i.putExtra("tonPlusScoresP1", tonPlusScores[0]);
        i.putExtra("tonPlusScoresP2", tonPlusScores[1]);
        i.putExtra("tonFortyPlusScoresP1", tonFortyPlusScores[0]);
        i.putExtra("tonFortyPlusScoresP2", tonFortyPlusScores[1]);
        i.putExtra("tonEightyScoresP1", tonEightyScores[0]);
        i.putExtra("tonEightyScoresP2", tonEightyScores[1]);
        i.putExtra("legsWonP1", legsWon[0]);
        i.putExtra("legsWonP2", legsWon[1]);
        i.putExtra("oneDartAveP1", oneDartAverage[0]);
        i.putExtra("oneDartAveP2", oneDartAverage[1]);
        i.putExtra("threeDartAveP1", threeDartAverage[0]);
        i.putExtra("threeDartAveP2", threeDartAverage[1]);
        startActivity(i);
    }

    //stats button opens the statistics activity
    public void onClick(View view) {//this method creates intent which allows to switch to other activity
        matchStats();
    }


    boolean isZero = true;

    public void numClicked(View sender) {//this method inputs 3 dart score but can't be zero i.e. can't input 00.

        timer.cancel();
        enter_button.setEnabled(true);

        Button bt = (Button) sender;
        if (scored.getText().length() > 2)
            return;//for e.g. if go with 2 it can only input 3 numbers (you click a number button to call this method as it's an onClick method)
        if (isZero) {
            if (bt.getText().toString().equals("0"))
                return; //stops user from being able to type in multiple zeros at the start.
            scored.setText(bt.getText());//this sets the value of the first number by getting the text from the button
            isZero = false;//set this to false so go on to the else statement after the first num is done
        } else {
            scored.append(bt.getText());//inputs subsequent numbers
        }
    }


    public void movePointerIfValidScore() {
        if (player == 0) {
            currentlyP2Icon.setVisibility(View.VISIBLE);//will switch the pointer to opponent when entered score as their turn.
            currentlyP1Icon.setVisibility(View.INVISIBLE);
        } else {
            currentlyP1Icon.setVisibility(View.VISIBLE);
            currentlyP2Icon.setVisibility(View.INVISIBLE);
        }
    }


    int player = 0;
    int[] legs = {0, 0};//keeps track of the number of legs won

    public void enterClicked(View sender) {


        //this nearly the same as the code section in onFinish method - try to reuse and modify
        Button[] buttons = {num_0, num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, del_button};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(true);
        }
        //the code above would be better if could be made more efficient as already using similiar code in onFinish
        int scoreHit = Integer.parseInt(scored.getText().toString());//convert the 3 dart score to store it as an integer


        int[] invalidScores = {179, 178, 176, 175, 173, 172, 169, 168, 166, 165, 163, 162};

        boolean invalidNumHit = false;
        for (int i = 0; i < invalidScores.length; i++) {
            if (scoreHit == invalidScores[i]) {
                invalidNumHit = true;
            }
        }


        if (scoreHit > 180 || scoreHit > scoreLeft[player] || scoreHit + 1 == scoreLeft[player] || (scoreHit == 159 && scoreLeft[player] == 159) || invalidNumHit) {
            //won't adjust score left if invalid score/checkout entered
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You've entered an invalid score, Try again!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 75);
            toast.show();

            scored.setText("0");//have to reset score to zero as won't do below as will return from method if invalid score
            isZero = true;//when the "numClicked" method is used again there will be proper formatting using number 0, i.e. can't proceed by clicking zero as first number (so can't have 00). Also, when input next number there won't be a zero in front of it (e.g. won't have 034).

            return;//will exit method i.e. won't adjust scores and stats and switch player if invalid score entered.

        }

        enter_button.setEnabled(false);//can't click enter again until stop is clicked (as it will be re-enabled then) or if run out of time (can click to resume game)

        //final CounterClass timer = new CounterClass(timePerShot * 1000, 1000);
        timer.start();//start the timer when the user clicks enter



        if (ignoreScoreHitIfTimeOut == true) {//don't want to adjust score for the number entered if time ran out
            scoreHit = 0;
        }

        if (scoreHit >= 60 && scoreHit < 100) {
            sixtyPlusScores[player]++;
        } else if (scoreHit >= 100 && scoreHit < 140) {
            tonPlusScores[player]++;
        } else if (scoreHit >= 140 && scoreHit < 180) {
            tonFortyPlusScores[player]++;
        } else if (scoreHit == 180) {
            tonEightyScores[player]++;
        } else {
            //do nothing not between 60 and 180
        }

        movePointerIfValidScore();


        scoreLeft[player] = scoreLeft[player] - scoreHit;

        if (scoreHit == 180) {

            int soundId = sp.load(this, R.raw.russ_bray_180, 1);

            sp.play(soundId, 1, 1, 0, 0, 1);

            MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.russ_bray_180); // in 2nd param u have to pass your desire ringtone
            //mPlayer.prepare();
            mPlayer.start();

        } else if (scoreHit == 140) {

            int soundId = sp.load(this, R.raw.russ_bray_140, 1);

            sp.play(soundId, 1, 1, 0, 0, 1);

            MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.russ_bray_140); // in 2nd param u have to pass your desire ringtone
            //mPlayer.prepare();
            mPlayer.start();

        } else if (scoreHit == 100) {

            int soundId = sp.load(this, R.raw.russ_bray_100, 1);

            sp.play(soundId, 1, 1, 0, 0, 1);

            MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.russ_bray_100); // in 2nd param u have to pass your desire ringtone
            //mPlayer.prepare();
            mPlayer.start();

        } else if (scoreHit == 60) {

            int soundId = sp.load(this, R.raw.russ_bray_60, 1);

            sp.play(soundId, 1, 1, 0, 0, 1);

            MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.russ_bray_60); // in 2nd param u have to pass your desire ringtone
            //mPlayer.prepare();
            mPlayer.start();


        } else {
            //do nothing if none of these scores
        }


        pointsScored[player] = pointsScored[player] + scoreHit;
        dartsThrown[player] += 3;
        if (player == 0) {
            remainingP1.setText("" + scoreLeft[player]);//will adjust the score left value if a valid input is inputted
        } else {
            remainingP2.setText("" + scoreLeft[player]);
        }


// MAYBE create method called legsWonCounter(){
        if (scoreLeft[player] == 0) {

            if (player == 0) {
                legs[player] = legs[player] + 1;
                player1Legs.setText("" + legs[player]);
                legsWon[player]++;
                dartsThrown[player] += 3;

                //get average score - player 1 average
                oneDartAverage[0] = pointsScored[0] / dartsThrown[0];
                threeDartAverage[0] = oneDartAverage[0] * 3;
                //player 2 average
                oneDartAverage[1] = pointsScored[1] / dartsThrown[1];
                threeDartAverage[1] = oneDartAverage[1] * 3;


                player = 1;//switch player as it is now their turn
            } else {
                legs[player] = legs[player] + 1;
                player2Legs.setText("" + legs[player]);
                legsWon[player]++;
                dartsThrown[player] += 3;

                //get average score for both players - player one
                oneDartAverage[0] = pointsScored[0] / dartsThrown[0];
                threeDartAverage[0] = oneDartAverage[0] * 3;
                //player two average
                oneDartAverage[1] = pointsScored[1] / dartsThrown[1];
                threeDartAverage[1] = oneDartAverage[1] * 3;


                player = 0;
            }
            scoreLeft[0] = startingScore;//If 0 remainingP1 reset remainingP1 to 501 to start a new game.
            scoreLeft[1] = startingScore;
            remainingP1.setText("" + startingScore);
            remainingP2.setText("" + startingScore);

            legsPlayed++;//for statistics screen
        }
// this would be the end of this method here => }


        //switch turn - turn switches in the legs won counter if section of code - but this is only used if the leg is won so this is here to switch player during the game>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        if (player == 0) {
            player = 1;
        } else {
            player = 0;
        }//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        scored.setText("0");
        isZero = true;//when the "numClicked" method is used again there will be proper formatting using number 0, i.e. can't proceed by clicking zero as first number (so can't have 00). Also, when input next number there won't be a zero in front of it (e.g. won't have 034).
        ignoreScoreHitIfTimeOut = false;//reset to false so will continue to adjust scores if timer doesn't run out


        if (legsPlayed == legsToPlay) {

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Game completed", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            matchStats();
            finish();//once the game is complete don't want to go back to this screen once complete.
        }
    }

    //have to find out how to do this best.
    public void deleteClicked(View sender) {
        Button bt = (Button) sender;
        scored.setText("0");
        isZero = true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {

            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            textViewTime.setText(hms);
        }

        @Override
        public void onFinish() {
            enter_button.setEnabled(true);//when timer reaches zero can click enter again to restart clock.

            textViewTime.setText("Time up. " + timeRunOutPenalty + " point penalty! Press Enter to re-start clock!");
            scoreLeft[player] += timeRunOutPenalty;// if the timer finishes before player presses stop, ignore the 3 dart score hit - just add 60 to what was already on (i.e. x += 60 is equivalent to x = x + 60) and add back scoreHit as player doesn't get the points.
            ignoreScoreHitIfTimeOut = true;

            Button[] buttons = {num_0, num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, del_button};
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setEnabled(false);
            }
        }
    }
}