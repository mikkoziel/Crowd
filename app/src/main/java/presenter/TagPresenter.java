package presenter;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.ArrayList;

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

    public void getAllTags(Activity activity)
    {
        ArrayList<Tag> adapter = new ArrayList<>();
        try {
            adapter = _tagInteractor.getTags();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        _appContent.setTags(adapter);
    }

    public void addGameTags(ArrayAdapter<Tag> adapter){
        _tagInteractor.addGameTags(adapter, _appContent.getGames());
    }
}
