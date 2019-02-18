package tools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

public class InternetChecker {
    private Activity _activity;

    public InternetChecker(Activity activity){
        this._activity = activity;
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) _activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            createAlertDialog("No Internet", "No Internet connection. Try later.");
            return false;
        }
        return true;
    }

    public void createAlertDialog(String title, String message){

        new AlertDialog.Builder(_activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("Close app", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _activity.finishAndRemoveTask();
                    }

                })
                .show();
    }

}
