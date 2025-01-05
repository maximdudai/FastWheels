package pt.ipleiria.estg.dei.fastwheels.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

import pt.ipleiria.estg.dei.fastwheels.model.Support;

public class SupportParser {

    public static Support parseSupportData(String response) {

        Support supportData = null;

        try {
            JSONObject fetchData = new JSONObject(response);

            int id = fetchData.getInt("id");
            int clientId = fetchData.getInt("clientId");
            String content = fetchData.getString("content");
            Timestamp createdAt = Timestamp.valueOf(fetchData.getString("createdAt"));
            int closed = fetchData.getInt("closed");
            String subject = fetchData.getString("subject");
            int reservationId = fetchData.getInt("reservationId");
            int status = fetchData.getInt("status");

            supportData = new Support(id, clientId, content, createdAt, closed, subject, reservationId, status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return supportData;
    }

}
