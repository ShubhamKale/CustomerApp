package com.example.shubham.customerapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by shubham on 26/9/15.
 */
public interface Communicator {
    public void loginToken(String str);
    public void changetotruck(JSONObject obj);
    public void changetodrop(JSONObject obj);
    public void changetofinal(JSONArray obj);
    public void gotofirst();
}
