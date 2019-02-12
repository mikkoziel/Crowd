package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.sql.SQLException;

import appView.EndGameActivity;
import appView.QuestionActivity;
import entity.Answer;
import entity.AppContent;
import entity.GivenAnswer;
import entity.Profile;
import entity.Question;
import interactor.NewAnswerInteractor;

public class NewAnswerPresenter extends AsyncTask<Void, Button, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private String _answer;
    private Question _question;
    private AppContent _appContent;
    @SuppressLint("StaticFieldLeak")
    private Button _bttn;
    private int _mode;
    private Answer _answerDB;

    private NewAnswerInteractor _newAnswerInteractor;
    private JsonPresenter _jsonPresenter;

    public NewAnswerPresenter(Activity activity, String answer, Question question, AppContent appContent, Button bttn, int mode){
        this._activity = activity;
        this._answer = answer;
        this._question = question;
        this._appContent = appContent;
        this._bttn = bttn;
        this._mode = mode;
        this._newAnswerInteractor = new NewAnswerInteractor();
        this._jsonPresenter = new JsonPresenter(_activity);
    }


    @Override
    protected void onPreExecute(){
        _bttn.setClickable(false);

    }

    @Override
    protected Void doInBackground(Void... voids){
        this._answerDB = _newAnswerInteractor.createNewAnswer(_answer, _question.getID());
        return null;
    }


    @Override
    protected void onPostExecute(Void voids){
        Intent intent = null;
        switch(_mode){
            case 0:
                intent = new Intent(_activity, QuestionActivity.class);
                break;
            case 1:
                intent = new Intent(_activity, EndGameActivity.class);
                break;

        }
        GivenAnswer given = new GivenAnswer(_appContent.getProfile(), _question, _answerDB);
        intent.putExtra("answer", given);
//        intent.putExtra("appContent", _appContent);
        _jsonPresenter.writeToJson(_appContent, 1);
        _activity.startActivity(intent);
        _bttn.setClickable(true);
    }
}
