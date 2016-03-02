package com.patricksimpelo.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = (ListView) findViewById(R.id.myListView);

        //final used because ised in OnItemClickListener, but can just use "parent" instead of myFriends in method
        final ArrayList<String> myFriends = new ArrayList<String>();
        myFriends.add("Janelle");
        myFriends.add("Dante");
        myFriends.add("Alex");
        myFriends.add("Vince");
        myFriends.add("Patricio");

        //ANOTHER was to make the list... Not recommended in implementation
        ArrayList<String> myFriends2 = new ArrayList<String>(asList("Janelle", "Dante", "Vince", "Alex"));

        //First arg: context; Second arg: format (there are different kinds in android.R.layout.*; Third arg: ArrayList of items
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myFriends);
        //Set list to myListView
        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //parent: Type of view we're in (listView in our case); Represents our listview
            //view: Represents what was actually tapped
            //position: # of row tapped
            //id: In our case, same as position, but can be other value. Not needed in this case
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Name Tapped", parent.getItemAtPosition(position).toString());
                // OR myFriends.get(position)
                Toast.makeText(getApplicationContext(), "Hello " + myFriends.get(position), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
