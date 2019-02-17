package interactor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import entity.Answer;
import entity.AppContent;
import entity.Avatar;
import entity.Game;
import entity.HighScore;
import entity.Item;
import entity.Profile;
import entity.Question;
import entity.Tag;

public class JsonInteractor {

    public JsonInteractor(){

    }

    public JSONObject writeToJson(AppContent appContent, int mode) {
        return appContent.toJson(mode);

    }

    public AppContent parseJSON(JSONObject object, int mode) throws JSONException {
        AppContent appContent = new AppContent();

//        switch(mode){
//            case 0:
//                appContent.setTags(parseTags(object.getJSONArray("_tags")));
//                appContent.setAvatars(parseAvatars(object.getJSONArray("_avatars")));
//                appContent.setShop(parseShop(object.getJSONArray("_shop")));
//                appContent.setHighScore(parseHighScores(object.getJSONArray("_highscore")));
//                appContent.setGames(parseGames(object.getJSONArray("_games"), appContent, mode));
//                appContent.setProfile(parseProfile(object.getJSONObject("_userProfile"), appContent));
//                appContent.setCurrentGameID(object.getInt("_currentGameID"));
//                break;
//            case 1:
//                appContent.setGames(parseGames(object.getJSONArray("_games"), appContent, mode));
//                appContent.setProfile(parseProfile(object.getJSONObject("_userProfile"), appContent));
//                appContent.setCurrentGameID(object.getInt("_currentGameID"));
//                break;
//        }
        appContent.setTags(parseTags(object.getJSONArray("_tags")));
        appContent.setAvatars(parseAvatars(object.getJSONArray("_avatars")));
        appContent.setShop(parseShop(object.getJSONArray("_shop")));
        appContent.setHighScore(parseHighScores(object.getJSONArray("_highscore")));
        appContent.setGames(parseGames(object.getJSONArray("_games"), appContent, mode));
        appContent.setProfile(parseProfile(object.getJSONObject("_userProfile"), appContent));
        appContent.setCurrentGameID(object.getInt("_currentGameID"));

        return appContent;
    }

    private byte[] parseArrayToBytes(Object bytes) {
        return ((bytes.toString()).getBytes());
    }

    private Profile parseProfile(JSONObject object, AppContent appContent) throws JSONException {
        String _name = object.getString("_name");
        int _ID = object.getInt("_ID");
        int _points = object.getInt("_points");
        int _level = object.getInt("_level");
        int _missingPoints = object.getInt("_missingPoints");
        int _money = object.getInt("_money");
        Avatar _avatar = appContent.getAvatar(object.getJSONObject("_avatar").getInt("_avatarID"));
        JSONArray _items = object.getJSONArray("_items");

        Profile profile = new Profile(_ID, _name, _points, _level, _money, _missingPoints, _avatar);

        for (int i = 0; i < _items.length(); i++) {
            if(!_items.isNull(i)) {
                Item item = appContent.getItem(_items.getJSONObject(i).getInt("_itemID"));
                profile.addItem(item);
            }
        }

        return profile;
    }

