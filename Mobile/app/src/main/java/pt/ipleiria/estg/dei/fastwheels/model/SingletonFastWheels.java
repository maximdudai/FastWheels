package pt.ipleiria.estg.dei.fastwheels.model;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.listeners.LoginListener;
import pt.ipleiria.estg.dei.fastwheels.listeners.ProfileListener;
import pt.ipleiria.estg.dei.fastwheels.listeners.ReservationListener;
import pt.ipleiria.estg.dei.fastwheels.listeners.VehicleListener;
import pt.ipleiria.estg.dei.fastwheels.parsers.LoginParser;
import pt.ipleiria.estg.dei.fastwheels.parsers.ProfileParser;
import pt.ipleiria.estg.dei.fastwheels.parsers.ReservationParser;
import pt.ipleiria.estg.dei.fastwheels.parsers.RegistoParser;
import pt.ipleiria.estg.dei.fastwheels.utils.Helpers;
import pt.ipleiria.estg.dei.fastwheels.utils.generateBase64;
import pt.ipleiria.estg.dei.fastwheels.parsers.VehicleParser;

public class SingletonFastWheels {

    private ArrayList<Vehicle> vehicles; // Lista de veículos
    private ArrayList<Reservation> reservations;

    private static SingletonFastWheels instance = null; // Instância única

    private VehicleDbHelper vehicleDbHelper = null; // Helper para banco de dados
    private ReservationDbHelper reservationDbHelper = null;
    private UserDbHelper userDbHelper = null; // Helper para banco de dados

    private User loggedUser = null;

    //volley
    private static RequestQueue volleyQueue;

    // listeners
    private LoginListener loginListener;
    private ProfileListener profileListener;
    private VehicleListener vehicleListener;
    private ReservationListener reservationListener;

    private FavoriteDbHelper favoriteDbHelper;
    private List<Favorite> favorites;

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
        reservations = new ArrayList<>();
        reservationDbHelper = new ReservationDbHelper(context);

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

    public void addUserDb(User user) {
        if (userDbHelper == null) {
            return;  // Se nao estiver inicializado sai
        }
        userDbHelper.addUserDb(user); // Adicionar à bd através do Helper
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
    public void addVehicleDb(Vehicle vehicle) {
        if (vehicleDbHelper == null) {
            return;  // Se nao estiver inicializado sai
        }
        vehicleDbHelper.addVehicleDb(vehicle); // Adicionar à bd através do Helper
    }

    public void editVehicleDb(Vehicle vehicle) {
        if (vehicleDbHelper == null) {
            return;
        }
        vehicleDbHelper.editVehicleDb(vehicle);
    }

    public void removeVehicleDb(int vehicleId) {
        if (vehicleDbHelper.removeVehicleDb(vehicleId)) {
            vehicles = vehicleDbHelper.getAllVehiclesDb(); // Atualiza a lista de veículos
        }
    }
    public void removeVehiclesDb() {
        vehicleDbHelper.clearAllVehicles();
    }

    // Carregar todos os veículos do banco de dados
    public ArrayList<Vehicle> getVehiclesDb() {
        vehicles = vehicleDbHelper.getAllVehiclesDb();
        return vehicles;
    }

    public void addVehiclesDb(ArrayList<Vehicle> vehiclesAPI) {
        if (vehicleDbHelper == null) return;

        vehicleDbHelper.clearAllVehicles();

        for (Vehicle v : vehicles) {
            vehicleDbHelper.addVehicleDb(v);
        }
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

    public boolean isVehicleFavorite(int vehicleId) {
        return favorites.stream().anyMatch(favorite -> favorite.getCarId() == vehicleId);
    }

    public void addFavorite(int clientId, int vehicleId) {
        Vehicle vehicle = getVehicleByIdBd(vehicleId);
        if (vehicle != null && favoriteDbHelper.addFavorite(clientId, vehicleId)) {
            Favorite favorite = new Favorite(0, clientId, vehicleId, new Date());
            favorites.add(favorite);
        }
    }

    public void removeFavorite(int clientId, int vehicleId) {
        if (favoriteDbHelper.removeFavorite(clientId, vehicleId)) {
            favorites.removeIf(favorite -> favorite.getClientId() == clientId && favorite.getCarId() == vehicleId);
        }
    }

    public ArrayList<Vehicle> getFavoriteVehiclesDb() {
        ArrayList<Vehicle> favoriteVehicles = new ArrayList<>();

        for (Favorite favorite : favorites) {
            Vehicle vehicle = getVehicleByIdBd(favorite.getCarId());
            if (vehicle != null) {
                favoriteVehicles.add(vehicle);
            }
        }

        return favoriteVehicles;
    }


    public ArrayList<Favorite> getFavorites() {
        return new ArrayList<>(favorites);
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
                                // Atualize os dados de favoritos diretamente na lista principal
                                vehicles.clear();
                                vehicles.addAll(favorites);

                                if (vehicleListener != null) vehicleListener.onRefreshVehicle();
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

    //region #REGISTER
    public void addUserAPI(User user, final Context context) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                Constants.API_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        User newUser = RegistoParser.parseRegistoData(response);
                        if (newUser != null) {
                            addUserDb(newUser);
                            setUser(newUser); //atualiza loggedUser
                        }
                    }
                }, new Response.ErrorListener() {
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user.getName());
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());

