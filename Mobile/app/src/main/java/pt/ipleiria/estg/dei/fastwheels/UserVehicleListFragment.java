package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.adapters.VehicleListAdapter;
import pt.ipleiria.estg.dei.fastwheels.listeners.VehicleListener;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class UserVehicleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, VehicleListener {

    private ListView lvVehicles;
    private ArrayList<Vehicle> vehicleList = null, vehiclesToShow = null;
    private SwipeRefreshLayout swipeRefreshLayout;

    private User loggedUser;

    public UserVehicleListFragment() {
        // Required empty public constructor
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

        // Configurar o FloatingActionButton
        FloatingActionButton fabSaveVehicle = view.findViewById(R.id.fabSaveVehicle);
        fabSaveVehicle.setVisibility(View.VISIBLE); // Torna o botão visível
        fabSaveVehicle.setOnClickListener(v -> {
            // Navega para o formulário do UserVehicle
            if (getActivity() instanceof UserVehicles) {
                ((UserVehicles) getActivity()).loadFragment(new UserVehicleFormFragment(),"UserVehicleFormFragment", null);
            }
        });

        SingletonFastWheels singleton = SingletonFastWheels.getInstance(getContext());
        //VehicleListener
        singleton.setVehicleListener(this);

        // Configuração da ListView
        vehiclesToShow = vehicleList = new ArrayList<Vehicle>();

        lvVehicles = view.findViewById(R.id.lvImgVehicle);
        singleton.getVehiclesAPI(getContext());
        loggedUser = singleton.getUser();
        vehicleList = singleton.getVehiclesDb();


        // mostrar apenas a lista dos meus veiculos
        vehiclesToShow.addAll(Helpers.filterVehicleListByPersonal(loggedUser, vehicleList));

        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.item_vehicle));
        lvVehicles.setOnItemClickListener((adapterView, itemView, position, id) -> {
            Vehicle selectedVehicle = vehiclesToShow.get(position); // Obter o veículo selecionado

            UserVehicleFormFragment formFragment = new UserVehicleFormFragment();

            // Passar os dados do veículo como argumentos
            Bundle args = new Bundle();
            args.putInt("VEHICLE_ID", selectedVehicle.getId());
            formFragment.setArguments(args);

            // Navegar para o formulário
            if (getActivity() instanceof UserVehicles) {
                ((UserVehicles) getActivity()).loadFragment(formFragment, "UserVehicleFormFragment", null);
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
        vehiclesToShow = vehicleList = new ArrayList<Vehicle>();

        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        vehiclesToShow.addAll(Helpers.filterVehicleListByPersonal(loggedUser, vehicleList));

        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.item_vehicle));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}