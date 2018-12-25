package back;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

import gui.AnswerLoger;

public class Loger implements Serializable {

    public Date date;
    public Game game;
    public Question question;

    public Loger(){}

    public void logAnswer(){
        setDate();
        AnswerLoger answerLoger = new AnswerLoger(date, game);
        answerLoger.execute("");
    }

    public void setDate(){
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        java.util.Date date = new Date();
        this.date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Date getDate() {
        return date;
    }

    public Game getGame() {
        return game;
    }

    public Question getQuestion() {
        return question;
    }

    //    public Profil user;
//    private Connector connector;
//    private Connection con;
//
//    public Loger(Profil profil, Connector con){
//        this.user = profil;
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
}
