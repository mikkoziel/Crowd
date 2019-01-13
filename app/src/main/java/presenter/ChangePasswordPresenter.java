package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Profile;
import interactor.ProfileInteractor;

public class ChangePasswordPresenter extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private String _username;
    private int _userID;
    private String _password;
    private String _passwordCheck;
    private String _passwordCheck2;
    private String _result = "";
    private Boolean _isSuccess = false;
    private int _mode;
    private int _semaphore;
    private ProfileInteractor _profileInteractor;

    public ChangePasswordPresenter(Activity activity, ProgressBar progress, Profile profile, String password, int mode){
        this._activity = activity;
        this._progress = progress;
        this._username = profile.getName();
        this._password = password;
        this._userID = profile.getID();
        this._mode = mode;
        this._semaphore = 4;
        this._profileInteractor = new ProfileInteractor();
    }

    @Override
    protected void onPreExecute(){
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            switch (_mode) {
                case 0:
                    _profileInteractor.modeCheckOld(_userID, _username, _password, _semaphore, _passwordCheck, _passwordCheck2);
//                  semafor = 1;
                    return "";  //?? return? a nie break?
                case 1:
                    _profileInteractor.modeChangeToNew(_password, _userID);
//                  semafor =1;
                    return "";
                }
            if(!_profileInteractor.getSuccess())
            {
                Toast.makeText(_activity, "Wrong Old Password", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Boolean oldOK;

//        try {
//            if (isConnect) {
//                oldOK = checkOld(connection, query1);
//
//                if(oldOK){
//                    changeToNew(connection, query2);
//                }
//                else{
//                    Toast.makeText(activity, "Wrong Old Password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return "";
    }

    @Override
    protected void onPostExecute(String r){
        _progress.setVisibility(View.GONE);
        Toast.makeText(_activity, _result, Toast.LENGTH_SHORT).show();
        if(_profileInteractor.getSuccess()){
            Toast.makeText(_activity, "Password Change OK", Toast.LENGTH_LONG).show();
        }
    }





//    private Boolean checkOld(Connection connection, String query) throws SQLException {
//        ResultSet res = connector.runQuery(query, connection);
//        if (res.next()) {
//            String name = res.getString("name");
//            if(username.equals(name)){
//                String oldPasswordRes = res.getString("password");
//                if(password.equals(oldPasswordRes)){
//                    return true;
//                }
//            }
//        }
//        return false;
//
//    }
//
//    private void changeToNew(Connection connection, String query){
//        int res = -1;
//        res = connector.updateQuery(query, connection);
//        if(res > 0){
//            result = "Success";
//            isSuccess = true;
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        else{
//            result = "Fail";
//            isSuccess = false;
//        }
//    }

    public Boolean getIsSuccess() {
        return _profileInteractor.getSuccess();
    }

    public int getSemaphore(){
        return _semaphore;
    }

    public void setPasswordCheck(String passwordCheck) {
        this._passwordCheck = passwordCheck;
    }

    public void setPasswordCheck2(String passwordCheck2) {
        this._passwordCheck2 = passwordCheck2;
    }
}
