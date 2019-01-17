package appView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Profile;
import entity.Game;
import entity.Tag;
import interactor.Logger;
import interactor.TagInteractor;
import presenter.SetQuestionPresenter;

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

//        String[] Tags = {"image", "text"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, Tags);
        TagInteractor tagInteractor = new TagInteractor();
        ArrayAdapter<Tag> adapter = null;
        try {
            adapter = tagInteractor.getTags(activity);
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
            }
        });

        Button sortBttn = rootView.findViewById(appView.R.id.sortButton);
        sortBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(sortText.getVisibility() == View.GONE) {
                    sortText.setVisibility(View.VISIBLE);
                }
                else{
                    Tag selected = (Tag) sortText.getText();
                    sortGame(selected, ll, lp);
                }
            }});

        addGames(ll, lp, profile.getGames());




        return rootView;
    }


    public void sortGame(Tag tag, LinearLayout ll, LinearLayout.LayoutParams lp){
        int count = ll.getChildCount();
//        Button v = null;
        for(int i=0; i<count; i++) {
            Button v = (Button) ll.getChildAt(i);
            for (Game game : profile.getGames()){
                if(game.getGameName().contentEquals(v.getText())){
                    v.setVisibility(View.VISIBLE);
                }
                else{
                    v.setVisibility(View.GONE);
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
}
