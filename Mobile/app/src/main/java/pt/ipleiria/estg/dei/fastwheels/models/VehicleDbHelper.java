/*
package pt.ipleiria.estg.dei.fastwheels.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class VehicleDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fastwheels";
    private static final int DB_VERSION = 1;

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
    private static final String PRICEDAY = "priceDay";

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
                PRICEDAY + " INTEGER NOT NULL " +
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

    //region MÉTODOS GERIR Vehicle
    public Vehicle addVehicleDb(Vehicle vehicle) {
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
        values.put(PRICEDAY, vehicle.getPriceDay().toPlainString());

        long id = db.insert(TABLE_VEHICLES, null, values); //long: verificar operação for bem-sucedida
        if (id > -1) { //db.insert retorna -1 quando falha
            // Criar um novo objeto Vehicle com o ID gerado
            Vehicle newVehicle = new Vehicle(
                    (int) id, // ID gerado pelo banco
                    vehicle.getClientId(),
                    vehicle.getCarBrand(),
                    vehicle.getCarModel(),
                    vehicle.getCarYear(),
                    vehicle.getCarDoors(),
                    vehicle.getCreatedAt(),
                    vehicle.isStatus(),
                    vehicle.getAvailableFrom(),
                    vehicle.getAvailableTo(),
                    vehicle.getAddress(),
                    vehicle.getPostalCode(),
                    vehicle.getCity(),
                    vehicle.getPriceDay(),
                    vehicle.getVehiclePhotos()
            );

            // Adicionar fotos associadas
            if (vehicle.getVehiclePhotos() != null) {
                for (VehiclePhoto photo : vehicle.getVehiclePhotos()) {
                    VehiclePhoto newPhoto = new VehiclePhoto(0, (int) id, photo.getPhotoUrl());
                    addPhotoDb(newPhoto);
                }
            }

            return newVehicle;
        }
        return null; // Retorna null em caso de falha
    }
    //endregion


    //region MÉTODOS GERIR VehiclePhoto
    // Adicionar uma nova foto
    public VehiclePhoto addPhotoDb(VehiclePhoto photo) {
        ContentValues values = new ContentValues();
        values.put(PHOTO_CAR_ID, photo.getCarId());
        values.put(PHOTO_URL, photo.getPhotoUrl());

        long id = db.insert(TABLE_VEHICLE_PHOTOS, null, values);
        if (id > -1) {
            return new VehiclePhoto((int) id, photo.getCarId(), photo.getPhotoUrl());
        }
        return null;
    }
    //endregion
}
*/