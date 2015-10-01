package com.example.shubham.customerapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shubham.driverapp.R;

/**
 * Created by shubham on 30/9/15.
 */
public class droppointform extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.droppointform,container,false);

        Bundle b=getArguments();
        int a= (int) b.get("dropno");
        TextView tv=(TextView)v.findViewById(R.id.dropname);
        tv.setText("Drop Point "+a);
        return v;
    }

}
