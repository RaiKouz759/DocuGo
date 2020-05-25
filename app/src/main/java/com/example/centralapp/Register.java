package com.example.centralapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Register extends Activity {

    EditText name, password, email;
    String Name, Password, Email;
    Context ctx=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.register_name);
        password = findViewById(R.id.register_password);
        email = findViewById(R.id.register_email);
    }

    public void register_register(View v){
        Name = name.getText().toString().trim();
        Password = password.getText().toString().trim();
        Email = email.getText().toString().trim();
        if (Name.isEmpty() || Password.isEmpty()) {
            Toast.makeText(ctx, "Please enter all fields!", Toast.LENGTH_SHORT).show();
        } else {
            AttemptRegister b = new AttemptRegister();
            b.execute(Name, Password, Email);
        }
    }

    class AttemptRegister extends AsyncTask<String, String, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String email = params[2];
            String response="";
            JSONObject jsonObject = null;

            try {
                URL url = new URL("http://10.0.2.2/register.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    String postParameters = "username="+username+"&password="+password+"&email="+email;
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

            } catch (MalformedURLException e) {
                e.printStackTrace(); //prints the throwable along with other details like the line number and class name where the exception occurred
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            int success = 0;
            String message = "";
            try {
                success = s.getInt("success");
                message = s.getString("message");
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            if(success == 1){
                Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ctx, MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
            }

        }
    }

}