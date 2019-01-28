package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import appView.TabMenuActivity;
import entity.AppContent;
import entity.Profile;
import interactor.ProfileInteractor;

public class ChangePasswordPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private String _password;
    private String _passwordCheck;
    private String _passwordCheck2;
    private int _mode;
    private Boolean _semaphore;

    private AppContent _appContent;
    private ProfileInteractor _profileInteractor;

    public ChangePasswordPresenter(Activity activity, ProgressBar progress, String password, int mode, AppContent appContent){
        this._activity = activity;
        this._progress = progress;
        this._password = password;
        this._mode = mode;
        this._semaphore = true;
        this._appContent = appContent;
        this._profileInteractor = new ProfileInteractor();
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Profile profile = _appContent.getProfile();
        try {
            switch (_mode) {
                case 0:
                    _profileInteractor.modeCheckOld(profile, _password, _passwordCheck, _passwordCheck2);
                    break;
                case 1:
                    _profileInteractor.modeChangeToNew(_password, profile);
                    break;
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        _appContent.updateCurrentProfile(profile);
        _semaphore = false;
        return null;
    }

    @Override
    protected void onPostExecute(Void voids){
        _progress.setVisibility(View.GONE);
        String result = _profileInteractor.getResult();
        Toast.makeText(_activity, result, Toast.LENGTH_LONG).show();
        _profileInteractor.endWork();

        if (_profileInteractor.isSuccess()) {
            Intent intent = new Intent(_activity, TabMenuActivity.class);
            intent.putExtra("appContent", _appContent);
            _activity.startActivity(intent);
        }
    }

    public Boolean getSuccess() {
        return _profileInteractor.isSuccess();
    }

    public Boolean getSemaphore(){
        return _semaphore;
    }

    public void setPasswordCheck(String passwordCheck) {
        this._passwordCheck = passwordCheck;
    }

    public void setPasswordCheck2(String passwordCheck2) {
        this._passwordCheck2 = passwordCheck2;
    }
}
