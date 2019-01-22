package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.sql.SQLException;
import java.util.ArrayList;

import entity.Game;
import entity.Profile;
import interactor.ProfileInteractor;
import interactor.QuestionInteractor;

public class HighscorePresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private Profile _profile;
    private Intent _intent;
    private ArrayList<Profile> high;

    private ProfileInteractor _proflieInteractor;

    public HighscorePresenter(Profile profile,Activity activity, Intent intent) {
        this._profile = profile;
        this._activity = activity;
        this._intent = intent;
        this._proflieInteractor = new ProfileInteractor();
    }

    @Override
    protected void onPreExecute() {
//        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            this.high = _proflieInteractor.getHighscore();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if (_proflieInteractor.isSuccess()) {
            _intent.putExtra("profile", _profile);
            _intent.putExtra("high", high);
            _activity.startActivity(_intent);
        }
        _proflieInteractor.endWork();
    }
}