package Presenter;

import java.io.Serializable;

import Entity.Answer;
import Entity.Profil;
import Entity.Question;

public class GivenAnswer implements Serializable{

    public Profil profil;
    public Question question;
    public Answer answer;

    public GivenAnswer(Profil profil, Question question, Answer answer){
        this.profil = profil;
        this.question = question;
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }
}
