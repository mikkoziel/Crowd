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
import presenter.HighscorePresenter;

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

        TextView user = rootView.findViewById(R.id.user);

        user.setText(String.format("Username: %s\n Points: %s", profile.getName(), Integer.toString(profile.getPoints())));

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

    public void showHighscore(){
        Intent intent = new Intent(activity, HighscoreActivity.class);
        HighscorePresenter highscorePresenter = new HighscorePresenter(profile, activity, intent);
        highscorePresenter.execute();
    }

    public void showShop(){
        Intent intent = new Intent(activity, ShopActivity.class);
        intent.putExtra("profile", profile);
        activity.startActivity(intent);
    }

}
