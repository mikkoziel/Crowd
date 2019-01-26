package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class AppContent implements Serializable {
    private ArrayList<Game> _games;
    private ArrayList<Tag> _tags;
    private Profile _userProfile;
    private ArrayList<Avatar> _avatars;
    private Shop _shop;

    public AppContent()
    {
        this._games = null;
        this._tags = null;
        this._userProfile = null;
        this._shop = null;
    }

    public ArrayList<Game> getGames() {
        return _games;
    }
    public ArrayList<Tag> getTags() {return _tags;}

    public void addGame(Game game){
        this._games.add(game);
    }
    public void addTag(Tag tag){this._tags.add(tag);}
    public void setProfile(Profile profile){this._userProfile = profile;}
    public void setShop(Shop shop){this._shop = shop;}
}
