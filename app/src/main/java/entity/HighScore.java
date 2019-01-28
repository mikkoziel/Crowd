package entity;

import java.io.Serializable;

public class HighScore implements Serializable {
    private String _name;
    private int _points;
    private int _profileID;

    public HighScore(String name, int points, int profileID)
    {
        this._name = name;
        this._points = points;
        this._profileID = profileID;
    }

    public int getPoints(){return _points;}
    public String getName(){return _name;}
}
