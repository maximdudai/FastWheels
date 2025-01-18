package pt.ipleiria.estg.dei.fastwheels.model;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.LoginListener;
import pt.ipleiria.estg.dei.fastwheels.parsers.LoginParser;

public class SingletonFastWheels {

    private ArrayList<Vehicle> vehicles; // Lista de veículos
    private static SingletonFastWheels instance = null; // Instância única

    private VehicleDbHelper vehicleDbHelper = null; // Helper para a base de dados

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

    //region #Favorite API
    public void addFavoriteAPI(final int vehicleId, final Context context) {
        if (!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constants.API_FAVORITES + "/add",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(context, "Vehicle added to favorites!", Toast.LENGTH_SHORT).show();
                            if (vehicleListener != null) vehicleListener.onRefreshVehicle();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null) {
                                String responseData = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                                Log.e("API_ERROR", "Status Code: " + error.networkResponse.statusCode);
                                Log.e("API_ERROR", "Response Data: " + responseData);
                            }
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public byte[] getBody() {
                    Map<String, String> params = new HashMap<>();
                    params.put("userId", String.valueOf(loggedUser.getId()));
                    params.put("vehicleId", String.valueOf(vehicleId));

                    return new JSONObject(params).toString().getBytes(StandardCharsets.UTF_8);
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    generateBase64 base64Token = new generateBase64(loggedUser.getName(), loggedUser.getPassword());
                    headers.put("Authorization", base64Token.getBase64Token());
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    return headers;
                }
            };
            volleyQueue.add(request);
        }
    }

    public void removeFavoriteAPI(final int vehicleId, final Context context) {
        if (!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    Constants.API_FAVORITES + "/remove?userId=" + loggedUser.getId() + "&vehicleId=" + vehicleId,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(context, "Vehicle removed from favorites!", Toast.LENGTH_SHORT).show();
                            if (vehicleListener != null) vehicleListener.onRefreshVehicle();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null) {
                                String responseData = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                                Log.e("API_ERROR", "Status Code: " + error.networkResponse.statusCode);
                                Log.e("API_ERROR", "Response Data: " + responseData);
                            }
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    generateBase64 base64Token = new generateBase64(loggedUser.getName(), loggedUser.getPassword());
                    headers.put("Authorization", base64Token.getBase64Token());
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    return headers;
                }
            };
            volleyQueue.add(request);
        }
    }

    public void getFavoritesAPI(final Context context) {
        if (!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();

            if (vehicleListener != null) vehicleListener.onRefreshVehicle();
        } else {
            JsonArrayRequest jsonRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    Constants.API_FAVORITES + "?userId=" + loggedUser.getId(),
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            ArrayList<Vehicle> favorites = VehicleParser.parseVehiclesData(response);

                            if (favorites != null && !favorites.isEmpty()) {
                                if (vehicleListener != null) vehicleListener.onFavoritesLoaded(favorites);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String errorMsg = (error.getMessage() != null) ? error.getMessage() : "An unexpected error occurred.";
                    Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(jsonRequest);
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