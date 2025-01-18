package pt.ipleiria.estg.dei.fastwheels.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ipleiria.estg.dei.fastwheels.model.User;

public class RegistoParser {

    public static User parseRegistoData(String response) {
        User userData = null;

        try {
            JSONObject fetchData = new JSONObject(response);

            String usertoken = fetchData.optString("token");
            int userid = fetchData.optInt("id", -1);
            String username = fetchData.optString("username");
            String useremail = fetchData.optString("email");
            String userphone = fetchData.optString("phone");
            String userbalance = fetchData.optString("balance");
            String useriban = fetchData.optString("iban");

            userData = new User(usertoken, userid, username, useremail, userphone, userbalance, useriban);

        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println("Erro ao parsear os dados do user: " + e.getMessage());
        }
        return userData;
    }
}