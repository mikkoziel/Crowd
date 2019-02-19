package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.SQLException;

import interactor.ProfileInteractor;
import tools.InternetChecker;

public class RegistrationPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private String _username;
    private String _password;
    private ProfileInteractor _profileInteractor;
    @SuppressLint("StaticFieldLeak")
    private Button _submit;
    @SuppressLint("StaticFieldLeak")
    private Button _register;

    public RegistrationPresenter(Activity activity, ProgressBar progress, EditText loginT, EditText passwordT, Button submit, Button register){
        this._activity = activity;
        this._progress = progress;
        this._username = loginT.getText().toString();
        this._password = passwordT.getText().toString();
        this._profileInteractor = new ProfileInteractor();
        this._submit = submit;
        this._register = register;
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
        InternetChecker internetChecker = new InternetChecker(_activity);
        if(!internetChecker.isOnline()){
            this.cancel(true);
        }
    }

    @Override
    protected Void doInBackground(Void... voids){
        if(_profileInteractor.userCredentialsFilled(_username, _password))
        {
            try {
                _profileInteractor.registerLogin(_username, _password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids){
        _progress.setVisibility(View.GONE);
        _submit.setClickable(true);
        _register.setClickable(true);
        String result = _profileInteractor.getResult();
        Toast.makeText(_activity, result, Toast.LENGTH_LONG).show();
        _profileInteractor.endWork();
    }


}
