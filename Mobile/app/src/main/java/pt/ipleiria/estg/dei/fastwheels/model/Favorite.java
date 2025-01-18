package pt.ipleiria.estg.dei.fastwheels.model;

import java.util.Date;

public class Favorite {
    private final long id;
    private final long clientId;
    private final long carId;
    private Date createdAt;

    public Favorite(long id, long clientId, long carId, Date createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.carId = carId;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public long getClientId() {
        return clientId;
    }

    public long getCarId() {
        return carId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
