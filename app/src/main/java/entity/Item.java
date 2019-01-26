package entity;

import java.io.Serializable;

public class Item implements Serializable {
    private int _itemID;
    private String _name;
    private byte[] _icon;
    private int _price;
    private String _description;

    public Item(int itemID, String name, byte[] icon, int price, String description)
    {
        this._itemID = itemID;
        this._name = name;
        this._icon = icon;
        this._price = price;
        this._description = description;
    }

    public int getID() {return _itemID;}
    public String getName() {return _name;}
    public byte[] getIcon() {return _icon;}
    public int getPrice() {return _price;}
    public String getDescription() {return _description;}
}
