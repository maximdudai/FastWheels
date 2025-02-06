package pt.ipleiria.estg.dei.fastwheels.model;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    private int id, client, owner;
    private String chatId;

    private ArrayList<ChatMessage> chatMessages;

    public Chat(int id, int client, int owner, String chatId, ArrayList<ChatMessage> chatMessages) {
        this.id = id;
        this.client = client;
        this.owner = owner;
        this.chatId = chatId;
        this.chatMessages = chatMessages;
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

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }
}
