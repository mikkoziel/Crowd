package back;

import android.os.AsyncTask;

import java.io.Serializable;

public class Loger implements Serializable {
    public Profil user;
    private Connector connector;

    public Loger(Profil profil, Connector con){
        this.user = profil;
        this.connector = con;
    }

    public void logAnswer(){

    }

    public class logInBackground extends AsyncTask<String, String, String> {
        String z = "";
//        Boolean isSuccess = false;

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected String doInBackground(String... params){


            return z;
        }


        @Override
        protected void onPostExecute(String r){
        }
    }
}
