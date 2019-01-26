package entity;

import java.io.Serializable;

public class Avatar implements Serializable {
    private int _avatarID;
    private byte[] _icon;

    public Avatar(int avatarID, byte[] icon)
    {
        this._avatarID = avatarID;
        this._icon = icon;
    }

    public int getID(){return _avatarID;}
    public byte[] getIcon() {return _icon;}
}