                return new JSONObject(params).toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        volleyQueue.add(request);
    }
    //endregion

    //region #LoginListener
    public void loginAPI(String username, String password, final Context context) {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.API_AUTH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loggedUser = LoginParser.parseLoginData(response);

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
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                generateBase64 base64Token = new generateBase64(loggedUser.getName(), loggedUser.getPassword());

                Log.e("SINGLETON", "profile: " + loggedUser.getName() + " - " + loggedUser.getPassword());
                Log.e("SINGLETON", "token: " + base64Token.getBase64Token());

                headers.put("Authorization", base64Token.getBase64Token());
                headers.put("Content-Type", "application/json; charset=UTF-8");
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
    public void addVehicleAPI(final Vehicle vehicleData, final Context context) {
        if (!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constants.API_VEHICLES + "/create",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Vehicle vehicle = VehicleParser.parseVehicleData(response);

                            if (vehicle != null) {
                                addVehicleDb(vehicle);

                                if (vehicleListener != null)
                                    vehicleListener.onRefreshVehicle();
                            }
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
                    params.put("clientId", String.valueOf(vehicleData.getClientId()));
                    params.put("carBrand", vehicleData.getCarBrand());
                    params.put("carModel", vehicleData.getCarModel());
                    params.put("carYear", String.valueOf(vehicleData.getCarYear()));
                    params.put("carDoors", String.valueOf(vehicleData.getCarDoors()));
                    params.put("status", String.valueOf(!vehicleData.isStatus() ? 0 : 1));
                    params.put("availableFrom", String.valueOf(vehicleData.getAvailableFrom()));
                    params.put("availableTo", String.valueOf(vehicleData.getAvailableTo()));
                    params.put("address", vehicleData.getAddress());
                    params.put("postalCode", vehicleData.getPostalCode());
                    params.put("city", vehicleData.getCity());
                    params.put("priceDay", String.valueOf(vehicleData.getPriceDay()));
                    params.put("createdAt", Helpers.getCurrentDateTime());

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

    public void getVehiclesAPI(final Context context) {
        if (!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();

            if (vehicleListener != null)
                vehicleListener.onRefreshVehicle();
        } else {
            JsonArrayRequest jsonRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    Constants.API_VEHICLES,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            removeVehiclesDb();

                            vehicles.clear();
                            vehicles = VehicleParser.parseVehiclesData(response);

                            //make a loop only if there is vehicles on API
                            if(!vehicles.isEmpty()) {
                                for(Vehicle veh: vehicles) {
                                    addVehicleDb(veh);
                                }
                                if (vehicleListener != null)
                                    vehicleListener.onRefreshVehicle();
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

    public void editVehicleAPI(final Vehicle vehicleData, final Context context) {
        if (!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    Constants.API_VEHICLES + "/update?id=" + vehicleData.getId(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("API_RESPONSE", response);
                            Vehicle vehicle = VehicleParser.parseVehicleData(response);
                            editVehicleDb(vehicle);
                            if (vehicleListener != null) vehicleListener.onRefreshVehicle();
                        }
                    }, new Response.ErrorListener() {
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
                    params.put("clientId", String.valueOf(vehicleData.getClientId()));
                    params.put("carBrand", vehicleData.getCarBrand());
                    params.put("carModel", vehicleData.getCarModel());
                    params.put("carYear", String.valueOf(vehicleData.getCarYear()));
                    params.put("carDoors", String.valueOf(vehicleData.getCarDoors()));
                    params.put("status", String.valueOf(!vehicleData.isStatus() ? 0 : 1));
                    params.put("availableFrom", String.valueOf(vehicleData.getAvailableFrom()));
                    params.put("availableTo", String.valueOf(vehicleData.getAvailableTo()));
                    params.put("address", vehicleData.getAddress());
                    params.put("postalCode", vehicleData.getPostalCode());
                    params.put("city", vehicleData.getCity());
                    params.put("priceDay", String.valueOf(vehicleData.getPriceDay()));
                    params.put("createdAt", Helpers.getCurrentDateTime());

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

    public void removeVehicleAPI(final int vehicleId, final Context context) {
        if (!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    Constants.API_VEHICLES + "/delete?id=" + vehicleId,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("API_RESPONSE", response);
                            removeVehicleDb(vehicleId);
                            if (vehicleListener != null) vehicleListener.onRefreshVehicle();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null) {
                        String responseData = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.e("API_ERROR", "Status Code: " + error.networkResponse.statusCode);
                        Log.e("API_ERROR", "Response Data: " + responseData);
                    }
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
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


    public void setVehicleListener(VehicleListener vehicleListener) {
        this.vehicleListener = vehicleListener;
    }

    //region #Reservation API

    public void addReservationDB(Reservation reservation) {
        if (reservationDbHelper == null) {
            return;  // Se nao estiver inicializado sai
        }

        reservationDbHelper.addReservationDB(reservation); // Adicionar à bd através do Helper
    }
    public void removeReservationsDb() {
        reservationDbHelper.deleteAllReservationDB();
    }

    public void removeReservationDb(int id) {
        if(reservationDbHelper.deleteReservationDB(id) != -1) {
            reservations = reservationDbHelper.getAllReservations();
        }
    }

    public ArrayList<Reservation> getReservationsDb() {
        if(reservationDbHelper == null) {
            return null;
        }

        reservations = reservationDbHelper.getAllReservations();
        return reservations;
    }

    public void addReservationAPI(final Reservation reservationData, final Context context) {
        if (!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    Constants.API_RESERVATION + "/create",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Reservation reservation = ReservationParser.parseReservationData(response);

                            System.out.println("--->API reservation loaded: " + reservation.toString());

                            if (reservation != null) {
                                addReservationDB(reservation);

                                if(reservationListener != null)
                                    reservationListener.onReservationUpdate();
                            }
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
                    params.put("clientId", String.valueOf(reservationData.getClientId()));
                    params.put("carId", String.valueOf(reservationData.getCarId()));
                    params.put("dateStart", String.valueOf(reservationData.getDateEnd()));
                    params.put("dateEnd",String.valueOf(reservationData.getDateEnd()));
                    params.put("createAt", Helpers.getCurrentDateTime());
                    params.put("filled", "0");
                    params.put("value", String.valueOf(reservationData.getValue()));
                    params.put("feeValue", String.valueOf(reservationData.getFeeValue()));
                    params.put("carValue", String.valueOf(reservationData.getCarValue()));

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


    public void getReservationAPI(final Context context) {
        if (!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();

            if (reservationListener != null)
                reservationListener.onReservationUpdate();
        } else {
            JsonArrayRequest jsonRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    Constants.API_RESERVATION + "/user/" + loggedUser.getId(),
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            reservations.clear();
                            reservations = ReservationParser.parseReservationsData(response);

                            if (!reservations.isEmpty()) {
                                removeReservationsDb();

                                for (Reservation res : reservations) {
                                    addReservationDB(res);
                                }

                                if (reservationListener != null)
                                    reservationListener.onReservationUpdate();
                            }
                        }
                    }, new Response.ErrorListener() {
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

            volleyQueue.add(jsonRequest);

        }
    }

    public void removeReservationAPI (final int resId, final Context context){
        if(!VehicleParser.isConnectionInternet(context)) {
            Toast.makeText(context, "No internet access", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    Constants.API_RESERVATION + "/delete/" + resId,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            removeReservationDb(resId);

                            if (reservationListener != null) reservationListener.onReservationUpdate();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null) {
                        String responseData = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.e("API_ERROR", "Status Code: " + error.networkResponse.statusCode);
                        Log.e("API_ERROR", "Response Data: " + responseData);
                    }
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
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

    public void setReservationListener(ReservationListener reservationListener){
        this.reservationListener = reservationListener;
    }

    // endregion
    //endregion
}