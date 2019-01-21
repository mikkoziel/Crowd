package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable {
    private String _name;
    private int _ID;
    private int _points;
    private ArrayList<Game> _games;


    public Profile(int ID, String name, int points){
        this._ID = ID;
        this._name = name;
        this._points = points;
        this._games = new ArrayList<>();
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

    public void increasePoints(int extraPoints){_points += extraPoints;}

}
