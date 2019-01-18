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

public class SetQuestionPresenter extends AsyncTask<Void, Void, Void> {

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
    protected Void doInBackground(Void... voids) {
        try {
            _questionInteractor.emptyQuestions(_game);
            _questionInteractor.setQuestions(_game);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if (_questionInteractor.isSuccess()) {
            _intent.putExtra("game", _game);
            _activity.startActivity(_intent);
        }
        _questionInteractor.endWork();
    }
}