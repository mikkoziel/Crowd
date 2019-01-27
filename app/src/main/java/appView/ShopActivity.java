package appView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import entity.Profile;

public class ShopActivity extends AppCompatActivity {

    public Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent intent = getIntent();
        this.profile = (Profile) intent.getSerializableExtra("profile");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToProfil();
            }
        });
    }

    public void backToProfil(){
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("profile", profile);
        intent.putExtra("item", 0);
        this.startActivity(intent);
    }
}
