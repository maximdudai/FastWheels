package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.adapters.VehicleListAdapter;
import pt.ipleiria.estg.dei.fastwheels.listeners.VehicleListener;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class UserVehicleListFragment extends Fragment implements VehicleListener {

    private ListView lvVehicles;
    private ArrayList<Vehicle> vehicleList = null, vehiclesToShow = null;

    private User loggedUser;

    public UserVehicleListFragment() {
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
        lvVehicles = view.findViewById(R.id.lvImgVehicle);
        singleton.getVehiclesAPI(getContext());
        loggedUser = singleton.getUser();
        vehicleList = singleton.getVehiclesDb();
        vehiclesToShow = new ArrayList<Vehicle>();

        // mostrar apenas a lista dos meus veiculos
        vehiclesToShow = Helpers.filterVehicleListByPersonal(loggedUser, vehicleList);

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
        vehiclesToShow = vehicleList = new ArrayList<Vehicle>();

        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        vehiclesToShow = Helpers.filterVehicleListByPersonal(loggedUser, vehicleList);

        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.item_vehicle));
    }
}