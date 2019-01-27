package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.AppContent;
import entity.Profile;
import interactor.AvatarInteractor;
import interactor.GameInteractor;
import interactor.ProfileInteractor;
import interactor.TagInteractor;

public class CheckLoginPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private String _username;
    private String _password;
    private Intent _intent;
    @SuppressLint("StaticFieldLeak")
    private Button _submit;
    @SuppressLint("StaticFieldLeak")
    private Button _register;

    private AppContent _appContent;

    private ProfileInteractor _profileInteractor;
    private GameInteractor _gameInteractor;
    private TagInteractor _tagInteractor;
    private AvatarInteractor _avatarInteractor;

    public CheckLoginPresenter(Activity activity, ProgressBar progress, EditText loginT, EditText passwordT, Intent intent, Button submit, Button register, AppContent appContent){
        this._activity = activity;
        this._progress = progress;
        this._username = loginT.getText().toString();
        this._password = passwordT.getText().toString();
        this._intent = intent;
        this._submit = submit;
        this._register = register;

        this._appContent = appContent;

        this._profileInteractor = new ProfileInteractor();
        this._gameInteractor = new GameInteractor();
        this._tagInteractor = new TagInteractor();
        this._avatarInteractor = new AvatarInteractor();
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids){

        if(_profileInteractor.userCredentialsFilled(_username, _password))
        {
            try {
                ResultSet resultSet = _profileInteractor.checkLogin(_username, _password);
                if(_profileInteractor.isSuccess()) {
                    Profile profile = _profileInteractor.setProfile(resultSet, null);
                    _avatarInteractor.setAvatar(profile);
                    _gameInteractor.setGames(_appContent);
                    _appContent.setProfile(profile);
                    _intent.putExtra("appContent", _appContent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        _progress.setVisibility(View.GONE);
        String result = _profileInteractor.getResult();
        Toast.makeText(_activity, result, Toast.LENGTH_LONG).show();
        _submit.setClickable(true);
        _register.setClickable(true);

        _gameInteractor.endWork();
        _profileInteractor.endWork();
        _tagInteractor.endWork();
        _avatarInteractor.endWork();

        if (_profileInteractor.isSuccess())
            _activity.startActivity(_intent);
    }
}