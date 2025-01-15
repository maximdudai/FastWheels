package pt.ipleiria.estg.dei.fastwheels.listeners;

import android.content.Context;

import pt.ipleiria.estg.dei.fastwheels.model.User;

public interface ProfileListener {
    void onProfileUpdate(User user, final Context context);
}
