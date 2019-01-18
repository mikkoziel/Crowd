package interactor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Profile;

public class ProfileInteractor {
    private DataBaseConnector _dbConnector;
    private Connection _connection;
    private String _result;
    private Boolean _isSuccess;

    public ProfileInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._connection = _dbConnector.makeConnection();
        this._result = null;
        this._isSuccess = false;
    }

    public Boolean userCredentialsFilled(String username, String password)
    {
        if(username.trim().equals("")|| password.trim().equals("")) {
            setFailure("Please enter Username and Password");
            return false;
        }
        else
            return true;
    }

    public void registerLogin(String username, String password) throws SQLException {
        if(!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();

        ResultSet resultSet = getLogin(username);
        if (resultSet.next())
            setFailure("Login already exist");
        else {
            String query1 = "Insert into Profile(Name, Password, Points, Userlevel) values('" + username + "', '" + password + "', 0, 0)";
                int result = _dbConnector.updateQuery(query1, _connection);
                if(result > 0)
                    setSuccess("Login registration successful");
                else
                    setFailure("Login registration failed");
            }
    }

    private ResultSet getLogin(String username){
        String query = "select * from Profile where Name= '" + username + "'";
        return _dbConnector.runQuery(query, _connection);
    }


    public ResultSet checkLogin(String username, String password) throws SQLException {
        if(!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();

        ResultSet resultSet = getLogin(username);
        if (resultSet.next()) {
            if (resultSet.getString("password").equals(password))
                setSuccess("Login Successful");
            else
                setFailure("Invalid Credentials!");
        }
        else
            setFailure("This profile doesn't exist");
        return resultSet;
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

    private void setSuccess(String message)
    {
        _result = message;
        _isSuccess = true;
    }

    private void setFailure(String message)
    {
        _result = message;
        _isSuccess = false;
    }

    public Boolean isSuccess(){return _isSuccess;}
    public String getResult(){return _result;}

    public void endWork() throws SQLException
    {
        _connection.close();
    }



}
