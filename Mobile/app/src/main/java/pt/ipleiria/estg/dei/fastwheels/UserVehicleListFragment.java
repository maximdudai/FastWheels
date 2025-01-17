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
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;

public class UserVehicleListFragment extends Fragment implements VehicleListener {

    private ListView lvVehicles;
    private ArrayList<Vehicle> vehicleList;

    public UserVehicleListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Infla o layout do fragmento
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        //VehicleListener
        SingletonFastWheels.getInstance(getContext()).setVehicleListener(this);

        // Configuração da ListView
        lvVehicles = view.findViewById(R.id.lvImgVehicle);
        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehicleList, R.layout.item_vehicle));

        lvVehicles.setOnItemClickListener((adapterView, itemView, position, id) -> {
            Vehicle selectedVehicle = vehicleList.get(position); // Obter o veículo selecionado

            UserVehicleFormFragment formFragment = new UserVehicleFormFragment();

            // Passar os dados do veículo como argumentos
            Bundle args = new Bundle();
            args.putInt("VEHICLE_ID", selectedVehicle.getId());
            formFragment.setArguments(args);

            // Navegar para o formulário
            if (getActivity() instanceof UserVehicles) {
                ((UserVehicles) getActivity()).loadFragment(formFragment,"UserVehicleFormFragment");
            }
        });


        // Configurar o FloatingActionButton
        FloatingActionButton fabSaveVehicle = view.findViewById(R.id.fabSaveVehicle);
        fabSaveVehicle.setVisibility(View.VISIBLE); // Torna o botão visível
        fabSaveVehicle.setOnClickListener(v -> {
            // Navega para o formulário do UserVehicle
            if (getActivity() instanceof UserVehicles) {
                ((UserVehicles) getActivity()).loadFragment(new UserVehicleFormFragment(),"UserVehicleFormFragment");
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
        // Recarrega a lista de veículos do banco de dados
        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();

        // Atualiza o adaptador da ListView
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehicleList, R.layout.item_vehicle));
    }
}