    private ArrayList<Game> parseGames(JSONArray array, AppContent appContent, int mode) throws JSONException {
        ArrayList<Game> games = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            if( !array.isNull(i))
                games.add(parseGame(array.getJSONObject(i), appContent, mode));
        }
        return games;
    }

    private ArrayList<Tag> parseTags(JSONArray array) throws JSONException {
        ArrayList<Tag> tags = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            if( !array.isNull(i))
                tags.add(parseTag(array.getJSONObject(i)));
        }
        return tags;
    }

    private ArrayList<Avatar> parseAvatars(JSONArray array) throws JSONException {
        ArrayList<Avatar> avatars = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            if( !array.isNull(i))
                avatars.add(parseAvatar(array.getJSONObject(i)));
        }
        return avatars;
    }

    private ArrayList<Item> parseShop(JSONArray array) throws JSONException {
        ArrayList<Item> shop = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            if( !array.isNull(i))
                shop.add(parseItem(array.getJSONObject(i)));
        }
        return shop;
    }

    private ArrayList<HighScore> parseHighScores(JSONArray array) throws JSONException {
        ArrayList<HighScore> highScores = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            if( !array.isNull(i))
                highScores.add(parsehHighscore(array.getJSONObject(i)));
        }
        return highScores;
    }

    private Game parseGame(JSONObject object, AppContent appContent, int mode) throws JSONException {
        int _gameID = object.getInt("_gameID");
        String _gameName = object.getString("_gameName");
        Boolean _played = object.getBoolean("_played");
        int _index = object.getInt("_index");
        JSONArray _questions = object.getJSONArray("_questions");


        Game game = new Game(_gameID, _gameName);
        game.setPlayed(_played);
        game.setIndex(_index);

        for (int i = 0; i < _questions.length(); i++) {
            if( !_questions.isNull(i)) {

                Question question = parseQuestion(_questions.getJSONObject(i));
                game.addQuestion(question);
            }
        }
        if(mode != 2) {
            JSONArray _tags = object.getJSONArray("_tags");

            for (int i = 0; i < _tags.length(); i++) {
                if(!_tags.isNull(i)) {
                    Tag tag = appContent.getTag(_tags.getJSONObject(i).getInt("_tagID"));
                    game.addTag(tag);
                }
            }
        }
        return game;
    }

    private Tag parseTag(JSONObject object) throws JSONException {
        int _tagID = object.getInt("_tagID");
        String _tag = object.getString("_tag");

        return new Tag(_tagID, _tag);
    }

    private Answer parseAnswer(JSONObject object) throws JSONException {
        int _answerID = object.getInt("_answerID");
        String _answer = object.getString("_answer");
        int _type = object.getInt("_type");
        Boolean _defaultAnswer = object.getBoolean("_defaultAnswer");
        Boolean _isImageAnswer = object.getBoolean("_isImageAnswer");
        byte[] _image;
        if(_isImageAnswer)
            _image = parseArrayToBytes(object.get("_image"));
        else
            _image = null;

        int _showed = object.getInt("_showed");
        int _chosen = object.getInt("_chosen");

        Answer answer = new Answer(_answerID, _answer, _type, _defaultAnswer, _image, _showed, _chosen);
        answer.setIsImageAnswer(_isImageAnswer);
        return answer;
    }

    private Question parseQuestion(JSONObject object) throws JSONException {
        String _question = object.getString("_question");
        int _questionID = object.getInt("_questionID");
        int _type = object.getInt("_type");
        JSONArray _answers = object.getJSONArray("_answers");
        int _index = object.getInt("_index");
        Boolean _defaultAnswer = object.getBoolean("_defaultAnswer");
        Boolean _isImageQuestion = object.getBoolean("_isImageQuestion");
        String _image;
        if(_isImageQuestion)
            _image = object.getString("_image");
        else{
            _image = null;
        }

        Question question = new Question(_question, _questionID, _type, _defaultAnswer, _image);
        question.setIndex(_index);
        question.setIsImageQuestion(_isImageQuestion);

        for (int i = 0; i < _answers.length(); i++) {
            if(!_answers.isNull(i)) {
                Answer answer = parseAnswer(_answers.getJSONObject(i));
                question.addAnswer(answer);
            }
        }

        return question;
    }

    private Avatar parseAvatar(JSONObject object) throws JSONException {
        int _avatarID = object.getInt("_avatarID");
//        byte[] _icon = parseArrayToBytes(object.get("_icon"));
//        byte[] _locked = parseArrayToBytes(object.get("_locked"));
        int _itemID = object.getInt("_itemID");

//        return new Avatar(_avatarID, _icon, _locked, _itemID);
        return new Avatar(_avatarID, _itemID);
    }

    private Item parseItem(JSONObject object) throws JSONException {
        int _itemID = object.getInt("_itemID");
        String _name = object.getString("_name");
        byte[] _icon = parseArrayToBytes(object.get("_icon"));
        int _price = object.getInt("_price");
        String _description = object.getString("_description");

        return new Item(_itemID, _name, _icon, _price, _description);
    }

    private HighScore parsehHighscore(JSONObject object) throws JSONException {
        String _name = object.getString("_name");
        int _points = object.getInt("_points");
        int _profileID = object.getInt("_profileID");

        return new HighScore(_name, _points, _profileID);
    }
}
