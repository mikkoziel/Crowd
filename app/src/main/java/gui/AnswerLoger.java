package gui;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

public class AnswerLoger extends AsyncTask<String, Button, String> {

    public AnswerLoger(){

    }

    @Override
    protected void onPreExecute(){
//        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String r) {
    }

}
