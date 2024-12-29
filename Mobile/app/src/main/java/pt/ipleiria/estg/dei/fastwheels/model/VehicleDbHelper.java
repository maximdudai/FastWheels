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
                PRICE_DAY + " INTEGER NOT NULL " +
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
        values.put(PRICE_DAY, vehicle.getPriceDay().toPlainString());

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


    //EM TESTE

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


    //EM TESTE
    // Remover uma foto
    public boolean removePhotoDb(int photoId) {
        return db.delete(TABLE_VEHICLE_PHOTOS, PHOTO_ID + " = ?", new String[]{String.valueOf(photoId)}) > 0;
    }

    // Obter todas as fotos de um veículo
    public List<VehiclePhoto> getAllPhotosByVehicleId(int vehicleId) {
        List<VehiclePhoto> photos = new ArrayList<>();

        Cursor cursor = db.query(TABLE_VEHICLE_PHOTOS, new String[]{PHOTO_ID, PHOTO_CAR_ID, PHOTO_URL},
                PHOTO_CAR_ID + " = ?", new String[]{String.valueOf(vehicleId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                photos.add(new VehiclePhoto(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return photos;
    }

    //endregion


    /*
    * **********************CODIGO ANTES UNIFORMIZAR**********************************

    private static final String DB_NAME = "dbVehicles";
    private static final int DB_VERSION = 2;

    private final SQLiteDatabase db;

    private static final String TABLE_NAME = "vehicles";
    private static final String ID = "id";
    private static final String BRAND = "brand";
    private static final String MODEL = "model";
    private static final String YEAR = "year";
    private static final String DOORS = "doors";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String AVAILABLE_FROM = "available_from";
    private static final String AVAILABLE_TO = "available_to";

    public VehicleDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createVehicleTable = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BRAND + " TEXT NOT NULL, " +
                MODEL + " TEXT NOT NULL, " +
                YEAR + " INTEGER NOT NULL, " +
                DOORS + " INTEGER NOT NULL, " +
                LATITUDE + " FLOAT, " +
                LONGITUDE + " FLOAT, " +
                AVAILABLE_FROM + " TEXT, " +
                AVAILABLE_TO + " TEXT);";

        db.execSQL(createVehicleTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public Vehicle addVehicleDb(Vehicle vehicle) {
        ContentValues values = new ContentValues();
        values.put(BRAND, vehicle.getBrand());
        values.put(MODEL, vehicle.getCarModel());
        values.put(YEAR, vehicle.getCarYear());
        values.put(DOORS, vehicle.getCarDoors());
        if (vehicle.getLocation() != null) {
            values.put(LATITUDE, vehicle.getLocation().getLatitude());
            values.put(LONGITUDE, vehicle.getLocation().getLongitude());
        }
        if (vehicle.getAvailableFrom() != null) {
            values.put(AVAILABLE_FROM, vehicle.getAvailableFrom().toString());
        }
        if (vehicle.getAvailableTo() != null) {
            values.put(AVAILABLE_TO, vehicle.getAvailableTo().toString());
        }

        long id = this.db.insert(TABLE_NAME, null, values);
        if (id > -1) {
            vehicle.setId((int) id);
            return vehicle;
        }
        return null;
    }

    public boolean editVehicleDb(Vehicle vehicle) {
        ContentValues values = new ContentValues();
        values.put(BRAND, vehicle.getBrand());
        values.put(MODEL, vehicle.getCarModel());
        values.put(YEAR, vehicle.getCarYear());
        values.put(DOORS, vehicle.getCarDoors());
        if (vehicle.getLocation() != null) {
            values.put(LATITUDE, vehicle.getLocation().getLatitude());
            values.put(LONGITUDE, vehicle.getLocation().getLongitude());
        }
        if (vehicle.getAvailableFrom() != null) {
            values.put(AVAILABLE_FROM, vehicle.getAvailableFrom().toString());
        }
        if (vehicle.getAvailableTo() != null) {
            values.put(AVAILABLE_TO, vehicle.getAvailableTo().toString());
        }

        return this.db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(vehicle.getId())}) > 0;
    }

    public boolean removeVehicleDb(int id) {
        return this.db.delete(TABLE_NAME, ID + " = ?", new String[]{String.valueOf(id)}) == 1;
    }

    public Vehicle getVehicleById(int id) {
        Cursor cursor = this.db.query(TABLE_NAME, null, ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
            vehicle.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(BRAND)));
            vehicle.setCarModel(cursor.getString(cursor.getColumnIndexOrThrow(MODEL)));
            vehicle.setCarYear(cursor.getInt(cursor.getColumnIndexOrThrow(YEAR)));
            vehicle.setCarDoors(cursor.getInt(cursor.getColumnIndexOrThrow(DOORS)));

            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LONGITUDE));
            if (latitude != 0 || longitude != 0) {
                android.location.Location location = new android.location.Location(android.location.LocationManager.GPS_PROVIDER);
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                vehicle.setLocation(location);
            }

            String availableFrom = cursor.getString(cursor.getColumnIndexOrThrow(AVAILABLE_FROM));
            String availableTo = cursor.getString(cursor.getColumnIndexOrThrow(AVAILABLE_TO));
            if (availableFrom != null) vehicle.setAvailableFrom(java.sql.Timestamp.valueOf(availableFrom));
            if (availableTo != null) vehicle.setAvailableTo(java.sql.Timestamp.valueOf(availableTo));

            cursor.close();
            return vehicle;
        }
        return null;
    }

    public ArrayList<Vehicle> getAllVehiclesDb() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                vehicle.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(BRAND)));
                vehicle.setCarModel(cursor.getString(cursor.getColumnIndexOrThrow(MODEL)));
                vehicle.setCarYear(cursor.getInt(cursor.getColumnIndexOrThrow(YEAR)));
                vehicle.setCarDoors(cursor.getInt(cursor.getColumnIndexOrThrow(DOORS)));

                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LONGITUDE));
                if (latitude != 0 || longitude != 0) {
                    android.location.Location location = new android.location.Location(android.location.LocationManager.GPS_PROVIDER);
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    vehicle.setLocation(location);
                }

                String availableFrom = cursor.getString(cursor.getColumnIndexOrThrow(AVAILABLE_FROM));
                String availableTo = cursor.getString(cursor.getColumnIndexOrThrow(AVAILABLE_TO));
                if (availableFrom != null) vehicle.setAvailableFrom(java.sql.Timestamp.valueOf(availableFrom));
                if (availableTo != null) vehicle.setAvailableTo(java.sql.Timestamp.valueOf(availableTo));

                vehicles.add(vehicle);
            } while (cursor.moveToNext());
        }

        return vehicles;
    }

    *****************************************************************************   */
}
