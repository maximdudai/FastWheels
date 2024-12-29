package pt.ipleiria.estg.dei.fastwheels.parsers;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginParser {

    public static String parseLoginData(String response) {

        String token = "";

        try {
            JSONObject fetchData = new JSONObject(response);
            String userToken = fetchData.getString("token");

            token = userToken;
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return token;
    }

}
