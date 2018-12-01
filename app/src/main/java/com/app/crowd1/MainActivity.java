package com.app.crowd1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Consumer;

import back.Connector;
import back.Profil;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public ArrayList<Profil> profiles;
    Intent intent;

    Connection con;
    Connector connector;

    EditText loginT, passwordT;
    Button submit;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginT = (EditText) findViewById(R.id.login);
        passwordT = (EditText) findViewById(R.id.password);
        submit = findViewById(R.id.button);
        progress = findViewById(R.id.progressBar);

        progress.setVisibility(View.GONE);
        this.connector = new Connector();

    }

    public void loginBttn(View view){
        intent = new Intent(this, MenuActivity.class);

        CheckLogin checklogin =  new CheckLogin();
        checklogin.execute("");
    }

    public void registerBttn(View view){
        RegisterLogin registerLogin = new RegisterLogin();
        registerLogin.execute("");

    }

    public class CheckLogin extends AsyncTask<String, String, String>{
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params){
            String username = loginT.getText().toString();
            String password = passwordT.getText().toString();

            if(username.trim().equals("")|| password.trim().equals("")){
                z = "Please enter Username and Password";
            }
            else{
                try {
                    con = connector.connectionClass();
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    }
                    else{
                        String query = "select * from Profil where Name= '" + username.toString() + "' and password = '" + password.toString() + "'";
                        ResultSet res = connector.runQuery(query, con);
                        if(res.next()){
                           //z = res.toString();
                          // System.out.println(res);
                            int id = res.getInt("profilID");
                            String name = res.getString("name");
                            String points = res.getString("points");
                            Profil profil = new Profil(id, name, points);
                            z = "Login succesful";
                            isSuccess = true;
                            con.close();
                        }
                        else{
                            z = "Inwalid Credentils!";
                            isSuccess = false;
                        }
                    }
                }
                catch(Exception e){
                    isSuccess = false;
                    z = e.getMessage();

                }
            }

            return z;
        }


        @Override
        protected void onPostExecute(String r){
            progress.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess){
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        }
    }

    public class RegisterLogin extends AsyncTask<String, String, String>{
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params){
            String username = loginT.getText().toString();
            String password = passwordT.getText().toString();

            if(username.trim().equals("")|| password.trim().equals("")){
                z = "Please enter Username and Password";
            }
            else{
                try {
                    con = connector.connectionClass();
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    }
                    else{
                        String query = "select * from Profil where Name= '" + username.toString() + "'";
                        ResultSet res = connector.runQuery(query, con);
                        if(res.next()){
                            z = "Login already exist";
                            isSuccess = false;
                        }
                        else{
                            z = "Inwalid Credentils!";
                            String query1 = "Insert into Profil(Name, Password, Points) values('" + username.toString() + "', '" + password.toString() + "', 0)";
                            ResultSet res1 = connector.runQuery(query1, con);
                            if(res1.next()){
                                z = "Success";
                                isSuccess = true;
                                con.close();
                            }
                            else{
                                z = "Fail";
                                isSuccess = false;
                            }
                        }
                    }
                }
                catch(Exception e){
                    isSuccess = false;
                    z = e.getMessage();

                }
            }

            return z;
        }


        @Override
        protected void onPostExecute(String r){
            progress.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess){
                Toast.makeText(MainActivity.this, "Login Registration Successful", Toast.LENGTH_LONG).show();
            }
        }


    }


}

