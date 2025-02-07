package pt.ipleiria.estg.dei.fastwheels.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Notification {

    private int id, clientId, read;
    private String content;
    private Timestamp createdAt;

    public Notification(int id, int clientId, int read, String content, Timestamp createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.read = read;
        this.content = content;
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

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", read=" + read +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
