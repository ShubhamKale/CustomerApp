package com.example.shubham.customerapp;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shubham.driverapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by shubham on 29/9/15.
 */
public class TruckSelection extends Fragment {
    Spinner truck;
    ImageView truckimage;
    Button selecttruck;
    Communicator comm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.truckselection,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AsyncHttpClient client=new AsyncHttpClient();
        final JSONObject[] obj = new JSONObject[1];
        final ArrayList<String> vehicles = new ArrayList<String>();
        final ArrayList<String> vehiclesid = new ArrayList<String>();
        final ArrayList<String> vehiclesimage = new ArrayList<String>();
        truckimage=(ImageView)getActivity().findViewById(R.id.truckimage);
        client.post(getActivity(), "http://gobolt.in/carrier/details/", null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    Log.e("TAG",responseString);
                    obj[0] =new JSONObject(responseString);
                    JSONArray array=obj[0].getJSONArray("carriers_list");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        vehicles.add(obj.getString("manufacturer_name")+" "+obj.getString("trade_name"));
                        vehiclesid.add(obj.getString("id"));
                        vehiclesimage.add(obj.getString("carrier_image"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                truck=(Spinner)getActivity().findViewById(R.id.vehiclelist);
                ArrayAdapter adapter=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,vehicles);
                truck.setAdapter(adapter);
                truck.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(), "Position " + position + " " + vehiclesid.get(position), Toast.LENGTH_SHORT).show();
                        AsyncHttpClient getimg = new AsyncHttpClient();
                        URL newurl = null;
                        getimg.get("http://gobolt.in/" + vehiclesimage.get(position), new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Bitmap image = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                                truckimage.setImageBitmap(image);
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                selecttruck=(Button)getActivity().findViewById(R.id.selecttruck);
                selecttruck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position=truck.getSelectedItemPosition();
                        comm=(Communicator)getActivity();
                        try {
                            comm.changetodrop(new JSONObject().put("truck_id",position));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
