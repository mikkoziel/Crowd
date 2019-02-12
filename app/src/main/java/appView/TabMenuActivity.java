package appView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import entity.AppContent;
import entity.GlobalClass;
import presenter.JsonPresenter;

public class TabMenuActivity extends AppCompatActivity {

    private Intent _intent;
    private Activity _activity;
    private AppContent _appContent;

    private JsonPresenter _jsonPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(appView.R.layout.activity_tab_menu);

        this._intent = getIntent();
        this._activity = this;
        this._jsonPresenter = new JsonPresenter(_activity);
        this._appContent = _jsonPresenter.getJSON(0);
//        GlobalClass global = ((GlobalClass)getApplicationContext());
//        this._appContent = global.getAppContent();
        //        this._appContent = (AppContent) _intent.getSerializableExtra("appContent");

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(appView.R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(appView.R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        Intent intent = getIntent();
        int item = (int) intent.getSerializableExtra("item");
        mViewPager.setCurrentItem(item);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(appView.R.menu.menu_tab_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == appView.R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
//            _intent.putExtra("appContent", _appContent);
            switch(position) {
                case 0:
                    ProfileTabMenuActivity tab1 = new ProfileTabMenuActivity();
                    tab1.setOnCreate(_activity, _intent, _appContent);
                    return tab1;
                case 1:
                    MenuTabMenuActivity tab2 = new MenuTabMenuActivity();
                    tab2.setOnCreate(_activity, _intent, _appContent);
                    return tab2;
                case 2:
                    SettingsTabMenuActivity tab3 = new SettingsTabMenuActivity();
                    tab3.setOnCreate(_activity, _intent, _appContent);
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch(position) {
                case 0:
                    return "PROFIL";
                case 1:
                    return "GAMES";
                case 2:
                    return "SETTINGS";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        createAlertDialog("Closing Activity", "Are you sure you want to logout?");
    }

    public void createAlertDialog(String title, String message){

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(_activity, MainActivity.class);
//                        intent.putExtra("appContent", _appContent);
                        _activity.startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
 }
