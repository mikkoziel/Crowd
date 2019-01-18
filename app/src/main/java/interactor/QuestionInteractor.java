package interactor;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Game;
import entity.Question;

public class QuestionInteractor {
    private DataBaseConnector _dbConnector;
    private Connection _connection;
    private String _result;
    private Boolean _isSuccess;

    public QuestionInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._connection = _dbConnector.makeConnection();
        this._result = null;
        this._isSuccess = false;
    }

    public void emptyQuestions(Game game){game.getQuestions().clear();}

    public void setQuestions(Game game) throws SQLException {
        if (!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();

        ResultSet resultSet = getQuestions(game, _connection);
        while (resultSet.next()) {
            String content = resultSet.getString("questionText");
            int ID = resultSet.getInt("questionID");
            int type = resultSet.getInt("typeID");
            Boolean defaultAnswer = resultSet.getBoolean("defaultAnswer");

            Blob blobImage = resultSet.getBlob("questionImage");
            Question question;
            if (resultSet.wasNull())
                question = new Question(content, ID, type, defaultAnswer);

            else {
                byte[] byteImage = blobImage.getBytes(1, (int) blobImage.length());
                question = new Question(content, ID, type, defaultAnswer, byteImage);
            }
            game.addQuestion(question);
        }
        setSuccess("Game Starting");
    }

    // TO DO ograniczyć do 10 pytań
    private ResultSet getQuestions(Game game, Connection connection){
        String query = "select * from Question where gameID = " + game.getGameID() + ";";
        return _dbConnector.runQuery(query, connection);
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


    public void endWork() throws SQLException
    {
        _connection.close();
    }


}
