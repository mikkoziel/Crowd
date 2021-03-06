package interactor;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import entity.Answer;
import entity.Question;
import tools.DataBaseConnector;

public class PossibleAnswerInteractor {
    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public PossibleAnswerInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }

    public void setPossibleAnswers(Question question) throws SQLException {
        ArrayList<Integer> ids = selectAnswerID(question);
        int count = 0;
        int previous = 0;
        for(Integer x: ids){
            if(x != previous){
                count++;
            }
            previous = x;
        }
        int numberOfAnswer = (count > 4) ? 4: count;

        setRandomAnswer(ids, question, numberOfAnswer);
        if(question.getDefaultAnswer()){
            defaultAnswer(question);
        }
    }

    private ArrayList<Integer> selectAnswerID(Question question) throws SQLException {
        String query = "select answerID, shown, chosen from Answer where questionID= '" + question.getID() + "' and defaultAnswer = 0";

        ResultSet res = _dbConnector.runQuery(query);
        ArrayList<Integer> ids = new ArrayList<>();

        while(res.next()) {
            int showed = res.getInt("shown");
            int chosen = res.getInt("chosen");
            int id = res.getInt("answerID");
            int quantity;
            if(chosen == 0 || showed == 0)
                quantity = 1;
            else
                quantity = chosen * 100 /showed;
            for(int i = 0; i < quantity; i++){
                ids.add(id);
            }
        }
        return ids;
    }

    private void setRandomAnswer(ArrayList<Integer> ids, Question question, int numberOfAnswer) throws SQLException {
        Random rand = new Random();

        for(int i = 0; i < numberOfAnswer; i++){
            int random = rand.nextInt(ids.size());
            int randomElement = ids.get(random);
//            ids.remove(random);
            ids.removeAll(Arrays.asList(randomElement));
            getRandomAnswer(question, randomElement);
        }
        if(question.getAnswers().size() == numberOfAnswer){
            setSuccess("Answers added");
        }
        else{
            setFailure("Error. Answers not added");
        }

    }

    private void getRandomAnswer(Question question, int randomIndex) throws SQLException {
        String query = "select * from Answer where answerID= '" + randomIndex + "'";

        ResultSet resultSet = _dbConnector.runQuery(query);
        addPossibleAnswers(resultSet, question);
    }

    private void defaultAnswer(Question question) throws SQLException {
        String query = "select * from Answer where questionID= '" + question.getID() + "' and isDefault = 1";

        ResultSet resultSet = _dbConnector.runQuery(query);
        addPossibleAnswers(resultSet, question);
    }

    private void addPossibleAnswers(ResultSet res, Question question) throws SQLException {
        while(res.next()) {
            String content = res.getString("answerText");
            int ID = res.getInt("answerID");
            int type = res.getInt("typeID");
            Boolean defaultAnswer = res.getBoolean("isDefault");
            int showed = res.getInt("shown");
            int chosen = res.getInt("chosen");
            Blob blobImage = res.getBlob("answerPic");

            Answer answer;
            if (res.wasNull()) {
                answer = new Answer(ID, content, type, defaultAnswer, showed,chosen);
            }
            else{
                byte[] byteImage = blobImage.getBytes(1, (int)blobImage.length());
                answer = new Answer(ID, content, type, defaultAnswer, byteImage, showed, chosen);
            }
            question.addAnswer(answer);
            increaseAnswerShowedValue(answer);

        }
    }

    private void increaseAnswerShowedValue(Answer answer) throws SQLException
    {
        String query = "Select * from Answer where answerID = " + answer.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next())
            answer.setShowed(res.getInt("shown"));
        else
        {
            setFailure("Fail!");
            return;
        }

        answer.increaseShowed();

        query = "update Answer set shown = " + answer.getShowed() + " where answerID = " + answer.getID();
        _dbConnector.updateQuery(query);
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
