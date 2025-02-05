package pt.ipleiria.estg.dei.fastwheels.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.model.Chat;
import pt.ipleiria.estg.dei.fastwheels.model.User;

public class ChatParser {
    public static Chat parseChatData(String response) {

        Chat chatData = null;

        try {
            JSONObject fetchData = new JSONObject(response);

            int id = fetchData.getInt("id");
            int isAvailable = fetchData.getInt("inchat");
            int client = fetchData.getInt("client");
            int owner = fetchData.getInt("owner");
            String chat_id = fetchData.getString("chatId");

            chatData = new Chat(id, isAvailable, client, owner, chat_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chatData;
    }
}
