package appView;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.AppContent;
import entity.GivenAnswer;

import presenter.GivenAnswerPresenter;
import presenter.JsonPresenter;
import presenter.UpdateAppContentPresenter;

public class EndGameActivity extends Fragment {

    private ProgressBar _progress;
    private AppContent _appContent;
    private JsonPresenter _jsonPresenter;
    private View _view;
    private int _index;
    private GivenAnswer _given;

    public void setOnCreate(AppContent appContent, int index){
        this._appContent = appContent;
        this._index = index;
        this._given = null;
    }

    public void onDisplay(){
        if(_given != null){
            GivenAnswerPresenter givenAnswerPresenter = new GivenAnswerPresenter(_given, getActivity());
            givenAnswerPresenter.execute();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        this._view = inflater.inflate(R.layout.activity_end_game, container, false);

//        @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_end_game);

//        Intent intent = getIntent();

//        this._jsonPresenter = new JsonPresenter(this);
//        this._appContent = _jsonPresenter.getJSON(1);
//        //        this._appContent = (AppContent) intent.getSerializableExtra("appContent");

        this._progress = _view.findViewById(R.id.progress);
        _progress.setVisibility(View.GONE);

//        TODO: naprawić
//        if(getIntent().hasExtra("answer")){
        if(_given != null){
//            GivenAnswer given = (GivenAnswer) intent.getSerializableExtra("answer");
            GivenAnswerPresenter givenAnswerPresenter = new GivenAnswerPresenter(_given, getActivity());
            givenAnswerPresenter.execute();
        }

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
        UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(getActivity(), _progress, _appContent);
        updateAppContentPresenter.execute();
    }

    public void repeatButton(){
        Intent intent = new Intent(getActivity(), StartGameActivity.class);
//        intent.putExtra("appContent", _appContent);
        _jsonPresenter.writeToJson(_appContent, 1);
        _appContent.destroy();
        _appContent = null;
        this.startActivity(intent);
    }

//    public void onBackPressed() {
//        UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(getActivity(), _progress, _appContent);
//        updateAppContentPresenter.execute();
//    }

    public ProgressBar getProgress() {
        return _progress;
    }
}
