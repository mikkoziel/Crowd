package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class AppContent implements Serializable {
    private ArrayList<Game> _games;
    private ArrayList<Tag> _tags;
    private Profile _userProfile;
    private ArrayList<Avatar> _avatars;
    private Shop _shop;
    private ArrayList<HighScore> _highScore;

    public AppContent()
    {
        this._games = null;
        this._tags = null;
        this._userProfile = null;
        this._avatars = null;
        this._shop = null;
        this._highScore = null;
    }

    public ArrayList<Game> getGames() {
        return _games;
    }
    public ArrayList<Tag> getTags() {return _tags;}
    public Profile getProfile(){return _userProfile;}
    public ArrayList<HighScore> getHighScore(){return _highScore;}

    public void addGame(Game game){
        this._games.add(game);
    }
    public void addTag(Tag tag){this._tags.add(tag);}
    public void setProfile(Profile profile){this._userProfile = profile;}
    public void setAvatar(Avatar avatar){this._avatars.add(avatar);}
    public void setShop(Shop shop){this._shop = shop;}
    public void setHighScore(ArrayList<HighScore> highScore){this._highScore = highScore;}
}
