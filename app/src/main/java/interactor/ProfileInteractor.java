package interactor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Profile;

public class ProfileInteractor {
    private DataBaseConnector _dbConnector;
    private Connection _connection;
    private Boolean _isConnect;

    public ProfileInteractor()
    {
        _dbConnector = new DataBaseConnector();
        _connection = _dbConnector.makeConnection();
        _isConnect = _dbConnector.checkConnection(_connection);
    }

    private ResultSet getLogin(String username, Connection connection){
        String query = "select * from Profile where Name= '" + username + "'";
        return _dbConnector.runQuery(query, connection);
    }

    public Profile setProfile(ResultSet res) throws SQLException {
        int id = res.getInt("profilID");
        String name = res.getString("name");
        String points = res.getString("points");
        Profile profile = new Profile(id, name, points);
        _dbConnector.setResult("Login successful");
        _dbConnector.success(true);

        return profile;
    }

    public void setResult(String result){_dbConnector.setResult(result);}

    public void setSuccess(Boolean success){_dbConnector.success(success);}

    public Boolean getSuccess()
    {
        return _dbConnector.getSuccess();
    }

    public String getResult()
    {
        return _dbConnector.getResult();
    }

    public void registerLogin(String username, String password) throws SQLException {
        ResultSet res;

        if (_isConnect) {
            res = getLogin(username, _connection);
            if (res.next()) {
                _dbConnector.setResult("Login already exist");
                _dbConnector.success(false);
            }
            else{
                String query1 = "Insert into Profile(Name, Password, Points, Userlevel) values('" + username + "', '" + password + "', 0, 0)";
                int res1 = _dbConnector.updateQuery(query1, _connection);
                if(res1 > 0){
                    _dbConnector.setResult("Login registration successfull");
                    _dbConnector.success(true);
                    _connection.close();
                }
                else{
                    _dbConnector.setResult("Login registration failed");
                    _dbConnector.success(false);
                }
            }
        }
    }


    public ResultSet checkLogin(String username, String password) throws SQLException {
        ResultSet res = null;

        if(_isConnect) {
            res = getLogin(username, _connection);
            if (res.next()) {
                if(res.getString("password").equals(password)) {
                    _dbConnector.setResult("Login Successful");
                    _dbConnector.success(true);
                }
                else{
                    _dbConnector.setResult("Invalid Credentils!");
                    _dbConnector.success(false);
                }
            } else {
                _dbConnector.setResult("This profile doesn't exist");
                _dbConnector.success(false);
            }
        }
        return res;
    }

    public void modeCheckOld(Profile profile, String password, String passwordCheck, String passwordCheck2) throws SQLException {
        String query = "Select * from Profile where profilID = " + profile.getID();
        ResultSet res = _dbConnector.runQuery(query, _connection);
        if (res.next()) {
            String name = res.getString("name");
            String oldPasswordRes = res.getString("password");
            if(profile.getName().equals(name) && password.equals(oldPasswordRes)){
                checkRest(passwordCheck, passwordCheck2, password);
            }
            else{
                _dbConnector.setResult("Wrong old password");
                _dbConnector.success(false);
            }
        }
    }

    private void checkRest(String passwordCheck, String passwordCheck2, String password){
        if (!passwordCheck.equals(passwordCheck2)) {
            _dbConnector.setResult("Passwords doesn't match");
            _dbConnector.success(false);
        } else {
            if (password.equals(passwordCheck)) {
                _dbConnector.setResult("New password is the same as old password");
                _dbConnector.success(false);
            } else {
                _dbConnector.setResult("Correct Data");
                _dbConnector.success(true);
            }
        }
    }

    public void modeChangeToNew(String password, Profile profile){
        String query = "Update Profile set password = '" + password + "' where profilID = " + profile.getID();
        int res;
        res = _dbConnector.updateQuery(query, _connection);
        if(res > 0){
            _dbConnector.setResult("Password Change successfull");
            _dbConnector.success(true);
            try {
                _connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            _dbConnector.setResult("Password Change failed");
            _dbConnector.success(false);
        }
    }


}
