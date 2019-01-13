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
    private String _password;
    private String _passwordCheck;
    private String _passwordCheck2;
    private int _mode;
    private int _semaphore;
    private ProfileInteractor _profileInteractor;
    private Profile _profile;

    public ChangePasswordPresenter(Activity activity, ProgressBar progress, Profile profile, String password, int mode){
        this._activity = activity;
        this._progress = progress;
        this._password = password;
        this._mode = mode;
        this._semaphore = 4;
        this._profileInteractor = new ProfileInteractor();
        this._profile = profile;
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    //PYTANIE: co z tymi semaforami?
    @Override
    protected String doInBackground(String... strings) {
        try {
            switch (_mode) {
                case 0:
                    _profileInteractor.modeCheckOld(_profile, _password, _semaphore, _passwordCheck, _passwordCheck2);
//                  semafor = 1;
                    return "";  //?? return? a nie break?
                case 1:
                    _profileInteractor.modeChangeToNew(_password, _profile);
//                  semafor =1;
                    return "";
                }
                // PYTANIE cz1: tu ustawiamy toast jak no success
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
        Toast.makeText(_activity, _profileInteractor.getResult(), Toast.LENGTH_SHORT).show();
        // PYTANIE cz 2: tu ustawiamy toast jak succes, po co tak? nie można tutaj obu? i czmu nie korzytsamy z result ustawionego w profileInteractor?
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
