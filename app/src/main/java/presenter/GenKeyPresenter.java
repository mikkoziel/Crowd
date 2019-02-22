package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import appView.R;
import entity.AppContent;
import entity.GivenAnswer;
import entity.Profile;
import interactor.GenKeyInteractor;
import interactor.GivenAnswerInteractor;
import tools.InternetChecker;

public class GenKeyPresenter extends AsyncTask<Void, Void, Void> {

    private GenKeyInteractor _genKeyInteractor;
    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    private AppContent _appContent;
    @SuppressLint("StaticFieldLeak")
    private View _view;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;

    public GenKeyPresenter(Activity activity, AppContent appContent, View view, ProgressBar progress) {
        this._genKeyInteractor = new GenKeyInteractor();
        this._activity = activity;
        this._appContent = appContent;
        this._view = view;
        this._progress = progress;
    }

    @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
        InternetChecker internetChecker = new InternetChecker(_activity);
        if (!internetChecker.isOnline()) {
            this.cancel(true);
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Profile profile = _appContent.getProfile();
        if(_genKeyInteractor.checkItems(profile)){
            _genKeyInteractor.generateKey(profile);
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostExecute(Void voids) {
        if (_genKeyInteractor.isSuccess()) {
            ArrayList<Integer> keys = _genKeyInteractor.getResult();
            LinearLayout keyLay =_view.findViewById(R.id.keysLay);
            for(Integer x: keys){
                TextView keyText = new TextView(_activity);
                keyText.setText(Integer.toString(x));
                keyText.setGravity(View.TEXT_ALIGNMENT_CENTER);
                keyText.setTextSize(20);
                keyLay.addView(keyText);
            }
        }
        _genKeyInteractor.endWork();
        _progress.setVisibility(View.GONE);
    }
}

