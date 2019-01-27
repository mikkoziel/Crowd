package interactor;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.AppContent;
import entity.Game;
import entity.Profile;

public class GameInteractor {
    private DataBaseConnector _dbConnector;

    public GameInteractor()
    {
        this._dbConnector = new DataBaseConnector();
    }

    public void setGames(AppContent appContent) throws SQLException {
        String query = "select * from Game";

        ResultSet res = _dbConnector.runQuery(query);
        while (res.next()) {
            int gameID = res.getInt("gameID");
            Game game = new Game(gameID, res.getString("gameName"));
            appContent.addGame(game);
        }
    }

    public void endWork()
    {
        try {
            _dbConnector.closeConnection();
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }
}
