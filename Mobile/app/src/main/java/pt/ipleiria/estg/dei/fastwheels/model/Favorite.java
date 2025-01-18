package pt.ipleiria.estg.dei.fastwheels.model;

import java.util.Date;

public class Favorite {
    private int id, clientId, carId;
    private Date createdAt;

    public Favorite(int id, int clientId, int carId, Date createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.carId = carId;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getCarId() {
        return carId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
