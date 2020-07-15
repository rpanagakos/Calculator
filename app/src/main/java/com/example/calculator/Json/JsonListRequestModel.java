package com.example.calculator.Json;

import java.util.ArrayList;
import java.util.List;

public class JsonListRequestModel {

    private boolean success;
    private int timestamp;
    private String base;
    private String date;
    private RatesJsonModel rates;

    public JsonListRequestModel(boolean success, int timestamp, String base, String date, RatesJsonModel rates) {
        this.success = success;
        this.timestamp = timestamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRates(RatesJsonModel rates) {
        this.rates = rates;
    }

    public boolean getSuccess() {
        return success;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public RatesJsonModel getRates() {
        return rates;
    }
}
