package pt.ipleiria.estg.dei.fastwheels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    public void handleLogout(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.KEY_EMAIL);
        editor.remove(Constants.KEY_KEEP_LOGGED_IN);
        editor.remove(Constants.KEY_PASSWORD);
        editor.apply();

        Intent toLogin = new Intent(this, Login.class);
        startActivity(toLogin);
        finish();
    }
}