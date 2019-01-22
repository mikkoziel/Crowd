package appView;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import entity.Profile;

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
//        TextView points = rootView.findViewById(R.id.points);

        user.setText(String.format("Username: %s/n Points: %s", profile.getName(), Integer.toString(profile.getPoints())));
//        points.setText(String.format("Points: %s", Integer.toString(profile.getPoints())));

        Button highscore = rootView.findViewById(R.id.highscore);
        highscore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHighscore(rootView);
            }
        });

        return rootView;
    }

    public void showHighscore(View view){
        Intent intent = new Intent(activity, HighscoreActivity.class);
        intent.putExtra("profile", profile);
        activity.startActivity(intent);
    }

    public void showShop(View view){
//        Intent intent = new Intent(activity, hActivity.class);
//        intent.putExtra("profile", profile);
//        activity.startActivity(intent);
    }

}
