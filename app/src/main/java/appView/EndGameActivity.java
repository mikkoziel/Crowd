package appView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.Game;
import entity.Profile;
import entity.GivenAnswer;

import presenter.GivenAnswerPresenter;

public class EndGameActivity extends AppCompatActivity {

    public ProgressBar progress;
    public Game game;
    public Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Intent intent = getIntent();
        this.game = (Game)intent.getSerializableExtra("game");
        this.profile = (Profile) intent.getSerializableExtra("profile");

        this.progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);

        if(getIntent().hasExtra("answer")){
            GivenAnswer given = (GivenAnswer) intent.getSerializableExtra("answer");
            GivenAnswerPresenter givenAnswerPresenter = new GivenAnswerPresenter(given);
            givenAnswerPresenter.execute("");
        }

        TextView endText = findViewById(R.id.endgame);
        String text = "You answered all questions.\n What would you like to do?";
        endText.setText(text);
    }

    public void backBttn(View view){
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("profile", profile);
        this.startActivity(intent);
    }

    public void repeatBttn(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("game", game);
        intent.putExtra("profile", profile);
        this.startActivity(intent);
    }
}
