package entity;

import java.io.Serializable;
import java.util.ArrayList;

import interactor.Logger;

public class Profile implements Serializable {
    public String name;
    private int ID;
    private int points;
    private ArrayList<Game> games;


    public Profile(int ID, String name, String password){
        this.ID = ID;
        this.name = name;
        this.points = 0;
        this.games = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<Game> getGames() {
        return games;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void addPoints(int toAdd){
        this.points += toAdd;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public void addGames(Game game){
        this.games.add(game);
    }

}
