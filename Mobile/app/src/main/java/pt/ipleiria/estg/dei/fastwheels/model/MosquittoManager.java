package pt.ipleiria.estg.dei.fastwheels.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.MosquittoListener;
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
        switch (topic) {

            case Constants.MQTT_SUPPORTTICKET_CREATE:
            case Constants.MQTT_SUPPORTTICKET_UPDATE:
                Support createSupport = SupportParser.parseSupportData(data);
                String supportMessage = "New Update about your support ticket, please check it on our web platform";
                new Notification("Support Ticket", supportMessage);
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, supportMessage, Toast.LENGTH_SHORT).show();
                });
                break;

            case Constants.MQTT_RESERVATION_CREATE:
            case Constants.MQTT_RESERVATION_UPDATE:
                Reservation createReservation = ReservationParser.parseReservationData(data);
                String reservationMessage = "New Update about your reservation, please check it on our web platform";

                new Notification("Reservation", reservationMessage);
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, reservationMessage, Toast.LENGTH_SHORT).show();
                });

                break;

            case Constants.MQTT_CARREVIEW_CREATE:
                break;

            default:
                break;
        }
    }
}
