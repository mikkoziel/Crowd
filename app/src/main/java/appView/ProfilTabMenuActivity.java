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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    public int questionID = 1008;
    public String path = "/storage/sdcard/images/mario.png";

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

        Button button3 = rootView.findViewById(appView.R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    addImage(rootView);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button4 = rootView.findViewById(appView.R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    getImage(rootView, layout, lp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    public void addImage(View view) throws SQLException {
        if (_isConnect) {
            File file = new File(this.path);
            byte[] byteFile = imageToByte(file);
            makeConn(byteFile);
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

    private void makeConn(byte[] immAsBytes) throws SQLException {
        PreparedStatement pstmt = _connection.prepareStatement("UPDATE QUESTION SET questionImage = ? WHERE questionID = ?");
        ByteArrayInputStream bais = new ByteArrayInputStream(immAsBytes);
        pstmt.setBinaryStream(1, bais, immAsBytes.length);
        pstmt.setInt(2, questionID);
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

    public void getImage(View view, LinearLayout layout, LinearLayout.LayoutParams lp) throws SQLException {
        String query = "select * from Question where questionID = " + this.questionID + ";";
        if (_isConnect) {
            ResultSet res = _dbConnector.runQuery(query, _connection);
            while(res.next()) {
                Blob immAsBlob = res.getBlob("questionImage");
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
