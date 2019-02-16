package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import appView.EndGameActivity;
import appView.QuestionActivity;

import java.sql.SQLException;

import entity.Answer;
import entity.AppContent;
import entity.GivenAnswer;
import entity.Game;
import entity.Profile;
import entity.Question;

import interactor.PossibleAnswerInteractor;

public class PossibleAnswerPresenter extends AsyncTask<Void, Button, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private Intent _intent;
    private int _i;

    @SuppressLint("StaticFieldLeak")
    private LinearLayout _row1;
    @SuppressLint("StaticFieldLeak")
    private LinearLayout _row2;
    @SuppressLint("StaticFieldLeak")
    private LinearLayout _row3;
    private LinearLayout.LayoutParams _lp;

    private AppContent _appContent;
    private Game _game;
//    private Profile _profile;

    private PossibleAnswerInteractor _possibleAnswerInteractor;
    public PossibleAnswerPresenter(Activity activity,
                                   Intent intent,
                                   ProgressBar progress,
                                   LinearLayout.LayoutParams lp,
                                   AppContent appContent,
                                   Game game) {
        this._activity = activity;
        this._intent = intent;
        this._progress = progress;
        this._lp = lp;
        this._row1 = _activity.findViewById(appView.R.id.row1);
        this._row2 = _activity.findViewById(appView.R.id.row2);
        this._row3 = _activity.findViewById(appView.R.id.row3);

        this._appContent = appContent;
        this._game = game;
//        this._profile = appContent.getProfile();
        _game.nextIndex();

        this._possibleAnswerInteractor = new PossibleAnswerInteractor();
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids){
        for(Question question: _game.getQuestions()) {
//            this._question = question;
            try {
                question.getAnswers().clear();
                _possibleAnswerInteractor.setPossibleAnswers(question);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids){
        _possibleAnswerInteractor.endWork();
        _progress.setVisibility(View.GONE);
        if(_possibleAnswerInteractor.isSuccess()){
            _intent.putExtra("appContent", _appContent);
            _intent.putExtra("game", _game);
            _activity.startActivity(_intent);
        }
    }

    
}
