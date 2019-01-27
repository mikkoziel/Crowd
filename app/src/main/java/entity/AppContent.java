package entity;

import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class AppContent implements Serializable {
    private ArrayList<Game> _games;
    private ArrayAdapter<Tag> _tags;
    private Profile _userProfile;
    private ArrayList<Avatar> _avatars;
    private ArrayList<Item> _shop;
    private ArrayList<HighScore> _highScore;

    private int _currentGameID;

    public AppContent()
    {
        this._games = null;
        this._tags = null;
        this._userProfile = null;
        this._avatars = null;
        this._shop = null;
        this._highScore = null;
        this._currentGameID = -1;
    }

    public ArrayList<Game> getGames() {
        return _games;
    }
    public ArrayAdapter<Tag> getTags() {return _tags;}
    public Profile getProfile(){return _userProfile;}
    public ArrayList<HighScore> getHighScore(){return _highScore;}
    public int getCurrentGameID(){return _currentGameID;}

    public void addGame(Game game){
        this._games.add(game);
    }
    public void setTags(ArrayAdapter<Tag> tags){this._tags = tags;}
    public void setProfile(Profile profile){this._userProfile = profile;}
    public void setAvatar(Avatar avatar){this._avatars.add(avatar);}
    public void setShop(ArrayList<Item> shop){this._shop = shop;}
    public void setHighScore(ArrayList<HighScore> highScore){this._highScore = highScore;}
    public void setCurrentGameID(int gameID){this._currentGameID = gameID;}


    public void updateGame(Game game)
    {
        for(Game g : _games) {
            if (g.getID() == game.getID()) {
                g.updateGame(game.getQuestions(), game.getPlayed(),
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
}
