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
import java.util.ArrayList;

import entity.Answer;
import entity.GivenAnswer;
import entity.Game;
import entity.Profile;
import entity.Question;

import interactor.AnswerInteractor;

public class PossibleAnswerPresenter extends AsyncTask<String, Button, String> {

    @SuppressLint("StaticFieldLeak")
    public Activity _activity;
    @SuppressLint("StaticFieldLeak")
    public ProgressBar _progress;
    public Question _question;

    private Boolean _result;
    @SuppressLint("StaticFieldLeak")
    public LinearLayout _answerLayout;
    public LinearLayout.LayoutParams _lp;
    public Game _game;
    public Profile _profile;

    private AnswerInteractor _answerInteractor;

//
    public PossibleAnswerPresenter(Activity activity, Question question, ProgressBar progress, LinearLayout.LayoutParams lp, LinearLayout answerLayout, Game game, Profile profile) {
//    public PossibleAnswerPresenter(Question question, Connector connector) {

        this._activity = activity;
        this._question = question;
        this._result = false;
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

    @Override
    protected String doInBackground(String... params){
        ResultSet res = null;
        ArrayList<Answer> answers = new ArrayList<>();

        res = _answerInteractor.getAnswers(_question);
        try {
            while(res.next()) {
                String content = res.getString("answer");
                int ID = res.getInt("answerID");
                int type = res.getInt("typeID");
                int used = res.getInt("used");
                double percentageUsed = res.getDouble("percentageUsed");
                Answer answer = new Answer(ID, content, used, percentageUsed, type);
//                    answers.add(answer);
                answers.add(answer);
                _result = true;
//                    notifyAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Answer x : answers){
            Button bttn = setButtons(x.getAnswer(), x);
            publishProgress(bttn);
        }
            _question.setAnswers(answers);
        return "";
    }

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
