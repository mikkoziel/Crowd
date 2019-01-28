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
import interactor.ProfileInteractor;

public class UpdateProfilePresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;

    private AppContent _appContent;

    private GivenAnswerInteractor _givenAnswerInteractor;

    public UpdateProfilePresenter(Activity activity, ProgressBar progress, AppContent appContent) {
        this._activity = activity;
        this._progress = progress;
        this._appContent = appContent;

    }

        @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
    }

    //wszystko co given answer interactor zmieniał trzeba teraz ustawić
    @Override
    protected Void doInBackground(Void... voids) {
        Profile profile = _appContent.getProfile();
        Game game = _appContent.getCurrentGame();
        try {
            for(Question question : game.getQuestions())
            {
                for(Answer answer : question.getAnswers())
                {
                    _givenAnswerInteractor.updateShowedValue(answer);
                    _givenAnswerInteractor.updateChosenValue(answer);
                    question.updateAnswer(answer);
                }
                game.updateQuestion(question);
            }
            _appContent.updateGame(game);

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
            intent.putExtra("appContent", _appContent);
            _activity.startActivity(intent);
        }
        _givenAnswerInteractor.endWork();
    }
}
