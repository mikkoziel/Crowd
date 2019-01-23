package appView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import entity.Profile;

public class HighscoreActivity extends AppCompatActivity {

    public Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        Intent intent = getIntent();
        this.profile = (Profile) intent.getSerializableExtra("profile");
        ArrayList<Profile> high = (ArrayList<Profile>) intent.getSerializableExtra("high");

        LinearLayout ll = (LinearLayout) findViewById(appView.R.id.highscore);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToProfil();
            }
        });

        for(Profile x: high){
            TextView text = new TextView(this);
            text.setText(String.format("Username: %s\n Points: %s", x.getName(), Integer.toString(x.getPoints())));
            ll.addView(text, lp);
        }
    }

    public void backToProfil(){
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("profile", profile);
        intent.putExtra("item", 0);
        this.startActivity(intent);
    }

}
