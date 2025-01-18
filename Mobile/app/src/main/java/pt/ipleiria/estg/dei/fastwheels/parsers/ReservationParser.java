package pt.ipleiria.estg.dei.fastwheels.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.model.Reservation;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;


public class ReservationParser {

    public static Reservation parseReservationData(String response) {

        Reservation reservationData = null;

        try {
            JSONObject fetchData = new JSONObject(response);

            int id = fetchData.getInt("id");
            int clientId = fetchData.getInt("clientId");
            int carId = fetchData.getInt("carId");

            Timestamp dateStart = Timestamp.valueOf(fetchData.getString("dateStart"));
            Timestamp dateEnd = Timestamp.valueOf(fetchData.getString("dateEnd"));

            int filled = fetchData.getInt("filled");
            double value = fetchData.getDouble("value");
            double feeValue = fetchData.getDouble("feeValue");
            double carValue = fetchData.getDouble("carValue");

            reservationData = new Reservation(id, clientId, carId, dateStart, dateEnd, filled, value, feeValue, carValue);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reservationData;
    }

    public static ArrayList<Reservation> parseReservationsData(JSONArray response) {
        ArrayList<Reservation> reservationsList = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject fetchData = response.getJSONObject(i);

                int id = fetchData.getInt("id");
                int clientId = fetchData.getInt("clientId");
                int carId = fetchData.getInt("carId");

                Timestamp dateStart = Timestamp.valueOf(fetchData.getString("dateStart"));
                Timestamp dateEnd = Timestamp.valueOf(fetchData.getString("dateEnd"));

                int filled = fetchData.getInt("filled");
                double value = fetchData.getDouble("value");
                double feeValue = fetchData.getDouble("feeValue");
                double carValue = fetchData.getDouble("carValue");

                Reservation reservation = new Reservation(id, clientId, carId, dateStart, dateEnd, filled, value, feeValue, carValue);
                reservationsList.add(reservation);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return reservationsList;
    }
}
