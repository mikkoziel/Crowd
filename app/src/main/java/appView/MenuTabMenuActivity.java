package appView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.sql.Connection;

import entity.Profile;
import entity.Game;
import interactor.Logger;
import presenter.SetQuestionPresenter;

public class MenuTabMenuActivity extends Fragment {

    public Profile profile;
    public Logger logger;
    public Connection con;
    public Intent intent;
    public Intent thisIntent;
    public ProgressBar progress;
    public Activity activity;

    public void setOnCreate(Activity activity, Intent intent){
        this.activity = activity;
        this.thisIntent = intent;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(appView.R.layout.menu_tab_menu, container, false);

//        this.connector = (Connector)thisIntent.getSerializableExtra("connector");
        this.profile = (Profile) thisIntent.getSerializableExtra("profile");

        this.progress = rootView.findViewById(appView.R.id.progressMenu);
        progress.setVisibility(View.GONE);

//        this.logger = new Logger(profile, connector);
        this.intent = new Intent(activity, GameActivity.class);
        intent.putExtra("profile", profile);
        //    intent.putExtra("logger", logger);*

        LinearLayout ll = (LinearLayout) rootView.findViewById(appView.R.id.layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

//        ArrayList<?> games = (ArrayList<?>) thisIntent.getSerializableExtra("games");
//        ListIterator<?> iterator = profile.getGames().listIterator();

        for (final Game game : profile.getGames()) {
//            while (iterator.hasNext()) {
//            final Game game = (Game) iterator.next();
            Button gameButton = new Button(activity);
            gameButton.setText(game.getGameName());
            gameButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    if (!game.getPlayed() || !createAlertDialog("Game Activity", "Do you want to continue previous game?")){
                    SetQuestionPresenter setQuestionPresenter = new SetQuestionPresenter(game, activity, progress, intent);
                    setQuestionPresenter.execute("");
                    //                    while(game.getQuestions().isEmpty()){
//
//                    }
//
//                    setAnswers(game);

//                        SetQuestions setQuestions = new SetQuestions(game);
//                        setQuestions.execute("");
                }
//                }
            });
            ll.addView(gameButton, lp);
//            return rootView;

        }
        return rootView;
    }


    public static class MenuActivity extends AppCompatActivity {

        public Profile profile;
        public Logger logger;
        public Connection con;
        public Intent intent;
        public ProgressBar progress;
        public Activity activity;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(appView.R.layout.activity_menu);

            Intent thisIntent = getIntent();
    //        this.connector = (Connector)thisIntent.getSerializableExtra("connector");
            this.profile = (Profile)thisIntent.getSerializableExtra("profile");

            this.progress = findViewById(appView.R.id.progressMenu);
            progress.setVisibility(View.GONE);

    //        this.logger = new Logger(profile, connector);
            this.intent = new Intent(this, GameActivity.class);
            intent.putExtra("profile", profile);
            this.activity = this;
        //    intent.putExtra("logger", logger);*

            LinearLayout ll = (LinearLayout)findViewById(appView.R.id.layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    //        ArrayList<?> games = (ArrayList<?>) thisIntent.getSerializableExtra("games");
    //        ListIterator<?> iterator = profile.getGames().listIterator();

            for (final Game game : profile.getGames()) {
    //            while (iterator.hasNext()) {
    //            final Game game = (Game) iterator.next();
                Button gameButton = new Button(this);
                gameButton.setText(game.getGameName());
                gameButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
    //                    if (!game.getPlayed() || !createAlertDialog("Game Activity", "Do you want to continue previous game?")){
                        SetQuestionPresenter setQuestionPresenter = new SetQuestionPresenter(game, activity, progress, intent);
                        setQuestionPresenter.execute("");
                        //                    while(game.getQuestions().isEmpty()){
    //
    //                    }
    //
    //                    setAnswers(game);

    //                        SetQuestions setQuestions = new SetQuestions(game);
    //                        setQuestions.execute("");
                    }
    //                }
                });
                ll.addView(gameButton, lp);
            }

    //        Button bttn = new Button(this);
    //        bttn.setText("Try");
    //        final Intent inten = new Intent(this, QuestionActivity.class);
    //        bttn.setOnClickListener(new View.OnClickListener() {
    //            public void onClick(View v) {
    //                startActivity(inten);
    //            }
    //        });
    //        ll.addView(bttn, lp);
        }

    //    public void setAnswers(Game game){
    ////        int tmp = 0;
    //        //int size = game.getQuestions().size();
    //        for(Question x: game.getQuestions()){
    ////            if(x == null){
    ////                wait();
    ////            }
    //            PossibleAnswerPresenter answerSetter = new PossibleAnswerPresenter(x, connector, progress);
    //            answerSetter.execute("");
    ////            tmp++;
    //        }
    ////        while(game.getQuestions().get(tmp).getAnswers().isEmpty())
    //        activity.startActivity(intent);
    //    }

    //    public class SetQuestions extends AsyncTask<String, String, String> {
    //        String z = "";
    //        Boolean isSuccess = false;
    //        Game game;
    //
    //        SetQuestions(Game game){
    //            this.game = game;
    //        }
    //
    //        @Override
    //        protected void onPreExecute(){
    //            progress.setVisibility(View.VISIBLE);
    //        }
    //
    //        @Override
    //        protected String doInBackground(String... params){
    //           // try {
    //            try {
    //                z = setQ(game, connector);
    //                z = "Game starting";
    //                isSuccess = true;
    //            } catch (SQLException e) {
    //                e.printStackTrace();
    //            }
    //            // z = game.setQuestions(connector);
    ////            } catch (SQLException e) {
    ////                e.printStackTrace();
    ////                z = "Exception";
    ////            }
    //            return z;
    //        }
    //
    //        @Override
    //        protected void onPostExecute(String r){
    //            progress.setVisibility(View.GONE);
    //            Toast.makeText(MenuActivity.this, r, Toast.LENGTH_SHORT).show();
    //            if(isSuccess){
    //                Toast.makeText(MenuActivity.this, "Game starting", Toast.LENGTH_LONG).show();
    //                intent.putExtra("game", game);
    //                startActivity(intent);
    //            }
    //        }
    //
    //        public String setQ(Game game, Connector connector) throws SQLException {
    //            String z = "";
    //
    ////            Connection con = connector.connectionClass();
    ////            if (connector.getConnection() == null) {
    ////                z = "Check Your Internet Access!";
    ////            }
    ////            else {
    ////                String query = "select * from Question where gameID = " + game.getGameID() + ";";
    ////                ResultSet res = connector.runQuery(query);
    ////                game.setQuestions(res);
    //////                Thread thread = new Thread(new Runnable(){
    //////                    @Override
    //////                    public void run(){
    //////                        try {
    //////                            setAnswers();
    //////                        } catch (SQLException e) {
    //////                            e.printStackTrace();
    //////                        }
    //////                    }
    //////                });
    //////                thread.start();
    ////                connector.getConnection().close();
    ////                z = "All alright";
    ////            }
    //
    //            return z;
    //        }
    //
    ////        public void setAnswers() throws SQLException {
    ////            for(Question question : game.getQuestions()){
    ////                String query = "select top 3 * from Answer where questionID = " + question.getQuestionID() + ";";
    ////                ResultSet res = connector.runQuery(query);
    ////                question.setAnswers(res);
    ////            }
    ////        }
    //    }
        @Override
        public void onBackPressed() {
            createAlertDialog("Closing Activity", "Are you sure you want to logout?");
        }

        public Boolean createAlertDialog(String title, String message){
            final Boolean[] answer = new Boolean[]{Boolean.TRUE};
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            answer[0] = false;
                        }

                    })
                    .show();
            return answer[0];
        }
    }
}
