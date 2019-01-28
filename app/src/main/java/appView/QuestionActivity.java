package appView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.Game;
import entity.Profile;
import entity.Question;
import entity.GivenAnswer;

import interactor.QuestionInteractor;
import presenter.PossibleAnswerPresenter;
import presenter.GivenAnswerPresenter;

public class QuestionActivity extends AppCompatActivity {

    public Game game;
    public Profile profile;
    public Question _question;
    public ProgressBar progress;
    public Activity activity;
//    public LinearLayout answerLayout;
    public LinearLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(appView.R.layout.activity_question);


        Intent intent = getIntent();
        this.game = (Game)intent.getSerializableExtra("game");
        this.profile = (Profile) intent.getSerializableExtra("profile");


        this._question = game.getQuestions().get(game.getIndex());
        game.nextIndex();
        this.progress = findViewById(appView.R.id.progress);
        this.activity = this;
        if(getIntent().hasExtra("answer")){
            GivenAnswer given = (GivenAnswer) intent.getSerializableExtra("answer");
            GivenAnswerPresenter givenAnswerPresenter = new GivenAnswerPresenter(given, activity);
            givenAnswerPresenter.execute();
        }

//        this.answerLayout = findViewById(appView.R.id.answerlayout);
        this.lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        setQuestion();
        setAnswer();
    }

    public void setQuestion(){
        LinearLayout layout = findViewById(appView.R.id.questionlayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if(_question.isImageQuestion())
            setImageQuestion(layout, lp);
        else
            setTextQuestion(layout, lp);
    }

    public void setTextQuestion(LinearLayout layout, LinearLayout.LayoutParams lp){
        String questionText = _question.getQuestion();
        TextView question = setTextView(questionText);
        layout.addView(question, lp);
    }

    public void setImageQuestion(LinearLayout layout, LinearLayout.LayoutParams lp){
        String questionText = _question.getQuestion();
        TextView question = setTextView(questionText);

        //TODO Interactor out
        String imagePath =  _question.getImage();
        QuestionInteractor questionInteractor = new QuestionInteractor();

        byte[] byteImage = questionInteractor.readFromFile(imagePath);
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        ImageView questionI = setImageView(bitmapImage);

        layout.addView(questionI, lp);
        layout.addView(question, lp);
    }

    public TextView setTextView(String questionText){
        TextView question = new TextView(this);
        question.setText(questionText);
        question.setTextSize(26);
        question.setGravity(Gravity.CENTER);
        return question;
    }

    public ImageView setImageView(Bitmap questionImage){
        ImageView question = new ImageView(this);
        question.setImageBitmap(questionImage);
        return question;
    }

    public void setAnswer(){
        PossibleAnswerPresenter possibleAnswerPresenter = new PossibleAnswerPresenter(this, _question, progress, lp, game, profile);
        possibleAnswerPresenter.execute();
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
}
