package pt.ipleiria.estg.dei.fastwheels.parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.model.Notification;
import pt.ipleiria.estg.dei.fastwheels.model.Reservation;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;


public class NotificationParser {

    public static Notification parseNotificationData(String response) {

        Notification newNotification = null;

        try {
            JSONObject fetchData = new JSONObject(response);

            int id = fetchData.getInt("id");
            int clientId = fetchData.getInt("clientId");
            String content = fetchData.getString("content");
            Timestamp createdAt = Timestamp.valueOf(fetchData.getString("createdAt"));
            int read = fetchData.getInt("read");

            newNotification = new Notification(id, clientId, read, content, createdAt);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newNotification;
    }

    public static ArrayList<Notification> parseNotificationsData(JSONArray response) {
        ArrayList<Notification> notificationList = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject fetchData = response.getJSONObject(i);

                int id = fetchData.getInt("id");
                int clientId = fetchData.getInt("clientId");
                String content = fetchData.getString("content");
                Timestamp createdAt = Timestamp.valueOf(fetchData.getString("createdAt"));
                int read = fetchData.getInt("read");

                Notification notification = new Notification(id, clientId, read, content, createdAt);
                notificationList.add(notification);

                Log.d("NOTIFICATION", "New notification has been parse, total: " + notificationList.size());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return notificationList;
    }
}
