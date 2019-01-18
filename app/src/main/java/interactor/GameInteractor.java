package interactor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Game;
import entity.Profile;

public class GameInteractor {
    private DataBaseConnector _dbConnector;
    private Connection _connection;

    public GameInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._connection = _dbConnector.makeConnection();
    }

    public void setGames(Profile profile, TagInteractor tagInteractor) throws SQLException {
        String query = "select * from Game";
        if(!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();

        ResultSet res = _dbConnector.runQuery(query, _connection);
        while (res.next()) {
            int gameID = res.getInt("gameID");
            Game game = new Game(gameID, res.getString("gameName"));
            game.setTags(tagInteractor.getTagsForGame(gameID));
            profile.addGames(game);
        }
    }

    public void endWork() throws SQLException
    {
        _connection.close();
    }

}
