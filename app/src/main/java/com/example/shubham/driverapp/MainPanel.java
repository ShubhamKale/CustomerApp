package com.example.shubham.driverapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class MainPanel extends Fragment{
    TabHost tabhost;
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
}