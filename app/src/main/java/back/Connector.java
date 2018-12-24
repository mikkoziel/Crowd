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
import java.util.ArrayList;

import gui.AnswerSetter;

public class Connector implements Serializable {

    private String user = "mkoziel";
    public String password = "3w37r0APSZS9LoDy";
    private String database = "mkoziel";
    private String ip = "mysql.agh.edu.pl";
    public String port = "3306";
    private String nameClass = "net.sourceforge.jtds.jdbc.Driver";
//    private String connectionURL = "jdbc:jtds:sqlserver;//" + ip + ":" + port + "/" + database + ";user=" + user + ";password=" + password + ";";
    private String connectionURL = "jdbc:jtds:sqlserver://" + ip + "/" + database + ";user=" + user + ";password=" + password + ";";

//    private String user = "crowd";
//    private String password = "Ng65JF4j79-!";
//    private String database = "crowd";
//    private String ip = "den1.mssql8.gear.host";
//    private String nameClass = "net.sourceforge.jtds.jdbc.Driver";
//    private String connectionURL = "jdbc:jtds:sqlserver;//" + ip + "/" + database + ";user=" + user + ";password=" + password + ";";
//    private Connection connection;
    public Boolean isSuccess;
    private String result;

    public Connector(){
        this.isSuccess = false;
        this.result = "";
        establishConnection();
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getResult() {
        return result;
    }


//    public Connection getConnection() {
//        return connection;
//    }

    @SuppressLint("NewApi")
    public Connection establishConnection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
//        this.connection = null;
        try{
            Class.forName(nameClass);
//            this.connection = DriverManager.getConnection(connectionURL);
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
        if(connection == null){
            result = "Check Your Internet Access!";
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
//            result = "Check Your Internet Access!";
//            try {
//                connection = establishConnection();
//            } catch (Exception e) {
//                this.isSuccess = false;
//                this.result = e.getMessage();
//            }
//            if (connection == null) {
//                result = "Check Your Internet Access!";
//            }
//            else{
//                res = true;
//            }
            res = true;
        }
        else{
            result = "Check Your Internet Access!";
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
                this.isSuccess = false;
                this.result = e.getMessage();
            }
//        }
        return connection;
    }

    private ResultSet getLogin(String username, Connection connection){
        String query = "select * from Profil where Name= '" + username + "'";
        ResultSet res = runQuery(query, connection);
        return res;
    }

    public ResultSet checkLogin(String username, String password) throws SQLException {
        ResultSet res = null;
//        Connection connection = null;
        Connection connection = makeConnection();
        Boolean isConnect = checkConnection(connection);

        if(isConnect) {

//            try {
//                Connection con = connectionClass();
//                if (con == null) {
//                    result = "Check Your Internet Access!";
//                }
//                else{
//            String query = "select * from Profil where Name= '" + username + "' and password = '" + password + "'";
//            res = runQuery(query, connection);
            res = getLogin(username, connection);
            if (res.next()) {
                if(res.getString("password") == password) {
                    isSuccess = true;
                }
                else{
                    result = "Invalid Credentils!";
                    isSuccess = false;
                }
//                        setMenu(res);
//                        setGames();
//                        con.close();
            } else {
                result = "This profil doesn't exist";
                isSuccess = false;
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

    public Profil setMenu(ResultSet res) throws SQLException {
        int id = res.getInt("profilID");
        String name = res.getString("name");
        String points = res.getString("points");
        Profil profil = new Profil(id, name, points);
//        intent.putExtra("profil", profil);
        result = "Login succesful";
        isSuccess = true;

        return profil;
    }

    public void setGames(Profil profil) throws SQLException {
//        ArrayList<Game> games = new ArrayList<>();
        // Game[] games = ;
        String query = "select * from Game";
//        Connection connection = null;
        Connection connection = makeConnection();
        Boolean isConnect = checkConnection(connection);

        if(isConnect) {
            ResultSet res = runQuery(query, connection);
            while (res.next()) {
                Game game = new Game(res.getInt("gameID"), res.getString("gameName"));
                profil.addGames(game);
//            intent.putExtra("games", games);
            }
        }
    }

    public String registerLogin(String username, String password) throws SQLException {
        ResultSet res = null;
        Connection connection = makeConnection();
        Boolean isConnect = checkConnection(connection);

        if (isConnect) {
            res = getLogin(username, connection);
            if (res.next()) {
                result = "Login already exist";
                isSuccess = false;
            }
            else{
                result = "Inwalid Credentils!";
                String query1 = "Insert into Profil(Name, Password, Points) values('" + username + "', '" + password + "', 0)";
                int res1 = updateQuery(query1, connection);
                if(res1 > 0){
                    result = "Success";
                    isSuccess = true;
                    connection.close();
                }
                else{
                    result = "Fail";
                    isSuccess = false;
                }
            }
        }

        return result;
    }

    private ResultSet getQuestions(Game game, Connection connection){
        String query = "select * from Question where gameID = " + game.getGameID() + ";";
        ResultSet res = runQuery(query, connection);
        return res;
    }

    public String setQuestions(Game game) throws SQLException {
        ResultSet res = null;
        Connection connection = makeConnection();
        Boolean isConnect = checkConnection(connection);

        if (isConnect) {
            res = getQuestions(game, connection);

            while(res.next()) {
                String content = res.getString("question");
                int ID = res.getInt("questionID");
                int type = res.getInt("typeID");

                Question question = new Question(content, ID, type);

//                AnswerSetter answerSetter = new AnswerSetter(question, this);
//                answerSetter.execute("");

                game.addQuestion(question);
            }
            result = "Game Starting";
            isSuccess = true;
        }
        else{
            result = "Something went wrong";
            isSuccess = false;
        }

        return result;
    }

}
