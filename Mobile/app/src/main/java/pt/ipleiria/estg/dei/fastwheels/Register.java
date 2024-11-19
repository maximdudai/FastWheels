package pt.ipleiria.estg.dei.fastwheels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        if(userName.length() == 0 && userEmail.length() == 0 && userPassword.length() == 0) {

        }

        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}