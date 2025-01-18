package pt.ipleiria.estg.dei.fastwheels;

import static pt.ipleiria.estg.dei.fastwheels.utils.Helpers.showMessage;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.fastwheels.adapters.VehicleListAdapter;
import pt.ipleiria.estg.dei.fastwheels.model.FavoriteDbHelper;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;


public class VehicleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView lvVehicles;
    private ArrayList<Vehicle> vehicleList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private Integer appliedCarDoors;
    private String availableFrom;
    private String availableTo;
    private String locationFilter;

    public VehicleListFragment() {
        // Construtor padrão necessário
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Infla o layout do fragmento
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        // Configuração da ListView
        lvVehicles = view.findViewById(R.id.lvImgVehicle);
        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();

        // Recupera o sinalizador para mostrar favoritos
        boolean showFavorites = requireActivity().getIntent().getBooleanExtra("SHOW_FAVORITES", false);
        if (showFavorites) {
            List<Long> favoriteCarIds = getFavoriteCarIds(); // IDs favoritos do banco
            showFavorites(favoriteCarIds);
        } else {
            toggleFavorites(false, null);
        }

        // Configuração do clique nos itens da ListView
        lvVehicles.setOnItemClickListener((adapterView, view1, position, id) -> {
            Vehicle selectedVehicle = vehicleList.get(position); // Veículo selecionado
            Intent intent = new Intent(getContext(), VehicleDetailsActivity.class);
            intent.putExtra("VEHICLE_ID", selectedVehicle.getId());
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        // Configuração da barra de pesquisa
        MenuItem itemSearch = menu.findItem(R.id.itemSearch);
        searchView = (SearchView) itemSearch.getActionView();
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
                    if (v.getCarBrand().toLowerCase().contains(newText.toLowerCase()) ||
                            v.getCarModel().toLowerCase().contains(newText.toLowerCase())) {
                        tempVehicles.add(v);
                    }
                }

                // Atualiza o adaptador com os resultados da pesquisa
                lvVehicles.setAdapter(new VehicleListAdapter(getContext(), tempVehicles, R.layout.vehicle_list_item));
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.filterByCarDoors) {
            showSingleChoiceDialog("Número de Portas", new String[]{"2", "3", "4"}, selected -> filterByCarDoors(Integer.parseInt(selected)));
            return true;
        } else if (itemId == R.id.filterByAvailableFrom) {
            showDatePickerDialog("Data de Início", date -> filterByAvailableFrom(date));
            return true;
        } else if (itemId == R.id.filterByAvailableTo) {
            showDatePickerDialog("Data de Fim", date -> filterByAvailableTo(date));
            return true;
        } else if (itemId == R.id.filterByLocation) {
            showFilterDialog("Localização", "Digite a residência, código postal ou cidade", input -> filterByLocation(input));
            return true;
        } else if (itemId == R.id.deleteFilters) {
            clearFilters();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void clearFilters() {
        // Redefina os filtros aplicados
        appliedCarDoors = null;  // Número de portas
        availableFrom = null;    // Data de início
        availableTo = null;      // Data de fim
        locationFilter = null;   // Localização

        // Recarregar a lista sem filtros
        reloadListWithoutFilters();

        // Exibir uma mensagem para o usuário
        showMessage(getContext(), "Filtros removidos");
    }



    private void reloadListWithoutFilters() {
        // Obter todos os veículos sem filtros
        ArrayList<Vehicle> allVehicles = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();

        // Atualizar o adaptador da ListView com todos os veículos
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), allVehicles, R.layout.vehicle_list_item));
    }


    private void filterByCarDoors(int doors) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle v : SingletonFastWheels.getInstance(getContext()).getVehiclesDb()) {
            if (v.getCarDoors() == doors) {
                filteredList.add(v);
            }
        }
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), filteredList, R.layout.vehicle_list_item));
    }

    private void filterByAvailableFrom(Timestamp startDate) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle v : SingletonFastWheels.getInstance(getContext()).getVehiclesDb()) {
            if (v.getAvailableFrom().after(startDate)) {
                filteredList.add(v);
            }
        }
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), filteredList, R.layout.vehicle_list_item));
    }

    private void filterByAvailableTo(Timestamp endDate) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle v : SingletonFastWheels.getInstance(getContext()).getVehiclesDb()) {
            if (v.getAvailableTo().before(endDate)) {
                filteredList.add(v);
            }
        }
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), filteredList, R.layout.vehicle_list_item));
    }

    private void filterByLocation(String location) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle v : SingletonFastWheels.getInstance(getContext()).getVehiclesDb()) {

            if ((v.getAddress() != null && v.getAddress().toLowerCase().contains(location.toLowerCase())) ||
                    (v.getPostalCode() != null && v.getPostalCode().toLowerCase().contains(location.toLowerCase())) ||
                    (v.getCity() != null && v.getCity().toLowerCase().contains(location.toLowerCase()))) {
                filteredList.add(v);
            }
        }
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), filteredList, R.layout.vehicle_list_item));
    }


    private void showFilterDialog(String title, String hint, Consumer<String> onInputConfirmed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        final EditText input = new EditText(getContext());
        input.setHint(hint);
        builder.setView(input);

        builder.setPositiveButton("Filtrar", (dialog, which) -> {
            String userInput = input.getText().toString().trim();
            if (!userInput.isEmpty()) {
                onInputConfirmed.accept(userInput);
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showSingleChoiceDialog(String title, String[] options, Consumer<String> onOptionSelected) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        builder.setItems(options, (dialog, which) -> {
            onOptionSelected.accept(options[which]);
        });
        builder.show();
    }

    private void showDatePickerDialog(String title, Consumer<Timestamp> onDateSelected) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth, 0, 0, 0);
            Timestamp selectedDate = new Timestamp(calendar.getTimeInMillis());
            onDateSelected.accept(selectedDate);
        });
        datePickerDialog.show();
    }

    private List<Long> getFavoriteCarIds() {
        long clientId = SingletonFastWheels.getInstance(getContext()).getClientId();
        FavoriteDbHelper dbHelper = new FavoriteDbHelper(getContext());
        return dbHelper.getFavoriteCarIdsByClientId(clientId);
    }


    public void showFavorites(List<Long> favoriteCarIds) {
        ArrayList<Vehicle> favoriteVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicleList) { // Utilize 'vehicleList' existente no fragmento
            if (favoriteCarIds.contains(vehicle.getId())) { // Verifica se o ID do veículo está na lista de favoritos
                favoriteVehicles.add(vehicle);
            }
        }

        // Atualiza a lista exibida no adaptador da ListView
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), favoriteVehicles, R.layout.vehicle_list_item));
    }

    public void toggleFavorites(boolean showFavorites, List<Long> favoriteCarIds) {
        if (showFavorites) {
            showFavorites(favoriteCarIds); // Mostra apenas os veículos favoritos
        } else {
            // Restaura a lista completa
            lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehicleList, R.layout.vehicle_list_item));
        }
    }


    @Override
    public void onRefresh() {
        // Atualiza a lista ao realizar "pull to refresh"
        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehicleList, R.layout.vehicle_list_item));
        swipeRefreshLayout.setRefreshing(false);
    }
}
