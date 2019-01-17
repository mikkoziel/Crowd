package entity;

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

    @Override
    public String toString() {
        return _tag;
    }
}
