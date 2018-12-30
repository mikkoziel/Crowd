package com.app.crowd1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import back.Profil;
import gui.PasswordChanger;

public class changePasswordActivity extends AppCompatActivity {

    public ProgressBar progress;
    public Profil profil;
    public TextView oldPassword;
    public TextView newPassword;
    public TextView repeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        this.progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);

        Intent intent = getIntent();
        this.profil = (Profil) intent.getSerializableExtra("profil");

        this.oldPassword = findViewById(R.id.oldPassword);
        this.newPassword = findViewById(R.id.newPassword);
        this.repeatPassword = findViewById(R.id.repeatPassword);
    }

    public void backBttn(View view){
        Intent intent = new Intent(this, changePasswordActivity.class);
        intent.putExtra("profil", profil);
        this.startActivity(intent);
    }

    public void changePassword(View view){
        switch(checkPasswords()) {
            case 0:
                String newPasswordText = newPassword.getText().toString();
                PasswordChanger passwordChanger = new PasswordChanger(this, progress, profil, newPasswordText, 1);
                passwordChanger.execute("");
                return;
            case 1:
                Toast.makeText(this, "Passwords doesn't match", Toast.LENGTH_LONG).show();
                return;
            case 2:
                Toast.makeText(this, "New password is the same as old password", Toast.LENGTH_LONG).show();
                return;
            case 3:
                Toast.makeText(this, "Wrong old password", Toast.LENGTH_LONG).show();
                return;
            default:
        }
    }

    public int checkPasswords(){
        String oldPasswordText = oldPassword.getText().toString();
        PasswordChanger passwordChanger = new PasswordChanger(this, progress, profil, oldPasswordText, 0);
        passwordChanger.setPasswordCheck(newPassword.getText().toString());
        passwordChanger.setPasswordCheck2(repeatPassword.getText().toString());
        passwordChanger.execute("");

        while(passwordChanger.getSemafor() == 4){ }
//        if(passwordChanger.getIsSuccess()) {
//            if (newPassword.getText() == repeatPassword.getText()) {
//                return 1;
//            } else {
//                if (oldPassword.getText() == newPassword.getText()) {
//                    return 2;
//                } else {
//                    return 0;
//                }
//            }
//        }
//        else {
//            return 3;
//        }
        return passwordChanger.getSemafor();
    }
}
