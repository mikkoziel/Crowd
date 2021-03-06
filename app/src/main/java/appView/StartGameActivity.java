package appView;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import presenter.UpdateAppContentPresenter;

public class StartGameActivity extends Fragment{
    private ProgressBar _progress;
    private Activity _activity;

    private AppContent _appContent;
    private Game _game;
    private int _index;

    public void setOnCreate(AppContent appContent, int index, Activity activity, Game game){
        this._appContent = appContent;
        this._index = index;
        this._activity =  activity;
        this._game = game;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_start_game, container, false);

        this._progress = view.findViewById(R.id.progress);
        _progress.setVisibility(View.GONE);

        TextView gameText = view.findViewById(R.id.game);
        String text = "";
        if(_game.getQuestions().isEmpty()){
            text = _game.getName() + "\nYou answered all questions of this game";
        }
        else{
            text = _game.getName();
        }

        gameText.setText(text);

        LinearLayout buttonLayout = view.findViewById(R.id.chooselayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if(_game.getQuestions().isEmpty()){
            gameEmpty(buttonLayout, lp);
        }
        else {
            gameNotPlayed(buttonLayout, lp);
        }

        return view;
    }

    public void gameNotPlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        Button startButton = new Button(_activity);
        startButton.setText(R.string.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _game.setPlayed(true);
                _game.zeroIndex();
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
                UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(_activity, _progress, _appContent, _game);
                updateAppContentPresenter.execute();
            }
        });
        buttonLayout.addView(text, lp);
        buttonLayout.addView(startButton, lp);
    }

    public ProgressBar getProgress() {
        return _progress;
    }
}
