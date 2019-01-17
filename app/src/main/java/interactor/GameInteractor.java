package interactor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Game;
import entity.Profile;

public class GameInteractor {
    private DataBaseConnector _dbConnector;
    private TagInteractor _tagInteractor;

    public GameInteractor()
    {
        _dbConnector = new DataBaseConnector();
        _tagInteractor = new TagInteractor();
    }

    public void setGames(Profile profile) throws SQLException {
        String query = "select * from Game";
        Connection connection = _dbConnector.makeConnection();
        Boolean isConnect = _dbConnector.checkConnection(connection);

        if(isConnect) {
            ResultSet res = _dbConnector.runQuery(query, connection);
            while (res.next()) {
                int gameID = res.getInt("gameID");
                Game game = new Game(gameID, res.getString("gameName"));
                game.setTags(_tagInteractor.getTagsForGame(gameID));
                profile.addGames(game);
            }
        }
    }

}
