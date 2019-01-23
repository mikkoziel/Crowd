package interactor;

import java.sql.Date;
import java.sql.SQLException;

import entity.Answer;
import entity.GivenAnswer;
import entity.Profile;

public class GivenAnswerInteractor {
    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public GivenAnswerInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }

    public void logAnswer(GivenAnswer givenAnswer)
    {
        int profileID = givenAnswer.getProfile().getID();
        int questionID = givenAnswer.getQuestion().getQuestionID();
        int answerID = givenAnswer.getAnswer().getAnswerID();

        java.util.Date dateUtil = new java.util.Date();
        Date date = new Date(dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay());

        String query = "Insert into Log(profilID, questionID, answerID, date) values(" + profileID + ", " + questionID + ", " + answerID + ", " + date + ")";

        int result = _dbConnector.updateQuery(query);
        if(result > 0)
            setSuccess("Success");
        else
            setFailure("Fail");
    }

    public void updateAnswerChosenValue(Answer answer)
    {
        answer.increaseChosen();
        String query = "update Answer set chosen = " + answer.getChosen() + " where answerID = " + answer.getAnswerID();
        _dbConnector.updateQuery(query);
    }

    public void givePoints(Answer answer, Profile profile)
    {
        double percentage = (double)answer.getChosen() / (double)answer.getShowed();
        if(percentage < 0.1)
            profile.increasePoints(1);
        else if(percentage < 0.2)
            profile.increasePoints(2);
        else if(percentage < 0.3)
            profile.increasePoints(3);
        else if(percentage < 0.4)
            profile.increasePoints(4);
        else if(percentage < 0.5)
            profile.increasePoints(5);
        else if(percentage < 0.6)
            profile.increasePoints(6);
        else if(percentage < 0.7)
            profile.increasePoints(7);
        else if(percentage < 0.8)
            profile.increasePoints(8);
        else if(percentage < 0.9)
            profile.increasePoints(9);
        else
            profile.increasePoints(10);

        String query = "update Profile set points = " + profile.getPoints() + " where profilID = " + profile.getID();
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
