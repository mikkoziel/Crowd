package entity;

import java.io.Serializable;

public class Answer implements Serializable {
    private int _answerID;
    private String _answer;
    private int _used;
    private double _percentageUsed;
    private int _type;

    public Answer(int answerID, String answer){
        this._answerID = answerID;
        this._answer = answer;
    }

    public Answer(int answerID, String answer, int used, double percentageUsed, int type){
        this._answerID = answerID;
        this._answer = answer;
        this._used = used;
        this._percentageUsed = percentageUsed;
        this._type = type;
    }

    public int getAnswerID() {
        return _answerID;
    }

    public String getAnswer() {
        return _answer;
    }

    public int getUsed() {
        return _used;
    }

    public double getPercentageUsed() {
        return _percentageUsed;
    }

    public int getType() {
        return _type;
    }

    public void setAnswerID(int answerID) {
        this._answerID = answerID;
    }

    public void setAnswer(String answer) {
        this._answer = answer;
    }

    public void setUsed(int used) {
        this._used = used;
    }

    public void setPercentageUsed(double percentageUsed) {
        this._percentageUsed = percentageUsed;
    }

    public void setType(int type) {
        this._type = type;
    }
}
