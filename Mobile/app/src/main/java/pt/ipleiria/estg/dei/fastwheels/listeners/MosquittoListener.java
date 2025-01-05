package pt.ipleiria.estg.dei.fastwheels.listeners;

public interface MosquittoListener {
    void onMosquittoReceiveData(String topic, String data);
}
