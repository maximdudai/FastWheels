package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.adapters.VehicleListAdapter;
import pt.ipleiria.estg.dei.fastwheels.listeners.VehicleListener;
import pt.ipleiria.estg.dei.fastwheels.model.Reservation;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class UserReservedVehicleFragment extends Fragment implements VehicleListener {

    private ListView lvReservations;
    private ArrayList<Vehicle> reservationsList = null, vehiclesToShow = null;
    private ArrayList<Reservation> allReservations;

    private User loggedUser;

    public UserReservedVehicleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Infla o layout do fragmento
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        // Configurar o FloatingActionButton
        FloatingActionButton fabSaveVehicle = view.findViewById(R.id.fabSaveVehicle);
        fabSaveVehicle.setVisibility(View.VISIBLE); // Torna o botão visível

        fabSaveVehicle.setOnClickListener(v -> {
            // Navega para o formulário do UserVehicle
            if (getActivity() instanceof UserVehicles) {
                ((UserVehicles) getActivity()).loadFragment(new UserVehicleFormFragment(),"UserVehicleFormFragment");
            }
        });

        SingletonFastWheels singleton = SingletonFastWheels.getInstance(getContext());
        //VehicleListener
        singleton.setVehicleListener(this);

        // Configuração da ListView
        reservationsList = new ArrayList<Vehicle>();
        allReservations = new ArrayList<Reservation>();

        lvReservations = view.findViewById(R.id.lvImgVehicle);

        singleton.getReservationAPI(getContext());
        singleton.getReservationAPI(getContext());

        loggedUser = singleton.getUser();
        reservationsList = singleton.getVehiclesDb();
        allReservations = singleton.getReservationsDb();

        vehiclesToShow = Helpers.filterVehicleByReserved(reservationsList, allReservations);


        lvReservations.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.item_vehicle));
        lvReservations.setOnItemClickListener((adapterView, itemView, position, id) -> {
            Vehicle selectedVehicle = vehiclesToShow.get(position);

            UserVehicleFormFragment formFragment = new UserVehicleFormFragment();

            // Passar os dados do veículo como argumentos
            Bundle args = new Bundle();
            args.putInt("VEHICLE_ID", selectedVehicle.getId());
            formFragment.setArguments(args);

            // Navegar para o formulário
            if (getActivity() instanceof UserVehicles) {
                ((UserVehicles) getActivity()).loadFragment(formFragment, "UserVehicleFormFragment");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRefreshVehicle() {
        vehiclesToShow = reservationsList = new ArrayList<Vehicle>();

        reservationsList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        allReservations = SingletonFastWheels.getInstance(getContext()).getReservationsDb();

        vehiclesToShow = Helpers.filterVehicleByReserved(reservationsList, allReservations);

        lvReservations.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.item_vehicle));
    }
}