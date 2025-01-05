package pt.ipleiria.estg.dei.fastwheels.model;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.MosquittoListener;
import pt.ipleiria.estg.dei.fastwheels.modules.Notification;
import pt.ipleiria.estg.dei.fastwheels.parsers.SupportParser;

public class MosquittoManager implements MosquittoListener {
    @Override
    public void onMosquittoReceiveData(String topic, String data) {
        switch (topic) {

            case Constants.MQTT_SUPPORTTICKET_CREATE:
            case Constants.MQTT_SUPPORTTICKET_UPDATE:
                Support createSupport = SupportParser.parseSupportData(data);
                new Notification("Support Ticket", "New update has been placed about your support ticket, please visit out platform to see!");

                break;

            case Constants.MQTT_RESERVATION_CREATE:
            case Constants.MQTT_RESERVATION_UPDATE:
                
                break;

            case Constants.MQTT_CARREVIEW_CREATE:
                break;
        }
    }
}
