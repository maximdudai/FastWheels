package pt.ipleiria.estg.dei.fastwheels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        SingletonFastWheels.getInstance(getApplicationContext()).addUserAPI(newClient, getApplicationContext());


        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}