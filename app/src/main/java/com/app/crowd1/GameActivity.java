package com.app.crowd1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import back.Game;
import back.Profil;
import gui.QuestionSetter;

public class GameActivity extends AppCompatActivity {
    public Game game;
    public Profil profil;
    public ProgressBar progress;
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
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("setter", false);
                intent.putExtra("profil", profil);
                intent.putExtra("game", game);
                game.setPlayed(true);
                activity.startActivity(intent);
            }
        });

        Button newBttn = new Button(this);
        newBttn.setText("NEW GAME");
        newBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("setter", true);
                intent.putExtra("profil", profil);
                intent.putExtra("game", game);
                game.setPlayed(true);
                game.zeroIndex();
                QuestionSetter questionSetter = new QuestionSetter(game, activity, progress, profil.getConnector(), intent);
                questionSetter.execute("");
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
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("setter", true);
                intent.putExtra("profil", profil);
                intent.putExtra("game", game);
                game.setPlayed(true);
                game.zeroIndex();
                activity.startActivity(intent);
            }
        });

        buttonLayout.addView(startBttn, lp);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("profil", profil);
        this.startActivity(intent);
    }
}
