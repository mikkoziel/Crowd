package presenter;

import android.os.AsyncTask;
import android.widget.Button;

import entity.GivenAnswer;
import interactor.AnswerInteractor;

// void void void ?
public class GivenAnswerPresenter extends AsyncTask<String, Button, String> {
    private int _profileID;
    private int _questionID;
    private int _answerID;

    private AnswerInteractor _answerInteractor;

    public GivenAnswerPresenter(GivenAnswer given){
        this._profileID = given.getProfile().getID();
        this._questionID = given.getQuestion().getQuestionID();
        this._answerID = given.getAnswer().getAnswerID();
        this._answerInteractor = new AnswerInteractor();
    }

    @Override
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... strings) {
        _answerInteractor.logAnswer(_profileID, _questionID, _answerID);
        return "";
    }

    @Override
    protected void onPostExecute(String r) {
    }
}
