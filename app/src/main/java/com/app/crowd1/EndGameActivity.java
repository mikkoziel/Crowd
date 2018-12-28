package com.app.crowd1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import back.Game;
import back.Loger;
import back.Profil;
import gui.GivenAnswer;

public class EndGameActivity extends AppCompatActivity {

    public ProgressBar progress;
    public Game game;
    public Profil profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent inetnt = getIntent();
        this.game = (Game)inetnt.getSerializableExtra("game");
        this.profil = (Profil) inetnt.getSerializableExtra("profil");

        this.progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);

        if(getIntent().hasExtra("answer")){
            GivenAnswer given = (GivenAnswer) inetnt.getSerializableExtra("answer");
            Loger loger = profil.getLoger();
            loger.logAnswer(given, this, progress);
        }

        TextView endText = findViewById(R.id.endgame);
        String text = "You answered all questions.\n What would you like to do?";
        endText.setText(text);
    }

    public void backBttn(View view){
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("profil", profil);
        this.startActivity(intent);
    }

    public void repeatBttn(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("game", game);
        intent.putExtra("profil", profil);
        this.startActivity(intent);
    }
}
