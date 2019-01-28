package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import appView.TabMenuActivity;
import entity.AppContent;
import entity.Avatar;
import entity.Profile;

public class AvatarPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;

    private AppContent _appContent;
    private int _newAvatarID;

    public AvatarPresenter(Activity activity,
                           ProgressBar progress,
                           AppContent appContent,
                           int newAvatarID) {
        this._activity = activity;
        this._progress = progress;
        this._appContent = appContent;
        this._newAvatarID = newAvatarID;
    }

    @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Profile profile = _appContent.getProfile();
        Avatar newAvatar = _appContent.getAvatar(_newAvatarID);
        profile.setAvatar(newAvatar);
        _appContent.updateCurrentProfile(profile);
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        _progress.setVisibility(View.GONE);
        Intent intent = new Intent(_activity, TabMenuActivity.class);
        intent.putExtra("appContent", _appContent);
        intent.putExtra("item", 2);
        _activity.startActivity(intent);
    }
}
