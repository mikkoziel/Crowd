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
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

<<<<<<< HEAD
import entity.AppContent;

public class SettingsTabMenuActivity extends Fragment {

    private Activity _activity;
    private Intent _intent;
    private AppContent _appContent;
=======
import entity.Profile;
import presenter.AvatarPresenter;

public class SettingsTabMenuActivity extends Fragment {

    public Activity activity;
    public Intent thisIntent;
    public Profile profile;
    private ProgressBar _progress;
>>>>>>> master

    public void setOnCreate(Activity activity, Intent intent){
        this._activity = activity;
        this._intent = intent;
        this._appContent = (AppContent) _intent.getSerializableExtra("appContent");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(appView.R.layout.settings_tab_menu, container, false);

<<<<<<< HEAD
=======
        this.profile = (Profile) thisIntent.getSerializableExtra("profile");
        this._progress = rootView.findViewById(R.id.progress);
        _progress.setVisibility(View.GONE);

>>>>>>> master
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

        Button changeAvatarBttn = rootView.findViewById(R.id.changeAvatar);
        changeAvatarBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeAvatar(rootView);
            }
        });

        return rootView;
    }

    public void changePassword(View view){
        Intent intent = new Intent(_activity, ChangePasswordActivity.class);
        intent.putExtra("appContent", _appContent);
        _activity.startActivity(intent);
    }

    public void setLightTheme(){
        Toast.makeText(_activity, "Light Theme", Toast.LENGTH_LONG).show();
    }

    public void setDarkTheme(){
        Toast.makeText(_activity, "Dark Theme", Toast.LENGTH_LONG).show();
    }


    public void changeAvatar(View view) {
<<<<<<< HEAD
        Toast.makeText(_activity, "Change Avatar", Toast.LENGTH_LONG).show();
=======
//        Toast.makeText(activity, "Change Avatar", Toast.LENGTH_LONG).show();
        AvatarPresenter avatarPresenter = new AvatarPresenter(activity, profile, 0, 0, _progress);
        avatarPresenter.execute();
>>>>>>> master
    }

}
