package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable {
    private String _name;
    private int _ID;
    private int _points;
    private int _level;
    private int _missingPoints;
    private int _money;
    private Avatar _avatar;
    private ArrayList<Item> _items;


    public Profile(int ID, String name, int points, int level, int money, int missingPoints, Avatar avatar){
        this._ID = ID;
        this._name = name;
        this._points = points;
        this._level = level;
        this._missingPoints = missingPoints;
        this._money = money;
        this._avatar = avatar;
        this._items = new ArrayList<>();
    }

    public String getName() {
        return _name;
    }

    public int getID() {
        return _ID;
    }

    public int getPoints() {
        return _points;
    }

    public void setName(String name) { this._name = name; }

    public void increasePoints(int extraPoints){ _points += extraPoints; }

    public void decreasePoints(int negativePoints){
        if(_points - negativePoints > 0)
            _points -= negativePoints;
        else
            _points = 0;
    }

    public void setPoints(int points){_points = points;}

    public int getLevel(){return _level;}

    public void setLevel(int level){_level = level;}

    public void setMissingPoints(int missingPoints){_missingPoints = missingPoints;}

    public int getMissingPoints(){return _missingPoints;}

    public int getMoney(){return _money;}

    public void setMoney(int money){_money = money;}

    public void spendMoney(int money){_money -=money;}

    public void setAvatar(Avatar avatar) {
        this._avatar = avatar;
    }

    public Avatar getAvatar() {
        return _avatar;
    }

    public void setItems(ArrayList<Item> _items) {this._items = _items;}

    public ArrayList<Item> getItems(){return this._items;}

    public void addItem(Item item){this._items.add(item);}

    public boolean hasItem(int index){
        for(Item x: _items){
            if(x.getID() == index) {
                return true;
            }
        }
        return false;
    }

    public void updateProfile(int points, int level, int money, int missingPoints, Avatar avatar){
        this._points = points;
        this._level = level;
        this._missingPoints = missingPoints;
        this._money = money;
        this._avatar = avatar;
    }
}
