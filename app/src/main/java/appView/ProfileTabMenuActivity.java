package appView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import entity.AppContent;
import entity.Profile;
import presenter.HighScorePresenter;
import presenter.ShopPresenter;

public class ProfileTabMenuActivity extends Fragment {

    private Activity _activity;
    private Intent _intent;
    private AppContent _appContent;
    private Profile _profile;


    public void setOnCreate(Activity activity, Intent intent){
        this._activity = activity;
        this._intent = intent;
        this._appContent = (AppContent) _intent.getSerializableExtra("appContent");
        this._profile = _appContent.getProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.profil_tab_menu, container, false);

        populateView(rootView);

        Button highScore = rootView.findViewById(R.id.highscore);
        highScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHighScore();
            }
        });

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShop();
            }
        });

        return rootView;
    }

    public void populateView(View rootView){
        addAvatar(rootView);

        TextView user = rootView.findViewById(R.id.userName);
        user.setText(String.format("%s", _profile.getName()));

        TextView points = rootView.findViewById(R.id.points);
        points.setText(String.format("%s", Integer.toString(_profile.getPoints())));

        TextView stats = rootView.findViewById(R.id.level);
        stats.setText(String.format("%s", _profile.getLevel()));

        TextView missingPoints = rootView.findViewById(R.id.missPoints);
        missingPoints.setText(String.format("Missing points to next level: %s", Integer.toString(_profile.getMissingPoints())));
    }

    public void addAvatar(View rootView){
        ImageView avatar = rootView.findViewById(R.id.imageView);

        byte[] byteImage =  _profile.getAvatar().getIcon();
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        avatar.setImageBitmap(bitmapImage);
    }

    public void showHighScore(){
        Intent intent = new Intent(_activity, HighScoreActivity.class);
        intent.putExtra("appContent", _appContent);
        HighScorePresenter highscorePresenter = new HighScorePresenter(_activity, intent, _appContent);
        highscorePresenter.execute();
    }

    public void showShop(){
        Intent intent = new Intent(_activity, ShopActivity.class);
        intent.putExtra("appContent", _appContent);
        //_activity.startActivity(intent);
        ShopPresenter shopPresenter = new ShopPresenter(_activity, intent, _appContent);
        shopPresenter.execute();
    }

}
