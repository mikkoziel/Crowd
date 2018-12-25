package com.app.crowd1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import back.Connector;
import gui.LoginChecker;
import gui.LoginRegister;

public class MainActivity extends AppCompatActivity {
//    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
//    public ArrayList<Profil> profiles;
    Intent intent;

    Connector connector;

    EditText loginT, passwordT;
    Button submit;
    ProgressBar progress;

//    Bundle extras;


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
//        this.extras = new Bundle();

    }

    public void loginBttn(View view){
        intent = new Intent(this, TabMenuActivity.class);
//        intent.putExtra("connector", connector);

        LoginChecker loginChecker = new LoginChecker(this, progress, loginT, passwordT, connector, intent);
        loginChecker.execute("");
//        CheckLogin checklogin =  new CheckLogin();
//        checklogin.execute("");
    }

    public void registerBttn(View view){
        LoginRegister registerLogin = new LoginRegister(this, progress, loginT, passwordT, connector);
        registerLogin.execute("");
//        RegisterLogin registerLogin = new RegisterLogin();
//        registerLogin.execute("");

    }

//    public class CheckLogin extends AsyncTask<String, String, String> {
//        String z = "";
//        Boolean isSuccess = false;
//
//        @Override
//        protected void onPreExecute(){
//            progress.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(String... params){
//            String username = loginT.getText().toString();
//            String password = passwordT.getText().toString();
//
//            if(username.trim().equals("")|| password.trim().equals("")){
//                z = "Please enter Username and Password";
//            }
//            else {
//                ResultSet res = null;
//                try {
//                    res = connector.checkLogin(username, password);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                Profil profil = null;
//
//                try {
//                    if (res != null) {
//                        profil = connector.setMenu(res);
//                        connector.setGames(profil);
////                        connector.getConnection().close();
//                    }
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                profil.setConnector(connector);
//                intent.putExtra("profil", profil);
//                z = connector.getResult();
//                isSuccess = connector.getSuccess();
//            }
////            if(username.trim().equals("")|| password.trim().equals("")){
////                z = "Please enter Username and Password";
////            }
////            else{
////                try {
////                    con = connector.connectionClass();
////                    if (con == null) {
////                        z = "Check Your Internet Access!";
////                    }
////                    else{
////                        String query = "select * from Profil where Name= '" + username + "' and password = '" + password + "'";
////                        ResultSet res = connector.runQuery(query, con);
////                        if(res.next()){
////                            setMenu(res);
////                            setGames();
////                            con.close();
////                        }
////                        else{
////                            z = "Inwalid Credentils!";
////                            isSuccess = false;
////                        }
////                    }
////                }
////                catch(Exception e){
////                    isSuccess = false;
////                    z = e.getMessage();
////
////                }
////            }
//
//            return z;
//        }
//
//
//        @Override
//        protected void onPostExecute(String r){
//            progress.setVisibility(View.GONE);
//            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
//            if(isSuccess){
//                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
//                startActivity(intent);
//            }
//        }
//
//
//    }

//    public class RegisterLogin extends AsyncTask<String, String, String>{
//        String z = "";
//        Boolean isSuccess = false;
//
//        @Override
//        protected void onPreExecute(){
//            progress.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(String... params){
//            String username = loginT.getText().toString();
//            String password = passwordT.getText().toString();
//
//            if(username.trim().equals("")|| password.trim().equals("")){
//                z = "Please enter Username and Password";
//            }
//            else{
//                try {
////                    con = connector.connectionClass();
////                    if (con == null) {
////                        z = "Check Your Internet Access!";
////                    }
////                    else{
////                        String query = "select * from Profil where Name= '" + username + "'";
////                        ResultSet res = connector.runQuery(query);
////                        if(res.next()){
////                            z = "Login already exist";
////                            isSuccess = false;
////                        }
////                        else{
////                            z = "Inwalid Credentils!";
////                            String query1 = "Insert into Profil(Name, Password, Points) values('" + username + "', '" + password + "', 0)";
////                            ResultSet res1 = connector.runQuery(query1);
////                            if(res1.next()){
////                                z = "Success";
////                                isSuccess = true;
////                                con.close();
////                            }
////                            else{
////                                z = "Fail";
////                                isSuccess = false;
////                            }
////                        }
////                    }
//                }
//                catch(Exception e){
//                    isSuccess = false;
//                    z = e.getMessage();
//
//                }
//            }
//
//            return z;
//        }
//
//
//        @Override
//        protected void onPostExecute(String r){
//            progress.setVisibility(View.GONE);
//            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
//            if(isSuccess){
//                Toast.makeText(MainActivity.this, "Login Registration Successful", Toast.LENGTH_LONG).show();
//            }
//        }
//
//
//    }


}

