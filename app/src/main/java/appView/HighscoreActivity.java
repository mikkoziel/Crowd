package appView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import entity.Profile;

public class HighscoreActivity extends AppCompatActivity {

    public Profile profile;
    private int found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        Intent intent = getIntent();
        this.profile = (Profile) intent.getSerializableExtra("profile");
        ArrayList<Profile> high = (ArrayList<Profile>) intent.getSerializableExtra("high");

        this.found = 0;

        LinearLayout ll = (LinearLayout) findViewById(appView.R.id.highscore);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        setList(high, ll, lp);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToProfil();
            }
        });

    }

    public void backToProfil() {
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("profile", profile);
        intent.putExtra("item", 0);
        this.startActivity(intent);
    }

    private void setList(ArrayList<Profile> high, LinearLayout ll, LinearLayout.LayoutParams lp) {
        high = setTable(high);
        for (Profile x : high) {
            TextView text = new TextView(this);
            text.setText(String.format("Username: %s\n Points: %s", x.getName(), Integer.toString(x.getPoints())));
            if(x.getName().equals(profile.getName())) {
                text.setBackgroundColor(Color.parseColor("#33005555"));
                found = 1;
            }
            ll.addView(text, lp);
        }

    }

    private ArrayList<Profile> setTable(ArrayList<Profile> high) {
        Profile firstplace = high.remove(0);
        Profile secondplace = high.remove(0);
        Profile thirdplace = high.remove(0);

//        Profile firstplace = high.get(0);
//        Profile secondplace = high.get(1);
//        Profile thirdplace = high.get(2);

        TableRow row1 = (TableRow) findViewById(R.id.row1);
        TableRow row2 = (TableRow) findViewById(R.id.row2);
        TableRow row3 = (TableRow) findViewById(R.id.row3);
        TableRow row4 = (TableRow) findViewById(R.id.row4);

        TableRow.LayoutParams firstColumn= new TableRow.LayoutParams(0);
        TableRow.LayoutParams secondColumn= new TableRow.LayoutParams(1);
        TableRow.LayoutParams thirdColumn= new TableRow.LayoutParams(2);

        TextView text2 = new TextView(this);
        text2.setText(String.format("Username: %s\n Points: %s", secondplace.getName(), Integer.toString(secondplace.getPoints())));
        text2.setGravity(Gravity.CENTER);

        TextView place2 = new TextView(this);
        place2.setText("2\n");
        place2.setGravity(Gravity.CENTER);
        place2.setBackgroundColor(Color.parseColor("#33FF5555"));

        TextView empty1 = new TextView(this);
        empty1.setText("\n");
        empty1.setBackgroundColor(Color.parseColor("#33FF5555"));

        TextView text1 = new TextView(this);
        text1.setText(String.format("Username: %s\n Points: %s", firstplace.getName(), Integer.toString(firstplace.getPoints())));
        text1.setGravity(Gravity.CENTER);

        TextView place1 = new TextView(this);
        place1.setText("1\n");
        place1.setGravity(Gravity.CENTER);
        place1.setBackgroundColor(Color.parseColor("#33FF5555"));

        TextView empty2 = new TextView(this);
        empty2.setText("\n");
        empty2.setBackgroundColor(Color.parseColor("#33FF5555"));

        TextView empty4 = new TextView(this);
        empty4.setText("\n");
        empty4.setBackgroundColor(Color.parseColor("#33FF5555"));

        TextView text3 = new TextView(this);
        text3.setText(String.format("Username: %s\n Points: %s", thirdplace.getName(), Integer.toString(thirdplace.getPoints())));
        text3.setGravity(Gravity.CENTER);

        TextView place3 = new TextView(this);
        place3.setText("3\n");
        place3.setGravity(Gravity.CENTER);
        place3.setBackgroundColor(Color.parseColor("#33FF5555"));

        if(firstplace.getName().equals(profile.getName())){
            text1.setBackgroundColor(Color.parseColor("#33005555"));
            found = 1;
        }else{
            if(secondplace.getName().equals(profile.getName())){
                text2.setBackgroundColor(Color.parseColor("#33005555"));
                found = 1;
            }else{
                if(thirdplace.getName().equals(profile.getName())){
                    text3.setBackgroundColor(Color.parseColor("#33005555"));
                    found = 1;
                }
            }
        }


        row2.addView(text2, firstColumn);
        row3.addView(place2, firstColumn);
        row4.addView(empty1, firstColumn);
        row1.addView(text1, secondColumn);
        row2.addView(place1, secondColumn);
        row3.addView(empty2, firstColumn);
        row4.addView(empty4, firstColumn);
        row3.addView(text3, thirdColumn);
        row4.addView(place3, thirdColumn);

        return high;
    }
}
