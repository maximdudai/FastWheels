package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.listeners.NotificationListener;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;

public class UserNotifications extends AppCompatActivity implements NotificationListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notifications);

        // Load notification
        this.reloadNotification();

        fragmentManager = getSupportFragmentManager();

        Fragment fragment = new UserNotificationFragment();
        setTitle("Notifications");

        if(fragment != null)
            fragmentManager.beginTransaction().replace(R.id.fragmentNotificationsManager, fragment).commit();
    }

    @Override
    public void onNotificationUpdate() {
        this.reloadNotification();
    }

    private void reloadNotification() {
        SingletonFastWheels.getInstance(getApplicationContext()).getNotificationsAPI(getApplicationContext());
    }
}