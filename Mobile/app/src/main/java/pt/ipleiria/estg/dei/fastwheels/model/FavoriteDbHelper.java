package pt.ipleiria.estg.dei.fastwheels.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fastwheels";
    private static final int DATABASE_VERSION = 5;

    private final SQLiteDatabase db;

    // Table and columns
    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CLIENT_ID = "client_id";
    private static final String COLUMN_CAR_ID = "car_id";
    private static final String COLUMN_CREATED_AT = "created_at";

    public FavoriteDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FAVORITES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLIENT_ID + " INTEGER, " +
                COLUMN_CAR_ID + " INTEGER, " +
                COLUMN_CREATED_AT + " TIMESTAMP)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    // Insert a new favorite
    public long addFavoriteDB(Favorite favorite) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLIENT_ID, favorite.getClientId());
        values.put(COLUMN_CAR_ID, favorite.getCarId());
        values.put(COLUMN_CREATED_AT, favorite.getCreatedAt().toString());
        return db.insert(TABLE_FAVORITES, null, values);
    }

    // Get all favorites
    public List<Favorite> getAllFavorites() {
        List<Favorite> favorites = new ArrayList<>();
        Cursor cursor = db.query(TABLE_FAVORITES, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite(
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CLIENT_ID)),
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CAR_ID)),
                        new Date(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)))
                );
                favorites.add(favorite);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return favorites;
    }

    // Delete a favorite
    public int deleteFavoriteDB(long id) {
        return db.delete(TABLE_FAVORITES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<Long> getFavoriteCarIdsByClientId(long clientId) {
        List<Long> favoriteCarIds = new ArrayList<>();
        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{COLUMN_CAR_ID}, COLUMN_CLIENT_ID + " = ?", new String[]{String.valueOf(clientId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                favoriteCarIds.add(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CAR_ID)));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return favoriteCarIds;
    }

}