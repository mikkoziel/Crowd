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
import entity.AppContent;
import entity.GivenAnswer;
import entity.Game;
import entity.GlobalClass;
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
    private Profile _profile;
    private Question _question;

    private PossibleAnswerInteractor _possibleAnswerInteractor;
    private JsonPresenter _jsonPresenter;

    public PossibleAnswerPresenter(Activity activity,
                                   Intent intent,
                                   ProgressBar progress,
                                   LinearLayout.LayoutParams lp,
                                   AppContent appContent) {
        this._activity = activity;
        this._intent = intent;
        this._progress = progress;
        this._lp = lp;
        this._row1 = _activity.findViewById(appView.R.id.row1);
        this._row2 = _activity.findViewById(appView.R.id.row2);
        this._row3 = _activity.findViewById(appView.R.id.row3);

        this._appContent = appContent;
        this._game = appContent.getCurrentGame();
        this._profile = appContent.getProfile();
//        this._question = _game.getCurrentQueston();
        _game.nextIndex();

        this._possibleAnswerInteractor = new PossibleAnswerInteractor();
        this._jsonPresenter = new JsonPresenter(_activity);
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids){
        for(Question question: _game.getQuestions()) {
            this._question = question;
            try {
                _question.getAnswers().clear();
                _possibleAnswerInteractor.setPossibleAnswers(_question);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//        _i = 0;
//
//        for(Answer answer : _question.getAnswers()){
//            Button button = setButtons(answer.getAnswer(), answer);
//            publishProgress(button);
//        }
        }
        return null;
    }

//    @Override
//    protected void onProgressUpdate(Button... answer) {
//        if(_i < 2){
//            _row1.addView(answer[0], _lp);
//        }else{
//            if(_i < 4){
//                _row2.addView(answer[0], _lp);
//            }
//            else{
//                _row3.addView(answer[0], _lp);
//            }
//        }
//        _i +=1;
//    }

    @Override
    protected void onPostExecute(Void voids){
        _possibleAnswerInteractor.endWork();
        _progress.setVisibility(View.GONE);
        if(_possibleAnswerInteractor.isSuccess()){
//            _intent.putExtra("appContent", _appContent);
//            GlobalClass global = ((GlobalClass) _activity.getApplicationContext());
//            global.setAppContent(_appContent);
            _jsonPresenter.writeToJson(_appContent, 1);
            GlobalClass.getInstance().setAppContent(_appContent);
            _appContent = null;
            System.gc();
            //_intent.putExtra("game", _game);
            _activity.startActivity(_intent);
        }
    }

    private Button setButtons(String answerText, final Answer a){
        Button answer = new Button(_activity);

        answer.setText(String.format("\n%s\n", answerText));
        if(a.isImageAnswer()){
            Drawable image = new BitmapDrawable(_activity.getResources(), BitmapFactory.decodeByteArray(a.getImage(), 0, a.getImage().length));
            answer.setCompoundDrawablesWithIntrinsicBounds( image, null, null, null);
        }

        _game.updateQuestion(_question);
        _appContent.updateGame(_game);

        if(_game.getIndex() < _game.getQuestions().size()) {
            answer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(_activity, QuestionActivity.class);
                    GivenAnswer given = new GivenAnswer(_profile, _question, a);
                    intent.putExtra("answer", given);
//                    intent.putExtra("appContent", _appContent);
                    GlobalClass.getInstance().setAppContent(_appContent);
                    _jsonPresenter.writeToJson(_appContent, 1);
                    _appContent = null;
                    System.gc();
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
//                    intent.putExtra("appContent", _appContent);
                    GlobalClass.getInstance().setAppContent(_appContent);
                    _jsonPresenter.writeToJson(_appContent, 1);
                    _appContent.destroy();
                    _appContent = null;
                    System.gc();
                    _activity.startActivity(intent);
                }
            });
        }
        return answer;
    }
    
}
