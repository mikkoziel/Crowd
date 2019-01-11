package interactor;

import java.sql.Connection;
import java.util.Date;

public class AnswerInteractor {
    private DataBaseConnector _dbConnector;
    private Logger _logger;

    public AnswerInteractor(DataBaseConnector dbConnector, Logger logger)
    {
        _dbConnector = dbConnector;
        _logger = logger;
    }

    public AnswerInteractor()
    {
        _dbConnector = new DataBaseConnector();
        _logger = new Logger(_dbConnector);
    }

    public void logAnswer(int profileID, int questionID, int answerID)
    {
        _logger.setDate();
        Connection connection = _dbConnector.makeConnection();
        Boolean isConnect = _dbConnector.checkConnection(connection);
        String query = "Insert into Log(profileID, questionID, answerID, date) values(" + profileID + ", " + questionID + ", " + answerID + ", " + _logger.getDate() + ")";

        if (isConnect) {
            _logger.log(connection, query);
        }
    }
}
