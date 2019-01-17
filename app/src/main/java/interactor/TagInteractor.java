package interactor;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Game;
import entity.Tag;

public class TagInteractor {

    private DataBaseConnector _dbConnector;
    private Connection _connection;
    private Boolean _isConnect;

    public TagInteractor()
    {
        _dbConnector = new DataBaseConnector();
        _connection = _dbConnector.makeConnection();
        _isConnect = _dbConnector.checkConnection(_connection);
    }

    private ResultSet getTagsFromDB(Connection connection, String query){
        ResultSet res = _dbConnector.runQuery(query, connection);
        return res;
    }

    public ArrayAdapter<Tag> getTags(Activity activity) throws SQLException {
        ResultSet res;
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line);
        String query = "select * from Tag";

        if (_isConnect) {
            res = getTagsFromDB(_connection, query);
            while (res.next()) {
                String name = res.getString("tag");
                int ID = res.getInt("tagID");
                Tag tag = new Tag(ID, name);
                adapter.add(tag);
//                _dbConnector.setResult("Tags ");
//                _dbConnector.success(false);
            }
        }

        return adapter;
    }

    public ArrayList<Tag> getTagsForGame(int gameID) throws SQLException {
        ResultSet res;
        ArrayList<Integer> tags = new ArrayList<>();
        String query = "select * from GameTagRelation where gameID =" +gameID;

        if (_isConnect) {
            res = getTagsFromDB(_connection, query);
            while (res.next()) {
                int ID = res.getInt("tagID");
                tags.add(ID);
//                _dbConnector.setResult("Tags ");
//                _dbConnector.success(false);
            }
        }

        return makeIntToTag(tags);
    }

    private ArrayList<Tag> makeIntToTag(ArrayList<Integer> tagsInt) throws SQLException {
        ResultSet res;
        ArrayList<Tag>  tags = new ArrayList<>();
        for(int x: tagsInt){
            String query = "select * from Tag where tagID =" + x;
            if (_isConnect) {
                res = getTagsFromDB(_connection, query);
                while (res.next()) {
                    String name = res.getString("tag");
                    Tag tag = new Tag(x, name);
                    tags.add(tag);
//                _dbConnector.setResult("Tags ");
//                _dbConnector.success(false);
                }
            }
        }
        return tags;
    }

}
