package appView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.AppContent;
import entity.Game;
import entity.GlobalClass;
import presenter.JsonPresenter;
import presenter.SetQuestionPresenter;
import presenter.UpdateAppContentPresenter;

public class StartGameActivity extends Fragment{
    private ProgressBar _progress;
    private Activity _activity;

    private AppContent _appContent;
    private Game _game;
    private JsonPresenter _jsonPresenter;
    private GlobalClass _global;
    private int _index;

    public void setOnCreate(AppContent appContent, int index, Activity activity){
        this._appContent = appContent;
        this._index = index;
        this._activity =  activity;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start_game);
//
//        Intent intent = getIntent();
//
//        this._activity = this;
//        this._progress = findViewById(R.id.progress);
//        _progress.setVisibility(View.GONE);
//
//        this._jsonPresenter = new JsonPresenter(_activity);
////        this._appContent = _jsonPresenter.getJSON(1);
//        this._global = ((GlobalClass)getApplicationContext());
//        this._appContent = _global.getAppContent();
//
//        this._game = _appContent.getGame(_appContent.getCurrentGameID());
//
//        TextView gameText = findViewById(R.id.game);
//        gameText.setText(_game.getName());
//
//        LinearLayout buttonLayout = findViewById(R.id.chooselayout);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        if(_game.getQuestions().isEmpty()){
//            gameEmpty(buttonLayout, lp);
//        }
//        else {
//            if (_game.getPlayed()) {
//                gamePlayed(buttonLayout, lp);
//            } else {
//                gameNotPlayed(buttonLayout, lp);
//            }
//        }
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_start_game, container, false);
//        Intent intent = getIntent();

//        this._activity = getActivity();
        this._progress = view.findViewById(R.id.progress);
        _progress.setVisibility(View.GONE);

        this._jsonPresenter = new JsonPresenter(_activity);
//        this._appContent = _jsonPresenter.getJSON(1);
//        this._global = ((GlobalClass)getApplicationContext());
//        this._appContent = _global.getAppContent();

        this._game = _appContent.getGame(_appContent.getCurrentGameID());

        TextView gameText = view.findViewById(R.id.game);
        gameText.setText(_game.getName());

        LinearLayout buttonLayout = view.findViewById(R.id.chooselayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if(_game.getQuestions().isEmpty()){
            gameEmpty(buttonLayout, lp);
        }
        else {
            if (_game.getPlayed()) {
                gamePlayed(buttonLayout, lp);
            } else {
                gameNotPlayed(buttonLayout, lp);
            }
        }

        return view;
    }

    public void gamePlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        TextView previousText = new TextView(_activity);
        previousText.setText(R.string.previousGame);

        Button resumeButton = new Button(_activity);
        resumeButton.setText(R.string.resume);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(_activity, QuestionActivity.class);
                //intent.putExtra("game", _game);
                _game.setPlayed(true);
                _appContent.updateGame(_game);
//                intent.putExtra("appContent", _appContent);

//                _jsonPresenter.writeToJson(_appContent, 1);
//                _appContent.destroy();
//                _appContent = null;
//                _global.setAppContent(_appContent);
//                _activity.startActivity(intent);
                ((GameActivity)getActivity()).setViewPager(_index + 1);
            }
        });

//  TODO zrobić cos z tymi przysickami
//        Button newButton = new Button(_activity);
//        newButton.setText(R.string.newGame);
//        newButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
////                Intent intent = new Intent(_activity, QuestionActivity.class);
//                //intent.putExtra("game", _game);
//                _game.setPlayed(true);
//                _game.zeroIndex();
//                _appContent.updateGame(_game);
////                intent.putExtra("appContent", _appContent);
////                _jsonPresenter.writeToJson(_appContent);
//                SetQuestionPresenter setQuestionPresenter = new SetQuestionPresenter(_game, _activity, _progress, _index + 1, _appContent);
//                setQuestionPresenter.execute();
//            }
//        });

        buttonLayout.addView(previousText, lp);
        buttonLayout.addView(resumeButton, lp);
//        buttonLayout.addView(newButton, lp);
    }

    public void gameNotPlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        Button startButton = new Button(_activity);
        startButton.setText(R.string.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(_activity, QuestionActivity.class);
                //intent.putExtra("game", _game);
                _game.setPlayed(true);
                _game.zeroIndex();
                _appContent.updateGame(_game);
//                intent.putExtra("appContent", _appContent);
//                _jsonPresenter.writeToJson(_appContent, 1);
//                _appContent.destroy();
//                _appContent = null;
//                _global.setAppContent(_appContent);
//                _activity.startActivity(intent);
                ((GameActivity)getActivity()).setViewPager(_index + 1);
            }
        });
        buttonLayout.addView(startButton, lp);
    }

    public void gameEmpty( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        TextView text = new TextView(_activity);
        text.setText(R.string.emptyGame);
        Button startButton = new Button(_activity);
        startButton.setText(R.string.back);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _game.setPlayed(false);
                _game.zeroIndex();
                UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(_activity, _progress, _appContent);
                updateAppContentPresenter.execute();
            }
        });
        buttonLayout.addView(text, lp);
        buttonLayout.addView(startButton, lp);
    }

//    public void onBackPressed() {
//        UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(_activity, _progress, _appContent);
//        updateAppContentPresenter.execute();
//    }

    public ProgressBar getProgress() {
        return _progress;
    }
}
