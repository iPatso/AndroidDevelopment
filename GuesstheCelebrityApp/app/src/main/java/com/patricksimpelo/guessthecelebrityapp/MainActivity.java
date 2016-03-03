package com.patricksimpelo.guessthecelebrityapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Bitmap currentImage;
    ArrayList<String> imageUrls = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    int index = 0;
    int locationCorrectAnswer = 0;
    String[] answers = new String[4];
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    public class ContentDownloader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        imageView = (ImageView) findViewById(R.id.imageView);
        ContentDownloader task = new ContentDownloader();
        String result = null;

        // Populate both lists
        try {
            result = task.execute("http://www.posh24.com/celebrities").get();

            String[] splitResult = result.split("<div class=\"sidebarContainer\">");

            // Retrieve image URLs
            Pattern p = Pattern.compile("<img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {
                imageUrls.add(m.group(1));
            }

            // Retrieve celeb names
            p = Pattern.compile("alt=\"(.*?)\"");
            m = p.matcher(splitResult[0]);

            while (m.find()) {
                celebNames.add(m.group(1));
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        resetGuess();
    }

    public void resetGuess () {
        Random random = new Random();
        index = random.nextInt(imageUrls.size());
        ImageDownloader imageDownloader = new ImageDownloader();
        try {
            currentImage = imageDownloader.execute(imageUrls.get(index)).get();
            imageView.setImageBitmap(currentImage);

            //TODO: make '4' a constant
            locationCorrectAnswer = random.nextInt(4);
            int incorrectAnswerLocation;
            for (int i=0; i<4; i++) {
                if (i==locationCorrectAnswer) {
                    answers[i] = celebNames.get(index);
                } else {
                    incorrectAnswerLocation = random.nextInt(celebNames.size());

                    while (incorrectAnswerLocation == index) {
                        incorrectAnswerLocation = random.nextInt(celebNames.size());
                    }

                    answers[i] = celebNames.get(incorrectAnswerLocation);
                }
            }

            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void celebChosen(View view) {
        Log.i("Button tapped", String.valueOf(view.getTag()));
        String message = "";
        if (view.getTag().toString().equals(String.valueOf(locationCorrectAnswer))) {
            message = "Correct!";
        } else {
            message = "WRONG! It was " + celebNames.get(index);
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        resetGuess();
    }

}
