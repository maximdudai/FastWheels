package pt.ipleiria.estg.dei.fastwheels.model;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

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
}
