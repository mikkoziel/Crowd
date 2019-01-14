package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable{

    private String _question;
    private int _questionID;
    private int _type;
    private ArrayList<Answer> _answers;
    private int _index;

    public Question(String question, int questionID, int type){
        this._question = question;
        this._questionID = questionID;
        this._type = type;
        this._answers = new ArrayList<>();
        this._index = 0;
    }

    public Question()
    {
        this._question = "";
        this._questionID = 0;
        this._type = 0;
        this._answers = null;
        this._index = 0;
    }

    public String getQuestion() {
        return _question;
    }

    public int getQuestionID() {
        return _questionID;
    }

    public int getType() {
        return _type;
    }

    public ArrayList<Answer> getAnswers() {
        return _answers;
    }

    public int getIndex() {
        return _index;
    }

    public void setQuestion(String question) {
        this._question = question;
    }

    public void setQuestionID(int questionID) {
        this._questionID = questionID;
    }

    public void setType(int type) {
        this._type = type;
    }

    public void addAnswer(Answer answer){
        _answers.add(answer);
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this._answers = answers;
    }

    public void nextIndex(){this._index ++;}

//    public void setAnswers(ResultSet res) throws SQLException {
////        String z;
////        Boolean isSuccess = false;
////
////        Connection con = connector.connectionClass();
////        if (con == null) {
////            z = "Check Your Internet Access!";
////        }
////        else {
////            String query = "select * from Answer where questionID = " + questionID + "; ORDER BY RAND() LIMIT 3";
////            String query = "select * from Answer where questionID = " + questionID + ";";
////            ResultSet res = connector.runQuery(query, con);
//            while(res.next()){
//                int type = res.getInt("typeID");
//                Answer answer = new Answer(res.getInt("answerID"), res.getString("answer"));
//                answer.setType(type);
//                answers.add(answer);
//            }
//          //  con.close();
//       // }
//    }
}

