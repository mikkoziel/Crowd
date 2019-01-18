package presenter;

import android.os.AsyncTask;

import java.sql.SQLException;

import entity.GivenAnswer;
import interactor.GivenAnswerInteractor;

public class GivenAnswerPresenter extends AsyncTask<Void, Void, Void> {
    private int _profileID;
    private int _questionID;
    private int _answerID;

    private GivenAnswerInteractor _givenAnswerInteractor;

    public GivenAnswerPresenter(GivenAnswer given){
        this._profileID = given.getProfile().getID();
        this._questionID = given.getQuestion().getQuestionID();
        this._answerID = given.getAnswer().getAnswerID();
        this._givenAnswerInteractor = new GivenAnswerInteractor();
    }

    @Override
    protected void onPreExecute(){
    }

    @Override
    protected Void doInBackground(Void... voids) {
        _givenAnswerInteractor.logAnswer(_profileID, _questionID, _answerID);
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        _givenAnswerInteractor.endWork();
    }
}
