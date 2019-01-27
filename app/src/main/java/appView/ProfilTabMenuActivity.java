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
import android.widget.LinearLayout;
import android.widget.TextView;

import entity.Profile;
import presenter.HighScorePresenter;

public class ProfilTabMenuActivity extends Fragment {

    public Activity activity;
    public Intent thisIntent;
    public Profile profile;


    public void setOnCreate(Activity activity, Intent intent){
        this.activity = activity;
        this.thisIntent = intent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.profil_tab_menu, container, false);

        this.profile = (Profile) thisIntent.getSerializableExtra("profile");

        populateView(rootView);

        Button highscore = rootView.findViewById(R.id.highscore);
        highscore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHighscore();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
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
        user.setText(String.format("%s", profile.getName()));

        TextView points = rootView.findViewById(R.id.points);
        points.setText(String.format("%s", Integer.toString(profile.getPoints())));

        TextView stats = rootView.findViewById(R.id.level);
        stats.setText(String.format("%s", profile.getLevel()));

        TextView misspoints = rootView.findViewById(R.id.missPoints);
        misspoints.setText(String.format("Missing points to next level: %s", Integer.toString(profile.getMissingPoints())));
    }

    public void addAvatar(View rootView){
        ImageView avatar = rootView.findViewById(R.id.imageView);

        byte[] byteImage =  profile.getAvatar();
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        avatar.setImageBitmap(bitmapImage);

    }

    public void showHighscore(){
        Intent intent = new Intent(activity, HighscoreActivity.class);
        HighScorePresenter highscorePresenter = new HighScorePresenter(profile, activity, intent);
        highscorePresenter.execute();
    }

    public void showShop(){
        Intent intent = new Intent(activity, ShopActivity.class);
        intent.putExtra("profile", profile);
        activity.startActivity(intent);
    }

}
