package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop implements Serializable {
    private ArrayList<Item> _items;

    public Shop(ArrayList<Item> items)
    {
        this._items = items;
    }

    public ArrayList<Item> getItems(){return _items;}

}
