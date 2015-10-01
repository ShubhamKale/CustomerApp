package com.example.shubham.customerapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shubham.driverapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by shubham on 25/9/15.
 */
public class login extends Fragment{

    Button login;
    EditText username, password;
    TextView test;
    ProgressDialog progress;
    FrameLayout frame;
    Communicator comm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.logininformation, container, false);
        login = (Button) v.findViewById(R.id.userlogin);
        username = (EditText) v.findViewById(R.id.username);
        password = (EditText) v.findViewById(R.id.password);
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                AsyncHttpClient webhandler = new AsyncHttpClient();
                JSONObject credentials = new JSONObject();
                try {
                    credentials.put("username", username.getText());
                    credentials.put("password", password.getText());
                    StringEntity entity = new StringEntity(credentials.toString());
                    webhandler.post(getActivity(), "http://www.geeksstuff.org/alpha/vdata.php", entity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast t1 = Toast.makeText(getActivity(), "error occured" + responseString, Toast.LENGTH_SHORT);
                            t1.show();
                            progress.hide();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            Toast t1 = Toast.makeText(getActivity(), "message sent" + responseString, Toast.LENGTH_SHORT);
                            t1.show();
                            progress.hide();
                            Log.e("Tag", "status code" + statusCode);
                            comm=(Communicator)getActivity();
                            comm.loginToken("Login");
                        }

                        @Override
                        public void onProgress(long bytesWritten, long totalSize) {
                            super.onProgress(bytesWritten, totalSize);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void authenticate() {

    }
}
