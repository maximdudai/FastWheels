package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

        lvVehicles = view.findViewById(R.id.lvImgVehicle);
        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();

        // Configuração da ListView
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehicleList));
        Log.d("VehicleListFragment", "Adapter set with " + vehicleList.size() + " items.");

        lvVehicles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("VehicleListFragment", "Item clicked, position: " + i + ", id: " + l);
                Toast.makeText(getContext(), "Clicked position: " + i + ", ID: " + l, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), VehicleDetailsActivity.class);
                intent.putExtra("VEHICLE_ID", (int) l);
                startActivity(intent);
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

    private void addNewVehicle() {
        // Cria um novo veículo com todos os dados necessários
        Vehicle newVehicle = new Vehicle();

        // Definindo valores para os atributos obrigatórios
        newVehicle.setId(0); // Define um ID padrão (ou gere um ID único se necessário)
        newVehicle.setClientId(123); // Substitua por um ID válido de cliente
        newVehicle.setMark("Nissan"+" ");
        newVehicle.setCarModel("GTR");
        newVehicle.setCarYear(2024);
        newVehicle.setCarDoors(4); // Número de portas
        newVehicle.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Data atual como criada
        newVehicle.setStatus(true); // Status disponível
        newVehicle.setAvailableFrom(new Timestamp(System.currentTimeMillis())); // Disponível agora
        newVehicle.setAvailableTo(new Timestamp(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L)); // Disponível por 7 dias
        newVehicle.setLocation(null); // Substitua por um objeto Location válido ou use null se não aplicável

        // Adiciona o novo veículo à base de dados do Singleton
        SingletonFastWheels.getInstance(getContext()).addVehicleDb(newVehicle);

        // Adiciona o veículo à lista local (reflete o banco de dados)
        vehicleList.add(newVehicle);
    }
}
