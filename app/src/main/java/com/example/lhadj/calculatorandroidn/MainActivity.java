package com.example.lhadj.calculatorandroidn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1 = null;
    Button buttonNegative;
    private String pendingOperation = "=";
    private static final String OPERATION_VALUE = "OPERATION";
    private static final String OPERAND1_VALUE = "OPERAND1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.btnPoint);
        buttonNegative = (Button) findViewById(R.id.Negative);

        Button buttonEquals = (Button) findViewById(R.id.btnEquals);
        Button buttonDivide = (Button) findViewById(R.id.btnDivide);
        Button buttonMultiply = (Button) findViewById(R.id.btnMultiply);
        Button buttonMinus = (Button) findViewById(R.id.btnMinus);
        Button buttonPlus = (Button) findViewById(R.id.btnPlus);
        View.OnClickListener listener = new View.OnClickListener() {  // when any view is clicked this listener will interact
            @Override
            public void onClick(View view) {
                Button b = (Button) view; // not any view has a text so we cast the view to a button to filter only button views
                newNumber.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performeOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };


        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Double number = Double.valueOf(newNumber.getText().toString());
                    number *= -1;
                    newNumber.setText(number + "");
                } catch (NumberFormatException e) {
                    newNumber.setText("-");
                }
            }
        });


        buttonEquals.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
    }

    private void performeOperation(Double value, String operation) {
        if (operand1 == null) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }

            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (operand1 == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
            }
            result.setText(operand1.toString());
            newNumber.setText("");

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(OPERATION_VALUE, pendingOperation);
        if (operand1 != null)
            outState.putDouble(OPERAND1_VALUE, operand1);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(OPERATION_VALUE);
        operand1 = savedInstanceState.getDouble(OPERAND1_VALUE);
        displayOperation.setText(pendingOperation);
    }
}
