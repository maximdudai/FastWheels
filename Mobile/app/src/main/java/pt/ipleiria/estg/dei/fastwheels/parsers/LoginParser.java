package pt.ipleiria.estg.dei.fastwheels.parsers;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginParser {

    public static String parseLoginData(String response) {

        String token = "";

        try {
            JSONObject fetchData = new JSONObject(response);
            String fetchUserToken = fetchData.getString("token");


            token = fetchUserToken;
            System.out.println("Token a ver: " + token);
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return token;
    }

}
