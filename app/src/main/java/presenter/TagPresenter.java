package presenter;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.sql.SQLException;

import entity.Profile;
import entity.Tag;
import interactor.TagInteractor;

public class TagPresenter {

    private TagInteractor _tagInteractor;

    public TagPresenter(){
        this._tagInteractor = new TagInteractor();

    }

    public ArrayAdapter<Tag> getAllTags(Activity activity) throws SQLException {
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line);
        adapter = _tagInteractor.getTags(adapter);

        return adapter;
    }

    public void addGameTags(Profile profile, ArrayAdapter<Tag> adapter){
        _tagInteractor.addGameTags(profile, adapter);
    }

}
