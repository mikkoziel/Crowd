package com.app.crowd1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import back.Profil;

public class SettingsTabMenuActivity extends Fragment {

    public Activity activity;
    public Intent thisIntent;
    public Profil profil;

    public void setOnCreate(Activity activity, Intent intent){
        this.activity = activity;
        this.thisIntent = intent;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_tab_menu, container, false);

        this.profil = (Profil) thisIntent.getSerializableExtra("profil");

        return rootView;
    }

//    public void changePassword(View view){
//        Intent intent = new Intent(activity, changePasswordActivity.class);
//        intent.putExtra("profil", profil);
//        activity.startActivity(intent);
//    }
}
