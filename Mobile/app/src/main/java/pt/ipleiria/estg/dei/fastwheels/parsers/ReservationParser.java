package pt.ipleiria.estg.dei.fastwheels.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

import pt.ipleiria.estg.dei.fastwheels.model.Reservation;


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
            Timestamp createAt = Timestamp.valueOf(fetchData.getString("createAt"));

            int filled = fetchData.getInt("filled");
            double value = fetchData.getDouble("value");
            double feeValue = fetchData.getDouble("feeValue");
            double carValue = fetchData.getDouble("carValue");

            reservationData = new Reservation(id, clientId, carId, dateStart, dateEnd, createAt, filled, value, feeValue, carValue);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reservationData;
    }

}
