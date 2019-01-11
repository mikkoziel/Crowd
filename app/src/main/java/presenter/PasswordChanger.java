package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Profile;
import interactor.Connector;

public class PasswordChanger extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    @SuppressLint("StaticFieldLeak")
    public ProgressBar progress;
    private String username;
    private int userID;
    public String password;
    public String passwordCheck;
    public String passwordCheck2;
    private Connector connector;
    public String result = "";
    private Boolean isSuccess = false;
    private int mode;
    private int semafor;

    public PasswordChanger(Activity activity, ProgressBar progress, Profile profile, String password, int mode){
        this.activity = activity;
        this.progress = progress;
        this.username = profile.getName();
        this.password = password;
        this.connector = profile.getConnector();
        this.userID = profile.getID();
        this.mode = mode;
        this.semafor = 4;
    }

    @Override
    protected void onPreExecute(){
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        Connection connection = connector.makeConnection();
        Boolean isConnect = connector.checkConnection(connection);
        try {
            if (isConnect) {
                switch (mode) {
                    case 0:
                        modeCheckOld(connection);
//                        semafor = 1;
                        return "";
                    case 1:
                        modeChangeToNew(connection);
//                        semafor =1;
                        return "";
                }
            }
            else{
                Toast.makeText(activity, "Wrong Old Password", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Boolean oldOK;

//        try {
//            if (isConnect) {
//                oldOK = checkOld(connection, query1);
//
//                if(oldOK){
//                    changeToNew(connection, query2);
//                }
//                else{
//                    Toast.makeText(activity, "Wrong Old Password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return "";
    }

    @Override
    protected void onPostExecute(String r){
        progress.setVisibility(View.GONE);
        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
        if(isSuccess){
            Toast.makeText(activity, "Password Change OK", Toast.LENGTH_LONG).show();
        }
    }

    private void modeCheckOld(Connection connection) throws SQLException {
        String query = "Select * from Profile where profilID = " + userID;
        ResultSet res = connector.runQuery(query, connection);
        if (res.next()) {
            String name = res.getString("name");
            String oldPasswordRes = res.getString("password");
            if(username.equals(name) && password.equals(oldPasswordRes)){
                checkRest();
            }
            else{
                result = "Fail";
                isSuccess = false;
                semafor = 3;
            }
        }
    }

    public void checkRest(){
        if (!passwordCheck.equals(passwordCheck2)) {
            result = "Fail";
            isSuccess = false;
            semafor = 1;
        } else {
            if (!password.equals(passwordCheck)) {
                result = "Fail";
                isSuccess = false;
                semafor = 2;
            } else {
                result = "Success";
                isSuccess = true;
                semafor = 0;
            }
        }
    }

    private void modeChangeToNew(Connection connection){
        String query = "Update Profile set password = '" + password + "' where profilID = " + userID;
        int res = -1;
        res = connector.updateQuery(query, connection);
        if(res > 0){
            result = "Success";
            isSuccess = true;
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            result = "Fail";
            isSuccess = false;
        }
    }

//    private Boolean checkOld(Connection connection, String query) throws SQLException {
//        ResultSet res = connector.runQuery(query, connection);
//        if (res.next()) {
//            String name = res.getString("name");
//            if(username.equals(name)){
//                String oldPasswordRes = res.getString("password");
//                if(password.equals(oldPasswordRes)){
//                    return true;
//                }
//            }
//        }
//        return false;
//
//    }
//
//    private void changeToNew(Connection connection, String query){
//        int res = -1;
//        res = connector.updateQuery(query, connection);
//        if(res > 0){
//            result = "Success";
//            isSuccess = true;
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        else{
//            result = "Fail";
//            isSuccess = false;
//        }
//    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public int getSemafor(){
        return semafor;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public void setPasswordCheck2(String passwordCheck2) {
        this.passwordCheck2 = passwordCheck2;
    }
}
