package entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Tag implements Serializable {
    private int _tagID;
    private String _tag;

    public Tag(int ID, String tag){
        _tagID =  ID;
        _tag = tag;
    }

    public void set_tagID(int _tagID) {
        this._tagID = _tagID;
    }

    public void set_tag(String _tag) {
        this._tag = _tag;
    }

    public int get_tagID() {
        return _tagID;
    }

    public String get_tag() {
        return _tag;
    }

    public Boolean isEqual(int ID){
        return _tagID == ID;
    }

    @Override
    public String toString() {
        return _tag;
    }

    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("_tagID", _tagID);
            object.put("_tag", _tag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
