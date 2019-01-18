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
    private Connection _connection;

    public DataBaseConnector(){
        this._connection = establishConnection();
    }

    @SuppressLint("NewApi")
    private Connection establishConnection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String _user = "crowd";
        String _password = "Ng65JF4j79-!";
        String _database = "crowd";
        String _ip = "den1.mssql8.gear.host";
        String _nameClass = "net.sourceforge.jtds.jdbc.Driver";
        String _connectionURL = "jdbc:jtds:sqlserver;//" + _ip + "/" + _database + ";user=" + _user + ";password=" + _password + ";";

        try{
            Class.forName(_nameClass);
            _connection = DriverManager.getConnection(_connectionURL);
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
        return _connection;
    }

    public ResultSet runQuery(String query){
        Statement stmt;
        ResultSet result = null;

        if(_connection == null)
            _connection = establishConnection();

        try {
            stmt = _connection.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int updateQuery(String query){

        if(_connection == null)
            _connection = establishConnection();

        Statement stmt;
        int result = 0;
        try {
            stmt = _connection.createStatement();
            result = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void closeConnection() throws SQLException
    {
        _connection.close();
    }
}
