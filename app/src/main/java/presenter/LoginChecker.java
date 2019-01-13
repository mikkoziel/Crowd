package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Profile;
import interactor.GameInteractor;
import interactor.ProfileInteractor;

public class LoginChecker extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private String _username;
    private String _password;
    private Intent _intent;
    private Boolean _isSuccess = false;

    private ProfileInteractor _profileInteractor;
    private GameInteractor _gameInteractor;

    public LoginChecker(Activity activity, ProgressBar progress, EditText loginT, EditText passwordT, Intent intent){
        this._activity = activity;
        this._progress = progress;
        this._username = loginT.getText().toString();
        this._password = passwordT.getText().toString();
        this._intent = intent;
        this._profileInteractor = new ProfileInteractor();
        this._gameInteractor = new GameInteractor();
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params){

        String z;
        if(_username.trim().equals("")|| _password.trim().equals("")){
            z = "Please enter Username and Password";
        }
        else {
            ResultSet res = null;
            try {
                res = _profileInteractor.checkLogin(_username, _password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Profile profile = null;

            try {
                if (res != null) {
                    profile = _profileInteractor.setProfile(res);
                    _gameInteractor.setGames(profile);
                    //profile.setConnector(connector);
                    _intent.putExtra("profile", profile);
//                        connector.getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            z = _profileInteractor.getResult();
            _isSuccess = _profileInteractor.getSuccess();
        }
        return z;
    }


    @Override
    protected void onPostExecute(String r){
        _progress.setVisibility(View.GONE);
        Toast.makeText(_activity, r, Toast.LENGTH_SHORT).show();
        if(_isSuccess){
            Toast.makeText(_activity, "Login Successful", Toast.LENGTH_LONG).show();
            _activity.startActivity(_intent);
        }
    }


}
