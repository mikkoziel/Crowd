package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

import appView.ChangeAvatarActivity;
import appView.ChangePasswordActivity;
import entity.Profile;
import interactor.ProfileInteractor;

public class AvatarPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    private Profile _profile;
    private int _mode;

    private ProfileInteractor _profileInteractor;
    private ArrayList<byte[]> _avatars;

    public AvatarPresenter(Activity activity, Profile profile, int mode) {
        this._activity = activity;
        this._profile = profile;
        this._profileInteractor = new ProfileInteractor();
        this._mode = mode;

    }

    @Override
    protected void onPreExecute() {
//        _progress.setVisibility(View.VISIBLE);
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            switch(_mode){
                case 0:
                    _avatars = _profileInteractor.getAllAvatars();
                    break;
                case 1:
                    _profileInteractor.changeAvatar(_profile, 1);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if(_profileInteractor.isSuccess()) {
            Intent intent = new Intent(_activity, ChangeAvatarActivity.class);
            intent.putExtra("profile", _profile);
            intent.putExtra("avatars", _avatars);
            _activity.startActivity(intent);
        }
    }

}
