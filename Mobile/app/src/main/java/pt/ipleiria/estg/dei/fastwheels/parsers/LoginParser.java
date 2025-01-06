package pt.ipleiria.estg.dei.fastwheels.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.model.User;

public class LoginParser {

    public static User parseLoginData(String response) {

        User userData = null;

        try {
            JSONObject fetchData = new JSONObject(response);

            String token = fetchData.getString("token");
            String name = fetchData.getString("username");
            int id = fetchData.getInt("id");
            String email = fetchData.getString("email");
            String phone = fetchData.getString("phone");
            String balance = fetchData.getString("balance");
            String iban = fetchData.getString("iban");

            System.out.println("------> userData: " + fetchData);

            userData = new User(token, id, name, email, phone, balance, iban);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return userData;
    }
}
