package back;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector implements Serializable {
    public String user = "mkoziel";
    public String password = "3w37r0APSZS9LoDy";
    public String database = "mkoziel";
    public String ip = "mysql.agh.edu.pl";
    public String port = "3306";
    private String nameClass = "net.sourceforge.jtds.jdbc.Driver";
//    private String connectionURL = "jdbc:jtds:sqlserver;//" + ip + ":" + port + "/" + database + ";user=" + user + ";password=" + password + ";";
    private String connectionURL = "jdbc:jtds:sqlserver://" + ip + "/" + database + ";user=" + user + ";password=" + password + ";";

    public Connector(){}

    @SuppressLint("NewApi")
    public Connection connectionClass(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        try{
            Class.forName(nameClass);
            connection = DriverManager.getConnection(connectionURL);
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
        return connection;
    }

    public ResultSet runQuery(String query, Connection con){
        Statement stmt;
        ResultSet result = null;
        try {
            stmt = con.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
