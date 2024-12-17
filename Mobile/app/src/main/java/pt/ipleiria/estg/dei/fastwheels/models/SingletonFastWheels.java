package pt.ipleiria.estg.dei.fastwheels.models;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class SingletonFastWheels {
    private static SingletonFastWheels instance = null;

    private final VehicleDbHelper vehicleDbHelper;
    private final ArrayList<Vehicle> vehicles;

    private SingletonFastWheels(Context context) {
        vehicleDbHelper = new VehicleDbHelper(context);
        vehicles = new ArrayList<>();
    }

    public static synchronized SingletonFastWheels getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonFastWheels(context.getApplicationContext());
        }
        return instance;
    }

    public Vehicle getVehicle(int id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null;
    }

    // Adicionar veículo ao banco de dados e à lista local
    public void addVehicleDb(Vehicle vehicle) {
        Vehicle newVehicle = vehicleDbHelper.addVehicleDb(vehicle);
        if (newVehicle != null) {
            vehicles.add(newVehicle);
            Log.d("SingletonFastWheels", "Veículo adicionado: " + newVehicle);
        } else {
            Log.e("SingletonFastWheels", "Erro ao adicionar veículo.");
        }
    }

    // Adicionar foto associada a um veículo
    public void addVehiclePhoto(int vehicleId, String photoUrl) {
        VehiclePhoto newPhoto = vehicleDbHelper.addPhotoDb(new VehiclePhoto(0, vehicleId, photoUrl));
        if (newPhoto != null) {
            Vehicle vehicle = getVehicle(vehicleId);
            if (vehicle != null) {
                vehicle.getVehiclePhotos().add(newPhoto);
            }
        }
    }

}
