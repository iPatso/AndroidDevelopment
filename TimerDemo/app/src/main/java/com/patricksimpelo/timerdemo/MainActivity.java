package com.patricksimpelo.timerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        //METHOD 1 TO DO A TASK REGULARLY/TIMER
        //Will allow us to run a chunk of code (or event) in a delayed way (e.g. every X seconds)
        final Handler handler = new Handler();
        //The chunk of code or event
        Runnable run = new Runnable() {
            @Override
            public void run() {
                //Insert code to be run every SECOND (one second will be set in postDelayed method in millisecs)

                Log.i("Runnable has run!", "a second must have passed...");
                //this: this run method; 1000: legnth of delay in millisecons
                handler.postDelayed(this, 1000);
            }
        };

        //Execute the Runnable
        handler.post(run);
        */

        //METHOD 2: Count down timer
        //10000 : milliseconds until countdown has run out; 1000: frequency for the counter to tick down
        new CountDownTimer(10000, 1000) {
            public void onTick(long milliSecondsUntilDone) {
                //Countdown is counting down (every second in our case)
                // .valueOf converts long(or other type) to string
                Log.i("Seconds left", String.valueOf(milliSecondsUntilDone/1000));
            }

            public void onFinish() {
                // Counter is finished (after 10 seconds in our case)
                Log.i("Done!","Countdown timer finished");
            }
        }.start();
        //Note: Once this CountDownTimer is finished, it is destroyed

    }
}
