package pt.ipleiria.estg.dei.fastwheels;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.adapters.VehicleListAdapter;
import pt.ipleiria.estg.dei.fastwheels.listeners.VehicleListener;
import pt.ipleiria.estg.dei.fastwheels.model.Reservation;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class UserReservedVehicleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, VehicleListener {

    private ListView lvReservations;
    private ArrayList<Vehicle> vehicleList = null, vehiclesToShow = null;
    private ArrayList<Reservation> allReservations;
    private SwipeRefreshLayout swipeRefreshLayout;

    private User loggedUser;

    SingletonFastWheels singleton = null;

    public UserReservedVehicleFragment() {
        singleton = SingletonFastWheels.getInstance(getContext());
        singleton.setVehicleListener(this);

        //load data from api
        singleton.getReservationAPI(getContext());

        loggedUser = singleton.getUser();
        vehicleList = singleton.getVehiclesDb();
        allReservations = singleton.getReservationsDb();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Infla o layout do fragmento
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        Toolbar toolbarCars = view.findViewById(R.id.toolbarCars);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbarCars);
        if (((AppCompatActivity) requireActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lvReservations = view.findViewById(R.id.lvImgVehicle);

        vehiclesToShow = Helpers.filterVehicleByReserved(vehicleList, allReservations, loggedUser.getId());

        lvReservations.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.item_reserved));
        lvReservations.setOnItemClickListener((adapterView, itemView, position, id) -> {
            Vehicle selectedVehicle = vehiclesToShow.get(position);

            new AlertDialog.Builder(getContext())
                    .setMessage("Pretende remover a reserva?")
                    .setPositiveButton("Sim", (dialog, which) -> {

                        Reservation clickedReserve = Helpers.getReservationByVehicleAndUser(allReservations, loggedUser.getId(), selectedVehicle.getId());

                        if(clickedReserve != null) {
                            singleton.removeReservationAPI(clickedReserve.getId(), getContext());

                            //false = mark as unrented | true = mark as rented
                            selectedVehicle.setStatus(false);
                            singleton.editVehicleAPI(selectedVehicle, getContext());
                        }
                        this.updateVehicleList();

                    })
                    .setNegativeButton("Não", null)
                    .show();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.updateVehicleList();
    }

    private void updateVehicleList() {
        vehiclesToShow = vehicleList = new ArrayList<Vehicle>();

        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        allReservations = SingletonFastWheels.getInstance(getContext()).getReservationsDb();

        vehiclesToShow = Helpers.filterVehicleByReserved(vehicleList, allReservations, loggedUser.getId());

        lvReservations.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.item_reserved));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshVehicle() {
        this.updateVehicleList();
    }
}