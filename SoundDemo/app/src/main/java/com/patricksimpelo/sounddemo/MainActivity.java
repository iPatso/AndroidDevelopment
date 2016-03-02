package com.patricksimpelo.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mplayer;
    AudioManager audioManager;

    public void playPressed(View view) {
        mplayer.start();
    }

    public void pausePressed(View view) {
        mplayer.pause();
    }

    //No button for this yet
    public void stopPressed(View view) {
        mplayer.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mplayer = MediaPlayer.create(this, R.raw.cheersound); //For the sound file itself

        //Managing the sound of the system
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //For the manager
        //maxVolume set to whatever the max of the system is. Default it 100
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //Managing the volume based on the system information above
        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currentVolume); //setProgress is current progress/current volume

        //Listens for events of when the seekBar has been changed
        volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            //onStartTrackingTouch AND onStopTrackingTouch are NEEDED in this listener
            // but do not need any implementation
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
        });


        //Audio navigation (also called "scrubber")
        final SeekBar navigationControl = (SeekBar) findViewById(R.id.navigationBar);
        navigationControl.setMax(mplayer.getDuration());

        //Schedule a certain task to be done at a fixed schedule
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                navigationControl.setProgress(mplayer.getCurrentPosition());
            }
        }, 0, 100); //0secs before timer runs the first time, 1000 milliSeconds between (i.e. every second)

        navigationControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mplayer.seekTo(progress);
            }
        });
    }
}
