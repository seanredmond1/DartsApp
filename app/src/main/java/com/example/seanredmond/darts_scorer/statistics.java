package com.example.seanredmond.darts_scorer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class statistics extends ActionBarActivity {

    TextView legsCompleted;
    TextView sixtyPlusScoresP1;
    TextView sixtyPlusScoresP2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        legsCompleted = (TextView) findViewById(R.id.legsCompleted);
        sixtyPlusScoresP1 = (TextView) findViewById(R.id.sixtyPlusP1);
        sixtyPlusScoresP2 = (TextView) findViewById(R.id.sixtyPlusP2);



        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int legsFinished = extras.getInt("legsDone");
            int sixtyPlusesP1 = extras.getInt("sixtyPlusScoresP1");
            int sixtyPlusesP2 = extras.getInt("sixtyPlusScoresP2");


            legsCompleted.setText("" + legsFinished);
            sixtyPlusScoresP1.setText(""+ sixtyPlusesP1);
            sixtyPlusScoresP2.setText(""+ sixtyPlusesP2);


        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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
}
