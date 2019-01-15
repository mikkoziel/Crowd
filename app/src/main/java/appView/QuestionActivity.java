package appView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.Game;
import entity.Profile;
import entity.Question;
import entity.GivenAnswer;

import presenter.PossibleAnswerPresenter;
import presenter.GivenAnswerPresenter;

public class QuestionActivity extends AppCompatActivity {

    public Game game;
    public Profile profile;
    public Question question;
    public ProgressBar progress;
    public Activity activity;
    public LinearLayout answerLayout;
    public LinearLayout.LayoutParams lp;
//    public ListIterator<Question> questionIterator;
//    public ListIterator<Answer> answerIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(appView.R.layout.activity_question);


        Intent intent = getIntent();
        this.game = (Game)intent.getSerializableExtra("game");
        this.profile = (Profile) intent.getSerializableExtra("profile");


        this.question = game.getQuestions().get(game.getIndex());
        game.nextIndex();
        this.progress = findViewById(appView.R.id.progress);
        this.activity = this;
        if(getIntent().hasExtra("answer")){
            GivenAnswer given = (GivenAnswer) intent.getSerializableExtra("answer");
            GivenAnswerPresenter givenAnswerPresenter = new GivenAnswerPresenter(given);
            givenAnswerPresenter.execute();
        }

        this.answerLayout = findViewById(appView.R.id.answerlayout);
        this.lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView questionText = findViewById(appView.R.id.question);
        questionText.setText(question.getQuestion());

        setAnswer();
    }

    @Override
    public void onBackPressed() {
        createAlertDialog("Closing Activity", "Are you sure you want to end the game?");
    }

    public void createAlertDialog(String title, String message){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        game.prevIndex();
                        Intent intent = new Intent(activity, GameActivity.class);
                        intent.putExtra("profile", profile);
                        intent.putExtra("game", game);
                        activity.startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void setAnswer(){
        PossibleAnswerPresenter possibleAnswerPresenter = new PossibleAnswerPresenter(this, question, progress, lp, answerLayout, game, profile);
        possibleAnswerPresenter.execute();
    }
}
