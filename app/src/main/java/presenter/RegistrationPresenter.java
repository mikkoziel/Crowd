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
    private String _z = "";
    private Boolean _isSuccess = false;
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

        if(_username.trim().equals("")|| _password.trim().equals("")){
            _z = "Please enter Username and Password";
        }
        else{
            try {
                _z = _profileInteractor.registerLogin(_username, _password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            _isSuccess = _profileInteractor.getSuccess();
//            try {
//                    con = connector.connectionClass();
//                    if (con == null) {
//                        z = "Check Your Internet Access!";
//                    }
//                    else{
//                        String query = "select * from Profile where Name= '" + username + "'";
//                        ResultSet res = connector.runQuery(query);
//                        if(res.next()){
//                            z = "Login already exist";
//                            isSuccess = false;
//                        }
//                        else{
//                            z = "Inwalid Credentils!";
//                            String query1 = "Insert into Profile(Name, Password, Points) values('" + username + "', '" + password + "', 0)";
//                            ResultSet res1 = connector.runQuery(query1);
//                            if(res1.next()){
//                                z = "Success";
//                                isSuccess = true;
//                                con.close();
//                            }
//                            else{
//                                z = "Fail";
//                                isSuccess = false;
//                            }
//                        }
//                    }
//            }
//            catch(Exception e){
//                isSuccess = false;
//                z = e.getMessage();
//
//            }
        }

        return _z;
    }


    @Override
    protected void onPostExecute(String r){
        _progress.setVisibility(View.GONE);
        Toast.makeText(_activity, r, Toast.LENGTH_SHORT).show();
        if(_isSuccess){
            Toast.makeText(_activity, "Login Registration Successful", Toast.LENGTH_LONG).show();
        }
    }


}
