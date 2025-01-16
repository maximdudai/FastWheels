package pt.ipleiria.estg.dei.fastwheels.utils;

import android.util.Base64;

public class generateBase64 {
    private String username, password;

    public generateBase64(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getBase64Token() {
        String credentials = this.username + ":" + this.password;
        byte[] encodedBytes = Base64.encode(credentials.getBytes(), Base64.NO_WRAP);
        return "Basic " + new String(encodedBytes);
    }
}
