package com.app.crowd1;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import back.Question;

public class MenuActivity extends AppCompatActivity {

    Connection con;
    String user, pass, db, ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LinearLayout ll = (LinearLayout)findViewById(R.id.layout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        String z = "";

        try {
            con = connectionClass(user, pass, db, ip);
            if (con == null) {
                z = "Check Your Internet Access!";
            }
            else{
                String query = "select * from Game";
                Statement stmt = con.createStatement();
                ResultSet res = stmt.executeQuery(query);
                while(res.next()){
                    Question question = new Question(res.getString("Name"));
                    Button myButton = new Button(this);
                    myButton.setText(question.getQuestion());
                    ll.addView(myButton, lp);
                    con.close();
                }
            }
        }
        catch(Exception e){
            //isSuccess = false;
            z = e.getMessage();

        }

        for(int i = 0; i<20; i++) {
            Button myButton = new Button(this);
            myButton.setText("Push Me");
            ll.addView(myButton, lp);
        }
    }



    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connectioin = null;
        String connectionURL = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver;//den1.mssql8.gear.host/crowd;user=crowd;password=Ng65JF4j79-!;";
            connectioin = DriverManager.getConnection(connectionURL);
        }
        catch(SQLException e){
            Log.e("error here 1; ", e.getMessage());
        }
        catch(ClassNotFoundException e){
            Log.e("error here 2; ", e.getMessage());
        }
        catch(Exception e){
            Log.e("error here 1; ", e.getMessage());
        }
        return connectioin;
    }
}
