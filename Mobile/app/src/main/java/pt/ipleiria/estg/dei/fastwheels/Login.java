package pt.ipleiria.estg.dei.fastwheels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class Login extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;
    private TextView toRegisterButton;
    private CheckBox keepLoggedInCheckbox;

    private static final String PREFS_NAME = "FastWheelsPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_KEEP_LOGGED_IN = "keepLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        userEmail = findViewById(R.id.emailEditText);
        userPassword = findViewById(R.id.passwordEditText);
        toRegisterButton = findViewById(R.id.noAccount);
        keepLoggedInCheckbox = findViewById(R.id.keepLoggedInCheckbox);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean keepLoggedIn = sharedPreferences.getBoolean(KEY_KEEP_LOGGED_IN, false);

        if (keepLoggedIn) {
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, null);
            String savedPassword = sharedPreferences.getString(KEY_PASSWORD, null);

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

        if(!Helpers.isPasswordValid(userPassword.getText().toString())) {
            //TODO: custom error handler
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Helpers.isEmailValid(userEmail.getText().toString())) {
            //TODO: custom error handler
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Query to database (POST) to check if user exists

        if (keepLoggedInCheckbox.isChecked()) {
            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_KEEP_LOGGED_IN, true);
            editor.putString(KEY_EMAIL, userEmail.getText().toString());
            editor.putString(KEY_PASSWORD, userPassword.getText().toString());
            editor.apply();
        } else {
            // Remover credenciais caso não queira manter o login automático
            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }

        // Redirecionar para a MainActivity
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}