package com.patricksimpelo.sharedpreferencesdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.patricksimpelo.sharedpreferencesdemo", Context.MODE_PRIVATE);

        //sharedPreferences.edit().putString("username", "Pat").apply();

        String username = sharedPreferences.getString("username", "");

        Log.i("username", username);

    }
}
