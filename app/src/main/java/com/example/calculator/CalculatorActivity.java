package com.example.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.novoda.merlin.MerlinsBeard;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalculatorActivity extends AppCompatActivity {

    @BindView(R.id.displayText)
    TextView calculation;
    @BindView(R.id.resultText)
    TextView answer;

    String result = "", number = "", calculationString = "", currentOperator = "";
    double resultDouble = 0.0, numberDouble = 0.0, temp = 0.0;
    NumberFormat numberFormat;
    boolean dot_present = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //seet result to four demical digits
        numberFormat = new DecimalFormat("#.####");
    }


    public void numberClick(View view) {
        Button button = (Button) view;
        calculationString += button.getText();
        number += button.getText();
        numberDouble = Double.parseDouble(number);
        switch (currentOperator) {
            case "":
                temp = resultDouble + numberDouble;
                result = numberFormat.format(temp).toString();
                break;
            case "+":
                temp = resultDouble + numberDouble;
                result = numberFormat.format(temp).toString();
                break;
            case "-":
                temp = resultDouble - numberDouble;
                result = numberFormat.format(temp).toString();
                break;
            case "*":
                temp = resultDouble * numberDouble;
                result = numberFormat.format(temp).toString();
                break;
            case "/":
                if (numberDouble == 0.0) {
                    Toast.makeText(CalculatorActivity.this, "You cant divide with the  zero", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    temp = resultDouble / numberDouble;
                    result = numberFormat.format(temp).toString();
                    break;
                }
        }
        UpdateResult();
    }

    public void operatorClick(View view) {
        Button operator = (Button) view;
        if (!result.equals("")) {
            calculationString += "\n" + operator.getText();
            number = "";
            numberDouble = 0.0;
            resultDouble = temp;
            temp = 0.0;
            result = numberFormat.format(resultDouble).toString();
            currentOperator = operator.getText().toString();
            UpdateResult();
            dot_present = false;

        }
    }

    public void Clear(View view) {
        result = "";
        number = "";
        calculationString = "";
        currentOperator = "";
        resultDouble = 0.0;
        numberDouble = 0.0;
        temp = 0.0;
        UpdateResult();
        answer.setText(result);
        dot_present = false;

    }

    public void UpdateResult() {
        calculation.setText(calculationString);
    }

    public void showResult(View view) {
        answer.setVisibility(View.VISIBLE);
        if (!result.equals("")) {
            answer.setText(result);
        }
    }

    public void dotClick(View view) {
        if (!dot_present) {
            if (number.length() == 0) {
                number = "0.";
                calculationString = "0.";
                result = "0.";
                dot_present = true;
                UpdateResult();
            } else {
                number += ".";
                calculationString += ".";
                result += ".";
                dot_present = true;
                UpdateResult();
            }
        }
    }

    public void onModuloClick(View view) {
        if (!result.equals("")) {
            calculationString += "% ";
            //value of temp will change according to the operator
            switch (currentOperator) {
                case "":
                    temp = temp / 100;
                    break;
                case "+":
                    temp = resultDouble + ((resultDouble * numberDouble) / 100);
                    break;
                case "-":
                    temp = resultDouble - ((resultDouble * numberDouble) / 100);
                    break;
                case "x":
                    temp = resultDouble * (numberDouble / 100);
                    break;
                case "/":
                    try {
                        temp = resultDouble / (numberDouble / 100);
                    } catch (Exception e) {
                        result = e.getMessage();
                    }
                    break;
            }
            result = numberFormat.format(temp).toString();
            resultDouble = temp;
            //now we show the result
            UpdateResult();

        }
    }

    public void changeActivity(View view) {
        MerlinsBeard merlin = new MerlinsBeard.Builder().build(CalculatorActivity.this);
        if (merlin.isConnected()) {
            Intent change = new Intent(CalculatorActivity.this, MainActivity.class);
            startActivity(change);
        } else {
            Toast.makeText(CalculatorActivity.this, "Check you internet connection", Toast.LENGTH_LONG).show();
        }

    }
}