package appView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import presenter.FilePresenter;
import presenter.GivenAnswerPresenter;

public class QuestionActivity extends Fragment {

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
    private int _index;
    private GivenAnswer _given;
    private View _view;

    public void setOnCreate(AppContent appContent, Question question, int index, Game game){
        this._appContent = appContent;
        this._question = question;
        this._index = index;
        this._game = game;
        this._given = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        this._view = inflater.inflate(R.layout.activity_question, container, false);

        this._activity = getActivity();

        this._progress = _view.findViewById(appView.R.id.progress);
        _progress.setVisibility(View.GONE);

        this._row1 = _view.findViewById(appView.R.id.row1);
        this._row2 = _view.findViewById(appView.R.id.row2);
        this._row3 = _view.findViewById(appView.R.id.row3);

        this._filePresenter = new FilePresenter();
        this._lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        setContent();

        return _view;
    }

    public void setContent(){
        LinearLayout layout = _view.findViewById(appView.R.id.questionlayout);
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
            case 1005:
                setSplitQuestion(layout,lp);
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
        TextView question = new TextView(_activity);
        question.setText(questionText);
        question.setTextSize(26);
        question.setGravity(Gravity.CENTER);
        return question;
    }

    public ImageView setImageView(Bitmap questionImage){
        ImageView question = new ImageView(_activity);
        question.setImageBitmap(questionImage);
        return question;
    }

    public void setAnswer(){
        int _i = 0;

        for(Answer answer : _question.getAnswers()){
            Button button = setButtons(answer.getAnswer(), answer);
            _i = addButtonToView(button, _i);
        }
    }

    private Button setButtons(String answerText, final Answer a){
        Button answer = new Button(_activity);

        if(answerText != null && !answerText.equals("")) {
            answer.setText(String.format("\n%s\n", answerText));
        }
        if(a.isImageAnswer()){
            Drawable image = new BitmapDrawable(_activity.getResources(), BitmapFactory.decodeByteArray(a.getImage(), 0, a.getImage().length));
            answer.setCompoundDrawablesWithIntrinsicBounds( image, null, null, null);
        }

        _game.updateQuestion(_question);
        _appContent.updateGame(_game);

        answer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lockButtons();
                GivenAnswer given = new GivenAnswer(_appContent.getProfile(), _question, a);
                handleGiven(given);

                ((GameActivity)getActivity()).setViewPager(_index + 1);
                unlockButtons();
            }
        });
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
        LinearLayout layout = _view.findViewById(appView.R.id.answerlayout);

        final EditText answer = new EditText(_activity);
        answer.setGravity(Gravity.CENTER_HORIZONTAL);
        answer.requestFocus();
        final Button button = new Button(_activity);
        button.setText("SUBMIT");

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button.setClickable(false);
                GivenAnswer given = new GivenAnswer(_appContent.getProfile(), _question, _question.getAnswers().get(0), answer.getText().toString());
                handleGiven(given);

                ((GameActivity)getActivity()).setViewPager(_index + 1);
                button.setClickable(true);
            }
        });

        layout.addView(answer, lp);
        layout.addView(button, lp);

    }

    public void setSplitQuestion(LinearLayout layout, LinearLayout.LayoutParams lp){
        String questionText = _question.getQuestion();

        TextView question = setTextView(questionText);

        ImageView questionI = new ImageView(_activity);
//        questionI.setImageBitmap(questionImage);

        layout.addView(question, lp);
    }

    public void handleGiven(GivenAnswer given){
        if(given != null){
            GivenAnswerPresenter givenAnswerPresenter = new GivenAnswerPresenter(given, getActivity());
            givenAnswerPresenter.execute();
        }
    }

    public void lockButtons(){
        LinearLayout ll = _view.findViewById(appView.R.id.answerlayout);
        int count = ll.getChildCount();
        for(int i=0; i<count; i++) {
            LinearLayout tmp = (LinearLayout) ll.getChildAt(i);
            for(int j =0; j< tmp.getChildCount(); j++) {
                Button v = (Button) tmp.getChildAt(j);
                v.setClickable(false);
            }
        }
    }

    public void unlockButtons(){
        LinearLayout ll = _view.findViewById(appView.R.id.answerlayout);
        int count = ll.getChildCount();
        for(int i=0; i<count; i++) {
            LinearLayout tmp = (LinearLayout) ll.getChildAt(i);
            for(int j =0; j< tmp.getChildCount(); j++) {
                Button v = (Button) tmp.getChildAt(j);
                v.setClickable(true);
            }
        }
    }

}
