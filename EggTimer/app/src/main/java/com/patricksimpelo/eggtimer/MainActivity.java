package com.patricksimpelo.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final int MAX_SECONDS = 600;
    final int INITIAL_SECONDS = 10;
    final int ONE_SECOND = 1000;
    final int ONE_TENTH_SECOND = 100;
    boolean isTimerOn = false;

    TextView timer;
    CountDownTimer countDownTimer;
    SeekBar scrubber;
    Button button;

    public void updateTimer(int secondsLeft) {
        //Type casting to int rounds down
        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft - minutes * 60;
        String secondsString = String.valueOf(seconds);
        String minsString = String.valueOf(minutes);

        if (secondsString.length() == 1) {
            secondsString = "0" + secondsString;
        }
        if (minsString.length() == 1) {
            minsString = "0" + minsString;
        }

        timer.setText(String.valueOf(minsString) + ":" + String.valueOf(secondsString));
    }

    public void resetTimer() {
        button.setText("Go!");
        scrubber.setEnabled(true);
        isTimerOn = false;
        countDownTimer.cancel();

        scrubber.setProgress(INITIAL_SECONDS);
        updateTimer(INITIAL_SECONDS);
    }

    public void buttonToggle(View view) {
        button = (Button) findViewById(R.id.startStopButton);
        scrubber = (SeekBar) findViewById(R.id.scrubber);

        if (isTimerOn) {
            //Button pressed to STOP timer
            resetTimer();

        } else {
            //Button pressed to START timer
            scrubber.setEnabled(false);
            button.setText("Stop");
            isTimerOn = true;

            //Adding ONE_TENTH_SECOND to progress because the first onTick is called a few milliseconds after.
            //  For ecample, onTick starts at 9800 milliseconds. Then rounds down (b/c typecasted) and becomes 9000 (9 seconds)
            countDownTimer = new CountDownTimer(scrubber.getProgress() * ONE_SECOND + ONE_TENTH_SECOND, ONE_SECOND) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timer.setText("00:00");
                    resetTimer();
                    //Play sound
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                }
            };
            countDownTimer.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrubber = (SeekBar) findViewById(R.id.scrubber);
        timer = (TextView) findViewById(R.id.timerText);

        //600 = 10 mins = 600seconds
        scrubber.setMax(MAX_SECONDS);
        scrubber.setProgress(INITIAL_SECONDS);
        updateTimer(INITIAL_SECONDS);

        scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }
        });

    }
}
