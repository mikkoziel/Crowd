package interactor;

import java.sql.SQLException;

public class NewAnswerInteractor {
    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public NewAnswerInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }

    public void createNewAnswer(String answer, int questionID){
        String query1 = "Insert into Answer(questionID, anserText, used, percentageUsed, typeID, answerImage, defaultAnswer, chosen, showed) values(" + questionID +", "+ answer +", 0, NULL, 3, NULL, 0, 0, 0)" ;
        int result = _dbConnector.updateQuery(query1);
        if(result > 0)
            setSuccess("Answer added");
        else
            setFailure("Answer not added");
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
