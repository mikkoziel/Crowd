package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import entity.AppContent;
import entity.Item;
import entity.Profile;
import interactor.ProfileInteractor;
import interactor.ShopInteractor;

public class BuyItemPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    private AppContent _appContent;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private Item _item;
    @SuppressLint("StaticFieldLeak")
    private Button _buy;
    @SuppressLint("StaticFieldLeak")
    private Button _cancel;

    private ProfileInteractor _profileInteractor;
    private ShopInteractor _shopInteractor;

    public BuyItemPresenter(Activity activity, AppContent appContent, ProgressBar progressBar, Item item, Button buy, Button cancel) {
        this._activity = activity;
        this._appContent = appContent;
        this._progress = progressBar;
        this._item = item;
        this._buy = buy;
        this._cancel = cancel;
        this._profileInteractor = new ProfileInteractor();
        this._shopInteractor = new ShopInteractor();
    }

    @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
        _buy.setClickable(false);
        _cancel.setClickable(false);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Profile profile = _appContent.getProfile();
        if(profile.getMoney() >= _item.getPrice()) {
            profile.addItem(_item);
            profile.spendMoney(_item.getPrice());
            _shopInteractor.addItem(_item, profile);
            _profileInteractor.spendMoney(profile);
            _appContent.updateCurrentProfile(profile);
        }
        else{
            _shopInteractor.notEnoughMoney();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if(_shopInteractor.isSuccess()) {
            _progress.setVisibility(View.GONE);
            _cancel.setEnabled(false);
        }else{
            _buy.setClickable(true);
        }
        _buy.setClickable(true);

        String result = _shopInteractor.getResult();
        Toast.makeText(_activity, result, Toast.LENGTH_LONG).show();

        _profileInteractor.endWork();
        _shopInteractor.endWork();
    }
}
