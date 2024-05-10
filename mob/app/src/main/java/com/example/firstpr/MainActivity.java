package com.example.firstpr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button[] numberButtons = new Button[10];
    Button[] operationButtons = new Button[7];
    EditText inputEdit;
    TextView outputView;
    String input = "";
    String operation = "";
    double result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEdit = findViewById(R.id.inputEdit);
        outputView = findViewById(R.id.outputView);


        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            numberButtons[i] = findViewById(getResources().getIdentifier("button" + i, "id", getPackageName()));
            numberButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    input += finalI;
                    inputEdit.setText(input);
                }
            });
        }


        operationButtons[0] = findViewById(R.id.forplus);
        operationButtons[1] = findViewById(R.id.forminus);
        operationButtons[2] = findViewById(R.id.forumn);
        operationButtons[3] = findViewById(R.id.fordelenie);
        operationButtons[4] = findViewById(R.id.equalsButton);
        operationButtons[5] = findViewById(R.id.percentButton);
        operationButtons[6] = findViewById(R.id.sqrtButton);


        for (Button button : operationButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button clickedButton = (Button) v;
                    String buttonText = clickedButton.getText().toString();
                    if (buttonText.equals("=")) {
                        calculateResult();
                    } else if (buttonText.equals("%")) {
                        try {
                            double percent = evaluatePercent(input);
                            outputView.setText(String.valueOf(percent));
                            input = "";
                            inputEdit.setText(input);
                        } catch (Exception e) {
                            outputView.setText("Error");
                        }
                    } else if (buttonText.equals("sqrt")) {
                        try {
                            double sqrtResult = Math.sqrt(Double.parseDouble(input));
                            outputView.setText(String.valueOf(sqrtResult));
                            input = "";
                            inputEdit.setText(input);
                        } catch (Exception e) {
                            outputView.setText("Error");
                        }
                    } else if (buttonText.equals("log")) {
                        try {
                            double number = Double.parseDouble(input);
                            double logResult = Math.log(number);
                            outputView.setText(String.valueOf(logResult));
                            input = "";
                            inputEdit.setText(input);
                        } catch (Exception e) {
                            outputView.setText("Error");
                        }
                    } else {
                        input += buttonText;
                        inputEdit.setText(input);
                    }
                }
            });
        }


        Button clearButton = findViewById(R.id.clearButton);


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input = "";
                inputEdit.setText(input);
                outputView.setText("");
            }
        });


        Button logButton = findViewById(R.id.logButton);


        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double number = Double.parseDouble(input);
                    double logResult = Math.log(number);
                    outputView.setText(String.valueOf(logResult));
                    input = "";
                    inputEdit.setText(input);
                } catch (Exception e) {
                    outputView.setText("Error");
                }
            }
        });


        Button dotButton = findViewById(R.id.dotButton);


        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input += ".";
                inputEdit.setText(input);
            }
        });
    }

    private void calculateResult() {
        try {
            result = evaluateExpression(input);
            outputView.setText(String.valueOf(result));
            input = "";
            inputEdit.setText(input);
        } catch (Exception e) {
            outputView.setText("Error");
        }
    }

    private double evaluateExpression(String expression) {
        try {

            expression = expression.replaceAll("%", "").trim();


            expression = expression.replaceAll("[+]", " + ")
                    .replaceAll("[-]", " - ")
                    .replaceAll("[*]", " * ")
                    .replaceAll("[/]", " / ");


            String[] parts = expression.split("\\s+");
            double result = Double.parseDouble(parts[0]);
            char op = '+';

            for (int i = 1; i < parts.length; i++) {

                if (parts[i].matches("[+\\-*/]")) {
                    op = parts[i].charAt(0); // Получаем оператор
                } else {
                    double num = Double.parseDouble(parts[i]);

                    switch (op) {
                        case '+':
                            result += num;
                            break;
                        case '-':
                            result -= num;
                            break;
                        case '*':
                            result *= num;
                            break;
                        case '/':
                            result /= num;
                            break;
                    }
                }
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid expression");
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Division by zero");
        }
    }

    private double evaluatePercent(String expression) {
        try {
            expression = expression.replaceAll("%", "").trim();

            double number = Double.parseDouble(expression);
            return number / 100.0;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid expression");
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Division by zero");
        }
    }
}
