package interactor;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Logger implements Serializable {

    private DataBaseConnector _dbConnector;

    public Logger(DataBaseConnector dbConnector)
    {
        _dbConnector = dbConnector;
    }

    public Logger()
    {
        _dbConnector = new DataBaseConnector();
    }

    public Date date;
//    public Game game;
//    public Question question;

    public void setDate(){
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        java.util.Date date = new Date();
        java.util.Date dateUtil = new java.util.Date();
        this.date = new java.sql.Date(dateUtil.getTime());
    }

//    public void setGame(Game game) {
//        this.game = game;
//    }

//    public void setQuestion(Question question) {
//        this.question = question;
//    }

    public Date getDate() {
        return date;
    }

//    public Game getGame() {
//        return game;
//    }

//    public Question getQuestion() {
//        return question;
//    }

    //    public Profile user;
//    private Connector connector;
//    private Connection con;
//
//    public Logger(Profile profile, Connector con){
//        this.user = profile;
//        this.connector = con;
////        this.con = connector.establishConnection();
//    }
//
//    public void logAnswer(){
//
//    }
//
//    public class logInBackground extends AsyncTask<String, String, String> {
//        String z = "";
//        Boolean isSuccess = false;
//
//        @Override
//        protected void onPreExecute(){
//        }
//
//        @Override
//        protected String doInBackground(String... params){
////            try {
////                if (con == null) {
////                    z = "Check Your Internet Access!";
////                }
////                else{
////                    String query = "Insert into Log(profilID, questionID, AnswerID, Date) values(" + user.getID();
//////                    ResultSet res = connector.runQuery(query);
//////                    if(res.next()){
//////                        z = "Login succesful";
//////                        isSuccess = true;
//////                        con.close();
//////                    }
//////                    else{
//////                        z = "Inwalid Credentils!";
//////                        isSuccess = false;
//////                    }
////                }
////            }
////            catch(Exception e){
////                isSuccess = false;
////                z = e.getMessage();
////
////            }
////
//
//            return z;
//        }
//
//
//        @Override
//        protected void onPostExecute(String r){
//        }
//    }

    public void log(Connection connection, String query){
        int res = -1;

        res = _dbConnector.updateQuery(query, connection);
        if(res > 0){
            _dbConnector.setResult("Success");
            _dbConnector.success(true);
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            _dbConnector.setResult("Fail");
            _dbConnector.success(false);
        }
    }

}
