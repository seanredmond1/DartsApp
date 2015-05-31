package com.example.seanredmond.darts_scorer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class play_game extends ActionBarActivity {

    TextView scored, remainingP1, player1Legs, remainingP2, player2Legs, textViewTime;
    Button btnStart, btnStop;
    Button num_0, num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9, input_26, input_45;


    //statistics screen variables
    int legsPlayed = 0;
    int [] sixtyPlusScores = {0,0};
    int [] tonPlusScores = {0,0};
    int [] tonFortyPlusScores = {0,0};
    int [] tonEightyScores = {0,0};
    int [] legsWon = {0,0};
    int [] dartsThrown = {0,0};
    int [] pointsScored = {0,0};
    int oneDartAverage [] = {0, 0};
    int threeDartAverage [] = {0,0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewTime.setText("00:00:10");
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
        input_26 = (Button) findViewById(R.id.input_26);
        input_45 = (Button) findViewById(R.id.input_45);

        final CounterClass timer = new CounterClass(15000, 1000);//i.e. 3 mins (180 secs) for 180,000

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timer.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timer.cancel();
            }
        });

        scored = (TextView) findViewById(R.id.scoredTxtBox);
        scored.setText("0");//set initial value for textbox
        remainingP1 = (TextView) findViewById(R.id.remainingTxtBox);
        remainingP1.setText("501");//will display 501 as the initial value in the score left display box.
        player1Legs = (TextView) findViewById(R.id.textView9);
        player1Legs.setText("0");
        remainingP2 = (TextView) findViewById(R.id.textView5);
        remainingP2.setText("501");
        player2Legs = (TextView) findViewById(R.id.textView10);
        player2Legs.setText("0");

    }



    //stats button opens the statistics activity
    public void onClick(View view) {//this method creates intent which allows to switch to other activity
        Intent i = new Intent(getApplicationContext(), statistics.class);
        i.putExtra("legsDone", legsPlayed );
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









    boolean isZero = true;

    public void numClicked(View sender) {//this method inputs 3 dart score but can't be zero i.e. can't input 00.
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


    int player = 0;
    int[] scoreLeft = {501, 501};
    int[] legs = {0, 0};//keeps track of the number of legs won

    public void enterClicked(View sender) {

        //this nearly the same as the code section in onFinish method - try to reuse and modify
        Button[] buttons = {num_0, num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(true);
        }
        //the code above would be better if could be made more efficient as already using similiar code in onFinish

        int scoreHit = Integer.parseInt(scored.getText().toString());//convert the 3 dart score to store it as an integer


        final CounterClass timer = new CounterClass(10000, 1000);
        timer.start();//start the timer when the user clicks enter
        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timer.cancel();
            }
        });

// MAYBE create method called scoreRemainingCalc(){   (could maybe return an int to feed player score left 0 into next method so may not need to use scoreleft as an instance varibale

        int[] invalidScores = {179, 178, 176, 175, 173, 172, 169, 168, 166, 165, 163, 162};

        boolean invalidNumHit = false;
        for (int i = 0; i < invalidScores.length; i++) {
            if (scoreHit == invalidScores[i]) {
                invalidNumHit = true;
            }
        }
        if (scoreHit > 180 || scoreHit > scoreLeft[player] || scoreHit + 1 == scoreLeft[player] || (scoreHit == 159 && scoreLeft[player] == 159) || invalidNumHit) {
            //won't adjust score left if invalid score/checkout entered
        } else {

            if (scoreHit>=60 && scoreHit<100){
                sixtyPlusScores[player]++;
            }
            else if(scoreHit>=100&& scoreHit<140){
                tonPlusScores[player]++;
            }else if(scoreHit>=140 && scoreHit <180){
                tonFortyPlusScores[player]++;
            }else if(scoreHit==180){
                tonEightyScores[player]++;
            }else{
                //do nothing not between 100 and 180
            }

            scoreLeft[player] = scoreLeft[player] - scoreHit;

            pointsScored[player] = pointsScored[player]+scoreHit;
            dartsThrown[player]+=3;
            if (player == 0) {
                remainingP1.setText("" + scoreLeft[player]);//will adjust the score left value if a valid input is inputted
            } else {
                remainingP2.setText("" + scoreLeft[player]);
            }
        }


//this would be the end of the method so put closing bracket here => }


// MAYBE create method called legsWonCounter(){
        if (scoreLeft[player] == 0) {

            legsPlayed++;//for statistics screen

            if (player == 0) {
                legs[player] = legs[player] + 1;
                player1Legs.setText("" + legs[player]);
                legsWon[player]++;
                dartsThrown[player]+=3;

                //get average score - player 1 average
                oneDartAverage[0] = pointsScored[0]/dartsThrown[0];
                threeDartAverage[0]= oneDartAverage[0]*3;
                //player 2 average
                oneDartAverage[1] = pointsScored[1]/dartsThrown[1];
                threeDartAverage[1]= oneDartAverage[1]*3;


                player = 1;//switch player as it is now their turn
            } else {
                legs[player] = legs[player] + 1;
                player2Legs.setText("" + legs[player]);
                legsWon[player]++;
                dartsThrown[player]+=3;

                //get average score for both players - player one
                oneDartAverage[0] = pointsScored[0]/dartsThrown[0];
                threeDartAverage[0]= oneDartAverage[0]*3;
                //player two average
                oneDartAverage[1] = pointsScored[1]/dartsThrown[1];
                threeDartAverage[1]= oneDartAverage[1]*3;


                player = 0;
            }
            scoreLeft[0] = 501;//If 0 remainingP1 reset remainingP1 to 501 to start a new game.
            scoreLeft[1] = 501;
            remainingP1.setText("501");
            remainingP2.setText("501");

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

    }




    //have to find out how to do this best.
    public void deleteClicked(View sender) {
        Button bt = (Button) sender;
        scored.setText("0");
        isZero=true;
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
            // System.out.println(hms);
            textViewTime.setText(hms);
        }

        @Override
        public void onFinish() {
            textViewTime.setText("Time up. 60 point penalty! Press Enter to re-start clock!");
            scoreLeft[player] += 60;// if the timer finishes before player presses stop, ignore the 3 dart score hit - just add 60 to what was already on (i.e. x += 60 is equivalent to x = x + 60) and add back scoreHit as player doesn't get the points.

            Button[] buttons = {num_0, num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9};
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setEnabled(false);
            }
        }
    }
}