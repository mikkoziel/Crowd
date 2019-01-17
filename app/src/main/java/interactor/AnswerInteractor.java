package interactor;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

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
        String query = "Insert into Log(profilID, questionID, answerID, date) values(" + profileID + ", " + questionID + ", " + answerID + ", " + _logger.getDate() + ")";

        if (_isConnect) {
            _logger.log(_connection, query);
        }
    }

    public void getAnswers(Question question) throws SQLException {
        ArrayList<Integer> ids = selectAnswerID(question);

        if(question.get_defaultAnswer()){
            setRandomAnswer(ids, question, 4);
            defaultAnswer(question);
        }
        else{
            setRandomAnswer(ids, question, 5);
        }
    }

    private void defaultAnswer(Question question) throws SQLException {
        String query = "select * from Answer where questionID= '" + question.getQuestionID() + "' and defaultAnswer = 1";
        Connection connection = _dbConnector.makeConnection();
        addPossibleAnswers(_dbConnector.runQuery(query, connection), question);
    }

    private ArrayList<Integer> selectAnswerID(Question question) throws SQLException {
        String query = "select answerID from Answer where questionID= '" + question.getQuestionID() + "' and defaultAnswer = 0";
        Connection connection = _dbConnector.makeConnection();
        ResultSet res = _dbConnector.runQuery(query, connection);
        ArrayList<Integer> ids = new ArrayList<>();

        while(res.next()) {
            ids.add(res.getInt("answerID"));
        }
        return ids;
    }

    private void setRandomAnswer(ArrayList<Integer> ids, Question question, int numberOfAnswer) throws SQLException {
        Random rand = new Random();

        for(int i = 0; i < numberOfAnswer; i++){
            int random = rand.nextInt(ids.size());
            int randomElement = ids.get(random);
            ids.remove(random);
            getRandomAnswer(question, randomElement);
        }
    }

    private void getRandomAnswer(Question question, int randomIndex) throws SQLException {
        String query = "select * from Answer where answerID= '" + randomIndex + "'";
        Connection connection = _dbConnector.makeConnection();
        addPossibleAnswers(_dbConnector.runQuery(query, connection), question);
    }

    private void addPossibleAnswers(ResultSet res, Question question) throws SQLException {
        while(res.next()) {
            String content = res.getString("answerText");
            int ID = res.getInt("answerID");
            int type = res.getInt("typeID");
            int used = res.getInt("used");
            double percentageUsed = res.getDouble("percentageUsed");
            Boolean defaultAnswer = res.getBoolean("defaultAnswer");
            Blob blobImage = res.getBlob("answerImage");

            Answer answer;
            if (res.wasNull()) {
                answer = new Answer(ID, content, used, percentageUsed, type, defaultAnswer);
            }
            else{
                byte[] byteImage = blobImage.getBytes(1, (int)blobImage.length());
                answer = new Answer(ID, content, used, percentageUsed, type, defaultAnswer, byteImage);
            }
//            Answer answer = new Answer(ID, content, used, percentageUsed, type, defaultAnswer);
            question.addAnswer(answer);
        }
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





}
