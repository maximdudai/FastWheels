package pt.ipleiria.estg.dei.fastwheels.listeners;

import android.content.Context;

import pt.ipleiria.estg.dei.fastwheels.model.User;

public interface LoginListener {
    void onValidateLogin(final String token, final String email, final Context context);
}
