package pt.ipleiria.estg.dei.fastwheels.listeners;

import android.content.Context;

public interface ChatListener {
    void OnChatRequest(int requestUserId, int ownerId, final Context context);
}
