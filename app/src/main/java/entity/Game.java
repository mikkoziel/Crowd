package entity;

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
}
