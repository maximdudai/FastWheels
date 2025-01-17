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


    public static Vehicle parseVehicleData(String response) {
        Vehicle vehicleData = null;

        try {
            JSONObject fetchData = new JSONObject(response);

            int id = fetchData.optInt("id", -1);
            int clientId = fetchData.optInt("clientId", -1);
            String carBrand = fetchData.optString("carBrand");
            String carModel = fetchData.optString("carModel");
            int carYear = fetchData.optInt("carYear", 0);
            int carDoors = fetchData.optInt("carDoors", 0);
            boolean status = fetchData.optInt("status", 0) == 1;

            String availableFromStr = fetchData.optString("availableFrom");
            Timestamp availableFrom = availableFromStr != null
                    ? Timestamp.valueOf(availableFromStr)
                    : null;

            String availableToStr = fetchData.optString("availableTo");
            Timestamp availableTo = availableToStr != null
                    ? Timestamp.valueOf(availableToStr)
                    : null;

            String address = fetchData.optString("address");
            String postalCode = fetchData.optString("postalCode");
            String city = fetchData.optString("city", "");
            BigDecimal priceDay = new BigDecimal(fetchData.optString("priceDay"));

            List<VehiclePhoto> vehiclePhotos = new ArrayList<>();
            if (fetchData.has("vehiclePhotos")) {
                JSONArray photosArray = fetchData.getJSONArray("vehiclePhotos");
                for (int i = 0; i < photosArray.length(); i++) {
                    JSONObject photoJson = photosArray.getJSONObject(i);

                    int photoId = photoJson.optInt("Id", -1);
                    int carId = photoJson.optInt("carId", -1);
                    String photoUrl = photoJson.optString("photoUrl", "");

                    vehiclePhotos.add(new VehiclePhoto(photoId, carId, photoUrl));
                }
            }

            vehicleData = new Vehicle(id, clientId, carBrand, carModel, carYear, carDoors, status,
                    availableFrom, availableTo, address, postalCode, city,
                    priceDay, vehiclePhotos);

            System.out.println("Parsed Vehicle: " + vehicleData);

        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println("Erro ao parsear os dados do veículo: " + e.getMessage());
        }catch (IllegalArgumentException e) {
            System.err.println("Formato inválido detectado: " + e.getMessage());
        }

        return vehicleData;
    }


    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

}