package pt.ipleiria.estg.dei.fastwheels;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.adapters.NotificationAdapter;
import pt.ipleiria.estg.dei.fastwheels.listeners.NotificationListener;
import pt.ipleiria.estg.dei.fastwheels.model.Notification;
import pt.ipleiria.estg.dei.fastwheels.model.Reservation;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class UserNotificationFragment extends Fragment implements NotificationListener {

    private ListView lvNotifications;
    private NotificationAdapter adapter;
    private ArrayList<Notification> notifications = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        SingletonFastWheels.getInstance(context).setNotificationListener(this);

        SingletonFastWheels.getInstance(requireContext()).getNotificationsAPI(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_notification, container, false);
        lvNotifications = view.findViewById(R.id.lvNotifications);


        lvNotifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notification selectedNotification = notifications.get(i);

                new AlertDialog.Builder(getContext())
                        .setTitle("Received Notification")
                        .setMessage(selectedNotification.getContent())
                        .setPositiveButton("Close", (dialog, which) -> {

                            if(selectedNotification.getRead() == 0) {
                                selectedNotification.setRead(1);
                                SingletonFastWheels.getInstance(getContext()).updateNotificationAPI(selectedNotification, getContext());
                                onNotificationUpdate();
                            }
                        })
                        .show();
            }
        });

        adapter = new NotificationAdapter(requireContext(), notifications);
        lvNotifications.setAdapter(adapter);
        return view;
    }

    @Override
    public void onNotificationUpdate() {
        notifications.clear();
        notifications.addAll(SingletonFastWheels.getInstance(requireContext()).getNotificationsDB());
        requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
    }
}