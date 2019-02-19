package appView;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.AppContent;
import entity.Game;

import presenter.SetQuestionPresenter;
import presenter.UpdateAppContentPresenter;

public class EndGameActivity extends Fragment {

    private Activity _activity;
    private ProgressBar _progress;
    private AppContent _appContent;
    private View _view;
    private int _index;
    private Game _game;

    public void setOnCreate(AppContent appContent, int index, Game game, Activity activity){
        this._appContent = appContent;
        this._index = index;
        this._game = game;
        this._activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        this._view = inflater.inflate(R.layout.activity_end_game, container, false);


        this._progress = _view.findViewById(R.id.progress);
        _progress.setVisibility(View.GONE);

        TextView endText = _view.findViewById(R.id.endgame);
        String text = "You answered all questions.\n What would you like to do?";
        endText.setText(text);

        Button back = _view.findViewById(R.id.backToMenu);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backButton();
            }
        });

        Button repeat = _view.findViewById(R.id.repeatGame);
        repeat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                repeatButton();
            }
        });

        return _view;
    }

    public void backButton(){
        UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(getActivity(), _progress, _appContent, _game);
        updateAppContentPresenter.execute();
    }

    public void repeatButton(){
        Intent intent = new Intent(_activity, GameActivity.class);
        _game.clearQuestions();
        SetQuestionPresenter setQuestionPresenter = new SetQuestionPresenter(_game, _activity, _progress, intent, _appContent);
        setQuestionPresenter.execute();
    }

    public ProgressBar getProgress() {
        return _progress;
    }
}
