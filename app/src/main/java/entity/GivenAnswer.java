package entity;

import java.io.Serializable;

public class GivenAnswer implements Serializable{

    public Profile _profile;
    public Question _question;
    public Answer _answer;

    public GivenAnswer(Profile profile, Question question, Answer answer){
        this._profile = profile;
        this._question = question;
        this._answer = answer;
    }

    public Question getQuestion() {
        return _question;
    }

    public Answer getAnswer() {
        return _answer;
    }

    public Profile getProfile() {
        return _profile;
    }

    public void setQuestion(Question question) {
        this._question = question;
    }

    public void setAnswer(Answer answer) {
        this._answer = answer;
    }

    public void setProfile(Profile profile) {
        this._profile = profile;
    }
}
