package appView;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.Game;
import entity.Profile;
import presenter.SetQuestionPresenter;

public class GameActivity extends AppCompatActivity {
    public Game game;
    public Profile profile;
    public ProgressBar progress;
//    public Intent intent;
    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent inetnt = getIntent();
        this.game = (Game)inetnt.getSerializableExtra("game");
        this.profile = (Profile) inetnt.getSerializableExtra("profile");
        this.activity = this;

        this.progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
//        this.intent = new Intent(this, QuestionActivity.class);
//        intent.putExtra("profile", profile);
//        intent.putExtra("game", game);

        TextView gameText = (TextView) findViewById(R.id.game);
        gameText.setText(game.getGameName());

        LinearLayout buttonLayout = (LinearLayout)findViewById(R.id.chooselayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if(game.getQuestions().isEmpty()){
            gameEmpty(buttonLayout, lp);
        }
        else {
            if (game.getPlayed()) {
                gamePlayed(buttonLayout, lp);
            } else {
                gameNotPlayed(buttonLayout, lp);
            }
        }

    }

    public void gamePlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        TextView previousText = new TextView(this);
        previousText.setText("Do you want to continue previous game?");

        Button resumeBttn = new Button(this);
        resumeBttn.setText("RESUME");
        resumeBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("setter", false);
                intent.putExtra("profile", profile);
                intent.putExtra("game", game);
                game.setPlayed(true);
                activity.startActivity(intent);
            }
        });

        Button newBttn = new Button(this);
        newBttn.setText("NEW GAME");
        newBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("setter", true);
                intent.putExtra("profile", profile);
                intent.putExtra("game", game);
                game.setPlayed(true);
                game.zeroIndex();
                SetQuestionPresenter setQuestionPresenter = new SetQuestionPresenter(game, activity, progress, intent);
                setQuestionPresenter.execute("");
//                activity.startActivity(intent);
            }
        });

        buttonLayout.addView(previousText, lp);
        buttonLayout.addView(resumeBttn, lp);
        buttonLayout.addView(newBttn, lp);
    }

    public void gameNotPlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        Button startBttn = new Button(this);
        startBttn.setText("START");
        startBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("setter", true);
                intent.putExtra("profile", profile);
                intent.putExtra("game", game);
                game.setPlayed(true);
                game.zeroIndex();
                activity.startActivity(intent);
            }
        });

        buttonLayout.addView(startBttn, lp);
    }

    public void gameEmpty( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        TextView text = new TextView(this);
        text.setText("Game Empty. Go Back To Menu");
        Button startBttn = new Button(this);
        startBttn.setText("BACK");
        startBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, TabMenuActivity.class);
                intent.putExtra("profile", profile);
                game.setPlayed(false);
                game.zeroIndex();
                activity.startActivity(intent);
            }
        });
        buttonLayout.addView(text, lp);
        buttonLayout.addView(startBttn, lp);
    }


//    public class CheckLogin extends AsyncTask<String, String, String> {
//        String z = "";
//        Boolean isSuccess = false;
//
//        @Override
//        protected void onPreExecute(){
//            progress.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(String... params){
//            Connection con;
//            try {
//                z = setQ(game, connector);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                con = connector.connectionClass();
//                if (con == null) {
//                    z = "Check Your Internet Access!";
//                }
//                else{
//                    z = setQ(game, connector);
//                    String query = "select * from Profile where Name= '" + username + "' and password = '" + password + "'";
//                    ResultSet res = connector.runQuery(query, con);
//                    if(res.next()){
//                        con.close();
//                    }
//                    else{
//                        z = "Inwalid Credentils!";
//                        isSuccess = false;
//                    }
//                }
//            }
//            catch(Exception e){
//                isSuccess = false;
//                z = e.getMessage();
//
//            }
//
//
//            return z;
//        }
//
//
//        @Override
//        protected void onPostExecute(String r){
//            progress.setVisibility(View.GONE);
//            Toast.makeText(GameActivity.this, r, Toast.LENGTH_SHORT).show();
//            if(isSuccess){
//                Toast.makeText(GameActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
//                startActivity(intent);
//            }
//        }
//
//        public String setQ(Game game, Connector connector) throws SQLException {
//            String z = "";

//            Connection con = connector.connectionClass();
//            if (connector.getConnection() == null) {
//                z = "Check Your Internet Access!";
//            } else {
//                String query = "select * from Question where gameID = " + game.getGameID() + ";";
//                ResultSet res = connector.runQuery(query);
//                game.setQuestions(res);
//                Thread thread = new Thread(new Runnable(){
//                    @Override
//                    public void run(){
//                        try {
//                            setAnswers();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                thread.start();
//                connector.getConnection().close();
//                z = "All alright";
//            }
//            return z;
//        }
//    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("profile", profile);
        this.startActivity(intent);
    }
}
