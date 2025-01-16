package pt.ipleiria.estg.dei.fastwheels.parsers;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.model.VehiclePhoto;

public class VehicleParser {

    public static ArrayList<Vehicle> parseVehiclesData (JSONArray response) {
        ArrayList<Vehicle> vehiclesData = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject fetchData = (JSONObject) response.get(i);

                int id = fetchData.getInt("id");
                int clientId = fetchData.getInt("clientId");
                String carBrand = fetchData.getString("carBrand");
                String carModel = fetchData.getString("carModel");
                int carYear = fetchData.getInt("carYear");
                int carDoors = fetchData.getInt("carDoors");
                boolean status = fetchData.getInt("status") == 1;
                Timestamp availableFrom = Timestamp.valueOf(fetchData.getString("availableFrom"));
                Timestamp availableTo = Timestamp.valueOf(fetchData.getString("availableTo"));
                String address = fetchData.getString("address");
                String postalCode = fetchData.getString("postalCode");
                String city = fetchData.getString("city");
                BigDecimal priceDay = new BigDecimal(fetchData.getString("priceDay"));
                JSONArray photosArray = fetchData.getJSONArray("vehiclePhotos");

                List<VehiclePhoto> vehiclePhotos = new ArrayList<>();
                for (int j = 0; j < photosArray.length(); j++) {
                    JSONObject photoJson = photosArray.getJSONObject(j);

                    int Id = photoJson.getInt("Id");
                    int carId = photoJson.getInt("carId");
                    String photoUrl = photoJson.getString("photoUrl");

                    vehiclePhotos.add(new VehiclePhoto(Id, carId, photoUrl));
                }

                Vehicle auxVehicleData = new Vehicle(id, clientId, carBrand, carModel,
                        carYear, carDoors, status, availableFrom,
                        availableTo, address, postalCode, city, priceDay,vehiclePhotos);

                vehiclesData.add(auxVehicleData);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return vehiclesData;
    }


    public static Vehicle parseVehicleData (String response) {

        Vehicle vehicleData = null;

        try {
            JSONObject fetchData = new JSONObject(response);

            int id = fetchData.getInt("id");
            int clientId = fetchData.getInt("clientId");
            String carBrand = fetchData.getString("carBrand");
            String carModel = fetchData.getString("carModel");
            int carYear = fetchData.getInt("carYear");
            int carDoors = fetchData.getInt("carDoors");
            boolean status = fetchData.getInt("status") == 1;
            Timestamp availableFrom = Timestamp.valueOf(fetchData.getString("availableFrom"));
            Timestamp availableTo = Timestamp.valueOf(fetchData.getString("availableTo"));
            String address = fetchData.getString("address");
            String postalCode = fetchData.getString("postalCode");
            String city = fetchData.getString("city");
            BigDecimal priceDay = new BigDecimal(fetchData.getString("priceDay"));
            JSONArray photosArray = fetchData.getJSONArray("vehiclePhotos");

            List<VehiclePhoto> vehiclePhotos = new ArrayList<>();
            for (int i = 0; i < photosArray.length(); i++) {
                JSONObject photoJson = photosArray.getJSONObject(i);

                int Id = photoJson.getInt("Id");
                int carId = photoJson.getInt("carId");
                String photoUrl = photoJson.getString("photoUrl");

                vehiclePhotos.add(new VehiclePhoto(Id, carId, photoUrl));
            }

            vehicleData = new Vehicle(id, clientId, carBrand, carModel,
                    carYear, carDoors, status, availableFrom,
                    availableTo, address, postalCode, city, priceDay,vehiclePhotos);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return vehicleData;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}