package appView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import entity.Profile;
import presenter.PasswordChanger;

public class changePasswordActivity extends AppCompatActivity {

    public ProgressBar progress;
    public Profile profile;
    public TextView oldPassword;
    public TextView newPassword;
    public TextView repeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(appView.R.layout.activity_change_password);

        this.progress = findViewById(appView.R.id.progressBar);
        progress.setVisibility(View.GONE);

        Intent intent = getIntent();
        this.profile = (Profile) intent.getSerializableExtra("profile");

        this.oldPassword = findViewById(appView.R.id.oldPassword);
        this.newPassword = findViewById(appView.R.id.newPassword);
        this.repeatPassword = findViewById(appView.R.id.repeatPassword);
    }

    public void backBttn(View view){
        Intent intent = new Intent(this, changePasswordActivity.class);
        intent.putExtra("profile", profile);
        this.startActivity(intent);
    }

    public void changePassword(View view){
        switch(checkPasswords()) {
            case 0:
                String newPasswordText = newPassword.getText().toString();
                PasswordChanger passwordChanger = new PasswordChanger(this, progress, profile, newPasswordText, 1);
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
        PasswordChanger passwordChanger = new PasswordChanger(this, progress, profile, oldPasswordText, 0);
        passwordChanger.setPasswordCheck(newPassword.getText().toString());
        passwordChanger.setPasswordCheck2(repeatPassword.getText().toString());
        passwordChanger.execute("");

        while(passwordChanger.getSemafor() == 4){ }
        return passwordChanger.getSemafor();
    }
}
