package com.app.crowd1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import back.Connector;
import back.Game;
import back.Loger;
import back.Profil;

public class MenuActivity extends AppCompatActivity {

    public Connector connector;
    public Profil profil;
    public Loger loger;
    public Connection con;
    Intent intent;
    public ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent thisIntent = getIntent();
        this.connector = (Connector)thisIntent.getSerializableExtra("connector");
        this.profil = (Profil)thisIntent.getSerializableExtra("profil");

        this.progress = findViewById(R.id.progressMenu);
        progress.setVisibility(View.GONE);

        this.loger = new Loger(profil, connector);
        intent = new Intent(this, QuestionActivity.class);
    //    intent.putExtra("loger", loger);

        LinearLayout ll = (LinearLayout)findViewById(R.id.layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        ArrayList<?> games = (ArrayList<?>) thisIntent.getSerializableExtra("games");
        ListIterator<?> iterator = games.listIterator();

        while (iterator.hasNext()) {
            final Game game = (Game) iterator.next();
            Button gameButton = new Button(this);
            gameButton.setText(game.getGameName());
            gameButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!game.getPlayed() || !createAlertDialog("Game Activity", "Do you want to continue previous game?")){
                        SetQuestions setQuestions = new SetQuestions(game);
                        setQuestions.execute("");
                    }
                }
            });
            ll.addView(gameButton, lp);
        }
    }

    public class SetQuestions extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        Game game;

        SetQuestions(Game game){
            this.game = game;
        }

        @Override
        protected void onPreExecute(){
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params){
            try {
                z = game.setQuestions(connector);
                z = "Game starting";
                isSuccess = true;
            } catch (SQLException e) {
                e.printStackTrace();
                z = "Exception";
            }
            return z;
        }

        @Override
        protected void onPostExecute(String r){
            progress.setVisibility(View.GONE);
            Toast.makeText(MenuActivity.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess){
                Toast.makeText(MenuActivity.this, "Game starting", Toast.LENGTH_LONG).show();
                intent.putExtra("game", game);
                startActivity(intent);
            }
        }
    }
    @Override
    public void onBackPressed() {
        createAlertDialog("Closing Activity", "Are you sure you want to logout?");
    }

    public Boolean createAlertDialog(String title, String message){
        final Boolean[] answer = new Boolean[]{Boolean.TRUE};
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        answer[0] = false;
                    }

                })
                .show();
        return answer[0];
    }
}
