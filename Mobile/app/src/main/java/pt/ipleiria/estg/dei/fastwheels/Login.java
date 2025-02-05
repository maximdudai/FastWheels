package pt.ipleiria.estg.dei.fastwheels;

import android.content.Context;
import static pt.ipleiria.estg.dei.fastwheels.utils.Helpers.showMessage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.LoginListener;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.modules.Notification;

public class Login extends AppCompatActivity implements LoginListener {

    private EditText userEmail;
    private EditText userPassword;
    private TextView toRegisterButton;
    private CheckBox keepLoggedInCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.emailEditText);
        userPassword = findViewById(R.id.passwordEditText);
        toRegisterButton = findViewById(R.id.noAccount);
        keepLoggedInCheckbox = findViewById(R.id.keepLoggedInCheckbox);

        SingletonFastWheels.getInstance(getApplicationContext()).setLoginListener(this);
        
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        boolean keepLoggedIn = sharedPreferences.getBoolean(Constants.KEY_KEEP_LOGGED_IN, false);

        if (keepLoggedIn) {
            String savedUsername = sharedPreferences.getString(Constants.KEY_USERNAME, null);
            String savedPassword = sharedPreferences.getString(Constants.KEY_PASSWORD, null);

            if(savedUsername != null && savedPassword != null) {
                userEmail.setText(savedUsername);
                userPassword.setText(savedPassword);
            }
        }

        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister = new Intent(Login.this, Register.class);
                startActivity(toRegister);
            }
        });
    }

    public void handleLoginAccount(View v) {

        boolean isDataEmpty = userEmail.length() == 0 && userPassword.length() == 0;

        if(isDataEmpty)
            return;

        String loginEmail = userEmail.getText().toString();
        String loginPassword = userPassword.getText().toString();

/*
        if(!Helpers.isPasswordValid(loginPassword)) {
            //TODO: custom error handler
            showMessage(this, "Invalid Password");
            return;
        }
        if(!Helpers.isEmailValid(loginEmail)) {
            //TODO: custom error handler
            showMessage(this, "Invalid email address");
            return;
        }
*/
        // send authentication request to API
        SingletonFastWheels.getInstance(getApplicationContext()).loginAPI(loginEmail, loginPassword, getApplicationContext());
    }

    @Override
    public void onValidateLogin(User user, Context context) {

        if(user.getToken().isEmpty()) {
            Toast.makeText(context, "invalid authentication credentials", Toast.LENGTH_SHORT).show();
        } else {

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean(Constants.KEY_KEEP_LOGGED_IN, keepLoggedInCheckbox.isChecked());
            editor.putString(Constants.KEY_USERNAME, user.getName());
            editor.putString(Constants.KEY_EMAIL, user.getEmail());
            editor.putString(Constants.KEY_PASSWORD, userPassword.getText().toString());

            //atualizar a pass do user
            user.setPassword(userPassword.getText().toString());

            editor.apply();

            Notification notificacao1 = new Notification(Notification.TITLE_WELCOME, "Bem vindo! Novos veículos estão a sua espera");
            notificacao1.markAsRead();
            showMessage(this, ""+ notificacao1);
            //endregion

            // Redirecionar para a MainActivity
            Intent mainActivity = new Intent(this,  MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
    }
}