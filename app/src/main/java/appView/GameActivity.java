package appView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Objects;

import entity.AppContent;
import entity.Game;
import tools.CustomViewPager;
import entity.Question;
import presenter.UpdateAppContentPresenter;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private CustomViewPager mViewPager;
    private AppContent _appContent;

    private Game _game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.d(TAG, "onCreate: Started.");

        this._appContent = (AppContent) getIntent().getSerializableExtra("appContent");
        this._game = (Game) getIntent().getSerializableExtra("game");

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.containter);
        setupViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        int index = 0;

        StartGameActivity startGame = new StartGameActivity();
        startGame.setOnCreate(_appContent, index, this, _game);
        adapter.addFragment(startGame);

        if(!_game.getQuestions().isEmpty()) {
            for (Question question : _game.getQuestions()) {
                index += 1;
                QuestionActivity questionA = new QuestionActivity();
                questionA.setOnCreate(_appContent, question, index, _game);
                adapter.addFragment(questionA);
            }
        }

        index += 1;
        EndGameActivity endGame = new EndGameActivity();
        endGame.setOnCreate(_appContent, index, _game, this);
        adapter.addFragment(endGame);

        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }

    public Fragment getFragment(int fragmentNumber) {
        return ((SectionsStatePagerAdapter) Objects.requireNonNull(mViewPager.getAdapter())).getItem(fragmentNumber);
    }
    
    @Override
    public void onBackPressed() {
        int current = mViewPager.getCurrentItem();
        if(current == 0){
            StartGameActivity currentFragment = (StartGameActivity) getFragment(current);
            UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(this, currentFragment.getProgress(), _appContent, _game);
            updateAppContentPresenter.execute();
        }
        else{
            if(current == mViewPager.getAdapter().getCount() - 1){
                EndGameActivity currentFragment = (EndGameActivity) getFragment(current);
                UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(this, currentFragment.getProgress(), _appContent, _game);
                updateAppContentPresenter.execute();
            }
            else{
                mViewPager.setCurrentItem(0);
            }
        }
    }


}

