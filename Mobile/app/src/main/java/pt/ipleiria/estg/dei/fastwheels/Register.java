package pt.ipleiria.estg.dei.fastwheels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Timestamp;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.model.Notification;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class Register extends AppCompatActivity {

    private EditText userName, userEmail, userPassword, userRepeatPassword;
    private TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.nameEditText);
        userEmail = findViewById(R.id.emailEditText);
        userPassword = findViewById(R.id.passwordEditText);
        userRepeatPassword = findViewById(R.id.repeatPasswordEditText);

        loginButton = findViewById(R.id.alreadyRegisterd);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(Register.this, Login.class);
                startActivity(toLogin);
            }
        });
    }

    public void handleRegisterAccount(View v) {

        boolean isDataEmpty = userName.length() == 0 && userEmail.length() == 0 && userPassword.length() == 0;

        if(isDataEmpty)
            return;

        if(!userPassword.getText().toString().equals(userRepeatPassword.getText().toString())) {
            //TODO: custom error handler
            Toast.makeText(this, "password's don't match", Toast.LENGTH_SHORT).show();
            return;
        }

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


        // Dados inseridos
        String clientName = userName.getText().toString();
        String clientEmail = userEmail.getText().toString();
        String clientPassword = userPassword.getText().toString();

        int clientId = 0;

        // Criar o user
        User newClient = new User(
                "",
                clientId,
                clientName,
                clientEmail,
                "",
                "",
                ""
        );
        newClient.setPassword(clientPassword);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(Constants.KEY_KEEP_LOGGED_IN, false);
        editor.putString(Constants.KEY_USERNAME, clientName);
        editor.putString(Constants.KEY_EMAIL, clientEmail);
        editor.putString(Constants.KEY_PASSWORD, userPassword.getText().toString());
        editor.apply();

        SingletonFastWheels.getInstance(getApplicationContext()).addUserAPI(newClient, getApplicationContext());
        SingletonFastWheels.getInstance(getApplicationContext()).setUser(newClient);

        Notification welcomeNotifiy = new Notification(0, newClient.getId(), 1, "Bem vindo! Novos veículos estão a sua espera", new Timestamp(System.currentTimeMillis()));
        SingletonFastWheels.getInstance(getApplicationContext()).createNotificationAPI(welcomeNotifiy, getApplicationContext());

        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}