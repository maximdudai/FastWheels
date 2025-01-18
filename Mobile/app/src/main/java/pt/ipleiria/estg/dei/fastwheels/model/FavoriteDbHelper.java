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
import java.util.Date;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fastwheels";
    private static final int DB_VERSION = 14;

    private static final String TABLE_FAVORITES = "favorites";
    private static final String ID = "id";
    private static final String USER_ID = "userId";
    private static final String VEHICLE_ID = "vehicleId";

    private final SQLiteDatabase db;

    public FavoriteDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DATABASE:FAVORITES", "onCreate called");
        try {
            String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITES + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_ID + " INTEGER NOT NULL, " +
                    VEHICLE_ID + " INTEGER NOT NULL, " +
                    "UNIQUE (" + USER_ID + ", " + VEHICLE_ID + ") ON CONFLICT REPLACE" +
                    ");";
            db.execSQL(createTable);
            Log.d("DATABASE:FAVORITES", "Table created successfully");
        } catch (Exception e) {
            Log.e("DATABASE:ERROR", "Error creating table: " + e.getMessage());
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DATABASE:FAVORITES", "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    // Adicionar um veículo aos favoritos
    public long addFavorite(int userId, int vehicleId) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, userId);
        values.put(VEHICLE_ID, vehicleId);

        return db.insert(TABLE_FAVORITES, null, values);
    }

    // Remover um veículo dos favoritos
    public boolean removeFavorite(int userId, int vehicleId) {
        return db.delete(TABLE_FAVORITES, USER_ID + " = ? AND " + VEHICLE_ID + " = ?",
                new String[]{String.valueOf(userId), String.valueOf(vehicleId)}) > 0;
    }

    public List<Favorite> getFavorites(int userId) {
        List<Favorite> favorites = new ArrayList<>(); // Initialize the list

        System.out.println("--- fragment: userid: " + userId);

        // Include all required columns in the query
        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{ID, USER_ID, VEHICLE_ID},
                USER_ID + " = ?", new String[]{String.valueOf(userId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Create the Favorite object with retrieved data
                Favorite favItem = new Favorite(
                        cursor.getInt(cursor.getColumnIndexOrThrow(ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(VEHICLE_ID)),
                        new Date()
                );
                favorites.add(favItem); // Add to the list

            } while (cursor.moveToNext());
        }

        cursor.close();
        return favorites;
    }


}