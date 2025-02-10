package pt.ipleiria.estg.dei.fastwheels.model;

import android.os.Build;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {
    private int id, carId;
    private String comment;
    private Timestamp createdAt;

    public Review(int id, int carId, String comment, Timestamp createdAt) {
        this.id = id;
        this.carId = carId;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = formatter.format(new Date(this.createdAt.getTime()));
        return "(" + formatDate + ") " + comment + "\n\n";
    }

}
