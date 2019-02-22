package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import appView.R;
import entity.AppContent;
import entity.Profile;
import interactor.GenKeyInteractor;
import tools.InternetChecker;

public class GenKeyPresenter extends AsyncTask<Void, Void, ArrayList<Integer>> {

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
    protected ArrayList<Integer> doInBackground(Void... voids) {
        Profile profile = _appContent.getProfile();
        if(_genKeyInteractor.checkItems(profile)){
            ArrayList<Integer> keys = _genKeyInteractor.generateKey(profile);
            return keys;
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostExecute(ArrayList<Integer> keys) {
        if (_genKeyInteractor.isSuccess()) {
            LinearLayout keyLay =_view.findViewById(R.id.keysLay);
            TextView text = new TextView(_activity);
            text.setText("YOUR KEYS");
            text.setTextSize(20);
            text.setGravity(Gravity.CENTER);
            keyLay.addView(text, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for(Integer x: keys){
                TextView keyText = new TextView(_activity);
                keyText.setText(Integer.toString(x));
                keyText.setGravity(Gravity.CENTER);
                keyText.setTextSize(20);
                if(x.equals(keys.get(keys.size() - 1))){
                    keyText.setBackgroundColor(Color.parseColor("#33FF5555"));
                    TextView spaceText = new TextView(_activity);
                    spaceText.setText("\n");
                    spaceText.setTextSize(20);
                    keyLay.addView(spaceText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    TextView newText = new TextView(_activity);
                    newText.setText("YOUR NEW KEY");
                    newText.setTextSize(20);
                    newText.setGravity(Gravity.CENTER);
                    newText.setBackgroundColor(Color.parseColor("#33FF5555"));
                    keyLay.addView(newText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                }
                keyLay.addView(keyText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        }
        _genKeyInteractor.endWork();
        _progress.setVisibility(View.GONE);
    }
}

