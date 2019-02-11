package entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public int getID() {
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
    public int getShowed(){return _showed;}
    public int getChosen(){return _chosen;}

    public void setChosen(int chosen){_chosen  = chosen;}
    public void setShowed(int showed){_showed = showed;}
    public void increaseChosen(){_chosen++;}
    public void increaseShowed(){_showed++;}
    public void setIsImageAnswer(Boolean _isImageAnswer) {
        this._isImageAnswer = _isImageAnswer;
    }

    public void updateContent(int showed, int chosen)
    {
        this._showed = showed;
        this._chosen = chosen;
    }

    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("_answerID", _answerID);
            object.put("_answer", _answer);
            object.put("_type", _type);
            object.put("_defaultAnswer", _defaultAnswer);
            object.put("_isImageAnswer", _isImageAnswer);

            JSONArray image = new JSONArray();
            for(byte x: _image){
                image.put(x);
            }
            object.put("_image", image);

            object.put("_showed", _showed);
            object.put("_chosen", _chosen);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
