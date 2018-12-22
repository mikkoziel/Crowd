package gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import back.Answer;
import back.Connector;
import back.Question;

public class AnswerSetter extends AsyncTask<String, String, String> {

//    @SuppressLint("StaticFieldLeak")
//    public Activity activity;
    @SuppressLint("StaticFieldLeak")
    public ProgressBar progress;
    public Question question;
    public Connector connector;
    private Boolean result;


    public AnswerSetter(Question question, Connector connector, ProgressBar progress) {
        this.question = question;
        this.connector = connector;
        this.result = false;
        this.progress = progress;
    }

    @Override
    protected void onPreExecute(){
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params){
        ResultSet res = null;
//        ArrayList<Answer> answers = new ArrayList<>();
//        Boolean result = false;
        Connection connection = connector.makeConnection();
        Boolean isConnect = connector.checkConnection(connection);

        if(isConnect) {
            res = getAnswers(question, connection, connector);

            try {
                while(res.next()) {
                    String content = res.getString("answer");
                    int ID = res.getInt("answerID");
                    int type = res.getInt("typeID");
                    int used = res.getInt("used");
                    double percentageUsed = res.getDouble("percentageUsed");
                    Answer answer = new Answer(ID, content, used, percentageUsed, type);
//                    answers.add(answer);
                    question.addAnswer(answer);
                    result = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return "";
    }

    @Override
    protected void onPostExecute(String r){
        progress.setVisibility(View.GONE);
        //Toast.makeText(activity, r, Toast.LENGTH_SHORT).show();
//        if(isSuccess){
//            Toast.makeText(activity, "Game starting", Toast.LENGTH_LONG).show();
//            intent.putExtra("game", game);
//            activity.startActivity(intent);
//        }
    }


    public ResultSet getAnswers(Question question, Connection connection, Connector connector){
        String query = "select * from Answer where questionID= '" + question.getQuestionID() + "'";
        ResultSet res = connector.runQuery(query, connection);
        return res;
    }

    public Boolean getResult() {
        return result;
    }
}
