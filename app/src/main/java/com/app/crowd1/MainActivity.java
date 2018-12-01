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

import back.Profil;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public ArrayList<Profil> profiles;
    Intent intent;

    Connection con;
    String user, pass, db, ip;

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

        ip = "mssql8.gear.host";
        db = "crowd";
        user = "crowd";
        pass = "Ng65JF4j79-!";

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
                    con = connectionClass(user, pass, db, ip);
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    }
                    else{
                        String query = "select * from Profil where Name= '" + username.toString() + "' and password = '" + password.toString() + "'";
                        Statement stmt = con.createStatement();
                        ResultSet res = stmt.executeQuery(query);
                        if(res.next()){
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
                    con = connectionClass(user, pass, db, ip);
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    }
                    else{
                        String query = "select * from Profil where Name= '" + username.toString() + "'";
                        Statement stmt = con.createStatement();
                        ResultSet res = stmt.executeQuery(query);
                        if(res.next()){
                            z = "Login already exist";
                            isSuccess = false;
                        }
                        else{
                            z = "Inwalid Credentils!";
                            String query1 = "Insert into Profil(Name, Password, Points) values('" + username.toString() + "', '" + password.toString() + "', 0)";
                            Statement stmt1 = con.createStatement();
                            ResultSet res1 = stmt1.executeQuery(query1);
                            if(res.next()){
                                z = "Success";
                                isSuccess = true;
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

    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connectioin = null;
        String connectionURL = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver;//den1.mssql8.gear.host/crowd;user=crowd;password=Ng65JF4j79-!;";
            connectioin = DriverManager.getConnection(connectionURL);
        }
        catch(SQLException e){
            Log.e("error here 1; ", e.getMessage());
        }
        catch(ClassNotFoundException e){
            Log.e("error here 2; ", e.getMessage());
        }
        catch(Exception e){
            Log.e("error here 1; ", e.getMessage());
        }
        return connectioin;
    }

}

