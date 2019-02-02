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

import entity.AppContent;
import entity.Game;
import presenter.SetQuestionPresenter;
import presenter.UpdateAppContentPresenter;

public class GameActivity extends AppCompatActivity {
    private ProgressBar _progress;
    private Activity _activity;

    private AppContent _appContent;
    private Game _game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        this._appContent = (AppContent) intent.getSerializableExtra("appContent");
        this._game = _appContent.getGame(_appContent.getCurrentGameID());
        this._activity = this;

        this._progress = findViewById(R.id.progress);
        _progress.setVisibility(View.GONE);

        TextView gameText = findViewById(R.id.game);
        gameText.setText(_game.getName());

        LinearLayout buttonLayout = findViewById(R.id.chooselayout);
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

    }

    public void gamePlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        TextView previousText = new TextView(this);
        previousText.setText(R.string.previousGame);

        Button resumeButton = new Button(this);
        resumeButton.setText(R.string.resume);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(_activity, QuestionActivity.class);
                //intent.putExtra("game", _game);
                _game.setPlayed(true);
                _appContent.updateGame(_game);
                intent.putExtra("appContent", _appContent);
                _activity.startActivity(intent);
            }
        });

        Button newButton = new Button(this);
        newButton.setText(R.string.newGame);
        newButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(_activity, QuestionActivity.class);
                //intent.putExtra("game", _game);
                _game.setPlayed(true);
                _game.zeroIndex();
                _appContent.updateGame(_game);
                intent.putExtra("appContent", _appContent);
                SetQuestionPresenter setQuestionPresenter = new SetQuestionPresenter(_game, _activity, _progress, intent);
                setQuestionPresenter.execute();
            }
        });

        buttonLayout.addView(previousText, lp);
        buttonLayout.addView(resumeButton, lp);
        buttonLayout.addView(newButton, lp);
    }

    public void gameNotPlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        Button startButton = new Button(this);
        startButton.setText(R.string.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(_activity, QuestionActivity.class);
                //intent.putExtra("game", _game);
                _game.setPlayed(true);
                _game.zeroIndex();
                _appContent.updateGame(_game);
                intent.putExtra("appContent", _appContent);
                _activity.startActivity(intent);
            }
        });
        buttonLayout.addView(startButton, lp);
    }

    public void gameEmpty( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        TextView text = new TextView(this);
        text.setText(R.string.emptyGame);
        Button startButton = new Button(this);
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

    @Override
    public void onBackPressed() {
        UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(this, _progress, _appContent);
        updateAppContentPresenter.execute();
    }
}
