package pt.ipleiria.estg.dei.fastwheels.model;

public class Chat {

    private int id, client, owner, isAvailable;
    String chatId;

    public Chat(int id, int isAvailable, int client, int owner, String chatId) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.client = client;
        this.owner = owner;
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
