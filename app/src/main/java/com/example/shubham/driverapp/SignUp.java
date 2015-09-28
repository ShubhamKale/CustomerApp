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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by shubham on 27/9/15.
 */
public class SignUp extends Fragment {
    Button signup;
    EditText first,last,phone,email;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.signup,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        signup=(Button)getActivity().findViewById(R.id.signupmain);
        first=(EditText)getActivity().findViewById(R.id.firstname);
        last=(EditText)getActivity().findViewById(R.id.lastname);
        phone=(EditText)getActivity().findViewById(R.id.phoneno);
        email=(EditText)getActivity().findViewById(R.id.email);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient senddata=new AsyncHttpClient();
                JSONObject data=new JSONObject();
                try {
                        data.put("firstname",first.getText());
                        data.put("lastname",last.getText());
                        data.put("phone", phone.getText());
                        data.put("email", email.getText());
                    StringEntity entity=new StringEntity(data.toString());
                    senddata.post(getActivity(),"http://www.geeksstuff.org/alpha/vdata.php",entity ,"application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(getActivity(),"Error"+responseString,Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            Toast.makeText(getActivity(),"Got data"+responseString,Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception e)
                {
                    Log.e("Error",e.toString());
                }
            }
        });
    }
}
