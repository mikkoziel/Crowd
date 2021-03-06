package interactor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import entity.Answer;
import entity.GivenAnswer;
import entity.Profile;
import tools.DataBaseConnector;

public class GivenAnswerInteractor {
    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;
    private Boolean _isNewLevel;
    private String _levelInfo;

    public GivenAnswerInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
        this._isNewLevel = false;
        this._levelInfo = null;
    }

    public void handleGivenAnswer(GivenAnswer givenAnswer) throws SQLException
    {
        logAnswer(givenAnswer);
        increaseAnswerChosenValue(givenAnswer.getAnswer());
        givePoints(givenAnswer.getProfile(), getPercentage(givenAnswer.getAnswer()));
        updateLevel(givenAnswer.getProfile());
    }

    //Answer from db
    public void updateChosenValue(Answer answer) throws SQLException
    {
        String query = "Select * from Answer where answerID = " + answer.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            answer.setChosen(res.getInt("chosen"));
            setSuccess("Chosen updated");
        }
        else
            setFailure("Fail!");
    }

    //Answer from db
    public void updateShowedValue(Answer answer) throws SQLException
    {
        String query = "Select * from Answer where answerID = " + answer.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            answer.setShowed(res.getInt("shown"));
            setSuccess("Showed updated");
        }
        else
            setFailure("Fail!");
    }

    //Profile from db
    public void updatePointsValue(Profile profile) throws SQLException
    {
        String query = "Select * from Profile where profileID = " + profile.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            profile.setPoints(res.getInt("points"));
            setSuccess("Points updated");
        }
        else
            setFailure("Fail!");
    }

    //Profile from db
    public void updateUserLevelValue(Profile profile) throws SQLException
    {
        String query = "Select * from Profile where profileID = " + profile.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            profile.setLevel(res.getInt("userLevel"));
            setSuccess("level updated");
        }
        else
            setFailure("Fail!");
    }

    public void updateMissingPointsValue(Profile profile) throws SQLException
    {
        String query = "Select * from Profile where profileID = " + profile.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            profile.setMissingPoints(res.getInt("missingPoints"));
            setSuccess("missing points updated");
        }
        else
            setFailure("Fail!");
    }

    public void updateMoneyValue(Profile profile) throws SQLException
    {
        String query = "Select * from Profile where profileID = " + profile.getID();
        ResultSet res = _dbConnector.runQuery(query);
        if (res.next()) {
            profile.setMoney(res.getInt("money"));
            setSuccess("money updated");
        }
        else
            setFailure("Fail!");
    }

    //given answer
    private void logAnswer(GivenAnswer givenAnswer)
    {
        int profileID = givenAnswer.getProfile().getID();
        int questionID = givenAnswer.getQuestion().getID();
        int answerID = givenAnswer.getAnswer().getID();
        String answerText = givenAnswer.getText();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String date = dateFormat.format(today);

        String query = "Insert into Log(profileID, questionID, answerID, answerDate, answerText) values(" + profileID
                + ", " + questionID + ", " + answerID + ", '" + date + "', '" + answerText +"')";

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
        if(!isSuccess())
            return;
        answer.increaseChosen();
        String query = "update Answer set chosen = " + answer.getChosen() + " where answerID = " + answer.getID();
        _dbConnector.updateQuery(query);
    }

    //answer
    private double getPercentage(Answer answer) throws SQLException
    {
        updateChosenValue(answer);
        updateShowedValue(answer);
        if(!isSuccess())
            return 0;

        if(answer.getDefaultAnswer())
            return 0;
        else
            return (double)answer.getChosen() / (double)answer.getShowed();
    }

    //profile
    private void givePoints(Profile profile, double percentage) throws SQLException
    {
        updatePointsValue(profile);
        if(!isSuccess())
            return;

        if(percentage == 0)
            return;

        int points;

        if(percentage < 0.1)
            points = -2;
        else if(percentage < 0.2)
            points = -1;
        else if(percentage < 0.3)
            points = 1;
        else if(percentage < 0.4)
            points = 2;
        else if(percentage < 0.5)
            points = 3;
        else if(percentage < 0.6)
            points = 4;
        else if(percentage < 0.7)
            points = 5;
        else if(percentage < 0.8)
            points = 6;
        else if(percentage < 0.9)
            points = 7;
        else
            points = 8;

        profile.increasePoints(points);

        String query = "update Profile set points = " + profile.getPoints() + " where profileID = " + profile.getID();

        updateMoney(profile, points);
        _dbConnector.updateQuery(query);
    }


    //Profile
    private void updateLevel(Profile profile) throws SQLException
    {
        updateUserLevelValue(profile);
        if(!isSuccess())
            return;
        int previousLevel = profile.getLevel();

        updatePointsValue(profile);
        int points = profile.getPoints();
        findLevel(10, points, 1, profile);


        if(previousLevel != profile.getLevel())
        {
            if(profile.getLevel() - previousLevel > 0)
                setLevelInfo("Congratulations! You've gained " + profile.getLevel() + " level.");
            else
                setLevelInfo("Your level has dropped. If you not sure of the answer, better chose 'non of above' option.");
        }


        String query = "update Profile set userLevel = " + profile.getLevel() + " where profileID = " + profile.getID();
        _dbConnector.updateQuery(query);

        query = "update Profile set missingPoints = " + profile.getMissingPoints() + " where profileID = " + profile.getID();
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
    private void updateMoney(Profile profile, int points) throws SQLException
    {
        if (points <= 0)
            return;

        updateMoneyValue(profile);
        if(!isSuccess())
            return;

        int money = points * 10;
        profile.increaseMoney(money);
        String query = "update Profile set money = " + profile.getMoney() + " where profileID = " + profile.getID();
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

    private void setLevelInfo(String message)
    {
        _isNewLevel = true;
        _levelInfo = message;
    }

    public Boolean isSuccess(){return _isSuccess;}
    public String getResult(){return _result;}
    public Boolean isNewLevel(){return _isNewLevel;}
    public String getLevelInfo(){return _levelInfo;}


    public void endWork()
    {
        try {
            _dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
