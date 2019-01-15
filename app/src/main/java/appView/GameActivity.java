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

import entity.Game;
import entity.Profile;
import presenter.SetQuestionPresenter;

public class GameActivity extends AppCompatActivity {
    public Game game;
    public Profile profile;
    public ProgressBar progress;
    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent inetnt = getIntent();
        this.game = (Game)inetnt.getSerializableExtra("game");
        this.profile = (Profile) inetnt.getSerializableExtra("profile");
        this.activity = this;

        this.progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);

        TextView gameText = (TextView) findViewById(R.id.game);
        gameText.setText(game.getGameName());

        LinearLayout buttonLayout = (LinearLayout)findViewById(R.id.chooselayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if(game.getQuestions().isEmpty()){
            gameEmpty(buttonLayout, lp);
        }
        else {
            if (game.getPlayed()) {
                gamePlayed(buttonLayout, lp);
            } else {
                gameNotPlayed(buttonLayout, lp);
            }
        }

    }

    public void gamePlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        TextView previousText = new TextView(this);
        previousText.setText(R.string.previousGame);

        Button resumeBttn = new Button(this);
        resumeBttn.setText(R.string.resume);
        resumeBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("setter", false);
                intent.putExtra("profile", profile);
                intent.putExtra("game", game);
                game.setPlayed(true);
                activity.startActivity(intent);
            }
        });

        //TO DO: Co wkładamy do intenta? (Game 2 razy)
        Button newBttn = new Button(this);
        newBttn.setText(R.string.newGame);
        newBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("setter", true);
                intent.putExtra("profile", profile);
                intent.putExtra("game", game);
                game.setPlayed(true);
                game.zeroIndex();
                SetQuestionPresenter setQuestionPresenter = new SetQuestionPresenter(game, activity, progress, intent);
                setQuestionPresenter.execute();
            }
        });

        buttonLayout.addView(previousText, lp);
        buttonLayout.addView(resumeBttn, lp);
        buttonLayout.addView(newBttn, lp);
    }

    public void gameNotPlayed( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        Button startBttn = new Button(this);
        startBttn.setText(R.string.start);
        startBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("setter", true);
                intent.putExtra("profile", profile);
                intent.putExtra("game", game);
                game.setPlayed(true);
                game.zeroIndex();
                activity.startActivity(intent);
            }
        });

        buttonLayout.addView(startBttn, lp);
    }

    public void gameEmpty( LinearLayout buttonLayout, LinearLayout.LayoutParams lp){
        TextView text = new TextView(this);
        text.setText(R.string.emptyGame);
        Button startBttn = new Button(this);
        startBttn.setText(R.string.back);
        startBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity, TabMenuActivity.class);
                intent.putExtra("profile", profile);
                intent.putExtra("item", 1);
                game.setPlayed(false);
                game.zeroIndex();
                activity.startActivity(intent);
            }
        });
        buttonLayout.addView(text, lp);
        buttonLayout.addView(startBttn, lp);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("profile", profile);
        intent.putExtra("item", 1);
        this.startActivity(intent);
    }
}
