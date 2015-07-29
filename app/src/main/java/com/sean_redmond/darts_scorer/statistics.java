package com.sean_redmond.darts_scorer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class statistics extends ActionBarActivity {

    TextView legsCompleted;
    TextView sixtyPlusScoresP1;
    TextView sixtyPlusScoresP2;
    TextView tonPlusScoresP1;
    TextView tonPlusScoresP2;
    TextView tonFortyPlusScoresP1;
    TextView tonFortyPlusScoresP2;
    TextView tonEightyScoresP1;
    TextView tonEightyScoresP2;
    TextView legsWinnerP1;
    TextView legsWinnerP2;
    TextView oneDartAveScoreP1;
    TextView oneDartAveScoreP2;
    TextView threeDartAveScoreP1;
    TextView threeDartAveScoreP2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        legsCompleted = (TextView) findViewById(R.id.legsCompleted);
        sixtyPlusScoresP1 = (TextView) findViewById(R.id.sixtyPlusP1);
        sixtyPlusScoresP2 = (TextView) findViewById(R.id.sixtyPlusP2);
        tonPlusScoresP1 = (TextView) findViewById(R.id.tonPlusP1);
        tonPlusScoresP2 = (TextView) findViewById(R.id.tonPlusP2);
        tonFortyPlusScoresP1 = (TextView) findViewById(R.id.tonFortyPlusP1);
        tonFortyPlusScoresP2 = (TextView) findViewById(R.id.tonFortyPlusP2);
        tonEightyScoresP1 = (TextView) findViewById(R.id.tonEightyPlusP1);
        tonEightyScoresP2 = (TextView) findViewById(R.id.tonEightyPlusP2);
        legsWinnerP1 = (TextView) findViewById(R.id.legsWonP1);
        legsWinnerP2 = (TextView) findViewById(R.id.legsWonP2);
        oneDartAveScoreP1 = (TextView) findViewById(R.id.oneDAveP1);
        oneDartAveScoreP2 = (TextView) findViewById(R.id.oneDAveP2);
        threeDartAveScoreP1 = (TextView) findViewById(R.id.threeDAveP1);
        threeDartAveScoreP2 = (TextView) findViewById(R.id.threeDAveP2);



        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int legsFinished = extras.getInt("legsDone");
            int sixtyPlusesP1 = extras.getInt("sixtyPlusScoresP1");
            int sixtyPlusesP2 = extras.getInt("sixtyPlusScoresP2");
            int tonPlusesP1 = extras.getInt("tonPlusScoresP1");
            int tonPlusesP2 = extras.getInt("tonPlusScoresP2");
            int tonFortyPlusesP1 = extras.getInt("tonFortyPlusScoresP1");
            int tonFortyPlusesP2 = extras.getInt("tonFortyPlusScoresP2");
            int tonEightyPlusesP1 = extras.getInt("tonEightyScoresP1");
            int tonEightyPlusesP2 = extras.getInt("tonEightyScoresP2");
            int legsVictoryP1 = extras.getInt("legsWonP1");
            int legsVictoryP2 = extras.getInt("legsWonP2");
            int oneDartAvgP1 = extras.getInt("oneDartAveP1");
            int oneDartAvgP2 = extras.getInt("oneDartAveP2");
            int threeDartAvgP1 = extras.getInt("threeDartAveP1");
            int threeDartAvgP2 = extras.getInt("threeDartAveP2");



            legsCompleted.setText("" + legsFinished);
            sixtyPlusScoresP1.setText(""+ sixtyPlusesP1);
            sixtyPlusScoresP2.setText(""+ sixtyPlusesP2);
            tonPlusScoresP1.setText(""+ tonPlusesP1);
            tonPlusScoresP2.setText(""+tonPlusesP2);
            tonFortyPlusScoresP1.setText(""+ tonFortyPlusesP1);
            tonFortyPlusScoresP2.setText(""+ tonFortyPlusesP2);
            tonEightyScoresP1.setText(""+ tonEightyPlusesP1);
            tonEightyScoresP2.setText(""+ tonEightyPlusesP2);
            legsWinnerP1.setText(""+ legsVictoryP1);
            legsWinnerP2.setText(""+ legsVictoryP2);
            oneDartAveScoreP1.setText(""+ oneDartAvgP1);
            oneDartAveScoreP2.setText(""+ oneDartAvgP2);
            threeDartAveScoreP1.setText(""+threeDartAvgP1);
            threeDartAveScoreP2.setText(""+threeDartAvgP2);

        }
    }
}
