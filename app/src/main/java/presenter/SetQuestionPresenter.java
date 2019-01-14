package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.sql.SQLException;

import entity.Game;
import interactor.QuestionInteractor;

public class SetQuestionPresenter extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private Game _game;
    private Intent _intent;

    private QuestionInteractor _questionInteractor;

    public SetQuestionPresenter(Game game, Activity activity, ProgressBar progress, Intent intent) {
        this._game = game;
        this._activity = activity;
        this._progress = progress;
        this._intent = intent;
        this._questionInteractor = new QuestionInteractor();
    }

    @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            _questionInteractor.setQuestions(_game);
            result = _questionInteractor.getResultInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    //PYTANIE" po co ten r?
    protected void onPostExecute(String r) {
        if (_questionInteractor.isSuccess()) {
            _intent.putExtra("game", _game);
            _activity.startActivity(_intent);
        }

    }
}