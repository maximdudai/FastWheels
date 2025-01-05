package pt.ipleiria.estg.dei.fastwheels.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.MosquittoListener;
import pt.ipleiria.estg.dei.fastwheels.modules.Notification;
import pt.ipleiria.estg.dei.fastwheels.parsers.ReservationParser;
import pt.ipleiria.estg.dei.fastwheels.parsers.SupportParser;

public class MosquittoManager implements MosquittoListener {

    private Context context;
    private MosquittoListener mosquittoListener;

    public MosquittoManager(Context context) {
        if(context == null) {
            throw new IllegalArgumentException("Mosquitto context cannot be null!");
        }
        this.context = context.getApplicationContext();
    }

    @Override
    public void onMosquittoReceiveData(String topic, String data) {
        Log.d("FW_MQTT", "Received data from" + topic);
        switch (topic) {


            case Constants.MQTT_SUPPORTTICKET_CREATE:
            case Constants.MQTT_SUPPORTTICKET_UPDATE:
                Support createSupport = SupportParser.parseSupportData(data);
                new Notification("Support Ticket", "New update has been placed about your support ticket, please visit out platform to see!");
                Toast.makeText(context, "support ticket data", Toast.LENGTH_SHORT).show();
                break;

            case Constants.MQTT_RESERVATION_CREATE:
            case Constants.MQTT_RESERVATION_UPDATE:
                Reservation createReservation = ReservationParser.parseReservationData(data);
                new Notification("Reservation", "New reservation has beed created");
                Toast.makeText(context, "Reservation data", Toast.LENGTH_SHORT).show();
                break;

            case Constants.MQTT_CARREVIEW_CREATE:
                break;

            default:
                break;
        }
    }
}
