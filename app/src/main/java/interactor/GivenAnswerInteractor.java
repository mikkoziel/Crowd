package interactor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class GivenAnswerInteractor {
    private DataBaseConnector _dbConnector;
    private Connection _connection;
    private String _result;
    private Boolean _isSuccess;
    private Date _date;

    public GivenAnswerInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._connection = _dbConnector.makeConnection();
        this._result = null;
        this._isSuccess = false;

    }

    public void logAnswer(int profileID, int questionID, int answerID)
    {
        setDate();
        String query = "Insert into Log(profilID, questionID, answerID, date) values(" + profileID + ", " + questionID + ", " + answerID + ", " +_date + ")";

        if(!_dbConnector.checkConnection(_connection))
            _connection = _dbConnector.makeConnection();

        int result = _dbConnector.updateQuery(query, _connection);
        if(result > 0)
            setSuccess("Success");
        else
            setFailure("Fail");
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

    private void setDate(){
        java.util.Date dateUtil = new java.util.Date();
        this._date = new java.sql.Date(dateUtil.getTime());
    }

    public void endWork() throws SQLException
    {
        _connection.close();
    }

}
