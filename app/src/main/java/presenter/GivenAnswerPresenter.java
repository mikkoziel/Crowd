package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import java.sql.SQLException;

import entity.GivenAnswer;
import interactor.GivenAnswerInteractor;

public class GivenAnswerPresenter extends AsyncTask<Void, Void, Void> {
    private GivenAnswer _givenAnswer;
    private GivenAnswerInteractor _givenAnswerInteractor;
    @SuppressLint("StaticFieldLeak")
    private Activity _activity;

    public GivenAnswerPresenter(GivenAnswer given, Activity activity){
        this._givenAnswer = given;
        this._givenAnswerInteractor = new GivenAnswerInteractor();
        this._activity = activity;
    }

    @Override
    protected void onPreExecute(){
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            _givenAnswerInteractor.handleGivenAnswer(_givenAnswer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if(_givenAnswerInteractor.isNewLevel()){
            createAlertDialog("NEW LEVEL", _givenAnswerInteractor.getLevelInfo());
        }
        _givenAnswerInteractor.endWork();
    }

    private void createAlertDialog(String title, String message){
        new AlertDialog.Builder(_activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}
