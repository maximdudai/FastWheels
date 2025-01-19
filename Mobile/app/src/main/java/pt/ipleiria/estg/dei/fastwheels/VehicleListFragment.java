package pt.ipleiria.estg.dei.fastwheels;

import static pt.ipleiria.estg.dei.fastwheels.utils.Helpers.showMessage;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.fastwheels.adapters.VehicleListAdapter;
import pt.ipleiria.estg.dei.fastwheels.listeners.VehicleListener;
import pt.ipleiria.estg.dei.fastwheels.model.Favorite;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;


public class VehicleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, VehicleListener {

    private ListView lvVehicles;
    private ArrayList<Vehicle> vehicleList, vehiclesToShow;
    private List<Favorite> favoriteVehicles;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private Integer appliedCarDoors;
    private String availableFrom;
    private String availableTo;
    private String locationFilter;
    private User loggedUser;
    private String showFavorites;

    public VehicleListFragment() {
        loggedUser = SingletonFastWheels.getInstance(getContext()).getUser();

        vehiclesToShow = vehicleList = new ArrayList<>();
        favoriteVehicles = new ArrayList<>();

        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        favoriteVehicles = SingletonFastWheels.getInstance(getContext()).getFavorites();

        System.out.println("--->API favoritesvehicles: " + favoriteVehicles.size());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            showFavorites = getArguments().getString("SHOW_FAVORITES");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Infla o layout do fragmento
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        // Configuracao do Toolbar
        // Find the toolbar in the fragment's layout
        Toolbar toolbarCars = view.findViewById(R.id.toolbarCars);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbarCars);

        if (((AppCompatActivity) requireActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lvVehicles = view.findViewById(R.id.lvImgVehicle);

        if(favoriteVehicles == null)
            favoriteVehicles = SingletonFastWheels.getInstance(getContext()).getFavorites();

        if(vehicleList == null)
            vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();

        if(showFavorites.equals("1")) {
            vehiclesToShow.addAll(Helpers.filterVehiclesByFavorites(vehicleList, favoriteVehicles));
        } else {
            vehiclesToShow.addAll(Helpers.filterVehicleByNotPersonal(loggedUser, vehicleList));
        }

        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.vehicle_list_item));
        lvVehicles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Vehicle selectedVehicle = vehiclesToShow.get(position); // Obter o veículo selecionado

                Intent intent = new Intent(getContext(), VehicleDetailsActivity.class);
                intent.putExtra("VEHICLE_ID", selectedVehicle.getId());
                startActivity(intent);
            }
        });

        SingletonFastWheels.getInstance(getContext()).setVehicleListener(this);
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

        // Exibir uma mensagem para o user
        showMessage(getContext(), "Filtros removidos");
    }

    private void reloadListWithoutFilters() {
        vehiclesToShow = vehicleList = new ArrayList<Vehicle>();
        if(favoriteVehicles != null) favoriteVehicles.clear();

        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        favoriteVehicles = SingletonFastWheels.getInstance(getContext()).getFavorites();

        if(showFavorites.equals("1")) {
            vehiclesToShow.addAll(Helpers.filterVehiclesByFavorites(vehicleList, favoriteVehicles));
        } else {
            vehiclesToShow.addAll(Helpers.filterVehicleByNotPersonal(loggedUser, vehicleList));
        }

        System.out.println("--->vehs: #2: " + vehiclesToShow.size());

        // Atualizar o adaptador da ListView com todos os veículos
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.vehicle_list_item));
    }


    private void filterByCarDoors(int doors) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle v : vehiclesToShow) {
            if (v.getCarDoors() == doors) {
                filteredList.add(v);
            }
        }


        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), filteredList, R.layout.vehicle_list_item));
    }

    private void filterByAvailableFrom(Timestamp startDate) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle v : vehiclesToShow) {
            if (v.getAvailableFrom().after(startDate)) {
                filteredList.add(v);
            }
        }
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), filteredList, R.layout.vehicle_list_item));
    }

    private void filterByAvailableTo(Timestamp endDate) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle v : vehiclesToShow) {
            if (v.getAvailableTo().before(endDate)) {
                filteredList.add(v);
            }
        }
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), filteredList, R.layout.vehicle_list_item));
    }

    private void filterByLocation(String location) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle v : vehiclesToShow) {

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


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);

        // Atualiza a lista ao realizar "pull to refresh"
        vehiclesToShow = vehicleList = new ArrayList<Vehicle>();
        if(favoriteVehicles != null) favoriteVehicles.clear();

        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        favoriteVehicles = SingletonFastWheels.getInstance(getContext()).getFavorites();

        if(showFavorites.equals("1")) {
            vehiclesToShow.addAll(Helpers.filterVehiclesByFavorites(vehicleList, favoriteVehicles));
        } else {
            vehiclesToShow.addAll(Helpers.filterVehicleByNotPersonal(loggedUser, vehicleList));
        }

        System.out.println("--->vehs: #3: " + vehiclesToShow.size());

        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.vehicle_list_item));
    }

    @Override
    public void onRefreshVehicle() {
        vehiclesToShow = vehicleList = new ArrayList<Vehicle>();
        if(favoriteVehicles != null) favoriteVehicles.clear();

        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        favoriteVehicles = SingletonFastWheels.getInstance(getContext()).getFavorites();

        if(showFavorites.equals("1")) {
            vehiclesToShow.addAll(Helpers.filterVehiclesByFavorites(vehicleList, favoriteVehicles));
        } else {
            vehiclesToShow.addAll(Helpers.filterVehicleByNotPersonal(loggedUser, vehicleList));
        }

        System.out.println("--->vehs: #4: " + vehiclesToShow.size());

        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.vehicle_list_item));
    }

    @Override
    public void onResume() {
        super.onResume();

        vehiclesToShow = vehicleList = new ArrayList<Vehicle>();
        if(favoriteVehicles != null) favoriteVehicles.clear();

        vehicleList = SingletonFastWheels.getInstance(getContext()).getVehiclesDb();
        favoriteVehicles = SingletonFastWheels.getInstance(getContext()).getFavorites();

        if(showFavorites.equals("1")) {
            vehiclesToShow.addAll(Helpers.filterVehiclesByFavorites(vehicleList, favoriteVehicles));
        } else {
            vehiclesToShow.addAll(Helpers.filterVehicleByNotPersonal(loggedUser, vehicleList));
        }

        System.out.println("--->vehs: #5: " + vehiclesToShow.size());

        // Set the adapter
        lvVehicles.setAdapter(new VehicleListAdapter(getContext(), vehiclesToShow, R.layout.vehicle_list_item));
        lvVehicles.invalidateViews(); // Force refresh
    }
}