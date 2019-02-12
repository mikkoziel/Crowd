package appView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Objects;

import entity.AppContent;
import entity.Game;
import entity.GivenAnswer;
import entity.Question;
import presenter.JsonPresenter;
import presenter.UpdateAppContentPresenter;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;
    private AppContent _appContent;
    private JsonPresenter _jsonPresenter;

    private Game _game;
    private GivenAnswer _given;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Log.d(TAG, "onCreate: Started.");

        this._jsonPresenter = new JsonPresenter(this);
        this._appContent = _jsonPresenter.getJSON(0);
        this._game = _appContent.getGame(_appContent.getCurrentGameID());

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.containter);
        //setup the pager
        setupViewPager(mViewPager);


//        mViewPager.setCurrentItem();

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        int index = 0;

        StartGameActivity startGame = new StartGameActivity();
        startGame.setOnCreate(_appContent, index, this);
        adapter.addFragment(startGame);

        for(Question question: _game.getQuestions()){
            index =+ 1;
            QuestionActivity questionA = new QuestionActivity();
            questionA.setOnCreate(_appContent, question, index);
            adapter.addFragment(questionA);
        }

        index =+ 1;
        EndGameActivity endGame = new EndGameActivity();
        endGame.setOnCreate(_appContent, index);
        adapter.addFragment(endGame);
//        fragment.setOnCreate();
//        adapter.addFragment(new Fragment1(), "Fragment1");

        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
        ((SectionsStatePagerAdapter) mViewPager.getAdapter()).deleteFragment(fragmentNumber - 1);
    }

    public Fragment getFragment(int fragmentNumber) {
        return ((SectionsStatePagerAdapter) Objects.requireNonNull(mViewPager.getAdapter())).getItem(fragmentNumber);
    }



    //TODO Coś z tym zrobić
    @Override
    public void onBackPressed() {
        int current = mViewPager.getCurrentItem();
        int i;
        if(current == 0){
            StartGameActivity currentFragment = (StartGameActivity) getFragment(current);
            UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(this, currentFragment.getProgress(), _appContent);
            updateAppContentPresenter.execute();
        }
        else{
            if(current == mViewPager.getAdapter().getCount()){
                EndGameActivity currentFragment = (EndGameActivity) getFragment(current);
                UpdateAppContentPresenter updateAppContentPresenter = new UpdateAppContentPresenter(this, currentFragment.getProgress(), _appContent);
                updateAppContentPresenter.execute();
            }
            else{
                mViewPager.setCurrentItem(0);
            }
        }
    }


    public void setGiven(GivenAnswer given){
        this._given = given;
    }
}

