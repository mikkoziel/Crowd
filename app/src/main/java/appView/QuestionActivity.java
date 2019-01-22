package appView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.Answer;
import entity.Game;
import entity.Profile;
import entity.Question;
import entity.GivenAnswer;

import presenter.PossibleAnswerPresenter;
import presenter.GivenAnswerPresenter;

public class QuestionActivity extends AppCompatActivity {

    public Game _game;
    public Profile _profile;
    public Question _question;
    public ProgressBar progress;
    public Activity _activity;
    public LinearLayout answerLayout;
    public LinearLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(appView.R.layout.activity_question);


        Intent intent = getIntent();
        this._game = (Game)intent.getSerializableExtra("game");
        this._profile = (Profile) intent.getSerializableExtra("profile");


        this._question = _game.getQuestions().get(_game.getIndex());
        _game.nextIndex();
        this.progress = findViewById(appView.R.id.progress);
        progress.setVisibility(View.GONE);
        this._activity = this;

        if(getIntent().hasExtra("answer")){
            GivenAnswer given = (GivenAnswer) intent.getSerializableExtra("answer");
            GivenAnswerPresenter givenAnswerPresenter = new GivenAnswerPresenter(given);
            givenAnswerPresenter.execute();
        }

        this.answerLayout = findViewById(appView.R.id.answerlayout);
        this.lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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

        byte[] byteImage =  _question.getImage();
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        ImageView questionI = setImageView(bitmapImage);

        layout.addView(questionI, lp);
        layout.addView(question, lp);
    }

    public TextView setTextView(String questionText){
        TextView question = new TextView(this);
        question.setText(questionText);
        question.setGravity(Gravity.CENTER);
        return question;
    }

    public ImageView setImageView(Bitmap questionImage){
        ImageView question = new ImageView(this);
        question.setImageBitmap(questionImage);
        return question;
    }

    public void setAnswer(){
//        PossibleAnswerPresenter possibleAnswerPresenter = new PossibleAnswerPresenter(this, _question, progress, lp, answerLayout, game, profile);
//        possibleAnswerPresenter.execute();
        for(Answer x: _question.getAnswers()){
            Button answer = setButton(x.getAnswer(), x);
            answerLayout.addView(answer, lp);
        }
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
                        _game.prevIndex();
                        Intent intent = new Intent(_activity, GameActivity.class);
                        intent.putExtra("profile", _profile);
                        intent.putExtra("game", _game);
                        _activity.startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    private Button setButton(String answerText, final Answer a){
        Button answer = new Button(_activity);

        answer.setText(answerText);
        if(a.isImageAnswer()){
            Drawable image = new BitmapDrawable(_activity.getResources(), BitmapFactory.decodeByteArray(a.getImage(), 0, a.getImage().length));
            answer.setCompoundDrawablesWithIntrinsicBounds( image, null, null, null);
        }

        if(_game.getIndex() < _game.getQuestions().size()) {
            answer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(_activity, QuestionActivity.class);
                    GivenAnswer given = new GivenAnswer(_profile, _question, a);
                    intent.putExtra("answer", given);
                    intent.putExtra("profile", _profile);
                    intent.putExtra("game", _game);
                    _activity.startActivity(intent);
                }
            });
        }
        else{
            _game.setPlayed(false);
            answer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(_activity, EndGameActivity.class);
                    GivenAnswer given = new GivenAnswer(_profile, _question, a);
                    intent.putExtra("answer", given);
                    intent.putExtra("profile", _profile);
                    intent.putExtra("game", _game);
                    _activity.startActivity(intent);
                }
            });
        }
        return answer;
    }

}
