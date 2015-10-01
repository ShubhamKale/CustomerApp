package com.example.shubham.customerapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.shubham.driverapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by shubham on 29/9/15.
 */
public class MainOrderPage extends Fragment {
    Button next,popup;
    ProgressDialog progress;
    EditText address,city,pincode,state,fullname,phoneno;
    Communicator comm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v=inflater.inflate(R.layout.mainorderpage,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Please Wait!!");
        progress.setMessage("Wait!!");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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
                progress.show();
                AsyncHttpClient web=new AsyncHttpClient();
                final JSONObject[] data = {null};
                final String add=address.getText().toString();
                final String cityname=city.getText().toString();
                final String pin=pincode.getText().toString();
                final String statename=state.getText().toString();
                final String name=fullname.getText().toString();
                final String mobile=phoneno.getText().toString();
                String ne=add.replaceAll(" ","+");
                JsonHttpResponseHandler json=new JsonHttpResponseHandler();
                RequestHandle handle=web.post(getActivity(), "https://maps.googleapis.com/maps/api/geocode/json?address=+" + ne + "+" + cityname + "+" +pin + "+" + statename + "+&sensor=false&components=country%3aIN&key=AIzaSyD2vaD7LdiKd67IuK98sKyeCGf0e6gabNw", null, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.e("TAG", responseString);
                        try {
                            data[0] =new JSONObject(responseString);
                            Log.e("TAG","AFter getting the data");
                            try {
                                JSONArray array= data[0].getJSONArray("results");
                                Log.e("TAG","Got json array");
                                JSONObject obj=array.getJSONObject(0);
                                JSONObject obj1=obj.getJSONObject("geometry");
                                String lat=obj1.getJSONObject("location").getString("lat");
                                String log=obj1.getJSONObject("location").getString("lng");
                                String placeid=obj.getString("place_id");
                                String fulladdress=add+cityname+pin+statename;

                                Log.e("Final address",fulladdress);
                                Log.e("Final longitude",lat);
                                Log.e("Final latitude", log + " " + placeid);

                                final JSONObject details=new JSONObject();
                                details.put("fulladdress",fulladdress);
                                details.put("longitude",log);
                                details.put("latitude",lat);
                                details.put("placeid",placeid);
                                details.put("FullName",name);
                                details.put("Mobile",mobile);
                                AsyncHttpClient send=new AsyncHttpClient();
                                StringEntity detailsentity=new StringEntity(details.toString());
                                send.post(getActivity(), "http://www.geeksstuff.org/alpha/vdata.php", detailsentity, "application/json", new TextHttpResponseHandler() {
                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                        Toast.makeText(getActivity(), "Sent" + responseString, Toast.LENGTH_SHORT).show();
                                        comm=(Communicator)getActivity();
                                        comm.changetotruck(details);
                                        progress.hide();
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        popup=(Button)getActivity().findViewById(R.id.popup);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pop = new PopupMenu(getActivity(), v);
                pop.inflate(R.menu.popupmenu);
                pop.show();

            }
        });
    }
}
