package appView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import entity.AppContent;
import entity.Item;

import static android.view.Gravity.CENTER;

public class ShopActivity extends AppCompatActivity {

    private AppContent _appContent;
    private ArrayList<Item> _shop;

    private LinearLayout _itemInfoLayout;
    private LinearLayout _itemsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent intent = getIntent();
        this._appContent =(AppContent) intent.getSerializableExtra("appContent");
        this._shop = _appContent.getShop();

        this._itemInfoLayout = findViewById(R.id.itemInfo);
        _itemInfoLayout.setVisibility(View.GONE);

        this._itemsLayout = findViewById(R.id.itemLayout);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToProfile();
            }
        });

        populateItems();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void populateItems(){
        int inRow = 3;
        int i =inRow;
        LinearLayout row = null;

        for(final Item item: _shop){
//            byte[] avatarIcon = item.getIcon();
            if(i == inRow) {
                i = 0;
                row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(0, 10, 0, 10);
                row.setGravity(CENTER);
                _itemsLayout.addView(row);
            }

            final Button itemButton = new Button(this);
//            Bitmap bitmapImage = BitmapFactory.decodeByteArray(avatarIcon, 0, avatarIcon.length);
//            Drawable drawableImage = new BitmapDrawable(getResources(), bitmapImage);
//            itemButton.setBackground(drawableImage);
            itemButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    itemBttnAction(item);
                }
            });
            itemButton.setPadding(10, 0, 10, 0);
//            _buttons.addView(avatarButton);

            FrameLayout frame = new FrameLayout(this);
            frame.setPadding(3,3,3,3);
            frame.setForegroundGravity(CENTER);
            frame.addView(itemButton, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT, CENTER));

            i+=1;

            row.addView(frame, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        }
    }

    private void itemBttnAction(Item item){
        _itemInfoLayout.setVisibility(View.VISIBLE);

        TextView itemName =  findViewById(R.id.itemName);
        TextView itemPrice =  findViewById(R.id.itemPrice);
        TextView itemDesc =  findViewById(R.id.itemDesc);

        itemName.setText(item.getName());
        itemPrice.setText(String.valueOf(item.getPrice()));
        itemDesc.setText(item.getDescription());

    }

    public void backToProfile(){
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("appContent", _appContent);
        intent.putExtra("item", 0);
        this.startActivity(intent);
    }

    public void cancelBttn(View view){
        _itemInfoLayout.setVisibility(View.GONE);
    }

    public void buyBttn(View view){
        _itemInfoLayout.setVisibility(View.GONE);
    }
}
