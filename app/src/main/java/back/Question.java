package back;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Question implements Serializable{

    public String question;
    private int questionID;
    public int type;
    public ArrayList<Answer> answers;
    public int index;

    public Question(String question, int questionID, int type){
        this.question = question;
        this.questionID = questionID;
        this.type = type;
        this.answers = new ArrayList<>();
        this.index = 0;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionID() {
        return questionID;
    }

    public int getType() {
        return type;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public int getIndex() {
        return index;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public void nextIndex(){this.index ++;}

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

