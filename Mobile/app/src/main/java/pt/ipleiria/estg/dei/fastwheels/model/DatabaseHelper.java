package pt.ipleiria.estg.dei.fastwheels.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
public class DatabaseHelper extends SQLiteOpenHelper {

    // ========================
    // 1) FAVORITES TABLE
    // ========================
    private static final String TABLE_FAVORITES = "favorites";

    // Columns
    private static final String COL_FAVORITE_ID = "favorite_id";     // PK
    private static final String COL_FAV_USER_ID = "user_id";         // FK -> users.user_id
    private static final String COL_FAV_VEHICLE_ID = "vehicle_id";   // FK -> usercars.vehicle_id

    // ========================
    // 2) RESERVATIONS TABLE
    // ========================
    private static final String TABLE_RESERVATIONS = "reservations";

    // Columns
    private static final String COL_RES_ID = "res_id";           // PK
    private static final String COL_RES_USER_ID = "user_id";     // FK -> users.user_id
    private static final String COL_RES_VEHICLE_ID = "vehicle_id";
    private static final String COL_RES_DATE_START = "date_start";
    private static final String COL_RES_DATE_END = "date_end";
    private static final String COL_RES_CREATED_AT = "created_at";
    private static final String COL_RES_FILLED = "filled";
    private static final String COL_RES_VALUE = "value";
    private static final String COL_RES_FEE_VALUE = "fee_value";
    private static final String COL_RES_CAR_VALUE = "car_value";

    // ========================
    // 3) USERS TABLE
    // ========================
    private static final String TABLE_USERS = "users";

    // Columns
    private static final String COL_USER_ID = "user_id";   // PK
    private static final String COL_USER_TOKEN = "token";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PHONE = "phone";
    private static final String COL_USER_BALANCE = "balance";
    private static final String COL_USER_IBAN = "iban";

    // ========================
    // 4) VEHICLES TABLE
    // ========================
    private static final String TABLE_VEHICLES = "usercars";

    // Columns
    private static final String COL_VEHICLE_ID = "vehicle_id";   // PK
    private static final String COL_VEH_USER_ID = "user_id";     // FK -> users.user_id
    private static final String COL_CAR_BRAND = "carBrand";
    private static final String COL_CAR_MODEL = "carModel";
    private static final String COL_CAR_YEAR = "carYear";
    private static final String COL_CAR_DOORS = "carDoors";
    private static final String COL_CREATED_AT_VEH = "createdAt";
    private static final String COL_STATUS = "status";
    private static final String COL_AVAILABLE_FROM = "availableFrom";
    private static final String COL_AVAILABLE_TO = "availableTo";
    private static final String COL_ADDRESS = "address";
    private static final String COL_POSTAL_CODE = "postalCode";
    private static final String COL_CITY = "city";
    private static final String COL_PRICE_DAY = "priceDay";

    // ========================
    // 5) VEHICLE PHOTOS TABLE
    // ========================
    private static final String TABLE_VEHICLE_PHOTOS = "carphotos";

    // Columns
    private static final String COL_PHOTO_ID = "photo_id";        // PK
    private static final String COL_PHOTO_VEHICLE_ID = "vehicle_id";
    private static final String COL_PHOTO_URL = "photoUrl";


    // ========================
    // 5) NOTIFICATIONS TABLE
    // ========================

    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String NOT_ID = "not_id";        // PK
    private static final String NOT_CLIENT_ID = "not_client_id";
    private static final String NOT_READ = "not_read";
    private static final String NOT_CONTENT = "not_content";
    private static final String NOT_CREATED_TIME = "not_created_time";

