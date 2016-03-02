package com.patricksimpelo.animations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void fade(View view) {
        //ImageView booboo = (ImageView) findViewById(R.id.booboo);
        //booboo.animate().alpha(1f).setDuration(2000);

        ImageView baby = (ImageView) findViewById(R.id.baby);

        //Tell compiler what we want to change and for long do we want the change to happen
        //"0f" => We want zero to be a float
        // Alpha values: 0 is no opacity. 1 is full.
        //Duration is set in milliseconds (1000th of a sec)
        //baby.animate().alpha(0f).setDuration(2000);

        //Animates off the right 1000px
        //baby.animate().translationXBy(1000f).setDuration(2000);

        //Animate image to the left 1000px
        //baby.animate().translationXBy(-1000f).setDuration(2000);

        //Rotation done in degrees
        //baby.animate().rotation(1800f).setDuration(2000);

        //Scale has two separate methods. "scaleX/Y" is more accurate if we know actual fraction
        baby.animate()
                .scaleXBy(0.5f).scaleYBy(0.5f)
                .translationYBy(-500f)
                .translationXBy(-500f)
                .rotation(360f*8)
                .setDuration(2000);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ImageView baby = (ImageView) findViewById(R.id.baby);
        //Sets image off screen
        baby.setTranslationX(500f);
        baby.setTranslationY(500f);
        baby.setScaleX(0.3f);
        baby.setScaleY(0.3f);

    }
}
