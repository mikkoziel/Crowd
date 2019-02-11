package appView;

import android.annotation.SuppressLint;
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

    @SuppressLint("StaticFieldLeak")
    private LinearLayout _row1;
    @SuppressLint("StaticFieldLeak")
    private LinearLayout _row2;
    @SuppressLint("StaticFieldLeak")
    private LinearLayout _row3;

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
        _progress.setVisibility(View.GONE);

        this._row1 = findViewById(appView.R.id.row1);
        this._row2 = findViewById(appView.R.id.row2);
        this._row3 = findViewById(appView.R.id.row3);
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
//        PossibleAnswerPresenter possibleAnswerPresenter = new PossibleAnswerPresenter(this, _progress, _lp, _appContent);
//        possibleAnswerPresenter.execute();
        int _i = 0;

        for(Answer answer : _question.getAnswers()){
            Button button = setButtons(answer.getAnswer(), answer);
            _i = addButtonToView(button, _i);
        }
    }

    private Button setButtons(String answerText, final Answer a){
        Button answer = new Button(_activity);

        answer.setText(String.format("\n%s\n", answerText));
        if(a.isImageAnswer()){
            Drawable image = new BitmapDrawable(_activity.getResources(), BitmapFactory.decodeByteArray(a.getImage(), 0, a.getImage().length));
            answer.setCompoundDrawablesWithIntrinsicBounds( image, null, null, null);
        }

        _game.updateQuestion(_question);
        _appContent.updateGame(_game);

        if(_game.getIndex() < _game.getQuestions().size()) {
            answer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(_activity, QuestionActivity.class);
                    GivenAnswer given = new GivenAnswer(_appContent.getProfile(), _question, a);
                    intent.putExtra("answer", given);
                    intent.putExtra("appContent", _appContent);
                    _activity.startActivity(intent);
                }
            });
        }
        else{
            _game.setPlayed(false);
            answer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(_activity, EndGameActivity.class);
                    GivenAnswer given = new GivenAnswer(_appContent.getProfile(), _question, a);
                    intent.putExtra("answer", given);
                    intent.putExtra("appContent", _appContent);
                    _activity.startActivity(intent);
                }
            });
        }
        return answer;
    }

    private int addButtonToView(Button answer, int _i) {

        if(_i < 2){
            _row1.addView(answer, _lp);
        }else{
            if(_i < 4){
                _row2.addView(answer, _lp);
            }
            else{
                _row3.addView(answer, _lp);
            }
        }
        return _i + 1;
    }

    @SuppressLint("SetTextI18n")
    public void setOpenAnswer(LinearLayout.LayoutParams lp) {
        LinearLayout layout = findViewById(appView.R.id.answerlayout);

        final EditText answer = new EditText(_activity);
        final Button button = new Button(_activity);
        button.setText("SUBMIT");

        if(_game.getIndex() < _game.getQuestions().size()) {
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    NewAnswerPresenter newAnswerPresenter = new NewAnswerPresenter(_activity, answer.getText().toString(), _question, _appContent, button, 0);
                    newAnswerPresenter.execute();
                }
            });
        }
        else{
            _game.setPlayed(false);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    NewAnswerPresenter newAnswerPresenter = new NewAnswerPresenter(_activity, answer.getText().toString(), _question, _appContent, button, 1);
                    newAnswerPresenter.execute();
                }
            });
        }

        layout.addView(answer, lp);
        layout.addView(button, lp);

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
