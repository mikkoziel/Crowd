package presenter;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.sql.SQLException;

import entity.AppContent;
import entity.Tag;
import interactor.TagInteractor;

public class TagPresenter {

    private AppContent _appContent;
    private TagInteractor _tagInteractor;

    public TagPresenter(AppContent appContent){
        this._appContent = appContent;
        this._tagInteractor = new TagInteractor();

    }

    public ArrayAdapter<Tag> getAllTags(Activity activity)
    {
        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(activity,
                android.R.layout.simple_dropdown_item_1line);
        try {
            _tagInteractor.setTags(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        _appContent.setTags(adapter);
        return adapter;
    }

    public void addGameTags(ArrayAdapter<Tag> adapter){
        _tagInteractor.addGameTags(adapter, _appContent.getGames());
    }

}
