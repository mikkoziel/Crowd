package gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.crowd1.MenuActivity;

import java.sql.ResultSet;
import java.sql.SQLException;

import back.Connector;
import back.Game;

public class QuestionSetter extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity activity;
    @SuppressLint("StaticFieldLeak")
    public ProgressBar progress;
    private Game game;
    private Connector connector;
    public Intent intent;

    private Boolean isSuccess = false;

    public QuestionSetter(Game game, Activity activity, ProgressBar progress, Connector connector, Intent intent){
        this.game = game;
        this.activity = activity;
        this.progress = progress;
        this.connector = connector;
        this.intent = intent;
    }

    @Override
    protected void onPreExecute(){
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params){
        // try {
//        ResultSet res = null;
        String z = "";
        try {
            z = connector.setQuestions(game);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        z = "Game starting";
        isSuccess = true;
        // z = game.setQuestions(connector);
//            } catch (SQLException e) {
//                e.printStackTrace();
//                z = "Exception";
//            }
        return z;
    }

    @Override
    protected void onPostExecute(String r){
//        progress.setVisibility(View.GONE);
//        Toast.makeText(activity, r, Toast.LENGTH_SHORT).show();
        if(isSuccess){
//            Toast.makeText(activity, "Game starting", Toast.LENGTH_LONG).show();
            intent.putExtra("game", game);
//            activity.startActivity(intent);
        }
    }
//
//    public void setQ(Game game, Connector connector) throws SQLException {
//        String z = "";



//            Connection con = connector.connectionClass();
//            if (connector.getConnection() == null) {
//                z = "Check Your Internet Access!";
//            }
//            else {
//                String query = "select * from Question where gameID = " + game.getGameID() + ";";
//                ResultSet res = connector.runQuery(query);
//                game.setQuestions(res);
////                Thread thread = new Thread(new Runnable(){
////                    @Override
////                    public void run(){
////                        try {
////                            setAnswers();
////                        } catch (SQLException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                });
////                thread.start();
//                connector.getConnection().close();
//                z = "All alright";
//            }

//        return z;
//    }

//        public void setAnswers() throws SQLException {
//            for(Question question : game.getQuestions()){
//                String query = "select top 3 * from Answer where questionID = " + question.getQuestionID() + ";";
//                ResultSet res = connector.runQuery(query);
//                question.setAnswers(res);
//            }
//        }
}
