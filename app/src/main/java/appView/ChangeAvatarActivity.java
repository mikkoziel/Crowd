package appView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import entity.AppContent;
import entity.Avatar;
import entity.Profile;
import presenter.AvatarPresenter;
import static android.view.Gravity.CENTER;

public class ChangeAvatarActivity extends AppCompatActivity {

    private Button btn_unfocus;
    private ArrayList<Button> _buttons;
    private ProgressBar _progress;

    private AppContent _appContent;
    private ArrayList<Avatar> _avatars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);

        Intent intent = getIntent();
        this._appContent = (AppContent) intent.getSerializableExtra("appContent");
        this._buttons = new ArrayList<>();
        _progress = findViewById(R.id.progress);
        _progress.setVisibility(View.GONE);

        _avatars = _appContent.getAvatars();
        LinearLayout layout = findViewById(R.id.avatarsLay);

        populateAvatars(layout);
        btn_unfocus = _buttons.get(0);
        setFocus(null, btn_unfocus);
    }

    public void populateAvatars(LinearLayout layout){
        int inRow = 3;
        int i =inRow;
        LinearLayout row = null;

        for(Avatar avatar : _avatars){
            byte[] avatarIcon = avatar.getIcon();
            if(i == inRow) {
                i = 0;
                row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(0, 10, 0, 10);
                row.setGravity(CENTER);
                layout.addView(row);
            }

            final Button avatarButton = new Button(this);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(avatarIcon, 0, avatarIcon.length);
            Drawable drawableImage = new BitmapDrawable(getResources(), bitmapImage);
            avatarButton.setBackground(drawableImage);
            avatarButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setFocus(btn_unfocus, avatarButton);
                }
            });
            avatarButton.setPadding(10, 0, 10, 0);
            _buttons.add(avatarButton);

            FrameLayout frame = new FrameLayout(this);
            frame.setPadding(3,3,3,3);
            frame.setForegroundGravity(CENTER);
            frame.addView(avatarButton, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT, CENTER));

            i+=1;

            row.addView(frame, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        }
    }

    public void setFocus(Button btn_unfocus, Button btn_focus){
        if(btn_unfocus != null) {
            FrameLayout unfocusLayout = (FrameLayout) btn_unfocus.getParent();
            unfocusLayout.setBackgroundResource(0);
        }
        FrameLayout focusLayout = (FrameLayout) btn_focus.getParent();
        focusLayout.setBackgroundColor(Color.parseColor("#33FF5555"));
        this.btn_unfocus = btn_focus;

    }

    public void cancelButton(View view){
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("appContent", _appContent);
        intent.putExtra("item", 2);
        this.startActivity(intent);
    }

    public void changeButton(View view){

        //TODO ?? co tu się dzieje?
        /*
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ((BitmapDrawable) btn_unfocus.getBackground()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        _profile.setAvatar(stream.toByteArray());
        AvatarPresenter avatarPresenter = new AvatarPresenter(this, _profile, 1, 1, _progress);
        avatarPresenter.execute();
    */
        //TODO moja propzycja, potrzeba tylko ID nowego avatara
        int index = _buttons.indexOf(btn_unfocus);
        int newAvatarID = _avatars.get(index).getID();
        AvatarPresenter avatarPresenter = new AvatarPresenter(this, _progress, _appContent, newAvatarID);
        avatarPresenter.execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("appContent", _appContent);
        intent.putExtra("item", 2);
        this.startActivity(intent);
    }

}
