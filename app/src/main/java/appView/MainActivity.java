package appView;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import presenter.CheckLoginPresenter;
import presenter.RegistrationPresenter;

public class MainActivity extends AppCompatActivity {
    public Intent intent;
    public EditText loginT, passwordT;
    public Button submit;
    public Button register;
    public ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginT = findViewById(R.id.login);
        passwordT = findViewById(R.id.password);
        submit = findViewById(R.id.button);
        register = findViewById(R.id.button2);
        progress = findViewById(R.id.progressBar);

        progress.setVisibility(View.GONE);

    }

    public void loginBttn(View view){
        intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("item", 1);
        submit.setClickable(false);
        register.setClickable(false);

        CheckLoginPresenter checkLoginPresenter = new CheckLoginPresenter(this, progress, loginT, passwordT, intent, submit, register);
        checkLoginPresenter.execute();
    }

    public void registerBttn(View view){
        submit.setClickable(false);
        register.setClickable(false);

        RegistrationPresenter registerLogin = new RegistrationPresenter(this, progress, loginT, passwordT, submit, register);
        registerLogin.execute();
    }

    @Override
    public void onBackPressed() {
        createAlertDialog("Closing Activity", "Are you sure you want to exit?");
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
                        finish();
                        System.exit(0);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}

