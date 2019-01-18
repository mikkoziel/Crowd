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
    private Question _question;

    @SuppressLint("StaticFieldLeak")
    private LinearLayout _answerLayout;
    private LinearLayout.LayoutParams _lp;
    private Game _game;
    private Profile _profile;

    private PossibleAnswerInteractor _possibleAnswerInteractor;

    public PossibleAnswerPresenter(Activity activity, Question question, ProgressBar progress, LinearLayout.LayoutParams lp, LinearLayout answerLayout, Game game, Profile profile) {
        this._activity = activity;
        this._question = question;
        this._progress = progress;
        this._lp = lp;
        this._answerLayout = answerLayout;
        this._profile = profile;
        this._game = game;
        this._possibleAnswerInteractor = new PossibleAnswerInteractor();
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids){
        try {
            _possibleAnswerInteractor.setPossibleAnswers(_question);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Answer answer : _question.getAnswers()){
            Button button = setButtons(answer.getAnswer(), answer);
            publishProgress(button);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Button... answer) {
        _answerLayout.addView(answer[0], _lp);
    }

    @Override
    protected void onPostExecute(Void voids){
        _progress.setVisibility(View.GONE);

        try {
            _possibleAnswerInteractor.endWork();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Button setButtons(String answerText, final Answer a){
        Button answer = new Button(_activity);

        answer.setText(answerText);
        if(a.isImageAnswer()){
            Drawable image = new BitmapDrawable(_activity.getResources(), BitmapFactory.decodeByteArray(a.getImage(), 0, a.getImage().length));
            answer.setCompoundDrawablesWithIntrinsicBounds( image, null, null, null);
        }

        if(_game.getIndex() < _game.getQuestions().size()) {
            answer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(_activity, QuestionActivity.class);
                    GivenAnswer given = new GivenAnswer(_profile, _question, a);
                    intent.putExtra("answer", given);
                    intent.putExtra("profile", _profile);
                    intent.putExtra("game", _game);
                    _activity.startActivity(intent);
                }
            });
        }
        else{
            _game.setPlayed(false);
            answer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(_activity, EndGameActivity.class);
                    GivenAnswer given = new GivenAnswer(_profile, _question, a);
                    intent.putExtra("answer", given);
                    intent.putExtra("profile", _profile);
                    intent.putExtra("game", _game);
                    _activity.startActivity(intent);
                }
            });
        }
        return answer;
    }
    
}
