package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.SQLException;

import entity.Profile;
import interactor.ProfileInteractor;

public class ChangePasswordPresenter extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private String _username;
    private int _userID;
    private String _password;
    private String _passwordCheck;
    private String _passwordCheck2;
    private String _result = "";
    private Boolean _isSuccess = false;
    private int _mode;
    private int _semaphore;
    private ProfileInteractor _profileInteractor;

    public ChangePasswordPresenter(Activity activity, ProgressBar progress, Profile profile, String password, int mode){
        this._activity = activity;
        this._progress = progress;
        this._username = profile.getName();
        this._password = password;
        this._userID = profile.getID();
        this._mode = mode;
        this._semaphore = 4;
        this._profileInteractor = new ProfileInteractor();
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            switch (_mode) {
                case 0:
                    _profileInteractor.modeCheckOld(_userID, _username, _password, _semaphore, _passwordCheck, _passwordCheck2);
//                  semafor = 1;
                    return "";  //?? return? a nie break?
                case 1:
                    _profileInteractor.modeChangeToNew(_password, _userID);
//                  semafor =1;
                    return "";
                }
            if(!_profileInteractor.getSuccess())
            {
                Toast.makeText(_activity, "Wrong Old Password", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String r){
        _progress.setVisibility(View.GONE);
        Toast.makeText(_activity, _result, Toast.LENGTH_SHORT).show();
        if(_profileInteractor.getSuccess()){
            Toast.makeText(_activity, "Password Change OK", Toast.LENGTH_LONG).show();
        }
    }

    public Boolean getIsSuccess() {
        return _profileInteractor.getSuccess();
    }

    public int getSemaphore(){
        return _semaphore;
    }

    public void setPasswordCheck(String passwordCheck) {
        this._passwordCheck = passwordCheck;
    }

    public void setPasswordCheck2(String passwordCheck2) {
        this._passwordCheck2 = passwordCheck2;
    }
}
