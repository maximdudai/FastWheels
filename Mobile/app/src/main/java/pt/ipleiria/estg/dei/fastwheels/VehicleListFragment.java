package pt.ipleiria.estg.dei.fastwheels;

import static pt.ipleiria.estg.dei.fastwheels.utils.helpers.showError;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.sql.Timestamp;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.adapters.VehicleListAdapter;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;

public class VehicleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView lvVehicles;
    private ArrayList<Vehicle> vehicleList;
    private SwipeRefreshLayout swipeRefreshLayout;

    public VehicleListFragment() {
        // Construtor padrão necessário
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout do fragmento
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        // Configuração do SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        lvVehicles = view.findViewById(R.id.lv_vehicles);
        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();

        // Configuração da ListView
        VehicleListAdapter adapter = new VehicleListAdapter(getContext(), vehicleList);
        lvVehicles.setAdapter(adapter);

        // Configura o evento de clique
        lvVehicles.setOnItemClickListener((parent, view1, position, id) -> {
            Vehicle selectedVehicle = (Vehicle) adapter.getItem(position);
            Log.d("VehicleListFragment", "Veículo selecionado: " + selectedVehicle.getId());

            // Verifique se o ID é válido
            if (selectedVehicle != null) {
                Intent intent = new Intent(getContext(), VehicleDetailsActivity.class);
                intent.putExtra("VEHICLE_ID", selectedVehicle.getId());
                startActivity(intent);
            } else {
                showError(getContext(), "Veículo inválido");
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Infla o menu de pesquisa
        inflater.inflate(R.menu.search_menu, menu);

        // Configuração da barra de pesquisa
        MenuItem itemSearch = menu.findItem(R.id.itemSearch);
        SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Vehicle> tempVehicles = new ArrayList<>();

                // Filtra os veículos pelo texto digitado
                for (Vehicle v : SingletonFastWheels.getInstance(getContext()).getVehiclesDb()) {
                    if (v.getMark().toLowerCase().contains(newText.toLowerCase()) ||
                            v.getCarModel().toLowerCase().contains(newText.toLowerCase())) {
                        tempVehicles.add(v);
                    }
                }

                // Atualiza o adaptador com os resultados da pesquisa
                lvVehicles.setAdapter(new VehicleListAdapter(getContext(), tempVehicles));
                return true;
            }
        });
    }

    @Override
    public void onRefresh() {
        // Atualiza a lista ao realizar "pull to refresh"
        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehicleList));
        swipeRefreshLayout.setRefreshing(false);
    }
}
