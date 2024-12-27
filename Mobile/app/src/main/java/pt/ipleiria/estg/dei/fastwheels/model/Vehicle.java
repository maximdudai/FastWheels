package pt.ipleiria.estg.dei.fastwheels.model;

import java.sql.Timestamp;

public class Vehicle {
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

    public void setLocation(String location) { this.location = location; }

    public String getPostalCode() { return postalCode; }

    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }
}
