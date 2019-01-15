package presenter;

import android.os.AsyncTask;
import android.widget.Button;

import entity.GivenAnswer;
import interactor.AnswerInteractor;

// void void void ?
public class GivenAnswerPresenter extends AsyncTask<Void, Void, Void> {
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
    protected Void doInBackground(Void... voids) {
        _answerInteractor.logAnswer(_profileID, _questionID, _answerID);
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
    }
}
