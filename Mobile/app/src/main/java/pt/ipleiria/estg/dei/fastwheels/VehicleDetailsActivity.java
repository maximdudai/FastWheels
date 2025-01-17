package pt.ipleiria.estg.dei.fastwheels;

import static pt.ipleiria.estg.dei.fastwheels.utils.Helpers.showMessage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import pt.ipleiria.estg.dei.fastwheels.adapters.ImageListAdapter;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.model.VehiclePhoto;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class VehicleDetailsActivity extends AppCompatActivity {

    private TextView tvBrand, tvModel, tvYear, tvDoors, tvResidence, tvPrice, tvPostalCode, tvCity, tvAvailableFrom, tvAvailableTo;
    private ListView lvImgVehicle;
    private Calendar calendarAvailableFrom, calendarAvailableTo;
    private ArrayList<Uri> selectedImages;

    private static final int REQUEST_MEDIA_PERMISSION = 100;
    private static final int MAX_IMAGES = 3;

    private GridLayout glImgVehicle;
    private List<String> displayedImages = new ArrayList<>();
    private int selectedVehicle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        // Referências para os campos da UI
        tvBrand = findViewById(R.id.tvBrand);
        tvModel = findViewById(R.id.tvModel);
        tvYear = findViewById(R.id.tvYear);
        tvDoors = findViewById(R.id.tvDoors);
        tvResidence = findViewById(R.id.tvResidence);
        tvPrice = findViewById(R.id.tvPrice);
        tvPostalCode = findViewById(R.id.tvPostalCode);
        tvCity = findViewById(R.id.tvCity);
        tvAvailableFrom = findViewById(R.id.tvAvailableFrom);
        tvAvailableTo = findViewById(R.id.tvAvailableTo);
        glImgVehicle = findViewById(R.id.glImgVehicle);

        // Inicializando a lista de imagens
        selectedImages = new ArrayList<>();

        // Configurar os calendários iniciais
        calendarAvailableFrom = Calendar.getInstance();
        calendarAvailableTo = (Calendar) calendarAvailableFrom.clone();
        calendarAvailableTo.add(Calendar.DAY_OF_MONTH, 1);

        // Obter o ID do veículo passado pela Intent
        selectedVehicle = getIntent().getIntExtra("VEHICLE_ID", -1);

        if (selectedVehicle != -1) {
            // Busca o veículo no Singleton
            Vehicle vehicle = SingletonFastWheels.getInstance(getApplicationContext()).getVehicleByIdBd(selectedVehicle);

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

    private void refreshImageContainer() {
        System.out.println("--->DEBUG refreshImageContainer called");

        // Limpa as imagens exibidas e o container visual
        displayedImages.clear();
        glImgVehicle.removeAllViews();

        for (Uri uri : selectedImages) {
            // Verifica se a imagem já foi exibida
            if (!displayedImages.contains(uri.toString())) {
                displayedImages.add(uri.toString());

                // Cria um novo ImageView
                ImageView imageView = new ImageView(this); // Use 'this' como contexto
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();

                // Define tamanho fixo para largura e altura
                params.width = 600; // Largura fixa
                params.height = 600; // Altura fixa
                params.setMargins(8, 8, 8, 8); // Margem entre imagens
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); // Centraliza e corta

                // Carrega a imagem usando Glide
                Glide.with(this) // Use 'this' como contexto
                        .load(uri)
                        .error(R.drawable.gallery_icon)
                        .into(imageView);

                // Adiciona a imagem ao container
                glImgVehicle.addView(imageView);
            } else {
                System.out.println("--->DEBUG nothing to show");
            }
        }
    }

    private void populateVehicleDetails(Vehicle vehicle) {
        // Preencher os campos principais
        tvBrand.setText(vehicle.getCarBrand());
        tvModel.setText(vehicle.getCarModel());
        tvYear.setText(String.valueOf(vehicle.getCarYear()));
        tvDoors.setText(String.valueOf(vehicle.getCarDoors()));
        tvResidence.setText(vehicle.getAddress());
        tvPrice.setText(String.valueOf(vehicle.getPriceDay() + "€"));
        tvPostalCode.setText(vehicle.getPostalCode());
        tvCity.setText(vehicle.getCity());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        tvAvailableFrom.setText(dateFormat.format(vehicle.getAvailableFrom()));
        tvAvailableTo.setText(dateFormat.format(vehicle.getAvailableTo()));

        // Preencher as imagens
        selectedImages.clear(); // Limpa a lista antes de preencher
        if (vehicle.getVehiclePhotos() != null) {
            for (VehiclePhoto photo : vehicle.getVehiclePhotos()) {
                String photoUrl = photo.getPhotoUrl();
                if (photoUrl != null && !photoUrl.isEmpty()) {
                    selectedImages.add(Uri.parse(photoUrl)); // Adiciona o URI à lista
                }
            }
        }

        // Atualizar o container de imagens
        refreshImageContainer();
    }

    public void handleRentVehicle(View v) {
        Intent toReservation = new Intent(this, ReserveVehicle.class);
        toReservation.putExtra("SELECTED_VEHICLE", selectedVehicle);
        startActivity(toReservation);

    }
}