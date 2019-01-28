package interactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Game;

public class GameInteractor {
    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public GameInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }

    public ArrayList<Game> getGames() throws SQLException {
        ArrayList<Game> games = null;
        String query = "select * from Game";
        ResultSet res = _dbConnector.runQuery(query);
        while (res.next()) {
            int gameID = res.getInt("gameID");
            String name = res.getString("gameName");
            Game game = new Game(gameID, name);
            games.add(game);
        }
        return games;
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
