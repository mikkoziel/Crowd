package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;

import entity.AppContent;
import entity.Item;
import interactor.ShopInteractor;

public class ShopPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private Intent _intent;

    private AppContent _appContent;

    private ShopInteractor _shopInteractor;

    public ShopPresenter(Activity activity, Intent intent, AppContent appContent) {
        this._activity = activity;
        this._intent = intent;
        this._appContent = appContent;
        this._shopInteractor = new ShopInteractor();
    }

    @Override
    protected void onPreExecute() {
//        _progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            ArrayList<Item> shop = _shopInteractor.getShopContent();
            _appContent.setShop(shop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if (_shopInteractor.isSuccess()) {
//            _intent.putExtra("appContent", _appContent);
            _activity.startActivity(_intent);
        }
        _shopInteractor.endWork();
    }
}