package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserVehicleListFragment extends Fragment {

   public UserVehicleListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_vehicle_list, container, false);

        // Botão para formulário do uservehicle
        FloatingActionButton fabAddVehicle = view.findViewById(R.id.fabAddVehicle);
        fabAddVehicle.setOnClickListener(v -> {
            // Ir formulário do uservehicle
            if (getActivity() instanceof UserVehicles) {
                ((UserVehicles) getActivity()).loadFragment(new UserVehicleFormFragment());
            }
        });

        return view;
    }
}