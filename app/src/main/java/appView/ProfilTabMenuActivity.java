package appView;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View rootView = inflater.inflate(R.layout.profil_tab_menu, container, false);

        this.profile = (Profile) thisIntent.getSerializableExtra("profile");

        TextView user = rootView.findViewById(R.id.userName);
        TextView points = rootView.findViewById(R.id.points);

        user.setText("Username: " + profile.getName());
        points.setText("Points: " + Integer.toString(profile.getPoints()));

        return rootView;
    }







}
