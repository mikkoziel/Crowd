package com.app.crowd1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import back.Connector;
import back.Game;
import back.Profil;
import back.Question;

public class GameActivity extends AppCompatActivity {
    public Game game;
    public Profil profil;
    public ProgressBar progress;
    public Intent intent;
    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent inetnt = getIntent();
        this.game = (Game)inetnt.getSerializableExtra("game");
        this.profil = (Profil) inetnt.getSerializableExtra("profil");
        this.activity = this;

        this.progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        this.intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("profil", profil);
        intent.putExtra("game", game);

        TextView gameText = (TextView) findViewById(R.id.game);
        gameText.setText(game.getGameName());

        LinearLayout buttonLayout = (LinearLayout)findViewById(R.id.chooselayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (game.getPlayed()){
            gamePlayed(buttonLayout, lp);
        }
        else{
            gameNotPlayed(buttonLayout, lp);
        }

    }

    public void gamePlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        TextView previousText = new TextView(this);
        previousText.setText("Do you want to continue previous game?");

        Button resumeBttn = new Button(this);
        resumeBttn.setText("RESUME");
        resumeBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        Button newBttn = new Button(this);
        newBttn.setText("NEW GAME");
        newBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        buttonLayout.addView(previousText, lp);
        buttonLayout.addView(resumeBttn, lp);
        buttonLayout.addView(newBttn, lp);
    }

    public void gameNotPlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        Button startBttn = new Button(this);
        startBttn.setText("START");
        startBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activity.startActivity(intent);
            }
        });

        buttonLayout.addView(startBttn, lp);
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
//            Connection con;
//            try {
//                z = setQ(game, connector);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                con = connector.connectionClass();
//                if (con == null) {
//                    z = "Check Your Internet Access!";
//                }
//                else{
//                    z = setQ(game, connector);
//                    String query = "select * from Profil where Name= '" + username + "' and password = '" + password + "'";
//                    ResultSet res = connector.runQuery(query, con);
//                    if(res.next()){
//                        con.close();
//                    }
//                    else{
//                        z = "Inwalid Credentils!";
//                        isSuccess = false;
//                    }
//                }
//            }
//            catch(Exception e){
//                isSuccess = false;
//                z = e.getMessage();
//
//            }
//
//
//            return z;
//        }
//
//
//        @Override
//        protected void onPostExecute(String r){
//            progress.setVisibility(View.GONE);
//            Toast.makeText(GameActivity.this, r, Toast.LENGTH_SHORT).show();
//            if(isSuccess){
//                Toast.makeText(GameActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
//                startActivity(intent);
//            }
//        }
//
//        public String setQ(Game game, Connector connector) throws SQLException {
//            String z = "";

//            Connection con = connector.connectionClass();
//            if (connector.getConnection() == null) {
//                z = "Check Your Internet Access!";
//            } else {
//                String query = "select * from Question where gameID = " + game.getGameID() + ";";
//                ResultSet res = connector.runQuery(query);
//                game.setQuestions(res);
//                Thread thread = new Thread(new Runnable(){
//                    @Override
//                    public void run(){
//                        try {
//                            setAnswers();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                thread.start();
//                connector.getConnection().close();
//                z = "All alright";
//            }
//            return z;
//        }
//    }
}
