package pt.ipleiria.estg.dei.fastwheels.model;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.LoginListener;
import pt.ipleiria.estg.dei.fastwheels.parsers.LoginParser;

public class SingletonFastWheels {

    private ArrayList<Vehicle> vehicles; // Lista de veículos
    private static SingletonFastWheels instance = null; // Instância única

    private VehicleDbHelper vehicleDbHelper = null; // Helper para banco de dados

    private int clientId;

    //volley
    private static RequestQueue volleyQueue;

    // listeners
    private LoginListener loginListener;

    // Mosquitto
    private static Mosquitto mosquitto = null;
    private static MosquittoManager mosquittoManager = null;

    // Método para obter a instância única do Singleton
    public static synchronized SingletonFastWheels getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonFastWheels(context);
            volleyQueue = Volley.newRequestQueue(context);
            mosquitto = new Mosquitto(context);
            mosquitto.connect();
            mosquittoManager = new MosquittoManager(context.getApplicationContext());
            mosquitto.setMosquittoListener(mosquittoManager);

        }
        return instance;
    }

    // Construtor privado
    private SingletonFastWheels(Context context) {
        vehicles = new ArrayList<>();
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

    //region METODOS GERIR VEHICLE
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
    public long addVehicleDb(Vehicle vehicle) {
        if (vehicleDbHelper == null) {
            return -1;  // Se nao estiver inicializado sai
        }

        Vehicle auxVehicle = vehicleDbHelper.addVehicleDb(vehicle); // Adicionar à bd através do Helper

        if (auxVehicle != null) {
            vehicles.add(auxVehicle); // Adicionar à lista local
            System.out.println("Veículo adicionado com sucesso!");
        } else {
            System.err.println("Erro ao adicionar veículo!");
        }
        return auxVehicle.getId();
    }

    public boolean editVehicleDb(Vehicle vehicle) {
        boolean isEdited = vehicleDbHelper.editVehicleDb(vehicle); // Tenta editar o veículo no banco de dados
        if (isEdited) {
            vehicles = vehicleDbHelper.getAllVehiclesDb(); // Atualiza a lista de veículos do banco
            System.out.println("Veículo editado com sucesso!");
        } else {
            System.err.println("Erro ao editar veículo!");
        }
        return isEdited; // Retorna o resultado da operação
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

    //endregion

    //region METODOS GERIR VEHICLEPHOTO
    // Adicionar foto associada a um veículo
    public void addVehiclePhoto(int vehicleId, String photoUrl) {
        VehiclePhoto newPhoto = vehicleDbHelper.addPhotoDb(new VehiclePhoto(0, vehicleId, photoUrl));
        if (newPhoto != null) {
            Vehicle vehicle = getVehicleById(vehicleId);
            if (vehicle != null) {
                vehicle.getVehiclePhotos().add(newPhoto);
            }
        }
    }

    // Remover todas fotos de um veiculo
    public void removeAllVehiclePhotosBD(int vehicleId) {
        if (vehicleDbHelper.removeAllPhotosByVehicleIdDB(vehicleId)) {
            System.out.println("Todas as fotos do veículo foram removidas com sucesso!");
        } else {
            System.err.println("Erro ao remover fotos do veículo!");
        }
    }

    //endregion

    //region #LoginListener
    public void loginAPI(String username, String password, final Context context) {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.API_AUTH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                User loggedUserResponse = LoginParser.parseLoginData(response);

                if (loginListener != null)
                    loginListener.onValidateLogin(loggedUserResponse, context);
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

    //region #Profile



    //endregion
}