package interactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Profile;
import tools.DataBaseConnector;

public class GenKeyInteractor {
    private DataBaseConnector _dbConnector;
    private Boolean _isSuccess;
    private ArrayList<Integer> _keys;

    public GenKeyInteractor()
    {
        this._dbConnector = new DataBaseConnector();
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

    public void generateKey(Profile profile){
        if(_keys.isEmpty()){
            _keys.add(profile.getID() + 1);
        }
        else{
            int key = _keys.get(_keys.size() - 1);
            _keys.add(key);
        }
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

    private void setSuccess(ArrayList<Integer> keys)
    {
        _keys = keys;
        _isSuccess = true;
    }

    private void setFailure()
    {
        _keys = new ArrayList<>();
        _isSuccess = false;
    }

    public Boolean isSuccess(){return _isSuccess;}
    public ArrayList<Integer> getResult(){return _keys;}

    public void endWork()
    {
        try {
            _dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
