package pt.ipleiria.estg.dei.fastwheels.utils;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class helpers {

    public static boolean isEmailValid(String email) {
        if(email == null)
            return false;

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // password should include [A-Z] [a-z] [0-9] [min. length: 8 chars]
    public static boolean isPasswordValid(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static void showError(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
