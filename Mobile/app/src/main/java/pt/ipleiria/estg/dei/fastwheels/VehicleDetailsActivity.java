package pt.ipleiria.estg.dei.fastwheels;

import static pt.ipleiria.estg.dei.fastwheels.utils.Helpers.showMessage;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import pt.ipleiria.estg.dei.fastwheels.adapters.ImageListAdapter;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class VehicleDetailsActivity extends AppCompatActivity {

    private EditText etBrand, etModel, etYear, etDoors, etResidence, etPostalCode, etCity, etAvailableFrom, etAvailableTo;
    private ListView lvImgVehicle;
    private Calendar calendarAvailableFrom, calendarAvailableTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        // Referências para os campos da UI
        etBrand = findViewById(R.id.etBrand);
        etModel = findViewById(R.id.etModel);
        etYear = findViewById(R.id.etYear);
        etDoors = findViewById(R.id.etDoors);
        etResidence = findViewById(R.id.etResidence);
        etPostalCode = findViewById(R.id.etPostalCode);
        etCity = findViewById(R.id.etCity);
        etAvailableFrom = findViewById(R.id.etAvailableFrom);
        etAvailableTo = findViewById(R.id.etAvailableTo);
        lvImgVehicle = findViewById(R.id.lvImgVehicle);

        // Configurar os calendários iniciais
        calendarAvailableFrom = Calendar.getInstance();
        calendarAvailableTo = (Calendar) calendarAvailableFrom.clone();
        calendarAvailableTo.add(Calendar.DAY_OF_MONTH, 1);

        // Configurar os campos de data com o seletor de calendário
        setupDatePickers();

        // Obter o ID do veículo passado pela Intent
        int vehicleId = getIntent().getIntExtra("VEHICLE_ID", -1);

        if (vehicleId != -1) {
            // Busca o veículo no Singleton
            Vehicle vehicle = SingletonFastWheels.getInstance(getApplicationContext()).getVehicleById(vehicleId);

            if (vehicle != null) {
                // Preenche os campos com os dados do veículo
                populateVehicleDetails(vehicle);
            } else {
                // Lida com a ausência do veículo correspondente
                showMessage(this, "O veículo não foi encontrado.");
            }
        } else {
            // Lida com a ausência de um ID válido
            showMessage(this, "ID do veículo inválido.");
        }
    }

    private void setupDatePickers() {
        // Configurar o seletor de data para o campo AvailableFrom
        etAvailableFrom.setOnClickListener(v ->
                Helpers.showDatePickerDialog(this, etAvailableFrom, calendarAvailableFrom, (year, month, day) ->
                        etAvailableFrom.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month + 1, year))
                )
        );

        // Configurar o seletor de data para o campo AvailableTo
        etAvailableTo.setOnClickListener(v ->
                Helpers.showDatePickerDialog(this, etAvailableTo, calendarAvailableTo, (year, month, day) ->
                        etAvailableTo.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month + 1, year))
                )
        );
    }

    private void populateVehicleDetails(Vehicle vehicle) {
        etBrand.setText(vehicle.getBrand());
        etModel.setText(vehicle.getCarModel());
        etYear.setText(String.valueOf(vehicle.getCarYear()));
        etDoors.setText(String.valueOf(vehicle.getCarDoors()));

        // Preencher os campos de localização
        etResidence.setText(vehicle.getLocation());
        etPostalCode.setText(vehicle.getPostalCode());
        etCity.setText(vehicle.getCity());

        // Formatar as datas antes de exibir
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        etAvailableFrom.setText(dateFormat.format(vehicle.getAvailableFrom()));
        etAvailableTo.setText(dateFormat.format(vehicle.getAvailableTo()));

        // Exemplo de lista de imagens
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.car_test);
        imageList.add(R.drawable.car_test);
        imageList.add(R.drawable.car_test);

        // Configurar o adaptador
        ImageListAdapter adapter = new ImageListAdapter(this, imageList);
        lvImgVehicle.setAdapter(adapter);
    }
}
