package appView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.AppContent;
import entity.GivenAnswer;

import presenter.GivenAnswerPresenter;
import presenter.UpdateProfilePresenter;

public class EndGameActivity extends AppCompatActivity {

    private ProgressBar _progress;
    private AppContent _appContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();
        this._appContent = (AppContent) intent.getSerializableExtra("appContent");

        this._progress = findViewById(R.id.progress);
        _progress.setVisibility(View.GONE);

        if(getIntent().hasExtra("answer")){
            GivenAnswer given = (GivenAnswer) intent.getSerializableExtra("answer");
            GivenAnswerPresenter givenAnswerPresenter = new GivenAnswerPresenter(given, this);
            givenAnswerPresenter.execute();
        }

        TextView endText = findViewById(R.id.endgame);
        String text = "You answered all questions.\n What would you like to do?";
        endText.setText(text);
    }

    public void backButton(View view){
        UpdateProfilePresenter updateProfilePresenter = new UpdateProfilePresenter(this, _progress, _appContent);
        updateProfilePresenter.execute();
    }

    public void repeatButton(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("appContent", _appContent);
        this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        UpdateProfilePresenter updateProfilePresenter = new UpdateProfilePresenter(this, _progress, _appContent);
        updateProfilePresenter.execute();
    }
}
