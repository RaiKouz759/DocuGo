package com.example.centralapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText name, password;
    String Name, Password;
    Context ctx= this;
    String NAME=null, PASSWORD=null, EMAIL=null;
    JSONParser jsonParser = new JSONParser();

    int atemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.main_name);
        password = findViewById(R.id.main_password);

    }

    public void main_register(View v){ // button to register
        startActivity(new Intent(this, Register.class));
    }

    public void main_login(View v) { //button to login

        Name = name.getText().toString().trim();
        Password = password.getText().toString().trim();
        if (Name.isEmpty() || Password.isEmpty()) {
            Toast.makeText(ctx, "Please enter all fields!", Toast.LENGTH_SHORT).show();
        } else {
            AttemptLogin b = new AttemptLogin();
            b.execute(Name, Password);
        }
    }

    class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String response = "";
            JSONObject jsonObject = null;
            try {

                String err;
                URL url = new URL("http://10.0.2.2/loginLogic.php");

                  //might be another to parse data as output params.
//                List<Pair<String, String>> param = new ArrayList<>();
//                param.add(new Pair<>("username", name));
//                param.add(new Pair<>("password", password));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    String postParameters = "username="+username+"&password="+password;
                    PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                    out.print(postParameters);
                    out.close();
                    urlConnection.connect();


                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response += line;
                    }
                    try {
                        Log.i("tagconvertstr", "["+response+"]");
                        jsonObject = new JSONObject(response);

                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
                    }


                } finally {
                    urlConnection.disconnect();
                }
                return jsonObject;
//
//                String urlParams = "name="+name+"&password="+password;
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                OutputStream os = httpURLConnection.getOutputStream();
//                os.write(urlParams.getBytes());
//                os.flush();
//                os.close();
//                InputStream is = httpURLConnection.getInputStream();
//                BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//                StringBuilder responseStrBuilder = new StringBuilder();
//
//                String inputStr;
//                while ((inputStr = streamReader.readLine()) != null)
//                    responseStrBuilder.append(inputStr);
//                JSONObject jsonObject;
//                try {
//                    jsonObject = new JSONObject(responseStrBuilder.toString());
//                }
//                catch (JSONException e){
//                    e.printStackTrace();
//                    err = "Exception:" +e.getMessage();
//                    return "";
//                }
//
//
//                is.close();
//                httpURLConnection.disconnect();
//                if(data.length() < 2){
//                    System.out.println("Login failed.");
//                    Toast.makeText(MainActivity.this, "Please input a valid username/password", Toast.LENGTH_SHORT).show();
//                    finish();
//                    return null;
//                }
//                return jsonObject.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("exception","Exception: "+e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("exception","Exception: "+e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            if(s == null){
                return;
            }
            int success;
            String message;

            try {
                success = s.getInt("success");
                message = s.getString("message");

                if(success == 1) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ctx, Home.class);
                    i.putExtra("name", Name);
                    i.putExtra("password", Password);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }
            }
            catch(JSONException e){
                e.printStackTrace();
                return;
            }

        }
    }
}




