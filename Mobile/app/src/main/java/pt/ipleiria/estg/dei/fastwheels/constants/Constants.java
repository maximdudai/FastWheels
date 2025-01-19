package pt.ipleiria.estg.dei.fastwheels.constants;


public class Constants {
    public static final String DB_NAME = "fastwheels";
    public static final int DB_VERSION = 5;

    public static final String PREFS_NAME = "FastWheelsPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_KEEP_LOGGED_IN = "keepLoggedIn";


    // API ENDPOINTS
    public static final String API_AUTH = "http://54.229.223.123:8080/api/clients/login";
    public static final String API_VEHICLES = "http://54.229.223.123:8080/api/vehicles";
    public static final String API_PROFILE = "http://54.229.223.123:8080/api/clients/update";
    public static final String API_REGISTER = "http://54.229.223.123:8080/api/clients/register";
    public static final String API_RESERVATION = "http://54.229.223.123:8080/api/reservations";
    public static final String API_FAVORITES = "http://54.229.223.123:8080/api/favorite";

    // MOSQUITTO

    public static final String MQTT_HOST = "tcp://54.229.223.123:1883";
    public static final String MQTT_SUPPORTTICKET_UPDATE = "SUPPORTTICKET:UPDATE";
    public static final String MQTT_SUPPORTTICKET_CREATE = "SUPPORTTICKET:CREATE";
    public static final String MQTT_RESERVATION_UPDATE = "RESERVATION:UPDATE";
    public static final String MQTT_RESERVATION_CREATE = "RESERVATION:CREATE";
    public static final String MQTT_CARREVIEW_CREATE = "CARREVIEW:CREATE";


}