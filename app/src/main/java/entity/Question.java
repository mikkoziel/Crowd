package entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String _image;

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

    public Question(String question, int questionID, int type, Boolean defaultAnswer, String imagePath){
        this._question = question;
        this._questionID = questionID;
        this._type = type;
        this._answers = new ArrayList<>();
        this._index = 0;
        this._defaultAnswer = defaultAnswer;
        this._isImageQuestion = true;
        this._image = imagePath;
    }

    public String getQuestion() {
        return _question;
    }
    public int getID() {
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
    public Boolean isImageQuestion(){return _isImageQuestion;}

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
    public void setIndex(int _index) {
        this._index = _index;
    }
    public void setDefaultAnswer(Boolean _defaultAnswer) {
        this._defaultAnswer = _defaultAnswer;
    }
    public void setIsImageQuestion(Boolean isImageQuestion){this._isImageQuestion = isImageQuestion;}

    public void updateContent(ArrayList<Answer> answers, int index)
    {
        this._answers = answers;
        this._index = index;
    }

    public void updateAnswer(Answer answer)
    {
        for(Answer a: _answers)
        {
            if(a.getID() == answer.getID()) {
                a.updateContent(answer.getShowed(), answer.getChosen());
                return;
            }

        }
    }

    public void setImage(String image){
        this._image = image;
        this._isImageQuestion = true;
    }

    public String getImage(){return _image;}

    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("_question", _question);
            object.put("_questionID", _questionID);
            object.put("_type", _type);
            object.put("_index", _index);
            object.put("_defaultAnswer", _defaultAnswer);
            object.put("_image", _image);
            object.put("_isImageQuestion", _isImageQuestion);
            JSONArray answers = new JSONArray();
            for(Answer answer: _answers){
                answers.put(answer.toJson());
            }
            object.put("_answers", answers);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}

