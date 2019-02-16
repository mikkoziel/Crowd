package interactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Game;
import entity.Tag;
import tools.DataBaseConnector;

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

    public ArrayList<Tag> getTags() throws SQLException {
        ArrayList<Tag> tags = new ArrayList<>();

        String query = "select * from Tag";
        ResultSet resultSet = _dbConnector.runQuery(query);

        while (resultSet.next()) {
            String name = resultSet.getString("tag");
            int ID = resultSet.getInt("tagID");
            Tag tag = new Tag(ID, name);
            tags.add(tag);
            setSuccess("Tags ok");
        }
        return tags;
    }

    public void addGameTags(ArrayList<Tag> adapter, ArrayList<Game> games){
        for(Game game : games)
            try {
                int gameID = game.getID();
                game.setTags(getTagsFromDB(gameID, adapter));
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private ArrayList<Tag> getTagsFromDB(int gameID, ArrayList<Tag> adapter) throws SQLException {

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

    private Tag findTag(int ID, ArrayList<Tag> adapter){
        for(Tag a: adapter)
        {
            if(a.get_tagID() == ID)
                return a;
        }
        return null;
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
