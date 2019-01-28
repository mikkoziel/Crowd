package appView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import entity.AppContent;

public class ShopActivity extends AppCompatActivity {

    private AppContent _appContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent intent = getIntent();
        this._appContent =(AppContent) intent.getSerializableExtra("appContent");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToProfile();
            }
        });
    }

    public void backToProfile(){
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("appContent", _appContent);
        intent.putExtra("item", 0);
        this.startActivity(intent);
    }
}
