package presenter;

import android.os.AsyncTask;

import java.sql.SQLException;

import entity.GivenAnswer;
import interactor.GivenAnswerInteractor;

public class GivenAnswerPresenter extends AsyncTask<Void, Void, Void> {
    private GivenAnswer _givenAnswer;
    private GivenAnswerInteractor _givenAnswerInteractor;

    public GivenAnswerPresenter(GivenAnswer given){
        this._givenAnswer = given;
        this._givenAnswerInteractor = new GivenAnswerInteractor();
    }

    @Override
    protected void onPreExecute(){
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            _givenAnswerInteractor.logAnswer(_givenAnswer);
            _givenAnswerInteractor.updateAnswerChosenValue(_givenAnswer.getAnswer());
            _givenAnswerInteractor.givePoints(_givenAnswer.getAnswer(), _givenAnswer.getProfile());
            _givenAnswerInteractor.updateLevel(_givenAnswer.getProfile());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        _givenAnswerInteractor.endWork();
    }
}
