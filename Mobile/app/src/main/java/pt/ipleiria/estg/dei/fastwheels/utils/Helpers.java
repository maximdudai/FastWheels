package pt.ipleiria.estg.dei.fastwheels.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helpers {

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

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean validateFieldIsNotEmpty(EditText editText, String errorMessage) {
        if (TextUtils.isEmpty(editText.getText())) {
            editText.setError(errorMessage);
            return false;
        }
        return true;
    }

    //region DatePicker
    public static void showDatePickerDialog(Context context, EditText editText,
                                            Calendar minDate, OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    if (listener != null) {
                        listener.onDateSet(selectedYear, selectedMonth, selectedDay);
                    }
                }, year, month, day);

        if (minDate != null) {
            datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
        }
        datePickerDialog.show();
    }
    public interface OnDateSetListener {
        void onDateSet(int year, int month, int day);
    }
    //endregion

    public static boolean validateFieldPrice(EditText editText, String errorMessage) {
        String priceText = editText.getText().toString();
        if (TextUtils.isEmpty(priceText)) {
            editText.setError(errorMessage);
            return false;
        }
        try {
            BigDecimal price = new BigDecimal(priceText);
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                editText.setError("O valor deve ser maior que zero");
                return false;
            }
        } catch (NumberFormatException e) {
            editText.setError("Formato esperado: 50.00");
            return false;
        }
        return true;
    }

}
