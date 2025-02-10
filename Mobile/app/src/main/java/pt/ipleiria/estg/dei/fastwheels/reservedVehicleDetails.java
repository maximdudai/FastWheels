package pt.ipleiria.estg.dei.fastwheels;

import static pt.ipleiria.estg.dei.fastwheels.utils.Helpers.showMessage;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import pt.ipleiria.estg.dei.fastwheels.model.Reservation;
import pt.ipleiria.estg.dei.fastwheels.model.Review;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.model.VehiclePhoto;
import pt.ipleiria.estg.dei.fastwheels.modules.InputDialog;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;

public class reservedVehicleDetails extends AppCompatActivity {
    private TextView tvBrand, tvModel, tvYear, tvDoors, tvResidence, tvPrice, tvPostalCode, tvCity;
    private ArrayList<Reservation> allReservations;
    private int selectedVehicle_id = 0;
    private Vehicle selectedVehicle;
    private User loggedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_vehicle_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tvBrand = findViewById(R.id.tvBrand);
        tvModel = findViewById(R.id.tvModel);
        tvYear = findViewById(R.id.tvYear);
        tvDoors = findViewById(R.id.tvDoors);
        tvResidence = findViewById(R.id.tvResidence);
        tvPrice = findViewById(R.id.tvDate);
        tvPostalCode = findViewById(R.id.tvPostalCode);
        tvCity = findViewById(R.id.tvCity);

        loggedUser = SingletonFastWheels.getInstance(getApplicationContext()).getUser();
        allReservations = SingletonFastWheels.getInstance(getApplicationContext()).getReservationsDb();

        selectedVehicle_id = getIntent().getIntExtra("VEHICLE_ID", -1);
        selectedVehicle = SingletonFastWheels.getInstance(getApplicationContext()).getVehicleByIdBd(selectedVehicle_id);
        if(selectedVehicle != null)
            populateVehicleDetails(selectedVehicle);

        findViewById(R.id.btnAddReview).setOnClickListener(v -> showDialog("Car Review", "Describe your experience", "review"));

    }

    public void handleRemoveReservation(View v) {
        new AlertDialog.Builder(getApplicationContext())
                    .setMessage("Pretende remover a reserva?")
                    .setPositiveButton("Sim", (dialog, which) -> {

                        Reservation clickedReserve = Helpers.getReservationByVehicleAndUser(allReservations, loggedUser.getId(), selectedVehicle.getId());

                        if(clickedReserve != null) {
                            SingletonFastWheels.getInstance(getApplicationContext()).removeReservationAPI(clickedReserve.getId(), getApplicationContext());

                            //false = mark as unrented | true = mark as rented
                            selectedVehicle.setStatus(false);

                            SingletonFastWheels.getInstance(getApplicationContext())
                                    .editVehicleAPI(selectedVehicle, getApplicationContext());
                        }
                        onSupportNavigateUp();
                    })
                    .setNegativeButton("Não", null)
                    .show();
    }

    private void showDialog(String title, String hint, String type) {
        InputDialog dialog = InputDialog.newInstance(title, hint, type);
        dialog.setOnInputListener(this::onInputReceived);
        dialog.show(getSupportFragmentManager(), "InputDialogFragment");
    }

    public void onInputReceived(String input, String type) {
        if(Objects.equals(type, "review")) {
            Review newReview = new Review(0, selectedVehicle_id, input, new Timestamp(System.currentTimeMillis()));
            SingletonFastWheels.getInstance(getApplicationContext()).addReviewAPI(newReview, getApplicationContext());

            Toast.makeText(this, "Review added successfully!", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle the back button click event
        onBackPressed();
        return true;
    }
}