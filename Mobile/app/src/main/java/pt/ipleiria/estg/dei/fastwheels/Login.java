package pt.ipleiria.estg.dei.fastwheels;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.LoginListener;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.modules.Notification;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class Login extends AppCompatActivity implements LoginListener {

    private EditText userEmail;
    private EditText userPassword;
    private TextView toRegisterButton;
    private CheckBox keepLoggedInCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        userEmail = findViewById(R.id.emailEditText);
        userPassword = findViewById(R.id.passwordEditText);
        toRegisterButton = findViewById(R.id.noAccount);
        keepLoggedInCheckbox = findViewById(R.id.keepLoggedInCheckbox);

        SingletonFastWheels.getInstance(getApplicationContext()).setLoginListener(this);
        
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        boolean keepLoggedIn = sharedPreferences.getBoolean(Constants.KEY_KEEP_LOGGED_IN, false);

        if (keepLoggedIn) {
            String savedEmail = sharedPreferences.getString(Constants.KEY_EMAIL, null);
            String savedPassword = sharedPreferences.getString(Constants.KEY_PASSWORD, null);

            if (savedEmail != null && savedPassword != null) {
                // Redirecionar automaticamente para a MainActivity
                Intent mainActivity = new Intent(this, MainActivity.class);
                startActivity(mainActivity);
                finish(); // Finalizar a tela de login
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
//
//        if(!Helpers.isPasswordValid(loginPassword)) {
//            //TODO: custom error handler
//            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(!Helpers.isEmailValid(loginEmail)) {
//            //TODO: custom error handler
//            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
//            return;
//        }

        // send authentication request to API
        SingletonFastWheels.getInstance(getApplicationContext()).loginAPI(loginEmail, loginPassword, getApplicationContext());
    }

    @Override
    public void onValidateLogin(String token, String email, Context context) {
        System.out.println("----> token: " + token);
        if(token.isEmpty()) {
            Toast.makeText(context, "invalid authentication credentials", Toast.LENGTH_SHORT).show();
            return;
        }

        if (keepLoggedInCheckbox.isChecked()) {
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.KEY_KEEP_LOGGED_IN, true);
            editor.putString(Constants.KEY_EMAIL, userEmail.getText().toString());
            editor.putString(Constants.KEY_PASSWORD, userPassword.getText().toString());
            editor.apply();
        } else {
            // Remover credenciais caso não queira manter o login automático
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }

        // Redirecionar para a MainActivity
        Intent mainActivity = new Intent(this, UserProfile.class);
        startActivity(mainActivity);
        finish();
    }
}