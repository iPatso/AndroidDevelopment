package com.patricksimpelo.whattheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection connection = null;
            String result = "";

            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;

                    data = reader.read();
                }
                return result;

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please input a valid city name", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TextView weatherInfoTextView = (TextView) findViewById(R.id.weatherInfoTextView);
            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("weather");
                String message = "";

                Log.i("Weather Info", weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                for (int i=0; i<arr.length(); i++) {
                    JSONObject info = arr.getJSONObject(i);

                    message = info.getString("main") + ": " + info.getString("description") + "\r\n";
                }
                weatherInfoTextView.setText(message);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please input a valid city name", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getTheWeather(View view) {
        Log.i("Button Pressed", "Success");
        EditText cityText = (EditText) findViewById(R.id.cityTextField);
        String encodedCityName = "";
        //replaces symbols such as spaces with their encoded values
        try {
            encodedCityName = URLEncoder.encode(cityText.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (cityText.length() < 1) {
            Toast.makeText(getApplicationContext(), "Please input a City name", Toast.LENGTH_SHORT).show();
        } else {

            DownloadTask task = new DownloadTask();
            task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&appid=44db6a862fba0b067b1930da0d769e98");
        }

        //Remove keyboard
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityText.getWindowToken(), 0);

    }
}
