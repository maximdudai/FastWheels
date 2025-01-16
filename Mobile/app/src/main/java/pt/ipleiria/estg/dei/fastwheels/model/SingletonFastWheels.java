package pt.ipleiria.estg.dei.fastwheels.model;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.LoginListener;
import pt.ipleiria.estg.dei.fastwheels.listeners.ProfileListener;
import pt.ipleiria.estg.dei.fastwheels.listeners.VehicleListener;
import pt.ipleiria.estg.dei.fastwheels.parsers.LoginParser;
import pt.ipleiria.estg.dei.fastwheels.parsers.ProfileParser;
import pt.ipleiria.estg.dei.fastwheels.utils.generateBase64;
import pt.ipleiria.estg.dei.fastwheels.parsers.VehicleParser;

public class SingletonFastWheels {

    private ArrayList<Vehicle> vehicles; // Lista de veículos
    private static SingletonFastWheels instance = null; // Instância única

    private VehicleDbHelper vehicleDbHelper = null; // Helper para banco de dados

    private User loggedUser = null;

    //volley
    private static RequestQueue volleyQueue;

    // listeners
    private LoginListener loginListener;
    private ProfileListener profileListener;
    private VehicleListener vehicleListener;

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

    // region get/set user
    public User getUser() {
        return loggedUser;
    }
    public void setUser(User user) {
        this.loggedUser = user;
    }

    //region METODOS GERIR VEHICLE
    // Obter veículo específico pelo ID
    public Vehicle getVehicleByIdBd(int id) {
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

    public void addVehiclesDb(ArrayList<Vehicle> vehicles) {
        if (vehicleDbHelper == null) {
            return;
        }

        for (Vehicle v : vehicles) {
            vehicleDbHelper.removeVehicleDb(v.getId());
        }

        for (Vehicle v: vehicles){
            addVehicleDb(v);
        }
    }

    // TODO - CONFIRMAR SE É NECESSARIO
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
            Vehicle vehicle = getVehicleByIdBd(vehicleId);
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
                loggedUser =  LoginParser.parseLoginData(response);

                generateBase64 base64Token = new generateBase64(loggedUser.getName(), loggedUser.getPassword());
                loggedUser.setBase64token(base64Token);

                if (loginListener != null)
                    loginListener.onValidateLogin(loggedUser, context);
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

    public void updateProfileAPI(User user, final Context context) {
        StringRequest request = new StringRequest(Request.Method.PUT, Constants.API_PROFILE + "?id=" + loggedUser.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                User loggedUserResponse = ProfileParser.parseProfileData(response);
                if (profileListener != null)
                    profileListener.onProfileUpdate(loggedUserResponse, context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                String errorMessage = error.networkResponse != null ? new String(error.networkResponse.data) : "Unknown error";
                Log.e("SINGLETON", errorMessage);
                Toast.makeText(context, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", user.getName());
                params.put("email", user.getEmail());
                params.put("phone", user.getPhone());
                params.put("balance", user.getBalance());
                params.put("iban", user.getIban());
                params.put("password", user.getPassword());

                return new JSONObject(params).toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> headers = new HashMap<>();

                generateBase64 base64Token = new generateBase64(user.getName(), user.getPassword());
                headers.put("Authorization", base64Token.getBase64Token());  // Add a token if required
                return headers;
            }
        };

        volleyQueue.add(request);
    }
    public void setProfileListener(ProfileListener profileListener) {
        this.profileListener = profileListener;
    }

    //endregion

    //region #Vechicle API
    public void addVehicleAPI (final Vehicle vehicleData, final Context context){
        if(!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constants.API_VEHICLES + "/create",
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    addVehicleDb(VehicleParser.parseVehicleData(response));

                    if (vehicleListener != null)
                        vehicleListener.onRefreshVehicle();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public byte[] getBody() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("clientId", "" + vehicleData.getClientId());
                    params.put("carBrand", vehicleData.getCarBrand());
                    params.put("carModel", vehicleData.getCarModel());
                    params.put("carYear", "" + vehicleData.getCarYear());
                    params.put("carDoors", "" + vehicleData.getCarDoors());
                    params.put("status", "" + vehicleData.isStatus());
                    params.put("availableFrom", "" + vehicleData.getAvailableFrom());
                    params.put("availableTo", "" + vehicleData.getAvailableTo());
                    params.put("address", vehicleData.getAddress());
                    params.put("postalCode", vehicleData.getPostalCode());
                    params.put("city", vehicleData.getCity());
                    params.put("priceDay", "" + vehicleData.getPriceDay());

                    return new JSONObject(params).toString().getBytes(StandardCharsets.UTF_8);
                }
                @Override
                public Map<String, String> getHeaders()  {
                    Map<String, String> headers = new HashMap<>();

                    generateBase64 base64Token = new generateBase64(loggedUser.getName(), loggedUser.getPassword());
                    headers.put("Authorization", base64Token.getBase64Token());
                    return headers;
                }
            };
            volleyQueue.add(request);
        }
    }

    public void getVehiclesAPI(final Context context) {
        if (!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();

            if (vehicleListener != null)
                vehicleListener.onRefreshVehicle();
        } else {
            JsonArrayRequest Jsonrequest = new JsonArrayRequest(
                    Request.Method.GET,
                    Constants.API_VEHICLES,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            addVehiclesDb(VehicleParser.parseVehiclesData(response));

                            if (vehicleListener != null)
                                vehicleListener.onRefreshVehicle();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            volleyQueue.add(Jsonrequest);
        }
    }


    public void editVehicleAPI (final Vehicle vehicleData, final Context context){
        if(!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    Constants.API_VEHICLES + "/update?id=" + vehicleData.getId(),
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    editVehicleDb(VehicleParser.parseVehicleData(response));
//                    addVehicleDb(VehicleParser.parseVehicleData(response));

                    if (vehicleListener != null)
                        vehicleListener.onRefreshVehicle();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                public byte[] getBody() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("clientId", "" + vehicleData.getClientId());
                    params.put("carBrand", vehicleData.getCarBrand());
                    params.put("carModel", vehicleData.getCarModel());
                    params.put("carYear", "" + vehicleData.getCarYear());
                    params.put("carDoors", "" + vehicleData.getCarDoors());
                    params.put("status", "" + vehicleData.isStatus());
                    params.put("availableFrom", "" + vehicleData.getAvailableFrom());
                    params.put("availableTo", "" + vehicleData.getAvailableTo());
                    params.put("address", vehicleData.getAddress());
                    params.put("postalCode", vehicleData.getPostalCode());
                    params.put("city", vehicleData.getCity());
                    params.put("priceDay", "" + vehicleData.getPriceDay());

                    return new JSONObject(params).toString().getBytes(StandardCharsets.UTF_8);
                }
            };
            volleyQueue.add(request);
        }
    }

    public void removeVehicleAPI (final Vehicle vehicleData, final Context context){
        if(!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    Constants.API_VEHICLES + "/delete?id=" + vehicleData.getId(),
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    removeVehicleDb(vehicleData.getId());

                    if (vehicleListener != null)
                        vehicleListener.onRefreshVehicle();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(request);
        }
    }



    public void setVehicleListener(VehicleListener vehicleListener){
        this.vehicleListener = vehicleListener;
    }

    //endregion
}