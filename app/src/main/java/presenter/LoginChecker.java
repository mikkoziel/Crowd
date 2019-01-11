package presenter;

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

import entity.Profile;
import interactor.Connector;

public class LoginChecker extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    @SuppressLint("StaticFieldLeak")
    public ProgressBar progress;
    private String username;
    public String password;
    private Connector connector;
    private Intent intent;
    private Boolean isSuccess = false;

    public LoginChecker(Activity activity, ProgressBar progress, EditText loginT, EditText passwordT, Connector connector, Intent intent){
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

        String z;
        if(username.trim().equals("")|| password.trim().equals("")){
            z = "Please enter Username and Password";
        }
        else {
            ResultSet res = null;
            try {
                res = connector.checkLogin(username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Profile profile = null;

            try {
                if (res != null) {
                    profile = connector.setProfil(res);
                    connector.setGames(profile);
                    profile.setConnector(connector);
                    intent.putExtra("profile", profile);
//                        connector.getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            z = connector.getResult();
            isSuccess = connector.getSuccess();
        }
//            if(username.trim().equals("")|| password.trim().equals("")){
//                z = "Please enter Username and Password";
//            }
//            else{
//                try {
//                    con = connector.connectionClass();
//                    if (con == null) {
//                        z = "Check Your Internet Access!";
//                    }
//                    else{
//                        String query = "select * from Profile where Name= '" + username + "' and password = '" + password + "'";
//                        ResultSet res = connector.runQuery(query, con);
//                        if(res.next()){
//                            setMenu(res);
//                            setGames();
//                            con.close();
//                        }
//                        else{
//                            z = "Inwalid Credentils!";
//                            isSuccess = false;
//                        }
//                    }
//                }
//                catch(Exception e){
//                    isSuccess = false;
//                    z = e.getMessage();
//
//                }
//            }

        return z;
    }


    @Override
    protected void onPostExecute(String r){
        progress.setVisibility(View.GONE);
        Toast.makeText(activity, r, Toast.LENGTH_SHORT).show();
        if(isSuccess){
            Toast.makeText(activity, "Login Successful", Toast.LENGTH_LONG).show();
            activity.startActivity(intent);
        }
    }


}
