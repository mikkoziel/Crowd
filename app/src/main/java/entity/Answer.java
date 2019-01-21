package entity;

import java.io.Serializable;

public class Answer implements Serializable {
    private int _answerID;
    private String _answer;
    private int _type;
    private Boolean _defaultAnswer;
    private Boolean _isImageAnswer;
    private byte[] _image;
    private int _showed;
    private int _chosen;

    public Answer(int answerID, String answer){
        this._answerID = answerID;
        this._answer = answer;
    }

    public Answer(int answerID, String answer, int type, Boolean defaultAnswer, int showed, int chosen){
        this._answerID = answerID;
        this._answer = answer;
        this._type = type;
        this._defaultAnswer = defaultAnswer;
        this._image = null;
        this._isImageAnswer = false;
        this._showed = showed;
        this._chosen = chosen;
    }

    public Answer(int answerID, String answer, int type, Boolean defaultAnswer, byte[] image,  int showed, int chosen){
        this._answerID = answerID;
        this._answer = answer;
        this._type = type;
        this._defaultAnswer = defaultAnswer;
        this._image = image;
        this._isImageAnswer = true;
        this._showed = showed;
        this._chosen = chosen;

    }

    public int getAnswerID() {
        return _answerID;
    }

    public String getAnswer() {
        return _answer;
    }

    public int getType() {
        return _type;
    }

    public Boolean getDefaultAnswer() {
        return _defaultAnswer;
    }

    public Boolean isImageAnswer(){return _isImageAnswer;}

    public byte[] getImage(){return _image;}

    public void increaseShowed(){_showed++;}

    public int getShowed(){return _showed;}

    public void increaseChosen(){_chosen++;}

    public int getChosen(){return _chosen;}
}
