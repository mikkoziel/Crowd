package back;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.crowd1.MainActivity;
import com.app.crowd1.MenuActivity;
import com.app.crowd1.QuestionActivity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Game implements Serializable {
    private int gameID;
    public String gameName;
    public ArrayList<Question> questions;
    public Boolean played;
    public int index;

    public Game(int ID, String gameName){
        this.gameID = ID;
        this.gameName = gameName;
        this.questions = new ArrayList<>();
        this.played = false;
        this.index = 0;
    }

    public int getGameID() {
        return gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public Boolean getPlayed() {
        return played;
    }

    public int getIndex() {
        return index;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setPlayed(Boolean tmp) {
        this.played = tmp;
    }

    public void addQuestion(Question question){
        this.questions.add(question);
    }

    public void nextIndex(){this.index ++;}

    public void prevIndex(){this.index --;}

//    public String setQuestions(ResultSet res) throws SQLException {
//        String z = "";
//
////        Connection con = connector.connectionClass();
////        if (con == null) {
////            z = "Check Your Internet Access!";
////        }
////        else {
////            String query = "select * from Question where gameID = " + gameID + ";";
////            ResultSet res = connector.runQuery(query, con);
//            while(res.next()){
//                int type = res.getInt("typeID");
//                Question question = new Question(res.getString("question"), res.getInt("gameID"));
//                question.setType(type);
//                questions.add(question);
//            }
//      //  }
//        return z;
//    }


}
