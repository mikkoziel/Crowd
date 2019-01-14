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

public class RegistrationPresenter extends AsyncTask<String, String, String> {

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
    protected String doInBackground(String... params){
        String result = "";
        if(_username.trim().equals("")|| _password.trim().equals("")){
            result = "Please enter Username and Password";
        }
        else{
            try {
                result = _profileInteractor.registerLogin(_username, _password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    @Override
    protected void onPostExecute(String r){
        _progress.setVisibility(View.GONE);
        Toast.makeText(_activity, r, Toast.LENGTH_SHORT).show();
        if(_profileInteractor.getSuccess()){
            Toast.makeText(_activity, "Login Registration Successful", Toast.LENGTH_LONG).show();
        }
    }


}
