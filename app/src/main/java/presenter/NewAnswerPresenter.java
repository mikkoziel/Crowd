package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.sql.SQLException;

import interactor.NewAnswerInteractor;

public class NewAnswerPresenter extends AsyncTask<Void, Button, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private String _answer;
    private int _questionID;

    private NewAnswerInteractor _newAnswerInteractor;

    public NewAnswerPresenter(Activity activity, String answer, int questionID){
        this._activity = activity;
        this._answer = answer;
        this._questionID = questionID;
        this._newAnswerInteractor = new NewAnswerInteractor();
    }


    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids){
        _newAnswerInteractor.createNewAnswer(_answer, _questionID);
        return null;
    }


    @Override
    protected void onPostExecute(Void voids){
        _progress.setVisibility(View.GONE);
    }
}
