package pt.ipleiria.estg.dei.fastwheels.model;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.MosquittoListener;

public class Mosquitto {
    private MqttClient mqttClient;
    private static Mosquitto instance;
    public static final String MQTT_CLIENT = MqttClient.generateClientId();
    private MosquittoListener mosquittoListener;

    public Mosquitto(Context context) {

        try {
            mqttClient = new MqttClient(Constants.MQTT_HOST, MQTT_CLIENT, new MemoryPersistence());
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d("FW_MQTT", "Connection lost: ", cause);

                    while(!mqttClient.isConnected()) {
                        connect();

                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    Log.d("FW_MQTT", "Message coming from: " + topic);

                    if(mosquittoListener != null) {
                        mosquittoListener.onMosquittoReceiveData(topic, String.valueOf(message));
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Handle delivery completion
                }
            });
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

    }

    public static synchronized Mosquitto getInstance(Context context) {
        if(instance == null) {
            instance = new Mosquitto(context);
        }
        return instance;
    }

    public void connect() {
        try {
            mqttClient.connect();
            System.out.println("-> FW_MQTT: Mosquitto connected to the broker!");
            subscribe(Constants.MQTT_RESERVATION_CREATE);
            subscribe(Constants.MQTT_RESERVATION_UPDATE);
            subscribe(Constants.MQTT_SUPPORTTICKET_CREATE);
            subscribe(Constants.MQTT_SUPPORTTICKET_UPDATE);
            subscribe(Constants.MQTT_CARREVIEW_CREATE);

        } catch (MqttException e) {
            Log.e("FW_MQTT", "Error connecting to the broker!", e);
        }
    }

    public void disconnect() {
        try {
           if(!mqttClient.isConnected())
               return;

           mqttClient.disconnect();
        } catch (MqttException e) {
            Log.e("FW_MQTT", "Error connecting to the broker!", e);
        }
    }

    public void subscribe(String topic) {
        try {
            if(!mqttClient.isConnected())
                return;

            mqttClient.subscribe(topic);
            Log.d("FW_MQTT", "Subscribed to: " + topic);
        } catch (MqttException e) {
            Log.e("FW_MQTT", "Error connecting to the broker!", e);
        }
    }
    public void setMosquittoListener(MosquittoManager mqttManager) {
        this.mosquittoListener = mqttManager;
    }
}
