package entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public Tag getTag(int id){
        for(Tag a: _tags)
        {
            if(a.get_tagID() == id)
                return a;
        }
        return null;
    }

    public JSONObject toJson(int mode){
        JSONObject object = new JSONObject();
        try {
//            switch (mode){
//                case 0:
//                    object.put("_tags", tagsToJson());
//                    object.put("_avatars", avatarsToJson());
//                    object.put("_shop", itemsToJson());
//                    object.put("_games", gamesToJson(mode));
//                    object.put("_userProfile", _userProfile.toJson());
//                    object.put("_highscore", highscoreToJson());
//                    object.put("_currentGameID", _currentGameID);
//                    break;
//                case 1:
//                    object.put("_games", gamesToJson(mode));
//                    object.put("_userProfile", _userProfile.toJson());
//                    object.put("_currentGameID", _currentGameID);
//                    break;
//            }
            object.put("_tags", tagsToJson());
            object.put("_avatars", avatarsToJson());
            object.put("_shop", itemsToJson());
            object.put("_games", gamesToJson(mode));
            object.put("_userProfile", _userProfile.toJson());
            object.put("_highscore", highscoreToJson());
            object.put("_currentGameID", _currentGameID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    private JSONArray gamesToJson(int mode) throws JSONException {
        JSONArray games = new JSONArray();
        for (Game game : _games) {
            games.put(game.getID(), game.toJson(mode));
        }
        return games;
    }

    private JSONArray tagsToJson() throws JSONException {
        JSONArray tags = new JSONArray();
        for (Tag tag : _tags) {
            tags.put(tag.get_tagID(), tag.toJson());
        }
        return tags;
    }

    private JSONArray avatarsToJson() throws JSONException {
        JSONArray avatars = new JSONArray();
        for (Avatar avatar : _avatars) {
            avatars.put(avatar.getID(), avatar.toJson());
        }
        return avatars;
    }

    private JSONArray itemsToJson() throws JSONException {
        JSONArray items = new JSONArray();
        for (Item item : _shop) {
            items.put(item.getID(), item.toJson());
        }
        return  items;
    }

    private JSONArray highscoreToJson() throws JSONException {
        JSONArray highscores = new JSONArray();
        for (HighScore highScore : _highScore) {
            highscores.put(highScore.getProfileID(), highScore.toJson());
        }
        return highscores;
    }
    public void destroy(){
        for(Game game: _games) {
            game.destroy();
            game = null;
        }
        _games.clear();
        _games = null;

        for(Tag tag: _tags)
            tag = null;
        _tags.clear();
        _tags = null;

        _userProfile.destroy();
        _userProfile = null;

        for(Avatar avatar: _avatars)
            avatar = null;
        _avatars.clear();
        _avatars = null;

        for(Item item: _shop)
            item = null;
        _shop.clear();
        _shop = null;

        for(HighScore x: _highScore)
            x = null;
        _highScore.clear();
        _highScore = null;
    }
}