    public DatabaseHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1) FAVORITES
        try {
            String sqlFavorites = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITES + " ("
                    + COL_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_FAV_USER_ID + " INTEGER NOT NULL, "
                    + COL_FAV_VEHICLE_ID + " INTEGER NOT NULL, "
                    + "UNIQUE (" + COL_FAV_USER_ID + ", " + COL_FAV_VEHICLE_ID + ") ON CONFLICT REPLACE"
                    + ");";
            db.execSQL(sqlFavorites);
            Log.d("DATABASE", "Table favorites created successfully");
        } catch (Exception e) {
            Log.e("DATABASE", "Error creating favorites table: " + e.getMessage());
        }

        // 2) RESERVATIONS
        try {
            String sqlReservations = "CREATE TABLE IF NOT EXISTS " + TABLE_RESERVATIONS + " ("
                    + COL_RES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_RES_USER_ID + " INTEGER, "
                    + COL_RES_VEHICLE_ID + " INTEGER, "
                    + COL_RES_DATE_START + " TEXT, "
                    + COL_RES_DATE_END + " TEXT, "
                    + COL_RES_CREATED_AT + " TEXT, "
                    + COL_RES_FILLED + " INTEGER, "
                    + COL_RES_VALUE + " REAL, "
                    + COL_RES_FEE_VALUE + " REAL, "
                    + COL_RES_CAR_VALUE + " REAL"
                    + ");";
            db.execSQL(sqlReservations);
            Log.d("DATABASE", "Table reservations created successfully");
        } catch (Exception e) {
            Log.e("DATABASE", "Error creating reservations table: " + e.getMessage());
        }

        // 3) USERS
        try {
            String sqlUsers = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " ("
                    + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_USER_TOKEN + " TEXT NOT NULL, "
                    + COL_USER_NAME + " TEXT NOT NULL, "
                    + COL_USER_EMAIL + " TEXT NOT NULL, "
                    + COL_USER_PHONE + " TEXT NOT NULL, "
                    + COL_USER_BALANCE + " TEXT, "
                    + COL_USER_IBAN + " TEXT"
                    + ");";
            db.execSQL(sqlUsers);
            Log.d("DATABASE", "Table users created successfully");
        } catch (Exception e) {
            Log.e("DATABASE", "Error creating users table: " + e.getMessage());
        }

        // 4) VEHICLES (usercars)
        try {
            String sqlVehicles = "CREATE TABLE IF NOT EXISTS " + TABLE_VEHICLES + " ("
                    + COL_VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_VEH_USER_ID + " INTEGER, "
                    + COL_CAR_BRAND + " TEXT NOT NULL, "
                    + COL_CAR_MODEL + " TEXT NOT NULL, "
                    + COL_CAR_YEAR + " INTEGER NOT NULL, "
                    + COL_CAR_DOORS + " INTEGER NOT NULL, "
                    + COL_CREATED_AT_VEH + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + COL_STATUS + " INTEGER DEFAULT 0, "
                    + COL_AVAILABLE_FROM + " TIMESTAMP NOT NULL, "
                    + COL_AVAILABLE_TO + " TIMESTAMP NOT NULL, "
                    + COL_ADDRESS + " TEXT NOT NULL, "
                    + COL_POSTAL_CODE + " TEXT NOT NULL, "
                    + COL_CITY + " TEXT NOT NULL, "
                    + COL_PRICE_DAY + " REAL NOT NULL, "
                    + "FOREIGN KEY(" + COL_VEH_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + ") ON DELETE CASCADE"
                    + ");";
            db.execSQL(sqlVehicles);
            Log.d("DATABASE", "Table usercars created successfully");
        } catch (Exception e) {
            Log.e("DATABASE", "Error creating vehicles table: " + e.getMessage());
        }

        // 5) VEHICLE PHOTOS
        try {
            String sqlPhotos = "CREATE TABLE IF NOT EXISTS " + TABLE_VEHICLE_PHOTOS + " ("
                    + COL_PHOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_PHOTO_VEHICLE_ID + " INTEGER NOT NULL, "
                    + COL_PHOTO_URL + " TEXT NOT NULL, "
                    + "FOREIGN KEY(" + COL_PHOTO_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLES + "(" + COL_VEHICLE_ID + ") ON DELETE CASCADE"
                    + ");";
            db.execSQL(sqlPhotos);
            Log.d("DATABASE", "Table carphotos created successfully");
        } catch (Exception e) {
            Log.e("DATABASE", "Error creating carphotos table: " + e.getMessage());
        }
        // 6) NOTIFICATIONS TABLE

        try {
            String sqlUsers = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATIONS + " ("
                    + NOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NOT_CLIENT_ID + " TEXT NOT NULL, "
                    + NOT_READ + " TEXT NOT NULL, "
                    + NOT_CONTENT + " TEXT NOT NULL, "
                    + NOT_CREATED_TIME + " TEXT NOT NULL"
                    + ");";
            db.execSQL(sqlUsers);
            Log.d("DATABASE", "Table notifications created successfully");
        } catch (Exception e) {
            Log.e("DATABASE", "Error creating notifications table: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLE_PHOTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);

        // Recreate
        onCreate(db);
    }

    //=========================
    // FAVORITES CRUD
    //=========================
    public long addFavorite(int userId, int vehicleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FAV_USER_ID, userId);
        values.put(COL_FAV_VEHICLE_ID, vehicleId);
        return db.insert(TABLE_FAVORITES, null, values);
    }

    public boolean removeFavorite(int userId, int vehicleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_FAVORITES,
                COL_FAV_USER_ID + " = ? AND " + COL_FAV_VEHICLE_ID + " = ?",
                new String[]{String.valueOf(userId), String.valueOf(vehicleId)});
        return rows > 0;
    }

    public List<Favorite> getFavorites(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Favorite> favorites = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE_FAVORITES,
                new String[]{COL_FAVORITE_ID, COL_FAV_USER_ID, COL_FAV_VEHICLE_ID},
                COL_FAV_USER_ID + " = ?",
                new String[]{String.valueOf(userId)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                // Example Favorite constructor
                Favorite fav = new Favorite(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_FAVORITE_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_FAV_USER_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_FAV_VEHICLE_ID)),
                        new Date() // or whatever else
                );
                favorites.add(fav);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favorites;
    }

    //=========================
    // RESERVATIONS CRUD
    //=========================
    public void addReservationDB(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RES_USER_ID, reservation.getClientId());    // or reservation.getUserId()
        values.put(COL_RES_VEHICLE_ID, reservation.getCarId());    // rename accordingly
        values.put(COL_RES_DATE_START, reservation.getDateStart().toString());
        values.put(COL_RES_DATE_END, reservation.getDateEnd().toString());
        values.put(COL_RES_CREATED_AT, reservation.getCreateAt().toString());
        values.put(COL_RES_FILLED, reservation.getFilled());
        values.put(COL_RES_VALUE, reservation.getValue());
        values.put(COL_RES_FEE_VALUE, reservation.getFeeValue());
        values.put(COL_RES_CAR_VALUE, reservation.getCarValue());
        db.insert(TABLE_RESERVATIONS, null, values);
    }

    public ArrayList<Reservation> getAllReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESERVATIONS, null, null, null,
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_RES_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_RES_USER_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_RES_VEHICLE_ID)),
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COL_RES_DATE_START))),
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COL_RES_DATE_END))),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_RES_FILLED)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_RES_VALUE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_RES_FEE_VALUE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_RES_CAR_VALUE))
                );
                reservations.add(reservation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reservations;
    }

    public int updateReservationDB(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RES_USER_ID, reservation.getClientId());
        values.put(COL_RES_VEHICLE_ID, reservation.getCarId());
        values.put(COL_RES_DATE_START, reservation.getDateStart().toString());
        values.put(COL_RES_DATE_END, reservation.getDateEnd().toString());
        values.put(COL_RES_CREATED_AT, reservation.getCreateAt().toString());
        values.put(COL_RES_FILLED, reservation.getFilled());
        values.put(COL_RES_VALUE, reservation.getValue());
        values.put(COL_RES_FEE_VALUE, reservation.getFeeValue());
        values.put(COL_RES_CAR_VALUE, reservation.getCarValue());

        return db.update(TABLE_RESERVATIONS,
                values,
                COL_RES_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});
    }

    public int deleteReservationDB(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_RESERVATIONS,
                COL_RES_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteAllReservationDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESERVATIONS, null, null);
    }

    //=========================
    // USERS CRUD
    //=========================
    public User addUserDb(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // user.getId() might be for a server ID; if you want to force local PK, omit or rename
        values.put(COL_USER_ID, user.getId());
        values.put(COL_USER_TOKEN, user.getToken());
        values.put(COL_USER_NAME, user.getName());
        values.put(COL_USER_EMAIL, user.getEmail());
        values.put(COL_USER_PHONE, user.getPhone());
        values.put(COL_USER_BALANCE, user.getBalance());
        values.put(COL_USER_IBAN, user.getIban());

        long rowId = db.insert(TABLE_USERS, null, values);
        if (rowId != -1) {
            // Set the newly inserted row ID if needed
            user.setId((int) rowId);
            return user;
        }
        return null;
    }

    public boolean removeUserDb(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_USERS,
                COL_USER_ID + " = ?",
                new String[]{String.valueOf(userId)});
        return rows == 1;
    }

    //=========================
    // VEHICLES CRUD
    //=========================
    public void addVehicleDb(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_VEHICLE_ID, vehicle.getId()); // If you want local auto-inc, you might skip setting an ID
        values.put(COL_VEH_USER_ID, vehicle.getClientId());
        values.put(COL_CAR_BRAND, vehicle.getCarBrand());
        values.put(COL_CAR_MODEL, vehicle.getCarModel());
        values.put(COL_CAR_YEAR, vehicle.getCarYear());
        values.put(COL_CAR_DOORS, vehicle.getCarDoors());
        values.put(COL_STATUS, vehicle.isStatus() ? 1 : 0);
        values.put(COL_AVAILABLE_FROM, vehicle.getAvailableFrom().toString());
        values.put(COL_AVAILABLE_TO, vehicle.getAvailableTo().toString());
        values.put(COL_ADDRESS, vehicle.getAddress());
        values.put(COL_POSTAL_CODE, vehicle.getPostalCode());
        values.put(COL_CITY, vehicle.getCity());
        values.put(COL_PRICE_DAY, vehicle.getPriceDay().toPlainString());

        db.insert(TABLE_VEHICLES, null, values);

        // Insert photos (if any)
        if (vehicle.getVehiclePhotos() != null) {
            for (VehiclePhoto photo : vehicle.getVehiclePhotos()) {
                addPhotoDb(photo);
            }
        }
    }

    public boolean editVehicleDb(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_VEH_USER_ID, vehicle.getClientId());
        values.put(COL_CAR_BRAND, vehicle.getCarBrand());
        values.put(COL_CAR_MODEL, vehicle.getCarModel());
        values.put(COL_CAR_YEAR, vehicle.getCarYear());
        values.put(COL_CAR_DOORS, vehicle.getCarDoors());
        values.put(COL_STATUS, vehicle.isStatus() ? 1 : 0);
        values.put(COL_AVAILABLE_FROM, vehicle.getAvailableFrom().toString());
        values.put(COL_AVAILABLE_TO, vehicle.getAvailableTo().toString());
        values.put(COL_ADDRESS, vehicle.getAddress());
        values.put(COL_POSTAL_CODE, vehicle.getPostalCode());
        values.put(COL_CITY, vehicle.getCity());
        values.put(COL_PRICE_DAY, vehicle.getPriceDay().toPlainString());

        return db.update(TABLE_VEHICLES,
                values,
                COL_VEHICLE_ID + " = ?",
                new String[]{String.valueOf(vehicle.getId())}) > 0;
    }

    public boolean removeVehicleDb(int vehicleId) {
        // Remove all associated photos first
        removeAllPhotosByVehicleIdDB(vehicleId);

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_VEHICLES,
                COL_VEHICLE_ID + " = ?",
                new String[]{String.valueOf(vehicleId)}) > 0;
    }

    public ArrayList<Vehicle> getAllVehiclesDb() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        Cursor cursor = db.query(TABLE_VEHICLES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Vehicle v = new Vehicle(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEHICLE_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEH_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CAR_BRAND)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CAR_MODEL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAR_YEAR)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAR_DOORS)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_STATUS)) == 1,
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COL_AVAILABLE_FROM))),
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COL_AVAILABLE_TO))),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_ADDRESS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_POSTAL_CODE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_CITY)),
                        new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRICE_DAY))),
                        getAllPhotosByVehicleId(
                                cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEHICLE_ID)))
                );
                vehicles.add(v);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return vehicles;
    }

    public void clearAllVehicles() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VEHICLES, null, null);
    }

    //=========================
    // PHOTOS CRUD
    //=========================
    public VehiclePhoto addPhotoDb(VehiclePhoto photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PHOTO_VEHICLE_ID, photo.getCarId());
        values.put(COL_PHOTO_URL, photo.getPhotoUrl());

        long id = db.insert(TABLE_VEHICLE_PHOTOS, null, values);
        if (id > -1) {
            return new VehiclePhoto((int) id, photo.getCarId(), photo.getPhotoUrl());
        } else {
            Log.d("DATABASE", "Failed to save photo for vehicle ID: " + photo.getCarId());
            return null;
        }
    }

    public boolean removeAllPhotosByVehicleIdDB(int vehicleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_VEHICLE_PHOTOS,
                COL_PHOTO_VEHICLE_ID + " = ?",
                new String[]{String.valueOf(vehicleId)}) > 0;
    }

    public List<VehiclePhoto> getAllPhotosByVehicleId(int vehicleId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<VehiclePhoto> photos = new ArrayList<>();

        Cursor cursor = db.query(TABLE_VEHICLE_PHOTOS,
                new String[]{COL_PHOTO_ID, COL_PHOTO_VEHICLE_ID, COL_PHOTO_URL},
                COL_PHOTO_VEHICLE_ID + " = ?",
                new String[]{String.valueOf(vehicleId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int pid = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PHOTO_ID));
                int carId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PHOTO_VEHICLE_ID));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(COL_PHOTO_URL));
                photos.add(new VehiclePhoto(pid, carId, url));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return photos;
    }

    //=========================
    // NOTIFICATIONS CRUD
    //=========================

    public Notification addNotificationDB(Notification not) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // user.getId() might be for a server ID; if you want to force local PK, omit or rename
        values.put(NOT_ID, not.getId());
        values.put(NOT_CLIENT_ID, not.getClientId());
        values.put(NOT_CONTENT, not.getContent());
        values.put(NOT_CREATED_TIME, not.getCreatedAt().toString());
        values.put(NOT_READ, not.getRead());


        long rowId = db.insert(TABLE_NOTIFICATIONS, null, values);
        if (rowId != -1) {
            // Set the newly inserted row ID if needed
            not.setId((int) rowId);
            return not;
        }
        return null;
    }

    public void editNotificationDB(Notification not) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOT_ID, not.getId());
        values.put(NOT_CLIENT_ID, not.getClientId());
        values.put(NOT_CONTENT, not.getContent());
        values.put(NOT_CREATED_TIME, not.getCreatedAt().toString());
        values.put(NOT_READ, not.getRead());

        db.update(TABLE_NOTIFICATIONS,
                values,
                NOT_ID + " = ?",
                new String[]{String.valueOf(not.getId())});
    }

    public void clearNotificationsBD() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTIFICATIONS, null, null);
    }

}
