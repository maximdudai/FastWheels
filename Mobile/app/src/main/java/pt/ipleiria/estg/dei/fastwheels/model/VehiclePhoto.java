package pt.ipleiria.estg.dei.fastwheels.model;

//model for table CarPhoto
public class VehiclePhoto {

    private final int id;
    private final int carId;
    private String photoUrl;

    public VehiclePhoto(int id, int carId, String photoUrl) {
        this.id = id;
        this.carId = carId;
        this.photoUrl = photoUrl;
    }

    public int getId() {
        return id;
    }

    public int getCarId() {
        return carId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
