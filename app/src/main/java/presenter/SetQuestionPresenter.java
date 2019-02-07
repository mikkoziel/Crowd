package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.sql.SQLException;

import entity.AppContent;
import entity.Game;
import interactor.QuestionInteractor;

public class SetQuestionPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private Intent _intent;

    private AppContent _appContent;
    private Game _game;

    private QuestionInteractor _questionInteractor;

    public SetQuestionPresenter(Game game, Activity activity, ProgressBar progress, Intent intent) {
        this._activity = activity;
        this._progress = progress;
        this._intent = intent;
        this._appContent = (AppContent) intent
                .getSerializableExtra("appContent");
        this._game = game;
        this._questionInteractor = new QuestionInteractor();
    }

    @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            _game.getQuestions().clear();
            _questionInteractor.setQuestions(_game, _activity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        _appContent.updateGame(_game);
        _appContent.setCurrentGameID(_game.getID());
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        _questionInteractor.endWork();
        if (_questionInteractor.isSuccess()) {
            _intent.putExtra("appContent", _appContent);
            //_intent.putExtra("game", _game);
            _activity.startActivity(_intent);
        }
    }
}