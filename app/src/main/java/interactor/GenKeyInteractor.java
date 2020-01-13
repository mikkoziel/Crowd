package interactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Profile;
import tools.DataBaseConnector;

public class GenKeyInteractor {
    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;
    private ArrayList<Integer> _keys;

    public GenKeyInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._result = "";
        this._isSuccess = false;
        this._keys = new ArrayList<>();
    }

    public boolean checkItems(Profile profile){
        try {
            if(getGames(profile) < getItems(profile)){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public int getGames(Profile profile) throws SQLException {
        int games = 0;
        String query = "select accessKey from Game where ownerId = " + profile.getID();
        ResultSet res = _dbConnector.runQuery(query);
        while (res.next()) {
            int accessKey = res.getInt("accessKey");
            _keys.add(accessKey);
            games++;
        }
        return games;
    }

    public int getItems(Profile profile){
        int items = 0;
        for(int i = 11; i < 22; i ++){
            if(profile.hasItem(i)){
                items++;
            }
        }
        return items;
    }

    public ArrayList<Integer> generateKey(Profile profile){
        int key;
        if(_keys.isEmpty()){
            key = profile.getID() + 1;
        }
        else{
            key = _keys.get(_keys.size() - 1) + 1;
        }
        _keys.add(key);
        addToDB(profile.getID(), key);
        return _keys;
    }

    public void addToDB(int id, int key){
        String query = "Insert into Game(accessKey, ownerID) " +
                "values("+ key + ", " + id + ")";
        int res = _dbConnector.updateQuery(query);
        if (res > 0) {
            setSuccess("New Key:" + key);
        }
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
