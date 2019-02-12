package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import java.util.ArrayList;

import entity.AppContent;
import entity.HighScore;
import entity.Profile;
import interactor.HighScoreInteractor;


public class HighScorePresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private Intent _intent;

    private AppContent _appContent;

    private HighScoreInteractor _highScoreInteractor;

    public HighScorePresenter(Activity activity, Intent intent, AppContent appContent) {
        this._activity = activity;
        this._intent = intent;
        this._appContent = appContent;
        this._highScoreInteractor = new HighScoreInteractor();
    }

    @Override
    protected void onPreExecute() {
//        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            ArrayList<HighScore> highScore = _highScoreInteractor.getHighScore();
            _appContent.setHighScore(highScore);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if (_highScoreInteractor.isSuccess()) {
//            _intent.putExtra("appContent", _appContent);
            _activity.startActivity(_intent);
        }
        _highScoreInteractor.endWork();
    }
}