package pt.ipleiria.estg.dei.fastwheels.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pt.ipleiria.estg.dei.fastwheels.model.Reservation;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;

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

    public static boolean validateFieldAno(EditText editText, int minYear, int maxYear) {
        String yearet = editText.getText().toString();
        if (TextUtils.isEmpty(yearet)) {
            editText.setError("Campo obrigatório");
            return false;
        }

        try {
            int year = Integer.parseInt(yearet);

            if (year < minYear || year > maxYear) {
                editText.setError("Ano deve estar entre "+minYear+" e "+maxYear);
                return false;
            }
        } catch (NumberFormatException e) {
            editText.setError("Ano inválido");
            return false;
        }
        return true;
    }

    public static boolean validateNumPortas(EditText editText,int minPorta, int maxPorta) {
        String numPortasStr = editText.getText().toString().trim();

        if (TextUtils.isEmpty(numPortasStr)) {
            editText.setError("Campo obrigatório");
            return false;
        }

        try {
            int numPortas = Integer.parseInt(numPortasStr);

            if (numPortas < minPorta || numPortas > maxPorta) {
                editText.setError("Número de portas deve estar entre "+minPorta+" e "+maxPorta);
                return false;
            }
        } catch (NumberFormatException e) {
            editText.setError("Nº de portas inválido");
            return false;
        }

        return true;
    }

    public static boolean validateCodigoPostal(EditText editText) {
        String postalCode = editText.getText().toString().trim();
        String regex = "^\\d{4}-\\d{3}$"; // Formato ####-###

        if (TextUtils.isEmpty(postalCode)) {
            editText.setError("Campo obrigatório");
            return false;
        } else if (!postalCode.matches(regex)) {
            editText.setError("Formato inválido. Use ####-###");
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

    public static boolean validateFieldDisponivel(EditText editText, Calendar minimumDate, Context context) {
        if (TextUtils.isEmpty(editText.getText())) {
            editText.setError("Campo obrigatório");
            return false;
        }

        try {
            // Converter a data no formato dd/MM/yyyy para verificar
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-PT"));
            Date selectedDate = sdf.parse(editText.getText().toString());

            if (selectedDate == null || selectedDate.before(minimumDate.getTime())) {
                editText.setError("Data inválida");
                return false;
            } else {
                editText.setError(null);
            }
        } catch (Exception e) {
            editText.setError("Data inválida");
            return false;
        }
        return true;
    }

    public static boolean validateFieldPrice(EditText editText) {
        String priceText = editText.getText().toString();
        if (TextUtils.isEmpty(priceText)) {
            editText.setError("Campo obrigatório");
            return false;
        }
        try {
            BigDecimal price = new BigDecimal(priceText);
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                editText.setError("O valor deve ser maior que zero");
                return false;
            }

            if (price.scale() > 2) { // scale -> nº casas decimais
                editText.setError("O valor deve ter no máximo 2 casas decimais");
                return false;
            }

        } catch (NumberFormatException e) {
            editText.setError("Formato inválido. Use ##.##");
            return false;
        }
        return true;
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("pt-PT"));
        return dateTimeFormat.format(new Date());
    }

    public static ArrayList<Vehicle> filterVehicleListByPersonal(User loggedUser, ArrayList<Vehicle> vehicleList) {

        ArrayList<Vehicle> auxVehicle = new ArrayList<>();

        for(Vehicle car: vehicleList) {
            if(car.getClientId() == loggedUser.getId()) {
                auxVehicle.add(car);
            }
        }
        return auxVehicle;
    }
    public static ArrayList<Vehicle> filterVehicleByNotPersonal(User loggedUser, ArrayList<Vehicle> vehicleList) {

        ArrayList<Vehicle> auxVehicle = new ArrayList<>();

        for(Vehicle car: vehicleList) {
            if(car.getClientId() != loggedUser.getId()) {
                if(!car.isStatus()) // false = not rented | true = rented
                    auxVehicle.add(car);
            }
        }
        return auxVehicle;
    }
    public static ArrayList<Vehicle> filterVehicleByReserved(ArrayList<Vehicle> vehicleList, ArrayList<Reservation> reservatonList, int loggedUser) {

        ArrayList<Vehicle> auxVehicle = new ArrayList<>();

        for(Vehicle car: vehicleList) {

            for(Reservation reservs: reservatonList) {
                if(reservs.getCarId() == car.getId() && reservs.getClientId() == loggedUser) {
                    auxVehicle.add(car);
                }
            }
        }
        return auxVehicle;
    }

    public static Reservation getReservationByVehicleAndUser(ArrayList<Reservation> resList, int userId, int selectedVehicle) {

        System.out.println("--->API resList: " + resList);
        System.out.println("--->API userId: " + userId + ", selectedVehicle: " + selectedVehicle);

        for (Reservation reservs : resList) {
            System.out.println("--->API Checking: CarId=" + reservs.getCarId() + ", ClientId=" + reservs.getClientId());
            if (reservs.getCarId() == selectedVehicle && reservs.getClientId() == userId) {
                System.out.println("--->API Match found: " + reservs);
                return reservs;
            }
        }

        System.out.println("--->API No match found for userId=" + userId + ", vehicleId=" + selectedVehicle);
        return null;
    }

}
