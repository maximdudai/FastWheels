package pt.ipleiria.estg.dei.fastwheels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pt.ipleiria.estg.dei.fastwheels.utils.helpers;

public class Register extends AppCompatActivity {

    private EditText userName, userEmail, userPassword;
    private TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.nameEditText);
        userEmail = findViewById(R.id.emailEditText);
        userPassword = findViewById(R.id.passwordEditText);

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

        if(!helpers.isPasswordValid(userPassword.getText().toString())) {
            //TODO: custom error handler
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(helpers.isEmailValid(userEmail.getText().toString())) {
            //TODO: custom error handler
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Query to database (POST) to insert new user if inserted data is available

        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}