package pt.ipleiria.estg.dei.fastwheels.model;

import java.util.ArrayList;

public class ChatMessage {

    private String chatHour, chatUsername, chatContent;

    public ChatMessage(String chatHour,String chatUsername, String chatContent) {
        this.chatHour = chatHour;
        this.chatUsername = chatUsername;
        this.chatContent = chatContent;
    }

    public String getChatHour() {
        return chatHour;
    }

    public void setChatHour(String chatHour) {
        this.chatHour = chatHour;
    }

    public String getChatUsername() {
        return chatUsername;
    }

    public void setChatUsername(String chatUsername) {
        this.chatUsername = chatUsername;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
}
