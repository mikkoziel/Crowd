package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private int _gameID;
    private String _gameName;
    private ArrayList<Question> _questions;
    private Boolean _played;
    private int _index;

    public Game(int ID, String gameName){
        this._gameID = ID;
        this._gameName = gameName;
        this._questions = new ArrayList<>();
        this._played = false;
        this._index = 0;
    }

    public int getGameID() {
        return _gameID;
    }

    public String getGameName() {
        return _gameName;
    }

    public ArrayList<Question> getQuestions() {
        return _questions;
    }

    public Boolean getPlayed() {
        return _played;
    }

    public int getIndex() {
        return _index;
    }

    public void setGameID(int gameID) {
        this._gameID = gameID;
    }

    public void setGameName(String gameName) {
        this._gameName = gameName;
    }

    public void setPlayed(Boolean tmp) {
        this._played = tmp;
    }

    public void addQuestion(Question question){
        this._questions.add(question);
    }

    public void nextIndex(){this._index ++;}

    public void prevIndex(){this._index --;}

    public void zeroIndex(){this._index = 0;}

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
