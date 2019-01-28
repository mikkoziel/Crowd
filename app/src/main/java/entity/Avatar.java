package entity;

import java.io.Serializable;

public class Avatar implements Serializable {
    private int _avatarID;
    private byte[] _icon;
    private byte[] _locked;

    public Avatar(int avatarID, byte[] icon, byte[] locked)
    {
        this._avatarID = avatarID;
        this._icon = icon;
        this._locked = locked;
    }

    public int getID(){return _avatarID;}
    public byte[] getIcon() {return _icon;}
    public byte[] getLocked() {
        return _locked;
    }
}
