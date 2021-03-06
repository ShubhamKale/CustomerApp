package com.example.shubham.customerapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shubham.driverapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

import static android.widget.Toast.*;


public class MainActivity extends AppCompatActivity implements Communicator{

    Button btnLogin, btnsignup;
    EditText username, password;
    FrameLayout frame1,frame2;
    ViewGroup myview;
    JSONArray mainarray=new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.login);
        btnsignup = (Button) findViewById(R.id.signup);
        frame1 = (FrameLayout) findViewById(R.id.frame1);
        frame2 = (FrameLayout) findViewById(R.id.frame2);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myview=(ViewGroup)findViewById(R.id.frame1);
                frame1.setVisibility(View.VISIBLE);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                login log = new login();
                ft.replace(R.id.frame1, log, "OldFragment");
                ft.addToBackStack("");
                ft.commit();
                btnLogin.setVisibility(View.INVISIBLE);
                btnsignup.setVisibility(View.INVISIBLE);

            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myview = (ViewGroup) findViewById(R.id.frame2);
                frame1.setVisibility(View.VISIBLE);
                frame1.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SignUp sign = new SignUp();
                ft.replace(R.id.frame1, sign, "NewFragment");
                ft.addToBackStack(null);
                ft.commit();
                btnLogin.setVisibility(View.INVISIBLE);
                btnsignup.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loginToken(String str) {
        if(str.equals("Login"))
        {
            frame2.setVisibility(View.VISIBLE);
            frame1.setVisibility(View.INVISIBLE);
        }
        else
        {
            Toast t= makeText(this, str, LENGTH_SHORT);
        }
    }
    @Override
    public void changetodrop(JSONObject obj) {
        mainarray.put(obj);
        getFragmentManager().beginTransaction().replace(R.id.tab1,new DropPoint()).commit();
    }

    @Override
    public void changetofinal(JSONArray obj)  {
        mainarray.put(obj);

        AsyncHttpClient client=new AsyncHttpClient();
        StringEntity entitiy= null;
        try {
            entitiy = new StringEntity(mainarray.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(this, "http://www.geeksstuff.org/alpha/vdata.php", entitiy, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.e("TAG", responseString);
                getFragmentManager().beginTransaction().replace(R.id.tab1, new FinalPage()).commit();
            }
        });

    }

    @Override
    public void gotofirst() {
        getFragmentManager().beginTransaction().replace(R.id.tab1,new MainOrderPage()).commit();
    }

    @Override
    public void changetotruck(JSONObject obj) {
        mainarray.put(obj);
        getFragmentManager().beginTransaction().replace(R.id.tab1,new TruckSelection()).commit();
    }

    public String change()
    {
        String s="";
        frame1.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
        btnsignup.setVisibility(View.VISIBLE);
        return s;
    }
}
