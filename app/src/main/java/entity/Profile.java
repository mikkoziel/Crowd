package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable {
    private String _name;
    private int _ID;
    private int _points;
    private ArrayList<Game> _games;
    private int _level;
    private int _missingPoints;
    private int _money;


    public Profile(int ID, String name, int points, int level, int money){
        this._ID = ID;
        this._name = name;
        this._points = points;
        this._games = new ArrayList<>();
        this._level = level;
        this._missingPoints = -1;
        this._money = money;
    }

    public String getName() {
        return _name;
    }

    public int getID() {
        return _ID;
    }

    public int getPoints() {
        return _points;
    }

    public ArrayList<Game> getGames() {
        return _games;
    }

    public void setName(String name) {
        this._name = name;
    }

    public void addGames(Game game){
        this._games.add(game);
    }

    public void increasePoints(int extraPoints){
        _points =  _points + extraPoints;
    }

    public void setPoints(int points){_points = points;}

    public int getLevel(){return _level;}

    public void setLevel(int level){_level = level;}

    public void setMissingPoints(int missingPoints){_missingPoints = missingPoints;}

    public int getMissingPoints(){return _missingPoints;}

    public int getMoney(){return _money;}

    public void setMoney(int money){_money = money;}
}
