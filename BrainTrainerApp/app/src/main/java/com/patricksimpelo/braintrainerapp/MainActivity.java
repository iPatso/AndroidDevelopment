package com.patricksimpelo.braintrainerapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final int COUNTDOWN_SECONDS = 15;
    final int ONE_FIFTH_SECOND = 100;
    final int ONE_SECOND = 1000;
    final int MAX_NUM_BUTTONS = 4; //Need to manually change if more buttons are added...
    final int OPERAND_MAX = 20;
    final int ANSWER_MAX = OPERAND_MAX*2; //Adding the max operand to itself (i.e. mult. by 2) gives us this max answer
    boolean isAnswerButtonsActive = false;
    int correctAnswer;
    int numCorrect = 0;
    int totalQuestions = 0;
    CountDownTimer countDownTimer;
    TextView timer;
    TextView scoreView;
    TextView feedback;
    Button playAgainButton;
    GridLayout gridLayout;

    public void startGame(View view) {
        Button goButton = (Button) findViewById(R.id.goButton);
        RelativeLayout innerRL = (RelativeLayout) findViewById(R.id.innerRelativeLayout);
        goButton.setVisibility(View.INVISIBLE);
        innerRL.setVisibility(View.VISIBLE);
        startTrainer();
    }

    public void playAgainReset(View view) {
        scoreView.setText("0/0");
        feedback.setText("");
        numCorrect = 0;
        totalQuestions = 0;

        startTrainer();
    }

    public void updateTimer(int secondsLeft) {
        timer.setText(String.valueOf(secondsLeft) + "s");
    }

    public void startTrainer() {
        playAgainButton.setVisibility(View.INVISIBLE);
        isAnswerButtonsActive = true;
        updateTimer(COUNTDOWN_SECONDS);
        resetProblemAndAnswer();
        //Adding ONE_FIFTH_SECOND to progress because the first onTick is called a few milliseconds after.
        countDownTimer = new CountDownTimer(COUNTDOWN_SECONDS * ONE_SECOND + ONE_FIFTH_SECOND, ONE_SECOND) {

            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                playAgainButton.setVisibility(View.VISIBLE);
                isAnswerButtonsActive = false;
                feedback.setText("Your score: " + String.valueOf(numCorrect) + "/" + String.valueOf(totalQuestions));
            }
        }.start();
    }

    public void resetProblemAndAnswer() {
        TextView expressionText = (TextView) findViewById(R.id.expressionText);
        int firstOperand = new Random().nextInt(OPERAND_MAX + 1);
        int secondOperand = new Random().nextInt(OPERAND_MAX + 1);
        int randomIncorrectAnswer = new Random().nextInt(ANSWER_MAX) + 1;
        Button currentButton; //The button getting worked on in loop
        correctAnswer = firstOperand+secondOperand;

        expressionText.setText(String.valueOf(firstOperand) + " + " + String.valueOf(secondOperand));

        for (int i=0; i<gridLayout.getChildCount(); i++) {
            //Check until correct answer is a different number than the incorrect generated answer
            while (randomIncorrectAnswer == correctAnswer) {
                randomIncorrectAnswer = new Random().nextInt(ANSWER_MAX) + 1;
            }
            currentButton = (Button) gridLayout.getChildAt(i);
            currentButton.setText(String.valueOf(randomIncorrectAnswer));
            randomIncorrectAnswer = new Random().nextInt(ANSWER_MAX) + 1;
        }
        //Finally, set the correct answer in a button
        currentButton = (Button) gridLayout.getChildAt(new Random().nextInt(MAX_NUM_BUTTONS));
        currentButton.setText(String.valueOf(correctAnswer));

    }

    public void answerChosen(View view) {
        if (isAnswerButtonsActive) {
            String ourId = view.getResources().getResourceEntryName(view.getId()); //Get the id string assigned in activity_main.xml
            int resourceId = getResources().getIdentifier(ourId, "id", getPackageName()); //Get the resource path by giving the string named id (e.g. R.id.hello)
            Button button = (Button) findViewById(resourceId);
            int buttonAnswer = Integer.parseInt(button.getText().toString());

            //Check is correct answer
            if (buttonAnswer == correctAnswer) {
                feedback.setText("Correct!");
                numCorrect++;
            } else {
                feedback.setText("Wrong!");
            }
            totalQuestions++;
            scoreView.setText(String.valueOf(numCorrect) + "/" + String.valueOf(totalQuestions));

            resetProblemAndAnswer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = (TextView) findViewById(R.id.timerView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        scoreView = (TextView) findViewById(R.id.scoreView);
        feedback = (TextView) findViewById(R.id.feedbackText);
        gridLayout = (GridLayout) findViewById(R.id.answerGrid);
    }
}
