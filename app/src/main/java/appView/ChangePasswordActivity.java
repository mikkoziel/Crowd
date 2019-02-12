package appView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import entity.AppContent;
import entity.Profile;
import presenter.ChangePasswordPresenter;

public class ChangePasswordActivity extends AppCompatActivity {

    private ProgressBar _progress;
    private TextView _oldPassword;
    private TextView _newPassword;
    private TextView _repeatPassword;

    private AppContent _appContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(appView.R.layout.activity_change_password);

        this._progress = findViewById(appView.R.id.progressBar);
        _progress.setVisibility(View.GONE);

        Intent intent = getIntent();
        this._appContent = (AppContent) intent.getSerializableExtra("appContent");

        this._oldPassword = findViewById(appView.R.id.oldPassword);
        this._newPassword = findViewById(appView.R.id.newPassword);
        this._repeatPassword = findViewById(appView.R.id.repeatPassword);
    }

    public void backButton(View view){
        Intent intent = new Intent(this, TabMenuActivity.class);
//        intent.putExtra("appContent", _appContent);
        intent.putExtra("item", 2);
        this.startActivity(intent);
    }

    public void changePassword(View view){
        Button change = findViewById(R.id.changeButton);
        Button back = findViewById(R.id.button2);

        change.setClickable(false);
        back.setClickable(false);

        if(checkPasswords()) {
            String newPasswordText = _newPassword.getText().toString();
            ChangePasswordPresenter changePasswordPresenter = new ChangePasswordPresenter(this, _progress, newPasswordText, 1, _appContent);
            changePasswordPresenter.execute();
        }

        change.setClickable(true);
        back.setClickable(true);
    }

    public Boolean checkPasswords(){
        String oldPasswordText = _oldPassword.getText().toString();
        ChangePasswordPresenter changePasswordPresenter = new ChangePasswordPresenter(this, _progress, oldPasswordText, 0, _appContent);
        changePasswordPresenter.setPasswordCheck(_newPassword.getText().toString());
        changePasswordPresenter.setPasswordCheck2(_repeatPassword.getText().toString());
        changePasswordPresenter.execute();

        while(changePasswordPresenter.getSemaphore()){ }
        return changePasswordPresenter.getSuccess();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TabMenuActivity.class);
//        intent.putExtra("appContent", _appContent);
        intent.putExtra("item", 2);
        this.startActivity(intent);
    }

}
