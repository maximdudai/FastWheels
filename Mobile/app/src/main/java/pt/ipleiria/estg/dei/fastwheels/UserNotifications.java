package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class UserNotifications extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notifications);

        fragmentManager = getSupportFragmentManager();

        Fragment fragment = new UserNotificationFragment();
        setTitle("Notifications");

        if(fragment != null)
            fragmentManager.beginTransaction().replace(R.id.fragmentNotificationsManager, fragment)
                    .commitAllowingStateLoss();
    }

}