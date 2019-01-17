package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable {
    private String _name;
    private int _ID;
    private int _points;
    private ArrayList<Game> _games;


    public Profile(int ID, String name, String password){
        this._ID = ID;
        this._name = name;
        this._points = 0;
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

    public void setID(int ID) {
        this._ID = ID;
    }

    public void addPoints(int toAdd){
        this._points += toAdd;
    }

    public void setGames(ArrayList<Game> games) {
        this._games = games;
    }

    public void addGames(Game game){
        this._games.add(game);
    }

}
