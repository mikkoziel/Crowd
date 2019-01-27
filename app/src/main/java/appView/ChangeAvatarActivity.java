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
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

import entity.Profile;

public class ChangeAvatarActivity extends AppCompatActivity {

    private Profile _profile;
    private ArrayList<byte[]> _avatars;
    private Button btn_unfocus;
    private ArrayList<Button> _buttons;

//    private LinearLayout ll;
//    private LinearLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);

        Intent intent = getIntent();
        this._profile = (Profile) intent.getSerializableExtra("profile");
        this._avatars = ( ArrayList<byte[]>) intent.getSerializableExtra("avatars");
        this._buttons = new ArrayList<>();

        LinearLayout layout = findViewById(R.id.avatarsLay);

        populateAvatars(layout);
        btn_unfocus = _buttons.get(0);
        setFocus(null, btn_unfocus);
    }

    public void populateAvatars(LinearLayout layout){
        int i =2;
        LinearLayout row = null;
//        LinearLayout row = new LinearLayout(this);
//        row.setOrientation(LinearLayout.HORIZONTAL);
//        row.setPadding(0, 10, 0, 10);
//        row.setGravity(Gravity.CENTER_HORIZONTAL);
//        layout.addView(row);

        for(byte[] byteImage: _avatars){
            if(i == 2) {
                i = 0;
                row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(0, 10, 0, 10);
                row.setGravity(Gravity.CENTER_HORIZONTAL);
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
            frame.setForegroundGravity(Gravity.CENTER_VERTICAL);
            frame.addView(avatar, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));

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

}
