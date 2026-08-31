package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText display;

    double value1 = 0;
    double value2 = 0;

    String operator = "";

    boolean newNumber = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.etDisplay);

        // Number buttons
        int[] nums = {
                R.id.b0,
                R.id.b1,
                R.id.b2,
                R.id.b3,
                R.id.b4,
                R.id.b5,
                R.id.b6,
                R.id.b7,
                R.id.b8,
                R.id.b9,
                R.id.bDot
        };

        for (int id : nums) {

            Button button = findViewById(id);

            button.setOnClickListener(v -> {

                String number =
                        ((Button) v).getText().toString();

                // Start new number after operator or result
                if (newNumber) {
                    display.setText("");
                    newNumber = false;
                }

                // Prevent multiple dots
                if (number.equals(".") &&
                        display.getText().toString().contains(".")) {
                    return;
                }

                display.append(number);
            });
        }

        // Clear
        findViewById(R.id.bClear).setOnClickListener(v -> {

            display.setText("");

            value1 = 0;
            value2 = 0;
            operator = "";

            newNumber = false;
        });

        // Operators
        findViewById(R.id.bAdd)
                .setOnClickListener(v -> setOperator("+"));

        findViewById(R.id.bSub)
                .setOnClickListener(v -> setOperator("-"));

        findViewById(R.id.bMul)
                .setOnClickListener(v -> setOperator("*"));

        findViewById(R.id.bDiv)
                .setOnClickListener(v -> setOperator("/"));

        // Equal
        findViewById(R.id.bEqual)
                .setOnClickListener(v -> calculate());
    }


    private void setOperator(String op) {

        String text = display.getText().toString();

        // If display is empty
        if (text.isEmpty()) {

            // Allow negative number
            if (op.equals("-")) {
                display.setText("-");
            }

            return;
        }

        // If user has typed only "-"
        if (text.equals("-")) {
            return;
        }

        // If an operator already exists,
        // calculate the previous operation first
        if (!operator.isEmpty() && !newNumber) {
            calculate();
            text = display.getText().toString();
        }

        value1 = Double.parseDouble(text);

        operator = op;

        newNumber = true;
    }


    private void calculate() {

        String text = display.getText().toString();

        // No second number
        if (text.isEmpty() || text.equals("-")) {
            return;
        }

        // No operator
        if (operator.isEmpty()) {
            return;
        }

        value2 = Double.parseDouble(text);

        double result = 0;

        switch (operator) {

            case "+":
                result = value1 + value2;
                break;

            case "-":
                result = value1 - value2;
                break;

            case "*":
                result = value1 * value2;
                break;

            case "/":

                if (value2 == 0) {
                    display.setText("Cannot divide by 0");

                    value1 = 0;
                    value2 = 0;
                    operator = "";
                    newNumber = true;

                    return;
                }

                result = value1 / value2;
                break;
        }

        // Show integer without .0
        if (result == (long) result) {
            display.setText(String.valueOf((long) result));
        } else {
            display.setText(String.valueOf(result));
        }

        value1 = result;
        operator = "";
        newNumber = true;
    }
}

