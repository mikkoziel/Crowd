package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.sql.SQLException;

import appView.TabMenuActivity;
import entity.AppContent;
import entity.Profile;
import interactor.ProfileInteractor;

public class UpdateProfilePresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;

    private Profile _profile;
    private AppContent _appContent;

    private ProfileInteractor _profileInteractor;

    public UpdateProfilePresenter(Activity activity, ProgressBar progress, AppContent appContent) {
        this._activity = activity;
        this._progress = progress;
        this._appContent = appContent;
        this._profile = appContent.getProfile();
        this._profileInteractor = new ProfileInteractor();
    }

        @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
    }

    //TODO update showed i chosen, wszystko co given answer ustawiał
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            _profile = _profileInteractor.updateProfile(_profile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        _appContent.updateCurrentProfile(_profile);
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if (_profileInteractor.isSuccess()) {
            Intent intent = new Intent(_activity, TabMenuActivity.class);
            intent.putExtra("appContent", _appContent);
            _activity.startActivity(intent);
        }
        _profileInteractor.endWork();
    }
}
