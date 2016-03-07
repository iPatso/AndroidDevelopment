package com.patricksimpelo.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            //database.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");
            //database.execSQL("INSERT INTO users (name, age) VALUES ('Dante', 22)");
            //database.execSQL("INSERT INTO users (name, age) VALUES ('Joy', 22)");
            //database.execSQL("DELETE FROM users WHERE name='Dante'");
            //database.execSQL("UPDATE users SET age = 2 WHERE name = 'Joy'");

            //Creating new table with primary keys b/c cannot just add a new column
            //database.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INT(3), id INTEGER PRIMARY KEY)");
            //database.execSQL("INSERT INTO newUsers (name, age) VALUES ('Dante', 22)");
            //database.execSQL("INSERT INTO newUsers (name, age) VALUES ('Joy', 20)");
            //database.execSQL("INSERT INTO newUsers (name, age) VALUES ('Patrick', 23)");
            //database.execSQL("INSERT INTO newUsers (name, age) VALUES ('Janelle', 22)");
            //database.execSQL("DELETE FROM newUsers WHERE id = 5");


            Cursor c = database.rawQuery("SELECT * FROM newUsers", null);
            //Cursor c = database.rawQuery("SELECT * FROM users WHERE age < 23", null);
            //Cursor c = database.rawQuery("SELECT * FROM users WHERE name = 'Joy'", null);
            //Cursor c = database.rawQuery("SELECT * FROM users WHERE name = 'Dante' AND age = 22", null);
            //Cursor c = database.rawQuery("SELECT * FROM users WHERE name LIKE 'J%'", null);
            //Cursor c = database.rawQuery("SELECT * FROM users WHERE name LIKE '%a%' LIMIT 2", null);

            int eventIndex = c.getColumnIndex("name");
            int yearIndex = c.getColumnIndex("age");
            int keyIndex = c.getColumnIndex("id");
            boolean isNotNull = c.moveToFirst();

            while (isNotNull) {
                Log.i("UserResults - name", c.getString(eventIndex));
                Log.i("UserResults - age", String.valueOf(c.getInt(yearIndex)));
                Log.i("UserResults - id", String.valueOf(c.getInt(keyIndex)));

                isNotNull = c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
