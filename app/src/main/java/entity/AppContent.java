package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class AppContent implements Serializable {
    private ArrayList<Game> _games;
    private ArrayList<Tag> _tags;
    private Profile _userProfile;
    private ArrayList<Avatar> _avatars;
    private ArrayList<Item> _shop;
    private ArrayList<HighScore> _highScore;

    private int _currentGameID;

    public AppContent()
    {
        this._games = new ArrayList<>();
        this._tags = new ArrayList<>();
        this._userProfile = null;
        this._avatars = new ArrayList<>();
        this._shop = new ArrayList<>();
        this._highScore = new ArrayList<>();
        this._currentGameID = -1;
    }

    public ArrayList<Game> getGames() {
        return _games;
    }
    public ArrayList<Tag> getTags() {return _tags;}
    public Profile getProfile(){return _userProfile;}
    public ArrayList<HighScore> getHighScore(){return _highScore;}
    public int getCurrentGameID(){return _currentGameID;}
    public Game getCurrentGame(){return getGame(_currentGameID);}
    public ArrayList<Avatar> getAvatars() {
        return _avatars;
    }
    public ArrayList<Item> getShop() {return  _shop;}

    public void setGames(ArrayList<Game> games){
        this._games = games;
    }
    public void setTags(ArrayList<Tag> tags){this._tags = tags;}
    public void setProfile(Profile profile){this._userProfile = profile;}
    public void setAvatars(ArrayList<Avatar> avatars){this._avatars = avatars;}
    public void setShop(ArrayList<Item> shop){this._shop = shop;}
    public void setHighScore(ArrayList<HighScore> highScore){this._highScore = highScore;}
    public void setCurrentGameID(int gameID){this._currentGameID = gameID;}



    public void updateGame(Game game)
    {
        for(Game g : _games) {
            if (g.getID() == game.getID()) {
                g.updateContent(game.getQuestions(), game.getPlayed(),
                        game.getTags(), game.getIndex());
                return;
            }
        }
    }

    public void updateCurrentProfile(Profile profile)
    {
        _userProfile.updateProfile(profile.getPoints(), profile.getLevel(),
                profile.getMoney(), profile.getMissingPoints(),
                profile.getAvatar());
    }

    public Game getGame(int id)
    {
        for(Game g : _games) {
            if (g.getID() == id)
                return g;
        }
        return null;
    }

    public Avatar getAvatar(int id)
    {
        for(Avatar a: _avatars)
        {
            if(a.getID() == id)
                return a;
        }
        return null;
    }

    public Item getItem(int id)
    {
        for(Item a: _shop)
        {
            if(a.getID() == id)
                return a;
        }
        return null;
    }
}
