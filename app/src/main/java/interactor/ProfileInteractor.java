package interactor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Profile;

public class ProfileInteractor {
    private DataBaseConnector _dbConnector;

    public ProfileInteractor(DataBaseConnector dbConnector)
    {
        _dbConnector = dbConnector;
    }

    public ProfileInteractor()
    {
        _dbConnector = new DataBaseConnector();
    }

    private ResultSet getLogin(String username, Connection connection){
        String query = "select * from Profile where Name= '" + username + "'";
        ResultSet res = _dbConnector.runQuery(query, connection);
        return res;
    }

    public Profile setProfile(ResultSet res) throws SQLException {
        int id = res.getInt("profilID");
        String name = res.getString("name");
        String points = res.getString("points");
        Profile profile = new Profile(id, name, points);
//        intent.putExtra("profile", profile);
        _dbConnector.setResult("Login succesful");
        _dbConnector.success(true);

        return profile;
    }

    public Boolean isSuccess()
    {
        return _dbConnector.getSuccess();
    }

    public String getResult()
    {
        return _dbConnector.getResult();
    }


    public String registerLogin(String username, String password) throws SQLException {
        ResultSet res = null;
        Connection connection = _dbConnector.makeConnection();
        Boolean isConnect = _dbConnector.checkConnection(connection);

        if (isConnect) {
            res = getLogin(username, connection);
            if (res.next()) {
                _dbConnector.setResult("Login already exist");
                _dbConnector.success(false);
            }
            else{
                _dbConnector.setResult("Inwalid Credentils!");
                String query1 = "Insert into Profile(Name, Password, Points) values('" + username + "', '" + password + "', 0)";
                int res1 = _dbConnector.updateQuery(query1, connection);
                if(res1 > 0){
                    _dbConnector.setResult("Success");
                    _dbConnector.success(true);
                    connection.close();
                }
                else{
                    _dbConnector.setResult("Fail");
                    _dbConnector.success(false);
                }
            }
        }

        return _dbConnector.getResult();
    }


    public ResultSet checkLogin(String username, String password) throws SQLException {
        ResultSet res = null;
//        Connection connection = null;
        Connection connection = _dbConnector.makeConnection();
        Boolean isConnect = _dbConnector.checkConnection(connection);

        if(isConnect) {

//            try {
//                Connection con = connectionClass();
//                if (con == null) {
//                    result = "Check Your Internet Access!";
//                }
//                else{
//            String query = "select * from Profile where Name= '" + username + "' and password = '" + password + "'";
//            res = runQuery(query, connection);
            res = getLogin(username, connection);
            if (res.next()) {
                if(res.getString("password") == password) {
                    _dbConnector.success(true);
                }
                else{
                    _dbConnector.setResult("Invalid Credentils!");
                    _dbConnector.success(false);
                }
//                        setMenu(res);
//                        setGames();
//                        con.close();
            } else {
                _dbConnector.setResult("This profile doesn't exist");
                _dbConnector.success(false);
            }
//                }
//            }
//            catch(Exception e){
//                isSuccess = false;
//                result = e.getMessage();
//
//            }
//        }
        }

        return res;

    }


}
