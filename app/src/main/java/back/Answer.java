package back;

import java.io.Serializable;

public class Answer implements Serializable {
    public int answerID;
    public String answer;
    private int used;
    private double percentageUsed;

    public Answer(int answerID, String answer){
        this.answerID = answerID;
        this.answer = answer;
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
}
