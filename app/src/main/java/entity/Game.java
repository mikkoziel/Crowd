package entity;

import android.graphics.YuvImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private int _gameID;
    private String _gameName;
    private ArrayList<Question> _questions;
    private Boolean _played;
    private int _index;
    private ArrayList<Tag> _tags;

    public Game(int ID, String gameName){
        this._gameID = ID;
        this._gameName = gameName;
        this._questions = new ArrayList<>();
        this._played = false;
        this._index = 0;
        this._tags = new ArrayList<>();
    }

    public int getID() {
        return _gameID;
    }
    public String getName() {
        return _gameName;
    }
    public ArrayList<Question> getQuestions() {
        return _questions;
    }
    public Boolean getPlayed() {
        return _played;
    }
    public int getIndex() {
        return _index;
    }
    public ArrayList<Tag> getTags() {
        return _tags;
    }
    public Boolean haveTag(Tag tag) {
       return _tags.contains(tag);
    }
    public Question getCurrentQueston(){return _questions.get(_index);}

    @Override
    public String toString() {
        return _gameName;
    }

    public void setQuestions(ArrayList<Question> questions) {this._questions = questions;}
    public void setIndex(int index){this._index = index;}
    public void setGameID(int gameID) {
        this._gameID = gameID;
    }
    public void setGameName(String gameName) {
        this._gameName = gameName;
    }
    public void setPlayed(Boolean tmp) {
        this._played = tmp;
    }
    public void addQuestion(Question question){
        this._questions.add(question);
    }
    public void nextIndex(){this._index ++;}
    public void prevIndex(){this._index --;}
    public void zeroIndex(){this._index = 0;}
    public void setTags(ArrayList<Tag> tags) {
        this._tags = tags;
    }
    public void addTag(Tag tag){
        this._tags.add(tag);
    }

    public void updateContent(ArrayList<Question> questions, Boolean played,
                           ArrayList<Tag> tags, int index)
    {
        this._questions = questions;
        this._played = played;
        this._tags = tags;
        this._index = index;
    }

    public void updateQuestion(Question question)
    {
        for(Question q : _questions)
        {
            if(q.getID() == question.getID()) {
                question.updateContent(question.getAnswers(), question.getIndex());
            }
        }
    }

    public JSONObject toJson(int mode) {
        JSONObject object = new JSONObject();
        try {
            object.put("_gameID", _gameID);
            object.put("_gameName", _gameName);
            object.put("_played", _played);
            object.put("_index", _index);
            JSONArray questions = new JSONArray();
            for (Question question : _questions) {
                questions.put(question.getID(), question.toJson(mode));
            }
            object.put("_questions", questions);

            if(mode != 2) {
                JSONArray tags = new JSONArray();
                for (Tag tag : _tags) {
                    tags.put(tag.get_tagID(), tag.toJson());
                }
                object.put("_tags", tags);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }



    public void destroy() {
        for(Question question: _questions) {
            question.destroy();
            question = null;
        }
        _questions.clear();
        _questions = null;

        for(Tag tag: _tags)
            tag = null;
        _tags.clear();
        _tags = null;
    }
}
