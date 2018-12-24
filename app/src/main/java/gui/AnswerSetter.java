package gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.app.crowd1.QuestionActivity;
import com.app.crowd1.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import back.Answer;
import back.Connector;
import back.Game;
import back.Profil;
import back.Question;

public class AnswerSetter extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    public Activity activity;
    @SuppressLint("StaticFieldLeak")
    public ProgressBar progress;
    public Question question;
    public Connector connector;
    private Boolean result;
    @SuppressLint("StaticFieldLeak")
    private LinearLayout answerLayout;
    private LinearLayout.LayoutParams lp;
    public Game game;
    public Profil profil;

//
    public AnswerSetter(Activity activity, Question question, Connector connector, ProgressBar progress, LinearLayout.LayoutParams lp, LinearLayout answerLayout, Game game, Profil profil) {
//    public AnswerSetter(Question question, Connector connector) {

        this.activity = activity;
        this.question = question;
        this.connector = connector;
        this.result = false;
        this.progress = progress;
        this.lp = lp;
        this.answerLayout = answerLayout;
        this.profil = profil;
        this.game = game;
    }

    @Override
    protected void onPreExecute(){
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params){
        ResultSet res = null;
        ArrayList<Answer> answers = new ArrayList<>();
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
                    answers.add(answer);
                    result = true;
//                    notifyAll();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for(Answer x : answers){
                publishProgress(x.getAnswer());

            }
            question.setAnswers(answers);

        }
        return "";
    }

    @Override
    protected void onProgressUpdate(String... progress) {
//        if(progress ) {
//            Toast.makeText(this, returnVal, Toast.LENGTH_SHORT).show();
//        } else {
//
//        }
        String answerText = progress[0];
        Button answer = new Button(activity);
//            answer.setText(question.getAnswers().get(question.getIndex()).getAnswer());
        answer.setText(answerText);
        answer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("profil", profil);
                intent.putExtra("game", game);
                activity.startActivity(intent);
            }
        });
//        answer.setTextOff(answerText);
//        answer.setTextOn("Your choice");
//        answer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    answer.setText("Your choice");
//                } else {
//                    answer.setText(answerText);
//                }
//            }
//        });
//            question.nextIndex();
        answerLayout.addView(answer, lp);
    }

    @Override
    protected void onPostExecute(String r){
        progress.setVisibility(View.GONE);
//        answerLayout.addView(answer, lp);
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
