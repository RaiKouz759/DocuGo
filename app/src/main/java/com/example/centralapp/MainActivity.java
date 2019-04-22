package com.example.centralapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText name, password;
    String Name, Password;
    Context ctx=this;
    String NAME=null, PASSWORD=null, EMAIL=null;
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

        Name = name.getText().toString();
        Password = password.getText().toString();
        if (Name.isEmpty() || Password.isEmpty()) {
            Toast.makeText(ctx, "Please enter all fields!", Toast.LENGTH_SHORT).show();
        } else {
            BackGround b = new BackGround();
            b.execute(Name, Password);
        }
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            String data="";
            int tmp;

            try {
                String err;
                URL url = new URL("https://teambudak.000webhostapp.com/app/login.php");

                String urlParams = "name="+name+"&password="+password;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                    atemp=49;
                }
                //Toast.makeText(MainActivity.this, tmp, Toast.LENGTH_SHORT).show();
                //try{
                   // JSONObject flag = new JSONObject(data);
                  //  flag.
              //  }
              //  catch(JSONException e){
              //      e.printStackTrace();
              //      err = "Exception: "+e.getMessage();
             //   }
                   // if(atemp==49){
                        //Toast.makeText(MainActivity.this, "Please input a valid username/password", Toast.LENGTH_SHORT).show();
                       // Intent intent = new Intent(this, MainActivity.class);
                   //    finish();
                  //  }



                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err=null;
            try {
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("user_data");
                NAME = user_data.getString("name");
                PASSWORD = user_data.getString("password");
                EMAIL = user_data.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
            }

            Intent i = new Intent(ctx, Home.class);
            i.putExtra("name", s);
            i.putExtra("password", PASSWORD);
            i.putExtra("email", EMAIL);
            i.putExtra("err", err);
            startActivity(i);

        }
    }
}




