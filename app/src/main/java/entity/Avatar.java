package entity;

import java.io.Serializable;

public class Avatar implements Serializable {
    private int _avatarID;
    private byte[] _icon;
    private byte[] _locked;
    private int _itemID;

    public Avatar(int avatarID, byte[] icon, byte[] locked, int itemID)
    {
        this._avatarID = avatarID;
        this._icon = icon;
        this._locked = locked;
        this._itemID = itemID;
    }

    public int getID(){return _avatarID;}
    public byte[] getIcon() {return _icon;}
    public byte[] getLocked() {
        return _locked;
    }
    public int getItemID(){return _itemID;}
}
