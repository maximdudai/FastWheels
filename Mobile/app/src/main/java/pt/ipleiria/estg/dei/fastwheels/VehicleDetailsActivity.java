package pt.ipleiria.estg.dei.fastwheels;

import static pt.ipleiria.estg.dei.fastwheels.utils.Helpers.showMessage;

import android.location.Location;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pt.ipleiria.estg.dei.fastwheels.adapters.ImageListAdapter;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;

public class VehicleDetailsActivity extends AppCompatActivity {

    private EditText etBrand, etModel, etYear, etDoors, etLocationX, etLocationY, etAvailableFrom, etAvailableTo;
    private ListView lvImgVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        // Referências para os campos da UI
        etBrand = findViewById(R.id.etBrand);
        etModel = findViewById(R.id.etModel);
        etYear = findViewById(R.id.etYear);
        etDoors = findViewById(R.id.etDoors);
        etLocationX = findViewById(R.id.etLocationX);
        etLocationY = findViewById(R.id.etLocationY);
        etAvailableFrom = findViewById(R.id.etAvailableFrom);
        etAvailableTo = findViewById(R.id.etAvailableTo);
        lvImgVehicle = findViewById(R.id.lvImgVehicle);

        // Obter o ID do veículo passado pela Intent
        int vehicleId = getIntent().getIntExtra("VEHICLE_ID", -1);

        if (vehicleId != -1) {
            // Busca o veículo no Singleton
            Vehicle vehicle = SingletonFastWheels.getInstance(getApplicationContext()).getVehicleById(vehicleId);

            if (vehicle != null) {
                // Preenche os campos com os dados do veículo
                etBrand.setText(vehicle.getBrand());
                etModel.setText(vehicle.getCarModel());
                etYear.setText(String.valueOf(vehicle.getCarYear()));
                etDoors.setText(String.valueOf(vehicle.getCarDoors()));

                // Obter a localização (latitude e longitude)
                Location location = vehicle.getLocation();
                if (location != null) {
                    etLocationX.setText(String.valueOf(location.getLatitude()));
                    etLocationY.setText(String.valueOf(location.getLongitude()));
                } else {
                    etLocationX.setText("N/A");
                    etLocationY.setText("N/A");
                }

                // Formatar as datas antes de exibir
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                etAvailableFrom.setText(dateFormat.format(vehicle.getAvailableFrom()));
                etAvailableTo.setText(dateFormat.format(vehicle.getAvailableTo()));

                // Exemplo de lista de imagens (IDs de recursos)
                List<Integer> imageList = new ArrayList<>();
                imageList.add(R.drawable.car_test);
                imageList.add(R.drawable.car_test);
                imageList.add(R.drawable.car_test);

                // Configurar o adaptador
                ImageListAdapter adapter = new ImageListAdapter(this, imageList);
                lvImgVehicle.setAdapter(adapter);
            } else {
                // Lida com a ausência do veículo correspondente
                showMessage(this,"O veículo não foi encontrado.");
            }
        } else {
            // Lida com a ausência de um ID válido
            showMessage(this,"ID do veículo inválido.");
        }
    }
}
