package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.SQLException;

import interactor.ProfileInteractor;

public class RegistrationPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private String _username;
    private String _password;
    private ProfileInteractor _profileInteractor;

    public RegistrationPresenter(Activity activity, ProgressBar progress, EditText loginT, EditText passwordT){
        this._activity = activity;
        this._progress = progress;
        this._username = loginT.getText().toString();
        this._password = passwordT.getText().toString();
        this._profileInteractor = new ProfileInteractor();
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids){
        if(_profileInteractor.userCredentialsFilled(_username, _password))
        {
            try {
                _profileInteractor.registerLogin(_username, _password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids){
        _progress.setVisibility(View.GONE);
        String result = _profileInteractor.getResult();
        Toast.makeText(_activity, result, Toast.LENGTH_LONG).show();
    }


}
