package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.sql.SQLException;
import java.util.ArrayList;

import appView.ChangeAvatarActivity;
import appView.ChangePasswordActivity;
import appView.TabMenuActivity;
import entity.Profile;
import interactor.ProfileInteractor;

public class AvatarPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    private Profile _profile;
    private int _mode;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;

    private ProfileInteractor _profileInteractor;
    private ArrayList<byte[]> _avatars;
    private int _avatar;

    public AvatarPresenter(Activity activity, Profile profile, int mode, int avatarID, ProgressBar progress) {
        this._activity = activity;
        this._profile = profile;
        this._profileInteractor = new ProfileInteractor();
        this._mode = mode;
        this._avatar = avatarID;
        this._progress = progress;
    }

    @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            switch(_mode){
                case 0:
                    _avatars = _profileInteractor.getAllAvatars();
                    break;
                case 1:
//                    _profileInteractor.changeAvatar(_profile, _avatar);
                    _avatars = _profileInteractor.getAllAvatars();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        _progress.setVisibility(View.GONE);
        if(_profileInteractor.isSuccess()) {
            Intent intent = null;
            switch(_mode) {
                case 0:
                    intent = new Intent(_activity, ChangeAvatarActivity.class);
                    intent.putExtra("profile", _profile);
                    intent.putExtra("avatars", _avatars);
                    break;
                case 1:
                    intent = new Intent(_activity, TabMenuActivity.class);
                    intent.putExtra("profile", _profile);
                    intent.putExtra("item", 2);
                    break;
            }
            _activity.startActivity(intent);
        }
        _profileInteractor.endWork();
    }

}
