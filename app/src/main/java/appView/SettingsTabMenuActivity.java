package appView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import entity.Profile;

public class SettingsTabMenuActivity extends Fragment {

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
        final View rootView = inflater.inflate(appView.R.layout.settings_tab_menu, container, false);

        this.profile = (Profile) thisIntent.getSerializableExtra("profile");

        Button changeBttn = rootView.findViewById(appView.R.id.changePass);
        changeBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changePassword(rootView);
            }
        });

        ToggleButton themeBttn = rootView.findViewById(appView.R.id.themeBttn);
        themeBttn.setText("Dark Theme");
        themeBttn.setTextOff("Dark Theme");
        themeBttn.setTextOn("Light Theme");
        themeBttn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setLightTheme();
                } else {
                    setDarkTheme();
                }
            }
        });

        return rootView;
    }

    public void changePassword(View view){
        Intent intent = new Intent(activity, changePasswordActivity.class);
        intent.putExtra("profile", profile);
        activity.startActivity(intent);
    }

    public void setLightTheme(){
        Toast.makeText(activity, "Light Theme", Toast.LENGTH_LONG).show();
    }

    public void setDarkTheme(){
        Toast.makeText(activity, "Dark Theme", Toast.LENGTH_LONG).show();
    }

}
