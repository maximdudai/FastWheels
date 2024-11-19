package pt.ipleiria.estg.dei.fastwheels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;
    private TextView toRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        userEmail = findViewById(R.id.emailEditText);
        userPassword = findViewById(R.id.passwordEditText);
        toRegisterButton = findViewById(R.id.noAccount);

        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister = new Intent(Login.this, Register.class);
                startActivity(toRegister);
            }
        });
    }

    public void handleLoginAccount(View v) {

        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}