package com.example.shubham.driverapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainPanel extends Fragment{
    TabHost tabhost;
    Button next;
    EditText address,city,pincode,state,fullname,phoneno;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main_panel,container,false);
        tabhost=(TabHost)v.findViewById(R.id.tabHost);
        tabhost.setup();
        TabHost.TabSpec tab1=tabhost.newTabSpec("TAB 1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Tab 1");

        TabHost.TabSpec tab2=tabhost.newTabSpec("TAB 2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Tab 2");

        TabHost.TabSpec tab3=tabhost.newTabSpec("TAB 3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("Tab 3");

        tabhost.addTab(tab1);
        tabhost.addTab(tab2);
        tabhost.addTab(tab3);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        address=(EditText)getActivity().findViewById(R.id.address);
        city=(EditText)getActivity().findViewById(R.id.city);
        pincode=(EditText)getActivity().findViewById(R.id.pincode);
        state=(EditText)getActivity().findViewById(R.id.state);
        fullname=(EditText)getActivity().findViewById(R.id.fullname);
        phoneno=(EditText)getActivity().findViewById(R.id.mobileno);
        next=(Button)getActivity().findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient web=new AsyncHttpClient();
                final JSONObject[] data = {null};
                String add=address.getText().toString();
                String ne=add.replaceAll(" ","+");
                JsonHttpResponseHandler json=new JsonHttpResponseHandler();
                RequestHandle handle=web.post(getActivity(), "https://maps.googleapis.com/maps/api/geocode/json?address=+" + ne + "+" + city.getText().toString() + "+" + pincode.getText().toString() + "+" + state.getText().toString() + "+&sensor=false&components=country%3aIN&key=AIzaSyD2vaD7LdiKd67IuK98sKyeCGf0e6gabNw", null, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.e("TAG",responseString);
                        try {
                            data[0] =new JSONObject(responseString);
                            Log.e("TAG","AFter getting the data");
                            try {
                                JSONArray array= data[0].getJSONArray("results");
                                Log.e("TAG","Got json array");
                                for(int i=0;i<array.length();i++)
                                {
                                    Log.e("TAG","Object form the array"+i);
                                    JSONObject obj=array.getJSONObject(i);
                                    Log.e("TAG",obj.getString("formatted_address"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                }
        });
    }
}