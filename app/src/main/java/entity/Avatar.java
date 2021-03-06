package entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Avatar implements Serializable {
    private int _avatarID;
//    private byte[] _icon;
//    private byte[] _locked;
    private int _itemID;
    private String _iconName;
    private String _lockedName;

//    public Avatar(int avatarID, byte[] icon, byte[] locked, int itemID)
    public Avatar(int avatarID, int itemID)
    {
        this._avatarID = avatarID;
//        this._icon = icon;
//        this._locked = locked;
        this._itemID = itemID;
        this._iconName = "@drawable/avatar" + Integer.toString(avatarID);
        this._lockedName = "@drawable/locked" + Integer.toString(avatarID);
    }

    public int getID(){return _avatarID;}
//    public byte[] getIcon() {return _icon;}
//    public byte[] getLocked() {
//        return _locked;
//    }
    public int getItemID(){return _itemID;}

    public String getIconName() {
        return _iconName;
    }

    public String getLockedName() {
        return _lockedName;
    }

//    public JSONObject toJson(){
//        JSONObject object = new JSONObject();
//        try {
//            object.put("_avatarID", _avatarID);
////            JSONArray icon = new JSONArray();
////            for(byte x: _icon){
////                icon.put(x);
////            }
////            object.put("_icon", icon);
////
////            JSONArray locked = new JSONArray();
////            for(byte x: _locked){
////                locked.put(x);
////            }
////            object.put("_locked", locked);
//            object.put("_itemID", _itemID);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return object;
//    }
}
