package interactor;

import android.widget.ArrayAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Game;
import entity.Profile;
import entity.Tag;

public class TagInteractor {

    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public TagInteractor()
    {
        _dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }

    public ArrayAdapter<Tag> getTags(ArrayAdapter<Tag> adapter) throws SQLException {
        String query = "select * from Tag";

        ResultSet resultSet = _dbConnector.runQuery(query);
        while (resultSet.next()) {
            String name = resultSet.getString("tag");
            int ID = resultSet.getInt("tagID");
            Tag tag = new Tag(ID, name);
            adapter.add(tag);
            setSuccess("Tags ok");
            }
        return adapter;
    }

    public void addGameTags(Profile profile, ArrayAdapter<Tag> adapter){
        for(Game game : profile.getGames())
            try {
                int gameID = game.getGameID();
                game.setTags(getTagsFromDB(gameID, adapter));
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private ArrayList<Tag> getTagsFromDB(int gameID, ArrayAdapter<Tag> adapter) throws SQLException {

        ArrayList<Tag> tags = new ArrayList<>();
        String query = "select * from GameTagRelation where gameID =" +gameID;
        ResultSet res = _dbConnector.runQuery(query);
        while (res.next()) {
            int ID = res.getInt("tagID");
            Tag tag = findTag(ID, adapter);
            tags.add(tag);
            setSuccess("Tags ok");
        }
        return tags;
    }

    private Tag findTag(int ID, ArrayAdapter<Tag> adapter){
        Tag tag = null;
        for(int i=0 ; i<adapter.getCount() ; i++){
            tag = adapter.getItem(i);
            if(tag.isEqual(ID)){
                return tag;
            }
        }
        return tag;
    }

    private void setSuccess(String message)
    {
        _result = message;
        _isSuccess = true;
    }

    private void setFailure(String message)
    {
        _result = message;
        _isSuccess = false;
    }

    public Boolean isSuccess(){return _isSuccess;}
    public String getResult(){return _result;}


    public void endWork()
    {
        try {
            _dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
