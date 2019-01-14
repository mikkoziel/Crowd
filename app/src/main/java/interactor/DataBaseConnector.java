package interactor;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnector implements Serializable {
    private String _user = "crowd";
    private String _password = "Ng65JF4j79-!";
    private String _database = "crowd";
    private String _ip = "den1.mssql8.gear.host";
    private String _nameClass = "net.sourceforge.jtds.jdbc.Driver";
    private String _connectionURL = "jdbc:jtds:sqlserver;//" + _ip + "/" + _database + ";user=" + _user + ";password=" + _password + ";";
    //    private Connection connection;
    private Boolean _isSuccess;
    private String _result;

    public DataBaseConnector(){
        this._isSuccess = false;
        this._result = "";
        establishConnection();
    }

    public Boolean getSuccess() {
        return _isSuccess;
    }

    public String getResult() {
        return _result;
    }

    public void setResult(String result){_result = result;}
    public void success(Boolean isSuccess){_isSuccess = isSuccess;};


    @SuppressLint("NewApi")
    private Connection establishConnection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
//        this.connection = null;
        try{
            Class.forName(_nameClass);
//            this.connection = DriverManager.getConnection(connectionURL);
            connection = DriverManager.getConnection(_connectionURL);
        }
        catch(SQLException e){
            Log.e("error here 1; ", e.getMessage());
        }
        catch(ClassNotFoundException e){
            Log.e("error here 2; ", e.getMessage());
        }
        catch(Exception e){
            Log.e("error here 1; ", e.getMessage());
        }
        if(connection == null){
            _result = "Check Your Internet Access!";
        }
//        return connection;
        return connection;
    }

    public ResultSet runQuery(String query, Connection connection){
        Statement stmt;
        ResultSet result = null;
        try {
            stmt = connection.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int updateQuery(String query, Connection connection){
        Statement stmt;
        int result = 0;
        try {
            stmt = connection.createStatement();
            result = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public Boolean checkConnection(Connection connection){
        Boolean res = false;
        if (connection != null) {
            res = true;
        }
        else{
            _result = "Check Your Internet Access!";
        }
        return res;
    }

    public Connection makeConnection(){
        Connection connection = null;
//        if (connection == null) {
//            result = "Check Your Internet Access!";
        try {
            connection = establishConnection();
        } catch (Exception e) {
            this._isSuccess = false;
            this._result = e.getMessage();
        }
//        }
        return connection;
    }
}
