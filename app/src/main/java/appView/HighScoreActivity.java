package appView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import entity.AppContent;
import entity.HighScore;
import entity.Profile;

public class HighScoreActivity extends AppCompatActivity {

    private Intent _intent;
    private AppContent _appContent;
    private int _found;

    private Profile _profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        this._intent = getIntent();
        this._appContent = (AppContent) _intent.getSerializableExtra("appContent");
        this._found = 0;
        this._profile = _appContent.getProfile();

        ArrayList<HighScore> highScore =_appContent.getHighScore();

//        LinearLayout ll = (LinearLayout) findViewById(appView.R.id.highscore);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        setTables(highScore);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToProfile();
            }
        });

    }

    public void backToProfile() {
        Intent intent = new Intent(this, TabMenuActivity.class);
//        intent.putExtra("appContent", _appContent);
        intent.putExtra("item", 0);
        this.startActivity(intent);
    }

    private void setTables(ArrayList<HighScore> highScore) {
        ArrayList<HighScore> hs = setTable1(highScore);
        setTable2(hs);
    }

    private ArrayList<HighScore> setTable1(ArrayList<HighScore> high) {

        HighScore firstPlace = high.remove(0);
        HighScore secondPlace = high.remove(0);
        HighScore thirdPlace = high.remove(0);

        TableRow row1 = findViewById(R.id.row1);
        TableRow row2 = findViewById(R.id.row2);
        TableRow row3 = findViewById(R.id.row3);
        TableRow row4 = findViewById(R.id.row4);

        TableRow.LayoutParams firstColumn = new TableRow.LayoutParams(0);
        TableRow.LayoutParams secondColumn = new TableRow.LayoutParams(1);
        TableRow.LayoutParams thirdColumn = new TableRow.LayoutParams(2);

        //-------------------------------------------------------------------------------------------
        TextView text1 = new TextView(this);
        TextView text2 = new TextView(this);
        TextView text3 = new TextView(this);

        text1.setText(String.format("%s\n%s", firstPlace.getName(), Integer.toString(firstPlace.getPoints())));
        text2.setText(String.format("%s\n%s", secondPlace.getName(), Integer.toString(secondPlace.getPoints())));
        text3.setText(String.format("%s\n%s", thirdPlace.getName(), Integer.toString(thirdPlace.getPoints())));

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

        _found = 1;
        if (firstPlace.getName().equals(_profile.getName()))
            text1.setBackgroundColor(Color.parseColor("#33005555"));
        else if (secondPlace.getName().equals(_profile.getName()))
            text2.setBackgroundColor(Color.parseColor("#33005555"));
        else if (thirdPlace.getName().equals(_profile.getName()))
            text3.setBackgroundColor(Color.parseColor("#33005555"));
        else
            _found = 0;

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


    private void setTable2(ArrayList<HighScore> high) {

        TableLayout tl = findViewById(appView.R.id.table2);
        TableLayout.LayoutParams tp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        int i = 4;

        for (HighScore hs : high) {

            TextView place = new TextView(this);
            place.setText(String.format("%s  ", Integer.toString(i)));

            TextView name = new TextView(this);
            name.setText(String.format("%s", hs.getName()));

            TextView points = new TextView(this);
            points.setText(String.format("%s", Integer.toString(hs.getPoints())));
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
            if (hs.getName().equals(_profile.getName())) {
//                name.setBackgroundColor(Color.parseColor("#33005555"));
                row.setBackgroundColor(Color.parseColor("#33005555"));
                _found = 1;
            }

            //-------------------------------------------------------------------------------------------
            tl.addView(row, tp);
//            ll.addView(text, lp);
            i += 1;
        }
    }
}
