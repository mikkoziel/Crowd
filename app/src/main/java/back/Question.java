package back;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Question implements Serializable{

    public String question;
    public int questionID;
    public int type;
    public ArrayList<Answer> answers;

    public Question(){}

    public Question(String question, int questionID){

        this.question = question;
        this.questionID = questionID;
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

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setAnswers(ResultSet res) throws SQLException {
//        String z;
//        Boolean isSuccess = false;
//
//        Connection con = connector.connectionClass();
//        if (con == null) {
//            z = "Check Your Internet Access!";
//        }
//        else {
//            String query = "select * from Answer where questionID = " + questionID + "; ORDER BY RAND() LIMIT 3";
//            String query = "select * from Answer where questionID = " + questionID + ";";
//            ResultSet res = connector.runQuery(query, con);
            while(res.next()){
                int type = res.getInt("typeID");
                Answer answer = new Answer(res.getInt("answerID"), res.getString("answer"));
                answer.setType(type);
                answers.add(answer);
            }
          //  con.close();
       // }
    }
}

