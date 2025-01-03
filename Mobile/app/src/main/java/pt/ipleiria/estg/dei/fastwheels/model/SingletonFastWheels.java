package pt.ipleiria.estg.dei.fastwheels.model;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.LoginListener;
import pt.ipleiria.estg.dei.fastwheels.parsers.LoginParser;

public class SingletonFastWheels {

    private ArrayList<Vehicle> vehicles; // Lista de veículos
    private static SingletonFastWheels instance = null; // Instância única

    private VehicleDbHelper vehicleDbHelper = null; // Helper para banco de dados

    private int clientId;

    //private Context appContext;

    //volley
    private static RequestQueue volleyQueue;

    // listeners
    private LoginListener loginListener;

    // Método para obter a instância única do Singleton
    public static synchronized SingletonFastWheels getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonFastWheels(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    // Construtor privado
    private SingletonFastWheels(Context context) {
        //this.appContext = context.getApplicationContext(); // Evita vazamentos de memória
        vehicles = new ArrayList<>();
        //vehicleDbHelper = new VehicleDbHelper(appContext); // Inicializar o helper da base de dados
        vehicleDbHelper = new VehicleDbHelper(context);
    }

    // region Get/Set ClientID
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    // endregion

    // Obter veículo específico pelo ID
    public Vehicle getVehicleById(int id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null; // Retorna null se o veículo não for encontrado
    }

    // Adicionar veículo ao banco de dados e à lista
    public void addVehicleDb(Vehicle vehicle) {
        if (vehicleDbHelper == null) {
            return;  // Se nao estiver inicializado sai
        }

        Vehicle auxVehicle = vehicleDbHelper.addVehicleDb(vehicle); // Adicionar à bd através do Helper

        if (auxVehicle != null) {
            vehicles.add(auxVehicle); // Adicionar à lista local
            vehicles = vehicleDbHelper.getAllVehiclesDb(); // Recarrega a lista
            System.out.println("Veículo adicionado com sucesso!");
        } else {
            System.err.println("Erro ao adicionar veículo!");
        }
    }

    public void editVehicleDb(Vehicle vehicle) {
        if (vehicleDbHelper.editVehicleDb(vehicle)) { // Tenta editar o veículo no banco de dados
            vehicles = vehicleDbHelper.getAllVehiclesDb(); // Atualiza a lista de veículos do banco
            System.out.println("Veículo editado com sucesso!");
        } else {
            System.err.println("Erro ao editar veículo!");
        }
    }

    public void removeVehicleDb(int vehicleId) {
        if (vehicleDbHelper.removeVehicleDb(vehicleId)) { // Tenta remover o veículo pelo ID
            vehicles = vehicleDbHelper.getAllVehiclesDb(); // Atualiza a lista de veículos
            System.out.println("Veículo removido com sucesso!");
        } else {
            System.err.println("Erro ao remover veículo!");
        }
    }

    // Carregar todos os veículos do banco de dados
    public ArrayList<Vehicle> getVehiclesDb() {
        if (vehicles == null || vehicles.isEmpty()) {
            vehicles = vehicleDbHelper.getAllVehiclesDb();
        }
        return new ArrayList<>(vehicles); // Retorna uma nova lista para proteger os dados originais
    }

    // Consultar veículos por marca
    public ArrayList<Vehicle> getVehiclesByCarBrand(String brand) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getCarBrand().equalsIgnoreCase(brand)) {
                filteredList.add(vehicle);
            }
        }
        return filteredList;
    }

    // Adicionar foto associada a um veículo
    public void addVehiclePhoto(int vehicleId, String photoUrl) {
        VehiclePhoto newPhoto = vehicleDbHelper.addPhotoDb(new VehiclePhoto(0, vehicleId, photoUrl));
        if (newPhoto != null) {
            Vehicle vehicle = getVehicleById(vehicleId);
            if (vehicle != null) {
                /*
                if (vehicle.getVehiclePhotos() == null) { // Verifique se a lista de fotos foi inicializada
                    vehicle.setVehiclePhotos(new ArrayList<>());
                }
                */
                vehicle.getVehiclePhotos().add(newPhoto);
            }
        }
    }

    /* TODO REMOVER DEPOIS
    public void addVehiclePhoto(int vehicleId, String photoUrl) {
        try {
            Uri photoUri = Uri.parse(photoUrl);
            String fileName = "vehicle_photo_" + System.currentTimeMillis() + ".jpg";
            String internalPath = copyToInternalStorage(appContext, photoUri, fileName);

            VehiclePhoto newPhoto = vehicleDbHelper.addPhotoDb(new VehiclePhoto(0, vehicleId, internalPath));
            if (newPhoto != null) {
                Vehicle vehicle = getVehicleById(vehicleId);
                if (vehicle != null) {
                    if (vehicle.getVehiclePhotos() == null) { // Verifique se a lista de fotos foi inicializada
                        vehicle.setVehiclePhotos(new ArrayList<>());
                    }
                    vehicle.getVehiclePhotos().add(newPhoto);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao copiar a foto para o armazenamento interno: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String copyToInternalStorage(Context context, Uri uri, String fileName) throws IOException {
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(new File(context.getFilesDir(), fileName))) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            return new File(context.getFilesDir(), fileName).getAbsolutePath();
        } catch (IOException e) {
            throw e; // Relança a exceção para tratamento no método chamador
        }
    }

    public List<VehiclePhoto> getAllPhotosByVehicleId(int vehicleId) {
        List<VehiclePhoto> photos = new ArrayList<>();

        // Consulta para buscar fotos associadas a um veículo específico
        SQLiteDatabase db = vehicleDbHelper.getReadableDatabase(); // Obter banco de leitura
        Cursor cursor = db.query(
                VehicleDbHelper.TABLE_VEHICLE_PHOTOS,
                new String[]{VehicleDbHelper.PHOTO_ID, VehicleDbHelper.PHOTO_CAR_ID, VehicleDbHelper.PHOTO_URL},
                VehicleDbHelper.PHOTO_CAR_ID + " = ?",
                new String[]{String.valueOf(vehicleId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                photos.add(new VehiclePhoto(
                        cursor.getInt(cursor.getColumnIndexOrThrow(VehicleDbHelper.PHOTO_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(VehicleDbHelper.PHOTO_CAR_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(VehicleDbHelper.PHOTO_URL))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return photos;
    }
*/

    public void removeVehiclePhoto(int photoId) {
        vehicleDbHelper.removePhotoDb(photoId);
    }

    //region #LoginListener

    public void loginAPI(String username, String password, final Context context) {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.API_AUTH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String token = String.valueOf(LoginParser.parseLoginData(response));

                if(loginListener != null)
                    loginListener.onValidateLogin(token, username, context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "invalid authentication credentials", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        volleyQueue.add(request);
    }
    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
    //endregion


    // * **********************CODIGO ANTES UNIFORMIZAR**********************************
/*
    private ArrayList<Vehicle> vehicles; // Lista de veículos
    private static SingletonFastWheels instance = null; // Instância única

    private VehicleDbHelper vehicleDbHelper = null; // Helper para banco de dados

    //volley
    private static RequestQueue volleyQueue;

    // listeners
    private LoginListener loginListener;

    // Método para obter a instância única do Singleton
    public static synchronized SingletonFastWheels getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonFastWheels(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    // Construtor privado
    private SingletonFastWheels(Context context) {
        vehicles = new ArrayList<>();
        vehicleDbHelper = new VehicleDbHelper(context); // Inicializar o helper da base de dados

    }

    // Método para carregar todos os veículos do banco de dados
    public ArrayList<Vehicle> getVehiclesDb() {
        if (vehicles == null || vehicles.isEmpty()) {
            vehicles = vehicleDbHelper.getAllVehiclesDb();
        }
        return new ArrayList<>(vehicles); // Retorna uma nova lista para proteger os dados originais
    }

    // Obter veículo específico pelo ID
    public Vehicle getVehicleById(int id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null; // Retorna null se o veículo não for encontrado
    }

    // Adicionar veículo ao banco de dados e à lista
    public void addVehicleDb(Vehicle vehicle) {
        Vehicle auxVehicle = vehicleDbHelper.addVehicleDb(vehicle);
        if (auxVehicle != null) {
            vehicles.add(auxVehicle);
            vehicles = vehicleDbHelper.getAllVehiclesDb(); // Recarrega a lista
            System.out.println("Veículo adicionado com sucesso!");
        } else {
            System.err.println("Erro ao adicionar veículo!");
        }
    }

    public void editVehicleDb(Vehicle vehicle) {
        if (vehicleDbHelper.editVehicleDb(vehicle)) { // Tenta editar o veículo no banco de dados
            vehicles = vehicleDbHelper.getAllVehiclesDb(); // Atualiza a lista de veículos do banco
            System.out.println("Veículo editado com sucesso!");
        } else {
            System.err.println("Erro ao editar veículo!");
        }
    }


    public void removeVehicleDb(int vehicleId) {
        if (vehicleDbHelper.removeVehicleDb(vehicleId)) { // Tenta remover o veículo pelo ID
            vehicles = vehicleDbHelper.getAllVehiclesDb(); // Atualiza a lista de veículos
            System.out.println("Veículo removido com sucesso!");
        } else {
            System.err.println("Erro ao remover veículo!");
        }
    }

    // Consultar veículos por marca
    public ArrayList<Vehicle> getVehiclesByMark(String mark) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getBrand().equalsIgnoreCase(mark)) {
                filteredList.add(vehicle);
            }
        }
        return filteredList;
    }

    //region #LoginListener

    public void loginAPI(String username, String password, final Context context) {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.API_AUTH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                User user = LoginParser.parseLoginData(response);
                if (user != null) {
                    String token = user.getToken(); // Get the token from the User object
                    if (loginListener != null) {
                        loginListener.onValidateLogin(token, username, context);
                    }
                } else {
                    Toast.makeText(context, "Failed to parse login data", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Invalid authentication credentials", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        volleyQueue.add(request);
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }
    //endregion
    */
    //*****************************************************************************   */
}
