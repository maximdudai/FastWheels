package pt.ipleiria.estg.dei.fastwheels.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fastwheels";
    private static final int DB_VERSION = 1;

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
        String createFavoritesTable = "CREATE TABLE " + TABLE_FAVORITES + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_ID + " INTEGER NOT NULL, " +
                VEHICLE_ID + " INTEGER NOT NULL, " +
                "UNIQUE (" + USER_ID + ", " + VEHICLE_ID + ") ON CONFLICT REPLACE" +
                ");";

        db.execSQL(createFavoritesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    // Adicionar um veículo aos favoritos
    public boolean addFavorite(int userId, int vehicleId) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, userId);
        values.put(VEHICLE_ID, vehicleId);

        return db.insert(TABLE_FAVORITES, null, values) != -1;
    }

    // Remover um veículo dos favoritos
    public boolean removeFavorite(int userId, int vehicleId) {
        return db.delete(TABLE_FAVORITES, USER_ID + " = ? AND " + VEHICLE_ID + " = ?",
                new String[]{String.valueOf(userId), String.valueOf(vehicleId)}) > 0;
    }

    // Obter todos os veículos favoritos de um usuário
    public List<Integer> getFavorites(int userId) {
        List<Integer> favorites = new ArrayList<>();
        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{VEHICLE_ID},
                USER_ID + " = ?", new String[]{String.valueOf(userId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                favorites.add(cursor.getInt(cursor.getColumnIndexOrThrow(VEHICLE_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favorites;
    }
}