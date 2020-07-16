package com.example.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner first, second;
    private  HashMap<String, Double> list = new HashMap<>();
    List<String> listOfKeys = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        first = findViewById(R.id.firstConversion);
        second = findViewById(R.id.secondConversion);
        requestData(first);
        Log.d("RELIST2222", listOfKeys.toString());


    }

    private void requestData(final Spinner spinner){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://data.fixer.io/api/latest?access_key=0b4ab42fb72c68017105b6e43e650700&format=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject result = jsonObj.getJSONObject("rates");
                            list = new Gson().fromJson(String.valueOf(result), new TypeToken<HashMap<String, Double>>() {}.getType());
                            Log.i("MAP", "SomeText: " + new Gson().toJson(list));

                            listOfKeys = new ArrayList<String>(list.keySet());
                            Log.d("RELIST", listOfKeys.toString());
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, listOfKeys);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Log.i("MAP", e.toString());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
}