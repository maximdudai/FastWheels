package pt.ipleiria.estg.dei.fastwheels.model;

import android.location.Location;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import pt.ipleiria.estg.dei.fastwheels.model.VehiclePhoto;

//model for table UserCar
public class Vehicle {

    private final int id;
    private final int clientId;
    private String carBrand;
    private String carModel;
    private int carYear;
    private int carDoors;
    private Timestamp createdAt;
    private boolean status; // Estado Ativo ou Desativo
    private Timestamp availableFrom;
    private Timestamp availableTo;
    private String address;
    private String postalCode;
    private String city;
    private BigDecimal priceDay;
    private List<VehiclePhoto> vehiclePhotos;


    public Vehicle(int id, int clientId, String carBrand, String carModel, int carYear, int carDoors,
                   boolean status, Timestamp availableFrom, Timestamp availableTo,
                   String address, String postalCode, String city, BigDecimal priceDay, List<VehiclePhoto> vehiclePhotos) {
        this.id = id;
        this.clientId = clientId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carYear = carYear;
        this.carDoors = carDoors;
        this.status = status;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.priceDay = priceDay;
        this.vehiclePhotos = vehiclePhotos;
    }


    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public int getCarDoors() {
        return carDoors;
    }

    public void setCarDoors(int carDoors) {
        this.carDoors = carDoors;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Timestamp availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Timestamp getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Timestamp availableTo) {
        this.availableTo = availableTo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(BigDecimal priceDay) {
        this.priceDay = priceDay;
    }

    public List<VehiclePhoto> getVehiclePhotos() {
        return vehiclePhotos;
    }

    public void setVehiclePhotos(List<VehiclePhoto> vehiclePhotos) {
        this.vehiclePhotos = vehiclePhotos;
    }

    /*
    * **********************CODIGO ANTES UNIFORMIZAR**********************************

    private int id;                  // ID do veículo
    private int clientId;            // ID do cliente associado
    private String brand;             // Marca do carro
    private String Model;         // Modelo do carro
    private int Year;             // Ano do carro
    private int Doors;            // Número de portas
    private Timestamp createdAt;     // Data de criação
    private boolean status;          // Status (disponível ou não)
    private Timestamp availableFrom; // Data de início da disponibilidade
    private Timestamp availableTo;   // Data de término da disponibilidade
    private String location;        // Residência
    private String postalCode;       // Código postal
    private String city;             // Cidade

    public Vehicle() {
    }

    // Construtor
    public Vehicle(int id, int clientId, String brand, String carModel, int carYear, int carDoors,
                   Timestamp createdAt, boolean status, Timestamp availableFrom, Timestamp availableTo,
                   String location, String postalCode, String city) { // Adicionado o parâmetro location
        this.id = id;
        this.clientId = clientId;
        this.brand = brand;
        this.Model = carModel;
        this.Year = carYear;
        this.Doors = carDoors;
        this.createdAt = createdAt;
        this.status = status;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.location = location;
        this.postalCode = postalCode;
        this.city = city;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCarModel() {
        return Model;
    }

    public void setCarModel(String carModel) {
        this.Model = carModel;
    }

    public int getCarYear() {
        return Year;
    }

    public void setCarYear(int carYear) {
        this.Year = carYear;
    }

    public int getCarDoors() {
        return Doors;
    }

    public void setCarDoors(int carDoors) {
        this.Doors = carDoors;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Timestamp availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Timestamp getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Timestamp availableTo) {
        this.availableTo = availableTo;
    }

    public String getLocation() { return location; }

    public void setLocation(Location location) { // Setter para localização
        this.location = location;
    }
    //*****************************************************************************   */
}
