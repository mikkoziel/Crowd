package appView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import entity.Profile;
import entity.Game;
import entity.Tag;
import interactor.TagInteractor;
import presenter.SetQuestionPresenter;
import presenter.TagPresenter;

public class MenuTabMenuActivity extends Fragment {

    public Profile profile;
    public Intent intent;
    public Intent thisIntent;
    public ProgressBar progress;
    public Activity activity;

    public void setOnCreate(Activity activity, Intent intent){
        this.activity = activity;
        this.thisIntent = intent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(appView.R.layout.menu_tab_menu, container, false);

        this.profile = (Profile) thisIntent.getSerializableExtra("profile");

        this.progress = rootView.findViewById(appView.R.id.progressMenu);
        progress.setVisibility(View.GONE);

        this.intent = new Intent(activity, GameActivity.class);
        intent.putExtra("profile", profile);

        final LinearLayout ll = (LinearLayout) rootView.findViewById(appView.R.id.layout);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        TagPresenter tagPresenter = new TagPresenter();
        ArrayAdapter<Tag> adapter = null;
        try {
            adapter = tagPresenter.getAllTags(activity);
            tagPresenter.addGameTags(profile, adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final AutoCompleteTextView sortText = rootView.findViewById(appView.R.id.sortTag);
        sortText.setAdapter(adapter);
        sortText.setThreshold(1);
        sortText.setVisibility(View.GONE);

        sortText.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Tag selected = (Tag) arg0.getAdapter().getItem(arg2);
                Toast.makeText(activity,
                        "Clicked " + arg2 + " name: " + selected.get_tag(),
                        Toast.LENGTH_SHORT).show();
                sortGame(selected, ll, lp);
            }
        });

        Button sortBttn = rootView.findViewById(appView.R.id.sortButton);
        sortBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(sortText.getVisibility() == View.GONE) {
                    sortText.setVisibility(View.VISIBLE);
                    sortText.requestFocus();
                }
                else{
                    sortText.setVisibility(View.GONE);
                    allVisible(ll);
                }
            }});

        addGames(ll, lp, profile.getGames());


        return rootView;
    }


    public void sortGame(Tag tag, LinearLayout ll, LinearLayout.LayoutParams lp){
        int count = ll.getChildCount();
        for(int i=0; i<count; i++) {
            Button v = (Button) ll.getChildAt(i);
            for (Game game : profile.getGames()){
                if(game.getGameName().contentEquals(v.getText())){
                    if(game.haveTag(tag)) {
                        v.setVisibility(View.VISIBLE);
                    }
                    else{
                        v.setVisibility(View.GONE);
                    }
                }
            }

        }
    }

    public void addGames(LinearLayout ll, LinearLayout.LayoutParams lp, ArrayList<Game> games){
        for (final Game game : games) {
            Button gameButton = new Button(activity);
            gameButton.setText(game.getGameName());
            gameButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SetQuestionPresenter setQuestionPresenter = new SetQuestionPresenter(game, activity, progress, intent);
                    setQuestionPresenter.execute();
                }
//                }
            });
            ll.addView(gameButton, lp);

        }
    }

    public void allVisible(LinearLayout ll){
        int count = ll.getChildCount();
        for(int i=0; i<count; i++) {
            Button v = (Button) ll.getChildAt(i);
            v.setVisibility(View.VISIBLE);
        }
    }

}
