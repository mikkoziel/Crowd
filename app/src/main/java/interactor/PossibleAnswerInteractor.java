package interactor;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import entity.Answer;
import entity.Question;

public class PossibleAnswerInteractor {
    private DataBaseConnector _dbConnector;
    private Connection _connection;
    private String _result;
    private Boolean _isSuccess;

    public PossibleAnswerInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._connection = _dbConnector.makeConnection();
        this._result = null;
        this._isSuccess = false;
    }

    public void setPossibleAnswers(Question question) throws SQLException {
        ArrayList<Integer> ids = selectAnswerID(question);

        if(question.getDefaultAnswer()){
            setRandomAnswer(ids, question, 4);
            defaultAnswer(question);
        }
        else{
            setRandomAnswer(ids, question, 3);
        }
    }

    private ArrayList<Integer> selectAnswerID(Question question) throws SQLException {
        String query = "select answerID from Answer where questionID= '" + question.getQuestionID() + "' and defaultAnswer = 0";

        if(!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();
        ResultSet res = _dbConnector.runQuery(query, _connection);
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

        if(!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();
        ResultSet resultSet = _dbConnector.runQuery(query, _connection);
        addPossibleAnswers(resultSet, question);
    }

    private void defaultAnswer(Question question) throws SQLException {
        String query = "select * from Answer where questionID= '" + question.getQuestionID() + "' and defaultAnswer = 1";

        if(!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();

        ResultSet resultSet = _dbConnector.runQuery(query, _connection);
        addPossibleAnswers(resultSet, question);
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
            question.addAnswer(answer);
        }
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