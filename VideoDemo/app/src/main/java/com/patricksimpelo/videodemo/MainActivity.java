package com.patricksimpelo.videodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView funnyVideo = (VideoView) findViewById(R.id.videoView);

        //Set path for video
        funnyVideo.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.funnyvideo);

        //Set video controls
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(funnyVideo);
        funnyVideo.setMediaController(mediaController);

        //Start video
        funnyVideo.start();

    }
}
