package pt.ipleiria.estg.dei.fastwheels.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VehicleDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbVehicles";
    private static final int DB_VERSION = 1;

    private final SQLiteDatabase db;

    private static final String TABLE_NAME = "vehicles";
    private static final String ID = "id";
    private static final String MARK = "mark";
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
                MARK + " TEXT NOT NULL, " +
                MODEL + " TEXT NOT NULL, " +
                YEAR + " INTEGER NOT NULL, " +
                DOORS + " INTEGER NOT NULL, " +
                LATITUDE + " REAL, " +
                LONGITUDE + " REAL, " +
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
        values.put(MARK, vehicle.getMark());
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
        values.put(MARK, vehicle.getMark());
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
            vehicle.setMark(cursor.getString(cursor.getColumnIndexOrThrow(MARK)));
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
                vehicle.setMark(cursor.getString(cursor.getColumnIndexOrThrow(MARK)));
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
}
