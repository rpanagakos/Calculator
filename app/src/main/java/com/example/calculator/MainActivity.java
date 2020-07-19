package com.example.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;
import com.novoda.merlin.NetworkStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView first, second;
    private HashMap<String, Double> list = new HashMap<>();
    private RequestData viewModel;
    private Button convert;
    private EditText firstCurrency;
    private TextView secondTxtCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(RequestData.class);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        second = findViewById(R.id.secondConversion);
        convert = findViewById(R.id.convertButton);
        firstCurrency = findViewById(R.id.txtFirstConverstion);
        secondTxtCurrency = findViewById(R.id.txtSecondConverstion);
        first = findViewById(R.id.firstConversion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Currency Converter");
        toolbar.setTitleTextColor(getColor(R.color.oxford_blue));

        viewModel.getLiveData().observe(this, new Observer<HashMap<String, Double>>() {
            @Override
            public void onChanged(final HashMap<String, Double> stringDoubleHashMap) {
                Log.i("MAP", "SomeText: " + new Gson().toJson(stringDoubleHashMap));
                takeKeys(stringDoubleHashMap, second, first);

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        viewModel.runRequests(queue);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getLiveData().observe(MainActivity.this, new Observer<HashMap<String, Double>>() {
                    @Override
                    public void onChanged(HashMap<String, Double> stringDoubleHashMap) {
                        if (firstCurrency.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Enter correct amount of money", Toast.LENGTH_SHORT).show();
                        } else {
                            if (second.getText().toString().isEmpty() || first.getText().toString().isEmpty()) {
                                Toast.makeText(MainActivity.this, "Enter correct currency", Toast.LENGTH_SHORT).show();
                            } else {
                                if (stringDoubleHashMap.containsKey(first.getText().toString().replaceAll("\\s+", ""))) {
                                    if (stringDoubleHashMap.containsKey(second.getText().toString().replaceAll("\\s+", ""))) {
                                        try {
                                            double euro = Double.parseDouble(firstCurrency.getText().toString()) / stringDoubleHashMap.get(first.getText().toString());
                                            double currencyInput = stringDoubleHashMap.get(second.getText().toString());
                                            currencyInput = euro * currencyInput;
                                            secondTxtCurrency.setText(new DecimalFormat("##.##").format(currencyInput));
                                        } catch (NullPointerException e) {
                                            Toast.makeText(MainActivity.this, "Our developers are monkeys try again", Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }

                                    } else {
                                        Toast.makeText(MainActivity.this, "Incorrect 'To' currency", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Incorrect 'From' currency", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void takeKeys(HashMap<String, Double> map, AutoCompleteTextView first, AutoCompleteTextView second) {
        List<String> listOfKeys = new ArrayList<String>(map.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, listOfKeys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        first.setAdapter(adapter);
        second.setAdapter(adapter);
    }

}