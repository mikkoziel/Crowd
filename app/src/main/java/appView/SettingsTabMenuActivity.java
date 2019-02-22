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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import entity.AppContent;
import entity.Profile;
import presenter.AvatarPresenter;
import presenter.GenKeyPresenter;

public class SettingsTabMenuActivity extends Fragment {

    private Activity _activity;
    private Intent _intent;
    private AppContent _appContent;
    private View _rootView;
    private ProgressBar _progress;

    public void setOnCreate(Activity activity, Intent intent, AppContent appContent){
        this._activity = activity;
        this._intent = intent;
        this._appContent = appContent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this._rootView = inflater.inflate(appView.R.layout.settings_tab_menu, container, false);

        Button changeButton = _rootView.findViewById(appView.R.id.changePass);
        changeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changePassword(_rootView);
            }
        });
        this._progress = _rootView.findViewById(R.id.progress);
        _progress.setVisibility(View.GONE);

        ToggleButton themeBttn = _rootView.findViewById(appView.R.id.themeBttn);
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

        Button changeAvatarBttn = _rootView.findViewById(R.id.changeAvatar);
        changeAvatarBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeAvatar(_rootView);
            }
        });
        if(!_appContent.getProfile().hasItem(3)) {
            changeAvatarBttn.setClickable(false);
        }

        Button genKeyBttn = _rootView.findViewById(R.id.genKey);
        genKeyBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                genKey();
            }
        });

        return _rootView;
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
        if(_appContent.getProfile().hasItem(3)) {
            Intent intent = new Intent(_activity, ChangeAvatarActivity.class);
            intent.putExtra("appContent", _appContent);
            _activity.startActivity(intent);
        }
        else{
            Toast.makeText(_activity, "You need to buy MAGIC AVATAR to unlock this", Toast.LENGTH_LONG).show();
        }
    }

    private void genKey(){
        LinearLayout keyLay = _rootView.findViewById(R.id.keysLay);
        keyLay.removeAllViews();
        GenKeyPresenter genKeyPresenter = new GenKeyPresenter(_activity, _appContent, _rootView, _progress);
        genKeyPresenter.execute();
    }



}
