package gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import back.Connector;
import back.Game;
import back.Profil;

public class AnswerLoger extends AsyncTask<String, Button, String> {

    public Date date;
    private Connector connector;
    private int profilID;
    private int questionID;
    private int answerID;
    private String result;
    private Boolean isSuccess;
    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar progress;

    public AnswerLoger(Date date, GivenAnswer given, Activity activity, ProgressBar progress){
        this.date = date;
//        this.game = game;
        this.connector = given.getProfil().getConnector();
        this.profilID = given.getProfil().getID();
        this.questionID = given.getQuestion().getQuestionID();
        this.answerID = given.getAnswer().getAnswerID();
        this.result = "";
        this.isSuccess = false;
        this.activity = activity;
        this.progress = progress;
    }

    @Override
    protected void onPreExecute(){
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        Connection connection = connector.makeConnection();
        Boolean isConnect = connector.checkConnection(connection);
        String query = "Insert into Log(profilID, questionID, answerID, date) values(" + profilID + ", " + questionID + ", " + answerID + ", " + date + ")";

        if (isConnect) {
            log(connection, query);
        }

        return "";
    }

    @Override
    protected void onPostExecute(String r) {
        progress.setVisibility(View.GONE);
        Toast.makeText(activity, r, Toast.LENGTH_SHORT).show();
        if(isSuccess){
            Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show();
        }
    }

    private void log(Connection connection, String query){
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

}
