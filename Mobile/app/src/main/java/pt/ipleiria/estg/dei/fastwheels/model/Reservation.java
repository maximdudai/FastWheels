package pt.ipleiria.estg.dei.fastwheels.model;

import java.sql.Timestamp;

public class Reservation {
    private int id, clientId, carId, filled;
    private Double value, feeValue, carValue;
    private Timestamp dateStart, dateEnd, createAt;

    public Reservation(int id, int clientId, int carId, Timestamp dateStart, Timestamp dateEnd, int filled, double value, double feeValue, double carValue) {
        this.id = id;
        this.clientId = clientId;
        this.carId = carId;
        this.filled = filled;
        this.value = value;
        this.feeValue = feeValue;
        this.carValue = carValue;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.createAt = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getFilled() {
        return filled;
    }

    public void setFilled(int filled) {
        this.filled = filled;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getFeeValue() {
        return feeValue;
    }

    public void setFeeValue(Double feeValue) {
        this.feeValue = feeValue;
    }

    public Double getCarValue() {
        return carValue;
    }

    public void setCarValue(Double carValue) {
        this.carValue = carValue;
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
}
