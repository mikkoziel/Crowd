package interactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.HighScore;
import tools.DataBaseConnector;

public class HighScoreInteractor {

    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public HighScoreInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }

    public ArrayList<HighScore> getHighScore() throws Exception {
        String query = "Select Top 10 * from Profile order by points desc ";
        ResultSet res = _dbConnector.runQuery(query);
        ArrayList<HighScore> highScores = new ArrayList<>();
        while(res.next()) {
            int id = res.getInt("profilID");
            String name = res.getString("name");
            int points = res.getInt("points");

            HighScore highScore = new HighScore(name, points, id);

            highScores.add(highScore);
            setSuccess("HighScore set");
        }
        return highScores;
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
