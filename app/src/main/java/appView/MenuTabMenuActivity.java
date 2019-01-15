package appView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.sql.Connection;

import entity.Profile;
import entity.Game;
import interactor.Logger;
import presenter.SetQuestionPresenter;

public class MenuTabMenuActivity extends Fragment {

    public Profile profile;
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
        View rootView = inflater.inflate(appView.R.layout.menu_tab_menu, container, false);

        this.profile = (Profile) thisIntent.getSerializableExtra("profile");

        this.progress = rootView.findViewById(appView.R.id.progressMenu);
        progress.setVisibility(View.GONE);

        this.intent = new Intent(activity, GameActivity.class);
        intent.putExtra("profile", profile);

        LinearLayout ll = (LinearLayout) rootView.findViewById(appView.R.id.layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        for (final Game game : profile.getGames()) {
            Button gameButton = new Button(activity);
            gameButton.setText(game.getGameName());
            gameButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SetQuestionPresenter setQuestionPresenter = new SetQuestionPresenter(game, activity, progress, intent);
                    setQuestionPresenter.execute();
                }
//                }
            });
            ll.addView(gameButton, lp);

        }
        return rootView;
    }

}
