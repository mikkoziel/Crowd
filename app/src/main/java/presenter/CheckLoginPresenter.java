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

public class CheckLoginPresenter extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private String _username;
    private String _password;
    private Intent _intent;

    private ProfileInteractor _profileInteractor;
    private GameInteractor _gameInteractor;

    public CheckLoginPresenter(Activity activity, ProgressBar progress, EditText loginT, EditText passwordT, Intent intent){
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

        String result;
        if(_username.trim().equals("")|| _password.trim().equals("")){
            result = "Please enter Username and Password";
        }
        else {
            ResultSet res;
            try {
                res = _profileInteractor
                        .checkLogin(_username, _password);
                Profile profile = _profileInteractor.setProfile(res);
                _gameInteractor.setGames(profile);
                _intent.putExtra("profile", profile);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            result = _profileInteractor.getResult();
        }
        //PYTANIE: co roimy z tym resultem?
        return result;
    }


    @Override
    protected void onPostExecute(String r){
        _progress.setVisibility(View.GONE);
        Toast.makeText(_activity, r, Toast.LENGTH_SHORT).show();
        if(_profileInteractor.getSuccess()){
            Toast.makeText(_activity, "Login Successful", Toast.LENGTH_LONG).show();
            _activity.startActivity(_intent);
        }
    }


}
