package com.example.shubham.customerapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shubham.driverapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 30/9/15.
 */
public class DropPoint extends Fragment {
    Spinner noofdrop;
    Button submitdata;
    EditText address,city,pincode,state,fullname,phoneno;
    ArrayList<String> fragments=new ArrayList<String>();
    Communicator comm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.droppoint,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm=(Communicator)getActivity();
        ArrayList<Integer> no=new ArrayList<Integer>();
        for(int i=1;i<=10;i++)
        {
            no.add(i);
        }
        noofdrop=(Spinner)getActivity().findViewById(R.id.noofdroppoint);
        ArrayAdapter adapter=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,no);
        noofdrop.setAdapter(adapter);
        noofdrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < fragments.size(); i++) {
                    Fragment frag = getFragmentManager().findFragmentByTag(fragments.get(i));
                    getFragmentManager().beginTransaction().remove(frag).commit();
                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                for (int i = 0; i <= position; i++) {
                    Bundle bun = new Bundle();
                    bun.putInt("dropno", i + 1);
                    droppointform drop = new droppointform();
                    drop.setArguments(bun);
                    ft.add(R.id.droppoint, drop, "droppoint" + (i + 1));
                    fragments.add("droppoint" + (i + 1));

                }
                ft.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submitdata=(Button)getActivity().findViewById(R.id.submitdata);
        submitdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray array=new JSONArray();
                for(int i=0;i<fragments.size();i++)
                {
                    Fragment fragment=getFragmentManager().findFragmentByTag(fragments.get(i));
                    address=(EditText)fragment.getView().findViewById(R.id.dropaddress);
                    city=(EditText)fragment.getView().findViewById(R.id.dropcity);
                    pincode=(EditText)fragment.getView().findViewById(R.id.droppincode);
                    state=(EditText)fragment.getView().findViewById(R.id.dropstate);
                    fullname=(EditText)fragment.getView().findViewById(R.id.dropfullname);
                    phoneno=(EditText)fragment.getView().findViewById(R.id.dropmobileno);
                    JSONObject data1=new JSONObject();
                    try {
                        data1.put("address",address.getText().toString());
                        data1.put("city",address.getText().toString());
                        data1.put("pincode",pincode.getText().toString());
                        data1.put("state",pincode.getText().toString());
                        data1.put("fullname",fullname.getText().toString());
                        data1.put("phoneno",phoneno.getText().toString());
                        array.put(data1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                    comm.changetofinal(array);
            }
        });
    }
}
