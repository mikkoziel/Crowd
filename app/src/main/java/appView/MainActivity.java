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

import entity.AppContent;
import presenter.StartAppPresenter;
import presenter.RegistrationPresenter;

public class MainActivity extends AppCompatActivity {
    private EditText _loginT;
    private EditText _passwordT;
    private Button _submit;
    private Button _register;
    private ProgressBar _progress;

    private AppContent _appContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _loginT = findViewById(R.id.login);
        _passwordT = findViewById(R.id.password);
        _submit = findViewById(R.id.button);
        _register = findViewById(R.id.button2);
        _progress = findViewById(R.id.progressBar);

        _progress.setVisibility(View.GONE);

        _appContent = new AppContent();//empty for now
    }

    public void loginButton(View view){
        Intent intent = new Intent(this, TabMenuActivity.class);
        intent.putExtra("item", 1);
        _submit.setClickable(false);
        _register.setClickable(false);

        StartAppPresenter startAppPresenter = new StartAppPresenter(this, _progress, _loginT, _passwordT, intent, _submit, _register, _appContent);
        startAppPresenter.execute();
    }

    public void registerButton(View view){
        _submit.setClickable(false);
        _register.setClickable(false);

        RegistrationPresenter registerLogin = new RegistrationPresenter(this, _progress, _loginT, _passwordT, _submit, _register);
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

