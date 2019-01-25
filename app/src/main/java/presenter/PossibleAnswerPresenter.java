package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Layout;
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
    private int _i;

    @SuppressLint("StaticFieldLeak")
    private LinearLayout _answerLayout;
    @SuppressLint("StaticFieldLeak")
    private LinearLayout _row1;
    @SuppressLint("StaticFieldLeak")
    private LinearLayout _row2;
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
        this._row1 = _activity.findViewById(appView.R.id.row1);
        this._row2 = _activity.findViewById(appView.R.id.row2);
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

        _i = 0;
        for(Answer answer : _question.getAnswers()){
            Button button = setButtons(answer.getAnswer(), answer);
//            button.set();
            publishProgress(button);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Button... answer) {
        if(_i < 2){
            _row1.addView(answer[0], _lp);
        }else{
            if(_i < 4){
                _row2.addView(answer[0], _lp);
            }
            else{
                _answerLayout.addView(answer[0], _lp);
            }
        }
        _i +=1;
    }

    @Override
    protected void onPostExecute(Void voids){
        _progress.setVisibility(View.GONE);
        _possibleAnswerInteractor.endWork();

    }


    private Button setButtons(String answerText, final Answer a){
        Button answer = new Button(_activity);

        answer.setText(String.format("\n%s\n", answerText));
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
