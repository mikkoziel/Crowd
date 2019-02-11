package entity;

import org.json.JSONException;
import org.json.JSONObject;

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

    public int getProfileID() {
        return _profileID;
    }

    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("_profileID", _profileID);
            object.put("_name", _name);
            object.put("_profileID", _profileID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
