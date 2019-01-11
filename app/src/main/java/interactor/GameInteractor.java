package interactor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Game;
import entity.Profile;

public class GameInteractor {
    private DataBaseConnector _dbConnector;

    public GameInteractor(DataBaseConnector dbConnector)
    {
        _dbConnector = dbConnector;
    }


    public void setGames(Profile profile) throws SQLException {
//        ArrayList<Game> games = new ArrayList<>();
        // Game[] games = ;
        String query = "select * from Game";
//        Connection connection = null;
        Connection connection = _dbConnector.makeConnection();
        Boolean isConnect = _dbConnector.checkConnection(connection);

        if(isConnect) {
            ResultSet res = _dbConnector.runQuery(query, connection);
            while (res.next()) {
                Game game = new Game(res.getInt("gameID"), res.getString("gameName"));
                profile.addGames(game);
//            intent.putExtra("games", games);
            }
        }
    }

}
