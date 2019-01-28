package appView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import entity.AppContent;
import entity.Item;
import entity.Profile;
import presenter.HighScorePresenter;
import presenter.ShopPresenter;

import static android.view.Gravity.CENTER;

public class ProfileTabMenuActivity extends Fragment {

    private Activity _activity;
    private Intent _intent;
    private AppContent _appContent;
    private Profile _profile;
    private LinearLayout _itemInfoLayout;


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

        this._itemInfoLayout = rootView.findViewById(R.id.itemInfo);
        _itemInfoLayout.setVisibility(View.GONE);

        populateView(rootView);
        populateItems(rootView);

        Button highScore = rootView.findViewById(R.id.highscore);
        highScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHighScore();
            }
        });

        Button cancel = rootView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelBttn(rootView);
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

    private void populateItems(final View rootView){
        int inRow = 3;
        int i =inRow;
        LinearLayout row = null;
        LinearLayout itemLayout = rootView.findViewById(R.id.itemLayout);

        for(final Item item: _profile.getItems()){
            byte[] avatarIcon = item.getIcon();
            if(i == inRow) {
                i = 0;
                row = new LinearLayout(_activity);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(0, 10, 0, 10);
                row.setGravity(CENTER);
                itemLayout.addView(row);
            }

            final Button itemButton = new Button(_activity);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(avatarIcon, 0, avatarIcon.length);
            Drawable drawableImage = new BitmapDrawable(getResources(), bitmapImage);
            itemButton.setBackground(drawableImage);
            itemButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    itemBttnAction(item, rootView);
                }
            });
            itemButton.setPadding(10, 0, 10, 0);

            FrameLayout frame = new FrameLayout(_activity);
            frame.setPadding(3,3,3,3);
            frame.setForegroundGravity(CENTER);
            frame.addView(itemButton, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT, CENTER));

            i+=1;

            row.addView(frame, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        }
    }

    private void itemBttnAction(Item item, View rootView){
        _itemInfoLayout.setVisibility(View.VISIBLE);

        TextView itemName =  rootView.findViewById(R.id.itemName);
        TextView itemPrice =  rootView.findViewById(R.id.itemPrice);
        TextView itemDesc =  rootView.findViewById(R.id.itemDesc);

        itemName.setText(item.getName());
        itemPrice.setText(String.valueOf(item.getPrice()));
        itemDesc.setText(item.getDescription());

    }

    public void cancelBttn(View view){
        _itemInfoLayout.setVisibility(View.GONE);
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
        _activity.startActivity(intent);
//        ShopPresenter shopPresenter = new ShopPresenter(_activity, intent, _appContent);
//        shopPresenter.execute();
    }

}
