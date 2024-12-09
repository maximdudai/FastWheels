package pt.ipleiria.estg.dei.fastwheels.adapters;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.R;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;

public class VehicleListAdapter extends ArrayAdapter<Vehicle> {

    private final Context context;
    private final ArrayList<Vehicle> vehicles;

    public VehicleListAdapter(@NonNull Context context, ArrayList<Vehicle> vehicles) {
        super(context, R.layout.vehicle_list_item, vehicles);
        this.context = context;
        this.vehicles = vehicles;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.vehicle_list_item, parent, false);
        }

        // Obter o veículo na posição atual
        Vehicle vehicle = vehicles.get(position);

        // Referenciar os elementos de UI do layout vehicle_item.xml
        ImageView imgVehicle = convertView.findViewById(R.id.imgVehicle);
        TextView tvMark = convertView.findViewById(R.id.tvMark);
        TextView tvModel = convertView.findViewById(R.id.tvModel);
        TextView tvYear = convertView.findViewById(R.id.tvYear);
        TextView tvLocation = convertView.findViewById(R.id.tvLocation);
        TextView tvRating = convertView.findViewById(R.id.tvRating);
        TextView tvTrips = convertView.findViewById(R.id.tvTrips);
        Button btnRentVehicle = convertView.findViewById(R.id.btnRentVehicle);

        // Configurar os dados do veículo
        imgVehicle.setImageResource(R.drawable.car_test); // Substitua com imagens dinâmicas, se possível
        tvMark.setText(vehicle.getMark());
        tvModel.setText(vehicle.getCarModel());
        tvYear.setText(String.valueOf(vehicle.getCarYear()));

        // Configurar localização
        Location location = vehicle.getLocation();
        if (location != null) {
            String locationText = "Lat: " + location.getLatitude() + ", Long: " + location.getLongitude();
            tvLocation.setText(locationText);
        } else {
            tvLocation.setText("Location not available");
        }

        // Configurar rating (exemplo estático; adapte conforme necessário)
        tvRating.setText("0.0"); // Exemplo: substituir por vehicle.getRating(), se existir
        tvTrips.setText("0 trips"); // Exemplo: substituir por trips reais, se aplicável

        // Configurar o botão "Rent Vehicle"
        btnRentVehicle.setOnClickListener(v -> {
            // Ação para alugar veículo, por exemplo, abrir detalhes ou enviar requisição
            // Exemplo de um Toast:
            // Toast.makeText(context, "Renting vehicle: " + vehicle.getMark(), Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
