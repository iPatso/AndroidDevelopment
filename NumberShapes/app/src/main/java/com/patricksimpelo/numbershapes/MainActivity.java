package com.patricksimpelo.numbershapes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    class Number {
        int number;

        public boolean isSquare() {
            double sqrt = Math.sqrt(number);
            if (sqrt == Math.floor(sqrt))
                return true;
            return false;
        }

        public boolean isTriangular() {
            int x = 1;
            int triangularNumber = 1;

            while (triangularNumber < number) {
                x++;
                triangularNumber = triangularNumber + x;
            }

            if (triangularNumber == number)
                return true;
            return false;
        }

    }

    public void testNumber(View view) {

        EditText usersNumberField = (EditText) findViewById(R.id.usersNumber);
        String usersNumberString = usersNumberField.getText().toString();
        String message;

        if (usersNumberString.isEmpty()) {
            message = "Please input a number";

        } else {
            int usersNumber = Integer.parseInt(usersNumberString);
            Number num = new Number();
            num.number = usersNumber;
            if (num.isSquare() && num.isTriangular()) {
                message = "BOTH square and triangular!";
            } else if (num.isSquare()) {
                message = "ONLY a square";
            } else if (num.isTriangular()) {
                message = "ONLY triangular";
            } else {
                message = "NEITHER a square nor triangular";
            }
        }
        // NOTE: Spamming the button loads many toasts on a queue... Not wanted but can do research on it.
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
