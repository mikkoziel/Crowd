package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.sql.SQLException;

import appView.TabMenuActivity;
import entity.Answer;
import entity.AppContent;
import entity.Game;
import entity.Profile;
import entity.Question;
import interactor.GivenAnswerInteractor;
import tools.InternetChecker;

public class UpdateAppContentPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;

    private AppContent _appContent;

    private GivenAnswerInteractor _givenAnswerInteractor;
    private Game _game;

    public UpdateAppContentPresenter(Activity activity, ProgressBar progress, AppContent appContent, Game game) {
        this._activity = activity;
        this._progress = progress;
        this._appContent = appContent;
        this._givenAnswerInteractor = new GivenAnswerInteractor();
        this._game = game;

    }

        @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
        InternetChecker internetChecker = new InternetChecker(_activity);
        if(!internetChecker.isOnline()){
            this.cancel(true);
        }
    }

    //wszystko co given answer interactor zmieniał trzeba teraz ustawić
    @Override
    protected Void doInBackground(Void... voids) {
        Profile profile = _appContent.getProfile();
        try {
            for(Question question : _game.getQuestions())
            {
                for(Answer answer : question.getAnswers())
                {
                    _givenAnswerInteractor.updateShowedValue(answer);
                    _givenAnswerInteractor.updateChosenValue(answer);
                    question.updateAnswer(answer);
                }
                _game.updateQuestion(question);
            }

            _givenAnswerInteractor.updatePointsValue(profile);
            _givenAnswerInteractor.updateUserLevelValue(profile);
            _givenAnswerInteractor.updateMissingPointsValue(profile);
            _givenAnswerInteractor.updateMoneyValue(profile);
            _appContent.updateCurrentProfile(profile);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if (_givenAnswerInteractor.isSuccess()) {
            Intent intent = new Intent(_activity, TabMenuActivity.class);
//            intent.putExtra("appContent", _appContent);
//            _jsonPresenter.writeToJson(_appContent, 0);
//            GlobalClass.getInstance().setAppContent(_appContent);
//            _appContent.destroy();
//            _appContent = null;
//            System.gc();
            intent.putExtra("appContent", _appContent);
            intent.putExtra("item", 1);
            _activity.startActivity(intent);
        }
        _givenAnswerInteractor.endWork();
    }
}
