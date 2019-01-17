package appView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Profile;
import interactor.DataBaseConnector;

public class ProfilTabMenuActivity extends Fragment {

    public Activity activity;
    public Intent thisIntent;
    public Profile profile;

    public static final int PICK_PHOTO_FOR_AVATAR = 1;
//    public int ID = 96;
//    public String path = "/storage/sdcard/images/+.png";
//    public String choice = "answer";

    public EditText number;
    public ToggleButton toggle;
    public EditText image;

    private DataBaseConnector _dbConnector;
    private Connection _connection;
    private Boolean _isConnect;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public void setOnCreate(Activity activity, Intent intent){
        this.activity = activity;
        this.thisIntent = intent;

        _dbConnector = new DataBaseConnector();
        _connection = _dbConnector.makeConnection();
        _isConnect = _dbConnector.checkConnection(_connection);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.profil_tab_menu, container, false);

        this.profile = (Profile) thisIntent.getSerializableExtra("profile");

        TextView user = rootView.findViewById(R.id.userName);
        TextView points = rootView.findViewById(R.id.points);

        user.setText(String.format("Username: %s", profile.getName()));
        points.setText(String.format("Points: %s", Integer.toString(profile.getPoints())));

        final LinearLayout layout = rootView.findViewById(appView.R.id.itemlayout);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        verifyStoragePermissions(activity);

        number = rootView.findViewById(appView.R.id.login);
        toggle = rootView.findViewById(appView.R.id.toggle);
        image = rootView.findViewById(appView.R.id.image);

        Button button3 = rootView.findViewById(appView.R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    addImage(rootView, Integer.parseInt(number.getText().toString()), image.getText().toString(), toggle.getText().toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button4 = rootView.findViewById(appView.R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    getImage(rootView, layout, lp, Integer.parseInt(number.getText().toString()), toggle.getText().toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    public void addImage(View view, int number, String image, String choice) throws SQLException {
        if (_isConnect) {
            String path = "/storage/sdcard/images/" + image;
            File file = new File(path);
            byte[] byteFile = imageToByte(file);
            makeConn(byteFile, number, choice);
        }
        else{
            Toast.makeText(activity, "Connection null", Toast.LENGTH_LONG).show();
        }
    }

    private byte[] imageToByte(File file) {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private void makeConn(byte[] immAsBytes, int number, String choice) throws SQLException {
        PreparedStatement pstmt = _connection.prepareStatement("UPDATE "+ choice +" SET "+ choice +"Image = ? WHERE "+ choice +"ID = ?");
        ByteArrayInputStream bais = new ByteArrayInputStream(immAsBytes);
        pstmt.setBinaryStream(1, bais, immAsBytes.length);
        pstmt.setInt(2, number);
        int result = pstmt.executeUpdate();
        pstmt.close();
        if(result < 1){
            Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show();
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void getImage(View view, LinearLayout layout, LinearLayout.LayoutParams lp, int number, String choice) throws SQLException {
        String query = "select * from "+ choice +" where "+ choice +"ID = " + number + ";";
        if (_isConnect) {
            ResultSet res = _dbConnector.runQuery(query, _connection);
            while(res.next()) {
                Blob immAsBlob = res.getBlob( choice +"Image");
                byte[] immAsBytes = immAsBlob.getBytes(1, (int)immAsBlob.length());
                InputStream in = new ByteArrayInputStream(immAsBytes);
                Bitmap bmp = BitmapFactory.decodeStream(in);

                ImageView image = new ImageView(activity);
                image.setImageBitmap(bmp);
                layout.addView(image, lp);
            }
        }
    }



}
