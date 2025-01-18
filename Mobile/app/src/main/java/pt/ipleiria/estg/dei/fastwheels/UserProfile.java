package pt.ipleiria.estg.dei.fastwheels;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.ProfileListener;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.modules.InputDialog;

public class UserProfile extends AppCompatActivity implements InputDialog.OnInputListener, ProfileListener {
    private TextView loggedEmail, loggedName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        loggedEmail = findViewById(R.id.loggedEmail);
        loggedName = findViewById(R.id.loggedName);

        // get saved data
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);

        // update UI
        loggedEmail.setText(sharedPreferences.getString(Constants.KEY_EMAIL, null));
        loggedName.setText(sharedPreferences.getString(Constants.KEY_USERNAME, null));

        findViewById(R.id.changeCurrentEmail).setOnClickListener(v -> showDialog("Update Email", "Enter your new email", "email"));
        findViewById(R.id.changeCurrentPassword).setOnClickListener(v -> showDialog("Update Password", "Enter your new password", "password"));
        findViewById(R.id.changeCurrentIban).setOnClickListener(v -> showDialog("Update IBAN", "Enter your new IBAN", "iban"));
        findViewById(R.id.changeCurrentPhone).setOnClickListener(v -> showDialog("Update Phone Number", "Enter your new phone number", "phone"));


        //
        SingletonFastWheels.getInstance(getApplicationContext()).setProfileListener(this);
        user = SingletonFastWheels.getInstance(getApplicationContext()).getUser();
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

    public void handleSupport(View v) {
        Intent supportPage = new Intent(this, Support.class);
        startActivity(supportPage);
    }

    private void showDialog(String title, String hint, String type) {
        InputDialog dialog = InputDialog.newInstance(title, hint, type);
        dialog.setOnInputListener(this);
        dialog.show(getSupportFragmentManager(), "InputDialogFragment");
    }

    @Override
    public void onInputReceived(String input, String type) {
        switch (type) {
            case "email":
                user.setEmail(input);
                break;
            case "password":
                user.setPassword(input);
                break;
            case "iban":
                user.setIban(input);
                break;
            case "phone":
                user.setPhone(input);
                break;
            default:
                Toast.makeText(this, "Unknown type: " + input, Toast.LENGTH_SHORT).show();
        }

        SingletonFastWheels.getInstance(getApplicationContext()).setUser(user);
        SingletonFastWheels.getInstance(getApplicationContext()).updateProfileAPI(user, getApplicationContext());
    }

    @Override
    public void onProfileUpdate(User user, Context context) {

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Constants.KEY_USERNAME, user.getName());
        editor.putString(Constants.KEY_EMAIL, user.getEmail());
        editor.putString(Constants.KEY_PASSWORD, user.getPassword());

        loggedEmail.setText(user.getEmail());
        loggedName.setText(user.getName());

        System.out.println("---> profile update: " + sharedPreferences.getString(Constants.KEY_USERNAME, null));
        System.out.println("---> profile update: " + sharedPreferences.getString(Constants.KEY_EMAIL, null));
    }
}
