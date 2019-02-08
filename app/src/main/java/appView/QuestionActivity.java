package appView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.Answer;
import entity.AppContent;
import entity.Game;
import entity.GivenAnswer;

import entity.Question;
import interactor.QuestionInteractor;
import presenter.FilePresenter;
import presenter.NewAnswerPresenter;
import presenter.PossibleAnswerPresenter;
import presenter.GivenAnswerPresenter;

public class QuestionActivity extends AppCompatActivity {

    private ProgressBar _progress;
    private Activity _activity;
    private LinearLayout.LayoutParams _lp;

    private AppContent _appContent;
    private Game _game;
    private Question _question;
    private FilePresenter _filePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(appView.R.layout.activity_question);

        Intent intent = getIntent();
        this._appContent = (AppContent) intent.getSerializableExtra("appContent");
        this._game = _appContent.getCurrentGame();
        this._question = _game.getCurrentQueston();
        this._progress = findViewById(appView.R.id.progress);
        this._activity = this;
        if(getIntent().hasExtra("answer")){
            GivenAnswer given = (GivenAnswer) intent.getSerializableExtra("answer");
            GivenAnswerPresenter givenAnswerPresenter = new GivenAnswerPresenter(given, _activity);
            givenAnswerPresenter.execute();
        }

        this._filePresenter = new FilePresenter();
        this._lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        setContent();
    }

    public void setContent(){
        LinearLayout layout = findViewById(appView.R.id.questionlayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        switch(_question.getType()){
            case 1:
                setTextQuestion(layout, lp);
                setAnswer();
                break;
            case 1003:
                setImageQuestion(layout, lp);
                setAnswer();
                break;
            case 1004:
                setTextQuestion(layout, lp);
                setOpenAnswer(lp);
                break;
        }
//        if(_question.isImageQuestion())
//            setImageQuestion(layout, lp);
//        else
//            setTextQuestion(layout, lp);
    }

    public void setTextQuestion(LinearLayout layout, LinearLayout.LayoutParams lp){
        String questionText = _question.getQuestion();
        TextView question = setTextView(questionText);
        layout.addView(question, lp);
    }

    public void setImageQuestion(LinearLayout layout, LinearLayout.LayoutParams lp){
        String questionText = _question.getQuestion();
        TextView question = setTextView(questionText);

        String imagePath =  _question.getImage();
        byte[] byteImage = _filePresenter.readFromFile(imagePath);

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
        PossibleAnswerPresenter possibleAnswerPresenter = new PossibleAnswerPresenter(this, _progress, _lp, _appContent);
        possibleAnswerPresenter.execute();
    }

    @SuppressLint("SetTextI18n")
    public void setOpenAnswer(LinearLayout.LayoutParams lp) {
        LinearLayout layout = findViewById(appView.R.id.answerlayout);

        final EditText answer = new EditText(_activity);
        Button button = new Button(_activity);
        button.setText("SUBMIT");

        if(_game.getIndex() < _game.getQuestions().size()) {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    NewAnswerPresenter newAnswerPresenter = new NewAnswerPresenter(_activity, answer.getText().toString(), _question.getID());
                    newAnswerPresenter.execute();

                    Intent intent = new Intent(_activity, QuestionActivity.class);
//                    GivenAnswer given = new GivenAnswer(_profile, _question, a);
//                    intent.putExtra("answer", given);
                    intent.putExtra("appContent", _appContent);
                    _activity.startActivity(intent);
                }
            });
        }
        else{
            _game.setPlayed(false);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    Intent intent = new Intent(_activity, EndGameActivity.class);
//                    GivenAnswer given = new GivenAnswer(_profile, _question, a);
//                    intent.putExtra("answer", given);
//                    intent.putExtra("appContent", _appContent);
//                    _activity.startActivity(intent);
                }
            });
        }

        layout.addView(answer, lp);

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
                        intent.putExtra("appContent", _appContent);
                        _activity.startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
