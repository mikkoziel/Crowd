package appView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import entity.Profile;
import presenter.AvatarPresenter;
import presenter.UpdateProfilePresenter;

import static android.view.Gravity.CENTER;

public class ChangeAvatarActivity extends AppCompatActivity {

    private Profile _profile;
    private ArrayList<byte[]> _avatars;
    private Button btn_unfocus;
    private ArrayList<Button> _buttons;
    private ProgressBar _progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);

        Intent intent = getIntent();
        this._profile = (Profile) intent.getSerializableExtra("profile");
        this._avatars = ( ArrayList<byte[]>) intent.getSerializableExtra("avatars");
        this._buttons = new ArrayList<>();
        _progress = findViewById(R.id.progress);
        _progress.setVisibility(View.GONE);

        LinearLayout layout = findViewById(R.id.avatarsLay);

        populateAvatars(layout);
        btn_unfocus = _buttons.get(0);
        setFocus(null, btn_unfocus);
    }

    public void populateAvatars(LinearLayout layout){
        int inRow = 3;
        int i =inRow;
        LinearLayout row = null;

        for(byte[] byteImage: _avatars){
            if(i == inRow) {
                i = 0;
                row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(0, 10, 0, 10);
                row.setGravity(CENTER);
                layout.addView(row);
            }

            final Button avatar = new Button(this);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
            Drawable drawableImage = new BitmapDrawable(getResources(), bitmapImage);
            avatar.setBackground(drawableImage);
            avatar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setFocus(btn_unfocus, avatar);
                }
            });
            avatar.setPadding(10, 0, 10, 0);
            _buttons.add(avatar);

            FrameLayout frame = new FrameLayout(this);
            frame.setPadding(3,3,3,3);
            frame.setForegroundGravity(CENTER);
            frame.addView(avatar, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT, CENTER));

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

    public void cancelBttn(View view){
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("profile", _profile);
        intent.putExtra("item", 2);
        this.startActivity(intent);
    }

    public void changeBttn(View view){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ((BitmapDrawable) btn_unfocus.getBackground()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        _profile.setAvatar(stream.toByteArray());
        AvatarPresenter avatarPresenter = new AvatarPresenter(this, _profile, 1, 1, _progress);
        avatarPresenter.execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("profile", _profile);
        intent.putExtra("item", 2);
        this.startActivity(intent);
    }

}
