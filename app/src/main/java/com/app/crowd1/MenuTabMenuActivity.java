package com.app.crowd1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.sql.Connection;

import back.Connector;
import back.Game;
import back.Loger;
import back.Profil;
import gui.QuestionSetter;

public class MenuTabMenuActivity extends Fragment {

    public Connector connector;
    public Profil profil;
    public Loger loger;
    public Connection con;
    public Intent intent;
    public Intent thisIntent;
    public ProgressBar progress;
    public Activity activity;

    public void setOnCreate(Activity activity, Intent intent){
        this.activity = activity;
        this.thisIntent = intent;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_tab_menu, container, false);

//        this.connector = (Connector)thisIntent.getSerializableExtra("connector");
        this.profil = (Profil) thisIntent.getSerializableExtra("profil");

        this.progress = rootView.findViewById(R.id.progressMenu);
        progress.setVisibility(View.GONE);

//        this.loger = new Loger(profil, connector);
        this.intent = new Intent(activity, GameActivity.class);
        intent.putExtra("profil", profil);
        //    intent.putExtra("loger", loger);*

        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

//        ArrayList<?> games = (ArrayList<?>) thisIntent.getSerializableExtra("games");
//        ListIterator<?> iterator = profil.getGames().listIterator();

        for (final Game game : profil.getGames()) {
//            while (iterator.hasNext()) {
//            final Game game = (Game) iterator.next();
            Button gameButton = new Button(activity);
            gameButton.setText(game.getGameName());
            gameButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    if (!game.getPlayed() || !createAlertDialog("Game Activity", "Do you want to continue previous game?")){
                    QuestionSetter questionSetter = new QuestionSetter(game, activity, progress, profil.getConnector(), intent);
                    questionSetter.execute("");
                    //                    while(game.getQuestions().isEmpty()){
//
//                    }
//
//                    setAnswers(game);

//                        SetQuestions setQuestions = new SetQuestions(game);
//                        setQuestions.execute("");
                }
//                }
            });
            ll.addView(gameButton, lp);
//            return rootView;

        }
        return rootView;
    }


}
