package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

import appView.ChangePasswordActivity;
import entity.Profile;
import interactor.ProfileInteractor;

public class AvatarPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    private Profile _profile;

    private ProfileInteractor _profileInteractor;
    private ArrayList<byte[]> _avatars;

    public AvatarPresenter(Activity activity, Profile profile) {
        this._activity = activity;
        this._profile = profile;
        this._profileInteractor = new ProfileInteractor();

    }

    @Override
    protected void onPreExecute() {
//        _progress.setVisibility(View.VISIBLE);
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            _avatars = _profileInteractor.getAllAvatars();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if(_profileInteractor.isSuccess()) {
            Intent intent = new Intent(_activity, ChangePasswordActivity.class);
            intent.putExtra("profile", _profile);
            intent.putExtra("avatars", _avatars);
            _activity.startActivity(intent);
        }
    }

}
