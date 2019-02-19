//package interactor;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import entity.Answer;
//import tools.DataBaseConnector;
//
//public class NewAnswerInteractor {
//    private DataBaseConnector _dbConnector;
//    private String _result;
//    private Boolean _isSuccess;
//
//    public NewAnswerInteractor()
//    {
//        this._dbConnector = new DataBaseConnector();
//        this._result = null;
//        this._isSuccess = false;
//    }
//
//    public Answer createNewAnswer(String answer, int questionID){
//        String query1 = "Insert into Answer(questionID, answerText, used, percentageUsed, typeID, answerImage, defaultAnswer, chosen, showed) values(" + questionID +", "+ answer +", 0, 0.0, 3, NULL, 0, 1, 1)" ;
//        Statement result = _dbConnector.updateQueryWithRow(query1);
//        Answer answerInDB = null;
//        if(result == null)
//            setFailure("Answer not added");
//        else{
//            setSuccess("Answer added");
//            try (ResultSet rs = result.getGeneratedKeys()) {
//                answerInDB =  getOpenAnswer(rs);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return answerInDB;
//
//    }
//
//    private Answer getOpenAnswer(ResultSet res) throws SQLException {
//        if (res.next()) {
//            String content = res.getString("answerText");
//            int ID = res.getInt("answerID");
//            int type = res.getInt("typeID");
//            Boolean defaultAnswer = res.getBoolean("defaultAnswer");
//            int showed = res.getInt("showed");
//            int chosen = res.getInt("chosen");
//
//            return new Answer(ID, content, type, defaultAnswer, showed, chosen);
//        }
//
//        return null;
//    }
//
//
//    private void setSuccess(String message)
//    {
//        _result = message;
//        _isSuccess = true;
//    }
//
//    private void setFailure(String message)
//    {
//        _result = message;
//        _isSuccess = false;
//    }
//
//    public Boolean isSuccess(){return _isSuccess;}
//    public String getResult(){return _result;}
//
//
//    public void endWork()
//    {
//        try {
//            _dbConnector.closeConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
