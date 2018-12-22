package gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

import back.Connector;

public class LoginRegister extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    @SuppressLint("StaticFieldLeak")
    public ProgressBar progress;
    private String username;
    public String password;
    private Connector connector;
    private Intent intent;
    String z = "";
    Boolean isSuccess = false;

    public LoginRegister(Activity activity, ProgressBar progress, EditText loginT, EditText passwordT, Connector connector, Intent intent){
        this.activity = activity;
        this.progress = progress;
        this.username = loginT.getText().toString();
        this.password = passwordT.getText().toString();
        this.connector = connector;
        this.intent = intent;
    }

    @Override
    protected void onPreExecute(){
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params){

        if(username.trim().equals("")|| password.trim().equals("")){
            z = "Please enter Username and Password";
        }
        else{
            ResultSet res = null;
            try {
                res = connector.checkLogin(username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            try {
//                    con = connector.connectionClass();
//                    if (con == null) {
//                        z = "Check Your Internet Access!";
//                    }
//                    else{
//                        String query = "select * from Profil where Name= '" + username + "'";
//                        ResultSet res = connector.runQuery(query);
//                        if(res.next()){
//                            z = "Login already exist";
//                            isSuccess = false;
//                        }
//                        else{
//                            z = "Inwalid Credentils!";
//                            String query1 = "Insert into Profil(Name, Password, Points) values('" + username + "', '" + password + "', 0)";
//                            ResultSet res1 = connector.runQuery(query1);
//                            if(res1.next()){
//                                z = "Success";
//                                isSuccess = true;
//                                con.close();
//                            }
//                            else{
//                                z = "Fail";
//                                isSuccess = false;
//                            }
//                        }
//                    }
//            }
//            catch(Exception e){
//                isSuccess = false;
//                z = e.getMessage();
//
//            }
        }

        return z;
    }


    @Override
    protected void onPostExecute(String r){
        progress.setVisibility(View.GONE);
        Toast.makeText(activity, r, Toast.LENGTH_SHORT).show();
        if(isSuccess){
            Toast.makeText(activity, "Login Registration Successful", Toast.LENGTH_LONG).show();
        }
    }


}
