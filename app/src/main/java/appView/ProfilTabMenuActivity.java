package appView;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import entity.Profile;
import presenter.HighScorePresenter;

public class ProfilTabMenuActivity extends Fragment {

    public Activity activity;
    public Intent thisIntent;
    public Profile profile;


    public void setOnCreate(Activity activity, Intent intent){
        this.activity = activity;
        this.thisIntent = intent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.profil_tab_menu, container, false);

        this.profile = (Profile) thisIntent.getSerializableExtra("profile");

        populateView(rootView);

        Button highscore = rootView.findViewById(R.id.highscore);
        highscore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHighscore();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShop();
            }
        });

        return rootView;
    }

    public void populateView(View rootView){

        TextView user = rootView.findViewById(R.id.userName);
        user.setText(String.format("%s", profile.getName()));

        TextView points = rootView.findViewById(R.id.points);
        points.setText(String.format("%s", Integer.toString(profile.getPoints())));

        TextView stats = rootView.findViewById(R.id.stats);
        stats.setText(String.format("Level: %s\nMissing points to next level: %s", profile.getLevel(), Integer.toString(profile.getMissingPoints())));

    }

    public void showHighscore(){
        Intent intent = new Intent(activity, HighscoreActivity.class);
        HighScorePresenter highscorePresenter = new HighScorePresenter(profile, activity, intent);
        highscorePresenter.execute();
    }

    public void showShop(){
        Intent intent = new Intent(activity, ShopActivity.class);
        intent.putExtra("profile", profile);
        activity.startActivity(intent);
    }

}
