package interactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public void handleGivenAnswer(GivenAnswer givenAnswer) throws SQLException
    {
        logAnswer(givenAnswer);
        increaseAnswerChosenValue(givenAnswer.getAnswer());
        givePoints(givenAnswer.getProfile(), getPercentage(givenAnswer.getAnswer()));
        updateLevel(givenAnswer.getProfile());
        updateMoney(givenAnswer.getProfile());
    }

    //Answer
    private void updateChosenValue(Answer answer) throws SQLException
    {
        String query = "Select * from Answer where answerID = " + answer.getAnswerID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            answer.setChosen(res.getInt("chosen"));
            setSuccess("Chosen updated");
        }
        else
            setFailure("Fail!");
    }

    //Answer
    private void updateShowedValue(Answer answer) throws SQLException
    {
        String query = "Select * from Answer where answerID = " + answer.getAnswerID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            answer.setShowed(res.getInt("showed"));
            setSuccess("Showed updated");
        }
        else
            setFailure("Fail!");
    }

    //Profile
    private void updatePointsValue(Profile profile) throws SQLException
    {
        String query = "Select * from Profile where profilID = " + profile.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            profile.setPoints(res.getInt("points"));
            setSuccess("Points updated");
        }
        else
            setFailure("Fail!");
    }

    //Profile
    private void updateUserLevelValue(Profile profile) throws SQLException
    {
        String query = "Select * from Profile where profilID = " + profile.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            profile.setLevel(res.getInt("userlevel"));
            setSuccess("level updated");
        }
        else
            setFailure("Fail!");
    }

    //given answer
    private void logAnswer(GivenAnswer givenAnswer)
    {
        int profileID = givenAnswer.getProfile().getID();
        int questionID = givenAnswer.getQuestion().getQuestionID();
        int answerID = givenAnswer.getAnswer().getAnswerID();

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String date = dateFormat.format(today);

        String query = "Insert into Log(profilID, questionID, answerID, date) values(" + profileID + ", " + questionID + ", " + answerID + ", '" + date + "')";

        int result = _dbConnector.updateQuery(query);
        if(result > 0)
            setSuccess("Success");
        else
            setFailure("Fail");
    }

    //answer
    private void increaseAnswerChosenValue(Answer answer) throws SQLException
    {
        updateChosenValue(answer);
        answer.increaseChosen();
        String query = "update Answer set chosen = " + answer.getChosen() + " where answerID = " + answer.getAnswerID();
        _dbConnector.updateQuery(query);
    }

    //answer
    private double getPercentage(Answer answer) throws SQLException
    {
        updateChosenValue(answer);
        updateShowedValue(answer);

        if(answer.getDefaultAnswer())
            return 0;
        else
            return (double)answer.getChosen() / (double)answer.getShowed();
    }

    //profile
    private void givePoints(Profile profile, double percentage) throws SQLException
    {
        updatePointsValue(profile);

        if(percentage == 0)
            return;

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


    //Profile
    private void updateLevel(Profile profile) throws SQLException
    {
        updateUserLevelValue(profile);
        int previousLevel = profile.getLevel();

        updatePointsValue(profile);
        int points = profile.getPoints();
        findLevel(10, points, 1, profile);

        //TODO: congratulations alert
        if(previousLevel != profile.getLevel()) {
            setSuccess("Congratulations!");
        }


        String query = "update Profile set userlevel = " + profile.getLevel() + " where profilID = " + profile.getID();
        _dbConnector.updateQuery(query);

        query = "update Profile set missingPoints = " + profile.getMissingPoints() + " where profilID = " + profile.getID();
        _dbConnector.updateQuery(query);

    }

    //Profile
    private void findLevel(int basePoints, int userPoints, int targetLevel, Profile profile)
    {
        if(userPoints < basePoints)
        {
            profile.setLevel(targetLevel);
            profile.setMissingPoints(basePoints - userPoints);
        } else
            findLevel(basePoints*2, userPoints, targetLevel+1, profile);
    }

    //Profile
    private void updateMoney(Profile profile)
    {
        int money = profile.getPoints() * 10;
        String query = "update Profile set money = " + money + " where profilID = " + profile.getID();
        _dbConnector.updateQuery(query);
        profile.setMoney(money);
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
