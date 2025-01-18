package pt.ipleiria.estg.dei.fastwheels.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class VehicleDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fastwheels";
    private static final int DB_VERSION = 5;

    private final SQLiteDatabase db;

    // Tabela usercars
    private static final String TABLE_VEHICLES = "usercars";
    private static final String ID = "id";
    private static final String CLIENT_ID = "clientId";
    private static final String CAR_BRAND = "carBrand";
    private static final String CAR_MODEL = "carModel";
    private static final String CAR_YEAR = "carYear";
    private static final String CAR_DOORS = "carDoors";
    private static final String CREATED_AT = "createdAt";
    private static final String STATUS = "status";
    private static final String AVAILABLE_FROM = "availableFrom";
    private static final String AVAILABLE_TO = "availableTo";
    private static final String ADDRESS = "address";
    private static final String POSTAL_CODE = "postalCode";
    private static final String CITY = "city";
    private static final String PRICE_DAY = "priceDay";

    // Tabela carphotos
    private static final String TABLE_VEHICLE_PHOTOS = "carphotos";
    private static final String PHOTO_ID = "id";
    private static final String PHOTO_CAR_ID = "carId";
    private static final String PHOTO_URL = "photoUrl";

    public VehicleDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createVehicleTable = "CREATE TABLE " + TABLE_VEHICLES + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CLIENT_ID + " INTEGER, " +
                CAR_BRAND + " TEXT NOT NULL, " +
                CAR_MODEL + " TEXT NOT NULL, " +
                CAR_YEAR + " INTEGER NOT NULL, " +
                CAR_DOORS + " INTEGER NOT NULL, " +
                CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                STATUS + " INTEGER DEFAULT 0, " +
                AVAILABLE_FROM + " TIMESTAMP NOT NULL, " +
                AVAILABLE_TO + " TIMESTAMP NOT NULL, " +
                ADDRESS + " TEXT NOT NULL, " +
                POSTAL_CODE + " TEXT NOT NULL, " +
                CITY + " TEXT NOT NULL," +
                PRICE_DAY + " REAL NOT NULL " +
                ");";

        String createPhotoTable = "CREATE TABLE " + TABLE_VEHICLE_PHOTOS + " (" +
                PHOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PHOTO_CAR_ID + " INTEGER NOT NULL, " +
                PHOTO_URL + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + PHOTO_CAR_ID + ") REFERENCES " + TABLE_VEHICLES + "(" + ID + ") ON DELETE CASCADE" +
                ");";

        db.execSQL(createVehicleTable);
        db.execSQL(createPhotoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLE_PHOTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLES);
        onCreate(db);
    }

    //region METODOS GERIR VEHICLE
    public void addVehicleDb(Vehicle vehicle) {

        ContentValues values = new ContentValues();
        values.put(CLIENT_ID, vehicle.getClientId());
        values.put(CAR_BRAND, vehicle.getCarBrand());
        values.put(CAR_MODEL, vehicle.getCarModel());
        values.put(CAR_YEAR, vehicle.getCarYear());
        values.put(CAR_DOORS, vehicle.getCarDoors());
        values.put(STATUS, vehicle.isStatus() ? 1 : 0);
        values.put(AVAILABLE_FROM, vehicle.getAvailableFrom().toString());
        values.put(AVAILABLE_TO, vehicle.getAvailableTo().toString());
        values.put(ADDRESS, vehicle.getAddress());
        values.put(POSTAL_CODE, vehicle.getPostalCode());
        values.put(CITY, vehicle.getCity());
        values.put(PRICE_DAY, vehicle.getPriceDay().toPlainString());

        db.insert(TABLE_VEHICLES, null, values); //long: verificar operação for bem-sucedida

        Vehicle newVehicle = new Vehicle(
                vehicle.getId(),
                vehicle.getClientId(),
                vehicle.getCarBrand(),
                vehicle.getCarModel(),
                vehicle.getCarYear(),
                vehicle.getCarDoors(),
                vehicle.isStatus(),
                vehicle.getAvailableFrom(),
                vehicle.getAvailableTo(),
                vehicle.getAddress(),
                vehicle.getPostalCode(),
                vehicle.getCity(),
                vehicle.getPriceDay(),
                vehicle.getVehiclePhotos());

        if (vehicle.getVehiclePhotos() != null) {
            for (VehiclePhoto photo : vehicle.getVehiclePhotos()) {
                addPhotoDb(photo);
            }
        }
        System.out.println("--->API addVehicleDb - sucesso: " + newVehicle.toString());
    }

    public boolean editVehicleDb(Vehicle vehicle) {
        ContentValues values = new ContentValues();
        values.put(CLIENT_ID, vehicle.getClientId());
        values.put(CAR_BRAND, vehicle.getCarBrand());
        values.put(CAR_MODEL, vehicle.getCarModel());
        values.put(CAR_YEAR, vehicle.getCarYear());
        values.put(CAR_DOORS, vehicle.getCarDoors());
        values.put(STATUS, vehicle.isStatus() ? 1 : 0);
        values.put(AVAILABLE_FROM, vehicle.getAvailableFrom().toString());
        values.put(AVAILABLE_TO, vehicle.getAvailableTo().toString());
        values.put(ADDRESS, vehicle.getAddress());
        values.put(POSTAL_CODE, vehicle.getPostalCode());
        values.put(CITY, vehicle.getCity());
        values.put(PRICE_DAY, vehicle.getPriceDay().toPlainString());

        return db.update(TABLE_VEHICLES, values, ID + " = ?", new String[]{String.valueOf(vehicle.getId())}) > 0;
    }

    public boolean removeVehicleDb(int id) {
        removeAllPhotosByVehicleIdDB(id);

        return db.delete(TABLE_VEHICLES, ID + " = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public Vehicle getVehicleById(int id) {
        Cursor cursor = db.query(TABLE_VEHICLES, null, ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Vehicle vehicle = new Vehicle(
                    cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(CLIENT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CAR_BRAND)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CAR_MODEL)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(CAR_YEAR)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(CAR_DOORS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(STATUS)) == 1,
                    Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(AVAILABLE_FROM))),
                    Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(AVAILABLE_TO))),
                    cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(POSTAL_CODE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CITY)),
                    new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(PRICE_DAY))),
                    getAllPhotosByVehicleId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)))
            );
            cursor.close();
            return vehicle;
        }
        return null;
    }

    public ArrayList<Vehicle> getAllVehiclesDb() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        Cursor cursor = db.query(TABLE_VEHICLES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Vehicle vehicle = new Vehicle(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CLIENT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CAR_BRAND)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CAR_MODEL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CAR_YEAR)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(CAR_DOORS)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(STATUS)) == 1,
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(AVAILABLE_FROM))),
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(AVAILABLE_TO))),
                        cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(POSTAL_CODE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CITY)),
                        new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(PRICE_DAY))),
                        getAllPhotosByVehicleId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)))
                );
                vehicles.add(vehicle);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return vehicles;
    }

    public void clearAllVehicles() {
        System.out.println("---API removed from local db vehicles");
        db.delete(TABLE_VEHICLES, null, null);
    }

    //endregion

    //region METODOS GERIR VEHICLEPHOTO
    // Adicionar uma nova foto
    public VehiclePhoto addPhotoDb(VehiclePhoto photo) {
        ContentValues values = new ContentValues();
        values.put(PHOTO_CAR_ID, photo.getCarId());
        values.put(PHOTO_URL, photo.getPhotoUrl());

        long id = db.insert(TABLE_VEHICLE_PHOTOS, null, values);
        if (id > -1) {
            return new VehiclePhoto((int) id, photo.getCarId(), photo.getPhotoUrl());
        } else {
            System.out.println("-->TAG Failed to save photo for vehicle ID: " + photo.getCarId());
            return null;
        }
    }

    // Remover todas fotos de um veiculo
    public boolean removeAllPhotosByVehicleIdDB(int vehicleId) {
        return db.delete(TABLE_VEHICLE_PHOTOS, PHOTO_CAR_ID + " = ?", new String[]{String.valueOf(vehicleId)}) > 0;
    }

    // Obter todas as fotos de um veículo
    public List<VehiclePhoto> getAllPhotosByVehicleId(int vehicleId) {
        List<VehiclePhoto> photos = new ArrayList<>();

        Cursor cursor = db.query(TABLE_VEHICLE_PHOTOS, new String[]{PHOTO_ID, PHOTO_CAR_ID, PHOTO_URL},
                PHOTO_CAR_ID + " = ?", new String[]{String.valueOf(vehicleId)}, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(PHOTO_ID));
                int carId = cursor.getInt(cursor.getColumnIndexOrThrow(PHOTO_CAR_ID));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(PHOTO_URL));


                photos.add(new VehiclePhoto(id, carId, url));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return photos;
    }
    //endregion
}