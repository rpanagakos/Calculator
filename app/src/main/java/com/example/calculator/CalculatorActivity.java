package com.example.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.novoda.merlin.MerlinsBeard;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalculatorActivity extends AppCompatActivity{

    @BindView(R.id.displayText) TextView calculation;
    @BindView(R.id.resultText) TextView answer;

     String sAnswer = "", number_one = "", sCalculation = "", currentOperator = "";
     double Result = 0.0, numberOne = 0.0, temp = 0.0;
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


    public void numberClick(View view){
        Button button = (Button) view;
        sCalculation += button.getText();
        number_one += button.getText();
        numberOne = Double.parseDouble(number_one);
        switch (currentOperator) {
            case "":
                temp = Result + numberOne;
                sAnswer = numberFormat.format(temp).toString();
                break;
            case "+":
                temp = Result + numberOne;
                sAnswer = numberFormat.format(temp).toString();
                break;
            case "-":
                temp = Result - numberOne;
                sAnswer = numberFormat.format(temp).toString();
                break;
            case "*":
                temp = Result * numberOne;
                sAnswer = numberFormat.format(temp).toString();
                break;
            case "/":
                if (numberOne == 0.0){
                    Toast.makeText(CalculatorActivity.this, "You cant divide with the  zero", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    temp = Result / numberOne;
                    sAnswer = numberFormat.format(temp).toString();
                    break;
                }
        }
        UpdateResult();
    }

    public void operatorClick(View view){
        Button operator = (Button) view;
        if (!sAnswer.equals("")) {
                sCalculation += "\n" + operator.getText();
                number_one = "";
                numberOne = 0.0;
                Result = temp;
                temp = 0.0;
                sAnswer = numberFormat.format(Result).toString();
                currentOperator = operator.getText().toString();
                UpdateResult();
                dot_present = false;

        }
    }

    public void Clear(View view){
        sAnswer = "";
        number_one = "";
        sCalculation = "";
        currentOperator = "";
        Result = 0.0;
        numberOne = 0.0;
        temp = 0.0;
        UpdateResult();
        answer.setText(sAnswer);
        dot_present = false;

    }

    public void UpdateResult(){
            calculation.setText(sCalculation);
    }

    public void showResult(View view){
        answer.setVisibility(View.VISIBLE);
        if (!sAnswer.equals("")){
            answer.setText(sAnswer);
        }
    }

    public void dotClick(View view){
        if (!dot_present){
            if (number_one.length() == 0){
                number_one ="0.";
                sCalculation = "0.";
                sAnswer = "0.";
                dot_present = true;
                UpdateResult();
            } else {
                number_one += ".";
                sCalculation += ".";
                sAnswer += ".";
                dot_present = true;
                UpdateResult();
            }
        }
    }

    public void onModuloClick(View view) {
        if (!sAnswer.equals("")) {
            sCalculation += "% ";
            //value of temp will change according to the operator
            switch (currentOperator) {
                case "":
                    temp = temp / 100;
                    break;
                case "+":
                    temp = Result + ((Result * numberOne) / 100);
                    break;
                case "-":
                    temp = Result - ((Result * numberOne) / 100);
                    break;
                case "x":
                    temp = Result * (numberOne / 100);
                    break;
                case "/":
                    try {
                        temp = Result / (numberOne / 100);
                    } catch (Exception e) {
                        sAnswer = e.getMessage();
                    }
                    break;
            }
            sAnswer = numberFormat.format(temp).toString();
            Result = temp;
            //now we show the result
            UpdateResult();

        }
    }

    public void changeActivity(View view){
        MerlinsBeard merlin = new MerlinsBeard.Builder().build(CalculatorActivity.this);
        if (merlin.isConnected()){
            Intent change = new Intent(CalculatorActivity.this, MainActivity.class);
            startActivity(change);
        } else {
            Toast.makeText(CalculatorActivity.this, "Check you internet connection", Toast.LENGTH_LONG).show();
        }

    }
}