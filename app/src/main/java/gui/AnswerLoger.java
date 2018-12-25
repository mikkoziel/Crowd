package gui;

import android.os.AsyncTask;
import android.widget.Button;

import java.sql.Date;

import back.Game;
import back.Profil;

public class AnswerLoger extends AsyncTask<String, Button, String> {

    public Date date;
    public Game game;

    public AnswerLoger(Date date, Game game){
        this.date = date;
        this.game = game;
    }

    @Override
    protected void onPreExecute(){
//        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String r) {
    }

}
