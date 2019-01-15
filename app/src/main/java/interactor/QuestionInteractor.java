package interactor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Game;
import entity.Question;

public class QuestionInteractor {
    private DataBaseConnector _dbConnector;
    private Connection _connection;
    private Boolean _isConnect;

    public QuestionInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._connection = _dbConnector.makeConnection();
        this._isConnect = _dbConnector.checkConnection(_connection);
    }

    // TO DO ograniczyć do 10 pytań
    private ResultSet getQuestions(Game game, Connection connection){
        String query = "select * from Question where gameID = " + game.getGameID() + ";";
        return _dbConnector.runQuery(query, connection);
    }

    public Boolean isSuccess(){ return _dbConnector.getSuccess();}

    public String getResultInfo(){return _dbConnector.getResult();}

    public void emptyQuestions(Game game){game.getQuestions().clear();}

    public void setQuestions(Game game) throws SQLException {
        if (_isConnect) {
            ResultSet res = getQuestions(game, _connection);
            while(res.next()) {
                String content = res.getString("questionText");
                int ID = res.getInt("questionID");
                int type = res.getInt("typeID");

                Question question = new Question(content, ID, type);
                game.addQuestion(question);
            }
            _dbConnector.setResult("Game Starting");
            _dbConnector.success(true);
        }
        else{
            _dbConnector.setResult("Something went wrong");
            _dbConnector.success(false);
        }
    }


}
