package com.patricksimpelo.basicphrasesapp;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void buttonTapped(View view) {
        int id = view.getId();
        String ourId = "";

        //Get the id string assigned in activity_main.xml
        ourId = view.getResources().getResourceEntryName(id);

        //Get the resource path by giving the string named id (e.g. R.raw.hello)
        int resourceId = getResources().getIdentifier(ourId, "raw", getPackageName());
        MediaPlayer mplayer = MediaPlayer.create(this, resourceId);
        mplayer.start();
        //Releases the media player after playing the sound
        mplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        Log.i("Button tapped", ourId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
