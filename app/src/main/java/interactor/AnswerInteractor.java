package interactor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Answer;
import entity.Game;
import entity.Question;

public class AnswerInteractor {
    private DataBaseConnector _dbConnector;
    private Logger _logger;
    private Connection _connection;
    private Boolean _isConnect;

    public AnswerInteractor()
    {
        _dbConnector = new DataBaseConnector();
        _logger = new Logger(_dbConnector);
        _connection = _dbConnector.makeConnection();
        _isConnect = _dbConnector.checkConnection(_connection);
    }

    public void logAnswer(int profileID, int questionID, int answerID)
    {
        _logger.setDate();
        String query = "Insert into Log(profileID, questionID, answerID, date) values(" + profileID + ", " + questionID + ", " + answerID + ", " + _logger.getDate() + ")";

        if (_isConnect) {
            _logger.log(_connection, query);
        }
    }

    public ResultSet getAnswers(Question question)
    {
        String query = "select * from Answer where questionID= '" + question.getQuestionID() + "'";
        Connection connection = _dbConnector.makeConnection();
        return _dbConnector.runQuery(query, connection);
    }

    public void setAnswersForAllQuestions(Game game) throws SQLException {
        ResultSet res;
        if(_isConnect) {
            for (Question question : game.getQuestions()) {
                res = getTop3Answers(question);
                addPossibleAnswers(res, question);
            }
        }
    }

    private ResultSet getTop3Answers(Question question){
        String query = "select TOP 3 from Answer where questionID= '" + question.getQuestionID() + "'";
        return _dbConnector.runQuery(query, _connection);
    }

    public void addPossibleAnswers(ResultSet res, Question question) throws SQLException {
        while(res.next()) {
            String content = res.getString("answer");
            int ID = res.getInt("answerID");
            int type = res.getInt("typeID");
            int used = res.getInt("used");
            double percentageUsed = res.getDouble("percentageUsed");
            Answer answer = new Answer(ID, content, used, percentageUsed, type);
            question.addAnswer(answer);
        }
    }



}
