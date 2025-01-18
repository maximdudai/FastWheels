package pt.ipleiria.estg.dei.fastwheels.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ReservationDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fastwheels";
    private static final int DATABASE_VERSION = 11;

    private final SQLiteDatabase db;

    // Table and columns
    private static final String TABLE_RESERVATIONS = "reservations";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CLIENT_ID = "client_id";
    private static final String COLUMN_CAR_ID = "car_id";
    private static final String COLUMN_DATE_START = "date_start";
    private static final String COLUMN_DATE_END = "date_end";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_FILLED = "filled";
    private static final String COLUMN_VALUE = "value";
    private static final String COLUMN_FEE_VALUE = "fee_value";
    private static final String COLUMN_CAR_VALUE = "car_value";


    public ReservationDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DATABASE:RESERVATION", "onCreate called");
        try {
            String createTable = "CREATE TABLE IF NOT EXISTS reservations (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "client_id INTEGER, " +
                    "car_id INTEGER, " +
                    "date_start TEXT, " +
                    "date_end TEXT, " +
                    "created_at TEXT, " +
                    "filled INTEGER, " +
                    "value REAL, " +
                    "fee_value REAL, " +
                    "car_value REAL)";
            db.execSQL(createTable);
            Log.d("DATABASE:RESERVATION", "Table created successfully");
        } catch (Exception e) {
            Log.e("DATABASE:ERROR", "Error creating table: " + e.getMessage());
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        onCreate(db);
    }

    // Insert a new reservation
    public void addReservationDB(Reservation reservation) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_ID, reservation.getClientId());
        values.put(COLUMN_CAR_ID, reservation.getCarId());
        values.put(COLUMN_DATE_START, reservation.getDateStart().toString());
        values.put(COLUMN_DATE_END, reservation.getDateEnd().toString());
        values.put(COLUMN_CREATED_AT, reservation.getCreateAt().toString());
        values.put(COLUMN_FILLED, reservation.getFilled());
        values.put(COLUMN_VALUE, reservation.getValue());
        values.put(COLUMN_FEE_VALUE, reservation.getFeeValue());
        values.put(COLUMN_CAR_VALUE, reservation.getCarValue());

        db.insert(TABLE_RESERVATIONS, null, values);

        new Reservation(reservation.getId(), reservation.getClientId(), reservation.getCarId(), reservation.getDateStart(), reservation.getDateEnd(), 0, reservation.getValue(), reservation.getFeeValue(), reservation.getCarValue());
    }

    // Get all reservations
    public ArrayList<Reservation> getAllReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RESERVATIONS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CAR_ID)),
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_START))),
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_END))),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FILLED)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VALUE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_FEE_VALUE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CAR_VALUE))
                );
                reservations.add(reservation);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return reservations;
    }

    // Update a reservation
    public int updateReservationDB(Reservation reservation) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_ID, reservation.getClientId());
        values.put(COLUMN_CAR_ID, reservation.getCarId());
        values.put(COLUMN_DATE_START, reservation.getDateStart().toString());
        values.put(COLUMN_DATE_END, reservation.getDateEnd().toString());
        values.put(COLUMN_CREATED_AT, reservation.getCreateAt().toString());
        values.put(COLUMN_FILLED, reservation.getFilled());
        values.put(COLUMN_VALUE, reservation.getValue());
        values.put(COLUMN_FEE_VALUE, reservation.getFeeValue());
        values.put(COLUMN_CAR_VALUE, reservation.getCarValue());
        return db.update(TABLE_RESERVATIONS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(reservation.getId())});
    }

    // Delete a reservation
    public int deleteReservationDB(int id) {
        return db.delete(TABLE_RESERVATIONS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAllReservationDB() {
        db.delete(TABLE_RESERVATIONS, null, null);
    }
}
