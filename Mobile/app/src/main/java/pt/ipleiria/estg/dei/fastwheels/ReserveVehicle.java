package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;

public class ReserveVehicle extends AppCompatActivity {
    private int selectedVehicle = -1;
    private ArrayList<Vehicle> vehicles;

    private EditText name, email, contactNumber, location;

    private RadioGroup seguroRadio;
    private int selectedRadioSeguro;

    private RadioGroup pagamentoRadio;
    private int selectedRadioPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_vehicle);

        selectedVehicle = getIntent().getIntExtra("SELECTED_VEHICLE", -1);

        name = findViewById(R.id.et_nome_completo);
        email = findViewById(R.id.et_email);
        contactNumber = findViewById(R.id.et_contacto);
        location = findViewById(R.id.et_localizacao);

        //seguro (radio buttons)
        seguroRadio = findViewById(R.id.rg_opcao_seguro);
        selectedRadioSeguro = seguroRadio.getCheckedRadioButtonId();


    }
    // obter dados sobre o veiculo selecionado
    private Vehicle getVehicleData() {
        for(Vehicle car: vehicles) {
            if(car.getId() == selectedVehicle) {
                return car;
            }
        }
        return null;
    }

    // botao para confirmar reserva
    public void handleConfirmReservation(View v) {
        Vehicle veh = getVehicleData();
    }
}