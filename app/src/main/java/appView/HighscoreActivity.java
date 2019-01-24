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
import android.widget.TableLayout;
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

//        LinearLayout ll = (LinearLayout) findViewById(appView.R.id.highscore);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        setTables(high);

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

    private void setTables(ArrayList<Profile> high) {

        high = setTable1(high);
        setTable2(high);
    }

    private ArrayList<Profile> setTable1(ArrayList<Profile> high) {

        Profile firstplace = high.remove(0);
        Profile secondplace = high.remove(0);
        Profile thirdplace = high.remove(0);

        TableRow row1 = (TableRow) findViewById(R.id.row1);
        TableRow row2 = (TableRow) findViewById(R.id.row2);
        TableRow row3 = (TableRow) findViewById(R.id.row3);
        TableRow row4 = (TableRow) findViewById(R.id.row4);

        TableRow.LayoutParams firstColumn = new TableRow.LayoutParams(0);
        TableRow.LayoutParams secondColumn = new TableRow.LayoutParams(1);
        TableRow.LayoutParams thirdColumn = new TableRow.LayoutParams(2);

        //-------------------------------------------------------------------------------------------
        TextView text1 = new TextView(this);
        TextView text2 = new TextView(this);
        TextView text3 = new TextView(this);

        text1.setText(String.format("%s\n%s", firstplace.getName(), Integer.toString(firstplace.getPoints())));
        text2.setText(String.format("%s\n%s", secondplace.getName(), Integer.toString(secondplace.getPoints())));
        text3.setText(String.format("%s\n%s", thirdplace.getName(), Integer.toString(thirdplace.getPoints())));

        text1.setTextSize(20);
        text2.setTextSize(20);
        text3.setTextSize(20);

        text1.setGravity(Gravity.CENTER);
        text2.setGravity(Gravity.CENTER);
        text3.setGravity(Gravity.CENTER);

//        text2.setBackgroundColor(Color.TRANSPARENT);
//        text3.setBackgroundColor(Color.TRANSPARENT);

        //-------------------------------------------------------------------------------------------
        TextView place1 = new TextView(this);
        TextView place2 = new TextView(this);
        TextView place3 = new TextView(this);

        place1.setText("1\n");
        place2.setText("2\n");
        place3.setText("3\n");

        place1.setBackgroundColor(Color.parseColor("#33FF5555"));
        place2.setBackgroundColor(Color.parseColor("#33FF5555"));

        place1.setTextSize(20);
        place2.setTextSize(20);
        place3.setTextSize(20);

        place1.setGravity(Gravity.CENTER);
        place2.setGravity(Gravity.CENTER);
        place3.setGravity(Gravity.CENTER);

        //-------------------------------------------------------------------------------------------
        TextView empty1 = new TextView(this);
        TextView empty2 = new TextView(this);
        TextView empty4 = new TextView(this);


        empty1.setText("\n");
        empty2.setText("\n");
        empty4.setText("\n");

        empty1.setTextSize(20);
        empty2.setTextSize(20);
        empty4.setTextSize(20);

        empty2.setBackgroundColor(Color.parseColor("#33FF5555"));

        //-------------------------------------------------------------------------------------------
        if (firstplace.getName().equals(profile.getName())) {
            text1.setBackgroundColor(Color.parseColor("#33005555"));
            found = 1;
        } else {
            if (secondplace.getName().equals(profile.getName())) {
                text2.setBackgroundColor(Color.parseColor("#33005555"));
                found = 1;
            } else {
                if (thirdplace.getName().equals(profile.getName())) {
                    text3.setBackgroundColor(Color.parseColor("#33005555"));
                    found = 1;
                }
            }
        }

        //-------------------------------------------------------------------------------------------
        row4.setBackgroundColor(Color.parseColor("#33FF5555"));

        row2.addView(text2, firstColumn);
        row3.addView(place2, firstColumn);
        row1.addView(text1, secondColumn);
        row2.addView(place1, secondColumn);
        row3.addView(empty2, firstColumn);
        row3.addView(text3, thirdColumn);
        row4.addView(place3, thirdColumn);

        return high;
    }


    private void setTable2(ArrayList<Profile> high) {

        TableLayout tl = (TableLayout) findViewById(appView.R.id.table2);
        TableLayout.LayoutParams tp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        int i = 4;

        for (Profile x : high) {

            TextView place = new TextView(this);
            place.setText(String.format("%s  ", Integer.toString(i)));

            TextView name = new TextView(this);
            name.setText(String.format("%s", x.getName()));

            TextView points = new TextView(this);
            points.setText(String.format("%s", Integer.toString(x.getPoints())));
            points.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);

            place.setTextSize(16);
            name.setTextSize(16);
            points.setTextSize(16);

            //-------------------------------------------------------------------------------------------
            TableRow row = new TableRow(this);
            row.setPadding(0, 10, 0, 10);
            row.addView(place);
            row.addView(name);
            row.addView(points);

            //-------------------------------------------------------------------------------------------
            if (x.getName().equals(profile.getName())) {
//                name.setBackgroundColor(Color.parseColor("#33005555"));
                row.setBackgroundColor(Color.parseColor("#33005555"));
                found = 1;
            }

            //-------------------------------------------------------------------------------------------
            tl.addView(row, tp);
//            ll.addView(text, lp);
            i += 1;
        }
    }
}
