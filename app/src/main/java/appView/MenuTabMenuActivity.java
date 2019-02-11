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

import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import entity.AppContent;
import entity.Game;
import entity.Tag;
import presenter.JsonPresenter;
import presenter.SetQuestionPresenter;
import presenter.TagPresenter;

public class MenuTabMenuActivity extends Fragment {

    private Intent _intent;
    private ProgressBar _progress;
    private Activity _activity;
    private AppContent _appContent;
    private ArrayList<Game> _games;
    private ArrayList<Tag> _tags;

    private JsonPresenter _jsonPresenter;

    public void setOnCreate(Activity activity, Intent intent){
        this._activity = activity;
        this._intent = intent;
        this._jsonPresenter = new JsonPresenter(_activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(appView.R.layout.menu_tab_menu, container, false);

        this._progress = rootView.findViewById(appView.R.id.progressMenu);
        _progress.setVisibility(View.GONE);

        try {
            JSONObject object =_jsonPresenter.readJSONFromFile();
            this._appContent = _jsonPresenter.parseJSON(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        this._appContent = (AppContent) _intent.getSerializableExtra("appContent");
        this._games = _appContent.getGames();
        this._tags = _appContent.getTags();

        final LinearLayout ll = rootView.findViewById(appView.R.id.layout);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        //TODO tagi są ustawiane w check login execute, co tutaj się dzieje?
        TagPresenter tagPresenter = new TagPresenter(_appContent);
        ArrayAdapter<Tag> _adapter = new ArrayAdapter<>(_activity, android.R.layout.simple_dropdown_item_1line, _tags);
        if(!_adapter.isEmpty())
            tagPresenter.addGameTags(_adapter);


        final AutoCompleteTextView sortText = rootView.findViewById(appView.R.id.sortTag);
        sortText.setAdapter(_adapter);
        sortText.setThreshold(1);
        sortText.setVisibility(View.GONE);

        sortText.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Tag selected = (Tag) arg0.getAdapter().getItem(arg2);
                Toast.makeText(_activity,
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

        addGames(ll, lp);
        return rootView;
    }


    public void sortGame(Tag tag, LinearLayout ll, LinearLayout.LayoutParams lp){
        int count = ll.getChildCount();
        for(int i=0; i<count; i++) {
            Button v = (Button) ll.getChildAt(i);
            for (Game game : _games){
                if(game.getName().contentEquals(v.getText())){
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

    public void addGames(LinearLayout ll, LinearLayout.LayoutParams lp){
        final Intent intent = new Intent(_activity, GameActivity.class);
        intent.putExtra("appContent", _appContent);

        for (final Game game : _games) {
            Button gameButton = new Button(_activity);
            gameButton.setText(game.getName());
            gameButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SetQuestionPresenter setQuestionPresenter = new SetQuestionPresenter(game, _activity, _progress, intent);
                    setQuestionPresenter.execute();
                }
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
