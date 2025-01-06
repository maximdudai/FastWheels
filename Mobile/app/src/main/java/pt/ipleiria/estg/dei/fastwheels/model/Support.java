package pt.ipleiria.estg.dei.fastwheels.model;


import java.sql.Timestamp;

public class Support {
    private int id, clientId, closed, reservationId, status;
    private String content, subject;
    private Timestamp createdAt;

    public Support(int id, int clientId, String content, Timestamp createdAt, int closed, String subject, int reservationId, int status) {
        this.id = id;
        this.clientId = clientId;
        this.closed = closed;
        this.reservationId = reservationId;
        this.status = status;
        this.content = content;
        this.subject = subject;
        this.createdAt = createdAt;
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

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
