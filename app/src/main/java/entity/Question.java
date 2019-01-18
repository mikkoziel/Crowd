package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable{

    private String _question;
    private int _questionID;
    private int _type;
    private ArrayList<Answer> _answers;
    private int _index;
    private Boolean _defaultAnswer;
    private Boolean _isImageQuestion;
    private byte[] _image;

    public Question(String question, int questionID, int type, Boolean defaultAnswer){
        this._question = question;
        this._questionID = questionID;
        this._type = type;
        this._answers = new ArrayList<>();
        this._index = 0;
        this._defaultAnswer = defaultAnswer;
        this._isImageQuestion = false;
        this._image = null;
    }

    public Question(String question, int questionID, int type, Boolean defaultAnswer, byte[] image){
        this._question = question;
        this._questionID = questionID;
        this._type = type;
        this._answers = new ArrayList<>();
        this._index = 0;
        this._defaultAnswer = defaultAnswer;
        this._isImageQuestion = true;
        this._image = image;
    }

    public Question()
    {
        this._question = "";
        this._questionID = 0;
        this._type = 0;
        this._answers = null;
        this._index = 0;
        this._defaultAnswer = false;
        this._isImageQuestion = false;
        this._image = null;
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

    public Boolean getDefaultAnswer() {
        return _defaultAnswer;
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

    public void setDefaultAnswer(Boolean _defaultAnswer) {
        this._defaultAnswer = _defaultAnswer;
    }

    public void setIsImageQuestion(Boolean isImageQuestion){this._isImageQuestion = isImageQuestion;}

    public Boolean isImageQuestion(){return _isImageQuestion;}

    public void setImage(byte[] image){this._image = image;}

    public byte[] getImage(){return _image;}
}

