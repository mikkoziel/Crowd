package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import appView.EndGameActivity;
import appView.QuestionActivity;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Answer;
import entity.GivenAnswer;
import entity.Game;
import entity.Profile;
import entity.Question;

import interactor.AnswerInteractor;

public class PossibleAnswerPresenter extends AsyncTask<String, Button, String> {

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

    private AnswerInteractor _answerInteractor;

//
    public PossibleAnswerPresenter(Activity activity, Question question, ProgressBar progress, LinearLayout.LayoutParams lp, LinearLayout answerLayout, Game game, Profile profile) {
//    public PossibleAnswerPresenter(Question question, Connector connector) {

        this._activity = activity;
        this._question = question;
        this._progress = progress;
        this._lp = lp;
        this._answerLayout = answerLayout;
        this._profile = profile;
        this._game = game;
        this._answerInteractor = new AnswerInteractor();
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    // pytanie: tutaj ustawiamy odpowiedzi dla jednego pytania z jednej gry tak?
    @Override
    protected String doInBackground(String... params){
        ResultSet res = _answerInteractor.getAnswers(_question);
        try {
            _answerInteractor.addPossibleAnswers(res, _question);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //a to nie w widoku?
        for(Answer answer : _question.getAnswers()){
            Button button = setButtons(answer.getAnswer(), answer);
            publishProgress(button);
        }
        return ""; // TO DO: ???
    }

    //pytanie: można wykasować zakomentowany kod?
    @Override
    protected void onProgressUpdate(Button... answer) {
//        if(progress ) {
//            Toast.makeText(this, returnVal, Toast.LENGTH_SHORT).show();
//        } else {
//
//        }

//        answer.setTextOff(answerText);
//        answer.setTextOn("Your choice");
//        answer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    answer.setText("Your choice");
//                } else {
//                    answer.setText(answerText);
//                }
//            }
//        });
//            question.nextIndex();
        _answerLayout.addView(answer[0], _lp);
    }

    //pytanie: można wykasować zakomentowany kod?
    @Override
    protected void onPostExecute(String r){
        _progress.setVisibility(View.GONE);
//        answerLayout.addView(answer, lp);
        //Toast.makeText(activity, r, Toast.LENGTH_SHORT).show();
//        if(isSuccess){
//            Toast.makeText(activity, "Game starting", Toast.LENGTH_LONG).show();
//            intent.putExtra("game", game);
//            activity.startActivity(intent);
//        }
    }


    //pytanie: czy to ma być tutaj? nie powinno być w widoku?
    public Button setButtons(String answerText, final Answer a){
        Button answer = new Button(_activity);
//            answer.setText(question.getAnswers().get(question.getIndex()).getAnswer());
        answer.setText(answerText);
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
