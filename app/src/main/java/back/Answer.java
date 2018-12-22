package back;

import java.io.Serializable;

public class Answer implements Serializable {
    public int answerID;
    public String answer;
    private int used;
    private double percentageUsed;
    public int type;

    public Answer(int answerID, String answer){
        this.answerID = answerID;
        this.answer = answer;
    }

    public Answer(int answerID, String answer, int used, double percentageUsed, int type){
        this.answerID = answerID;
        this.answer = answer;
        this.used = used;
        this.percentageUsed = percentageUsed;
        this.type = type;
    }

    public int getAnswerID() {
        return answerID;
    }

    public String getAnswer() {
        return answer;
    }

    public int getUsed() {
        return used;
    }

    public double getPercentageUsed() {
        return percentageUsed;
    }

    public int getType() {
        return type;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public void setPercentageUsed(double percentageUsed) {
        this.percentageUsed = percentageUsed;
    }

    public void setType(int type) {
        this.type = type;
    }
}
