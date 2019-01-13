package appView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import entity.Profile;
import presenter.ChangePasswordPresenter;

public class ChangePasswordActivity extends AppCompatActivity {

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

    public void backButton(View view){
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra("profile", profile);
        this.startActivity(intent);
    }

    public void changePassword(View view){
        switch(checkPasswords()) {
            case 0:
                String newPasswordText = newPassword.getText().toString();
                ChangePasswordPresenter changePasswordPresenter = new ChangePasswordPresenter(this, progress, profile, newPasswordText, 1);
                changePasswordPresenter.execute("");
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
        ChangePasswordPresenter changePasswordPresenter = new ChangePasswordPresenter(this, progress, profile, oldPasswordText, 0);
        changePasswordPresenter.setPasswordCheck(newPassword.getText().toString());
        changePasswordPresenter.setPasswordCheck2(repeatPassword.getText().toString());
        changePasswordPresenter.execute("");

        while(changePasswordPresenter.getSemaphore() == 4){ }
        return changePasswordPresenter.getSemaphore();
    }
}
