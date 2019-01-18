package interactor;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Game;
import entity.Profile;
import entity.Tag;

public class TagInteractor {

    private DataBaseConnector _dbConnector;
    private Connection _connection;
    private String _result;
    private Boolean _isSuccess;

    public TagInteractor()
    {
        _dbConnector = new DataBaseConnector();
        _connection = _dbConnector.makeConnection();
        this._result = null;
        this._isSuccess = false;
    }

    public ArrayAdapter<Tag> getTags(Activity activity) throws SQLException {
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line);
        String query = "select * from Tag";

        if (!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();

        ResultSet resultSet = _dbConnector.runQuery(query, _connection);
        while (resultSet.next()) {
            String name = resultSet.getString("tag");
            int ID = resultSet.getInt("tagID");
            Tag tag = new Tag(ID, name);
            adapter.add(tag);
            setSuccess("Tags ok");
            }
        return adapter;
    }

    public void addTagsForUserGames(Profile profile)
    {
        for(Game game : profile.getGames())
            try {
                int gameID = game.getGameID();
                game.setTags(getTagsForGame(gameID));
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private ArrayList<Tag> getTagsForGame(int gameID) throws SQLException {

        ArrayList<Integer> tags = new ArrayList<>();
        String query = "select * from GameTagRelation where gameID =" +gameID;

        if (!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();

        ResultSet res = _dbConnector.runQuery(query, _connection);
        while (res.next()) {
            int ID = res.getInt("tagID");
            tags.add(ID);
            setSuccess("Tags ok");
        }
        return makeIntToTag(tags);
    }

    private ArrayList<Tag> makeIntToTag(ArrayList<Integer> tagsInt) throws SQLException {
        if (!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();

        ResultSet resultSet;
        ArrayList<Tag>  tags = new ArrayList<>();
        for(int x: tagsInt) {
            String query = "select * from Tag where tagID =" + x;
            resultSet = _dbConnector.runQuery(query, _connection);
            while (resultSet.next()) {
                String name = resultSet.getString("tag");
                Tag tag = new Tag(x, name);
                tags.add(tag);
                setSuccess("Tags ok");
            }
        }
        return tags;
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


    public void endWork() throws SQLException
    {
        _connection.close();
    }

}
