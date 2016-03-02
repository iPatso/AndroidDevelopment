package com.patricksimpelo.connectthreegame;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int YELLOW = 0;
    final int RED = 1;
    final int UNPLAYED = 2;
    int activePlayer = 0;
    boolean isGameActive = true;
    boolean isDraw;

    int[] gameState = {
            UNPLAYED,UNPLAYED,UNPLAYED,
            UNPLAYED,UNPLAYED,UNPLAYED,
            UNPLAYED,UNPLAYED,UNPLAYED};

    //Sets of winning tag positions (3 in each)
    int[][] winningPositions = {
            {0,1,2},{3,4,5},{6,7,8},{0,3,6},
            {1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    //Compares gamestate to winningPositions
    private boolean isGameOver() {
        for (int[] winningPos : winningPositions) {
            if (gameState[winningPos[0]] != 2
                    && gameState[winningPos[0]] == gameState[winningPos[1]]
                    && gameState[winningPos[1]] == gameState[winningPos[2]]) {

                isGameActive = false;
                return true;
            } else {
                isDraw = true;
                for (int state : gameState) {
                    if (state == UNPLAYED)
                        isDraw = false;
                }
                if (isDraw) {
                    isGameActive = false;
                    return true;
                }
            }
        }

        return false;
    }

    public void playAgain(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        //Hide play again popup
        layout.setVisibility(View.INVISIBLE);

        //Reset activePlayer, gameState, and set game to being active
        activePlayer = 0;
        isGameActive = true;
        for (int i=0; i<gameState.length; i++) {
            gameState[i] = UNPLAYED;
        }

        //Reset Board
        for (int i=0; i<gridLayout.getChildCount(); i++)
            //Can set to 0 which is an empty image
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);

    }

    public void dropin(View view) {
        //counter equals view because view is what was tapped on. Since we do not know which
        //  exact one was tapped by id, we can just say view and type cast it. In other words,
        //  view is what called this function
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt((counter.getTag().toString()));

        if (gameState[tappedCounter] == UNPLAYED && isGameActive) {
            //1. Move off the screen
            counter.setTranslationY(-1000f);

            //2. Apply which color piece to drop in
            if (activePlayer == YELLOW) {
                counter.setImageResource(R.drawable.yellow);
                gameState[tappedCounter] = YELLOW;
            } else {
                counter.setImageResource(R.drawable.red);
                gameState[tappedCounter] = RED;
            }

            //Change active player
            activePlayer = (activePlayer + 1) % 2;

            //3. Drop in
            counter.animate().translationYBy(1000f).setDuration(500);

            if (isGameOver()) {
                //A winner emerged
                LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                TextView winnerText = (TextView) findViewById(R.id.winnerText);
                int winnerInt = (activePlayer + 1) % 2;
                String winnerString;
                layout.setVisibility(View.VISIBLE);

                if (isDraw)
                    winnerString = "It's a Draw!";
                else if (winnerInt == YELLOW)
                    winnerString = "YELLOW is the winner!";
                else
                    winnerString = "RED is victorious!";

                winnerText.setText(winnerString);
            }
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
