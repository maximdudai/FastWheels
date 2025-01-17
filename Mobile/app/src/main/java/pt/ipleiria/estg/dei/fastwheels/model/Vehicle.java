package pt.ipleiria.estg.dei.fastwheels.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

//model for table UserCar
public class Vehicle {

    private int id;
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


    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carYear=" + carYear +
                ", carDoors=" + carDoors +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", availableFrom=" + availableFrom +
                ", availableTo=" + availableTo +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", priceDay=" + priceDay +
                ", vehiclePhotos=" + vehiclePhotos +
                '}';
    }
}