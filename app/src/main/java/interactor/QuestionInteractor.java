package interactor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Game;
import entity.Question;

public class QuestionInteractor {
    private DataBaseConnector _dbConnector;

    public QuestionInteractor(DataBaseConnector dbConnector)
    {
        _dbConnector = dbConnector;
    }


    private ResultSet getQuestions(Game game, Connection connection){
        String query = "select * from Question where gameID = " + game.getGameID() + ";";
        ResultSet res = _dbConnector.runQuery(query, connection);
        return res;
    }


    public String setQuestions(Game game) throws SQLException {
        ResultSet res = null;
        Connection connection = _dbConnector.makeConnection();
        Boolean isConnect = _dbConnector.checkConnection(connection);

        if (isConnect) {
            res = getQuestions(game, connection);

            while(res.next()) {
                String content = res.getString("question");
                int ID = res.getInt("questionID");
                int type = res.getInt("typeID");

                Question question = new Question(content, ID, type);

//                AnswerSetter answerSetter = new AnswerSetter(question, this);
//                answerSetter.execute("");

                game.addQuestion(question);
            }
            _dbConnector.setResult("Game Starting");
            _dbConnector.success(true);
        }
        else{
            _dbConnector.setResult("Something went wrong");
            _dbConnector.success(false);
        }

        return _dbConnector.getResult();
    }


}
