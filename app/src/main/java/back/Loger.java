package back;

import android.os.AsyncTask;
import android.view.View;

import java.sql.Connection;
import java.sql.ResultSet;

public class Loger {
    public Profil user;
    private Connector connector;
    private Connection con;

    public Loger(Profil profil, Connector con){
        this.user = profil;
        this.connector = con;
        this.con = connector.connectionClass();
    }

    public void logAnswer(){

    }

    public class logInBackground extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected String doInBackground(String... params){
            try {
                if (con == null) {
                    z = "Check Your Internet Access!";
                }
                else{
                    String query = "Insert into Log(profilID, questionID, AnswerID, Date) values(" + user.getID();
                    ResultSet res = connector.runQuery(query, con);
                    if(res.next()){
                        z = "Login succesful";
                        isSuccess = true;
                        con.close();
                    }
                    else{
                        z = "Inwalid Credentils!";
                        isSuccess = false;
                    }
                }
            }
            catch(Exception e){
                isSuccess = false;
                z = e.getMessage();

            }


            return z;
        }


        @Override
        protected void onPostExecute(String r){
        }
    }
}
