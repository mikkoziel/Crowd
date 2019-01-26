package appView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;

import entity.Profile;

public class ChangeAvatarActivity extends AppCompatActivity {

    private Profile _profile;
    private ArrayList<byte[]> _avatars;

//    private LinearLayout ll;
//    private LinearLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);

        Intent intent = getIntent();
        this._profile = (Profile) intent.getSerializableExtra("profile");
        this._avatars = ( ArrayList<byte[]>) intent.getSerializableExtra("avatars");

//        ll = (LinearLayout) findViewById(appView.R.id.layout);
//        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TableLayout table = (TableLayout) findViewById(appView.R.id.avatars);

        populateAvatars(table);
//        addButtons();
    }

    public void populateAvatars(TableLayout table){

    }

//    public void addButtons(){
//        Button cancel = new Button(this);
//        Button submit = new Button(this);
//    }
}
