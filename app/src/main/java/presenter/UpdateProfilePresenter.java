package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.sql.SQLException;

import appView.TabMenuActivity;
import entity.Profile;
import interactor.ProfileInteractor;

public class UpdateProfilePresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    private Profile _profile;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;

    private ProfileInteractor _profileInteractor;

    public UpdateProfilePresenter(Profile profile, Activity activity, ProgressBar progress) {
        this._activity = activity;
        this._profile = profile;
        this._progress = progress;
        this._profileInteractor = new ProfileInteractor();
    }

        @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            _profile = _profileInteractor.updateProfile(_profile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if (_profileInteractor.isSuccess()) {
            Intent intent = new Intent(_activity, TabMenuActivity.class);
            intent.putExtra("profile", _profile);
            intent.putExtra("item", 1);
            _activity.startActivity(intent);
        }
        _profileInteractor.endWork();
    }
}
