package com.example.calculator;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

public class RequestData extends ViewModel {

    private static String URL = "http://data.fixer.io/api/latest?access_key=0b4ab42fb72c68017105b6e43e650700&format=1";

    private MutableLiveData<HashMap<String, Double>> liveData = new MutableLiveData<>();

    public MutableLiveData<HashMap<String, Double>> getLiveData() {
        return liveData;
    }

    public void runRequests(RequestQueue queue) {
        makeRequest(URL, queue);
    }

    public void makeRequest(final String url, RequestQueue queue) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Convert response String to JsonModel
                        submitListData(url, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    private void submitListData(String url, String response) {
        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONObject result = jsonObj.getJSONObject("rates");
            HashMap<String, Double> list = new Gson().fromJson(String.valueOf(result), new TypeToken<HashMap<String, Double>>() {
            }.getType());
            if (URL.equals(url)) {
                liveData.postValue(list);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
