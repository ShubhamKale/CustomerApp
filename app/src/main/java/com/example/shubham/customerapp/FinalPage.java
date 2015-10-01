package com.example.shubham.customerapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shubham.driverapp.R;

import java.sql.BatchUpdateException;

/**
 * Created by shubham on 30/9/15.
 */
public class FinalPage extends Fragment {
    Button btn;
    Communicator comm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.finalpage,container,false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm=(Communicator)getActivity();
        btn=(Button)getActivity().findViewById(R.id.gotofirst);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.gotofirst();
            }
        });
    }
}
