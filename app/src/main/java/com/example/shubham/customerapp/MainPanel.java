package com.example.shubham.customerapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TabHost;
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

public class MainPanel extends Fragment{
    TabHost tabhost;
    Communicator comm;
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
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.tab1,new MainOrderPage());
        ft.commit();
    }
}