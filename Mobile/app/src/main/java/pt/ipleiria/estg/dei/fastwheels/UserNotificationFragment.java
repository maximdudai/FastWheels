package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.adapters.NotificationAdapter;
import pt.ipleiria.estg.dei.fastwheels.listeners.NotificationListener;
import pt.ipleiria.estg.dei.fastwheels.model.Notification;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;

public class UserNotificationFragment extends Fragment implements NotificationListener {

    private ListView lvNotifications;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_notification, container, false);

        lvNotifications = view.findViewById(R.id.lvNotifications);

        SingletonFastWheels.getInstance(getContext()).setNotificationListener(this);
        SingletonFastWheels.getInstance(getContext()).getNotificationsAPI(getContext());
        return view;
    }

    @Override
    public void onNotificationUpdate() {
        ArrayList<Notification> notifications = SingletonFastWheels.getInstance(getContext()).getNotificationsDB();
        if(!notifications.isEmpty()) {
            lvNotifications.setAdapter(new NotificationAdapter(getContext(), notifications));
        }
    }
}