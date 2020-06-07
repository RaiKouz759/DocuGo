package com.example.centralapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {
    String name, password, email, Err;
    TextView nameTV, emailTV, passwordTV, err, document_info;
    Spinner spinner_receiver, spinner_document;
    public static final String EmployeeNamearray = "name";
    public static final String EmployeeName = "name";
    public static final String EmployeeMailid = "email";
    public static final String JSON_ARRAY = "result";
    private JSONArray result;
    String EmpName;
    private ArrayList<String> arrayList;
    TextView employeename, mailid;

    private Spinner spinner;
    private static final String PATH_TO_SERVER = "http://10.0.2.2/output.php";
    protected List<DataObject> spinnerData;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        queue = Volley.newRequestQueue(this);
        requestJsonObject();

        spinner_receiver = findViewById(R.id.spinner_receiver);
        spinner_document = findViewById(R.id.spinner_document);
        spinner_document.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                document_info.setText(spinnerData.get(position).getContent());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        nameTV = findViewById(R.id.home_name);
        emailTV = findViewById(R.id.home_email);
        passwordTV = findViewById(R.id.home_password);
        err = findViewById(R.id.err);
        document_info = findViewById(R.id.textView3);

        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
        email = getIntent().getStringExtra("email");
        Err = getIntent().getStringExtra("err");


        nameTV.setText("Welcome " + name);
        passwordTV.setText("Your password is " + password);
        emailTV.setText("Your email is " + email);
        err.setText(Err);

    }
    private void requestJsonObject(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PATH_TO_SERVER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                spinnerData = Arrays.asList(mGson.fromJson(response, DataObject[].class));
                //display first question to the user
                if(null != spinnerData){
                    spinner = (Spinner) findViewById(R.id.spinner_document);
                    assert spinner != null;
                    spinner.setVisibility(View.VISIBLE);
                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(Home.this, spinnerData);
                    spinner.setAdapter(spinnerAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

}

