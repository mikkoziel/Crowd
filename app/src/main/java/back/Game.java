package back;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Game {
    private int gameID;
    public String gameName;
    public ArrayList<Question> questions;

    public Game(int ID, String gameName){
        this.gameID = ID;
        this.gameName = gameName;
        this.questions = new ArrayList<>();
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getGameID() {
        return gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setQuestions(Connector connector) throws SQLException {
        String z;
        Boolean isSuccess = false;

        Connection con = connector.connectionClass();
        if (con == null) {
            z = "Check Your Internet Access!";
        }
        else {
            String query = "select * from Question where gameID = " + gameID + ";";
            ResultSet res = connector.runQuery(query, con);
            if(res.next()){
//                z = "Login succesful";
//                isSuccess = true;
                con.close();
            }
            else{
//                z = "Inwalid Credentils!";
//                isSuccess = false;
            }
        }
    }
}
