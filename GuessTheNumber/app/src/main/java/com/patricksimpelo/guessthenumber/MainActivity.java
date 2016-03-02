package com.patricksimpelo.guessthenumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final int[] NUM_RANGE = {0, 20};
    final String[] QUESTIONS = {"Can you guess it?", "What is it?", "Do you know it?"};
    int randomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView randNumStatement = (TextView) findViewById(R.id.randNumStatement);
        TextView questionText = (TextView) findViewById(R.id.questionText);
        Random rand = new Random();


        randNumStatement.setText("I have a number stored in my memory between " + NUM_RANGE[0] + " and " + NUM_RANGE[1]);
        questionText.setText(QUESTIONS[rand.nextInt(QUESTIONS.length)]);
        randomNum = rand.nextInt(NUM_RANGE[1] + 1) + NUM_RANGE[0];

        //Set listener for guessButton
        Button guessButton = (Button)findViewById(R.id.guessButton);
        guessButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        EditText numberInput = (EditText) findViewById(R.id.numberInput);
                        String guessedNumberString = numberInput.getText().toString();
                        String message;

                        if (guessedNumberString.length() < 1) {
                            //Nothing inputted
                            message = "Please input a number";
                        } else {
                            int guessedNum = Integer.parseInt(guessedNumberString);
                            if (guessedNum > randomNum) {
                                message = "Too High!";
                            } else if (guessedNum < randomNum) {
                                message = "Too Low!";
                            } else {
                                message = "Correct! Good job!";
                            }
                        }
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        //Set listener for resetButton
        Button resetButton = (Button)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        randomNum = new Random().nextInt(NUM_RANGE[1] + 1) + NUM_RANGE[0];
                        Toast.makeText(getApplicationContext(), "New number generated", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
