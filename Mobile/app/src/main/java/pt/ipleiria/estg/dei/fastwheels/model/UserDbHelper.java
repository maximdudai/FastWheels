package pt.ipleiria.estg.dei.fastwheels.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDbHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "fastwheels";
    private static final int DB_VERSION = 3;

    private final SQLiteDatabase db;

    private static final String TABLE_NAME = "users";
    private static final String ID = "id";
    private static final String TOKEN = "token";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String BALANCE = "balance";
    private static final String IBAN = "iban";

    public UserDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TOKEN + " TEXT NOT NULL, " +
                NAME + " TEXT NOT NULL, " +
                EMAIL + " TEXT NOT NULL, " +
                PHONE + " TEXT NOT NULL, " +
                BALANCE + " TEXT, " +
                IBAN + " TEXT);";

        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public User addUserDb(User user) {
        ContentValues values = new ContentValues();
        values.put(TOKEN, user.getToken());
        values.put(NAME, user.getName());
        values.put(EMAIL, user.getEmail());
        values.put(PHONE, user.getPhone());
        values.put(BALANCE, user.getBalance());
        values.put(IBAN, user.getIban());


        long id = this.db.insert(TABLE_NAME, null, values);
        if (id > -1) {
            user.setId((int) id);
            return user;
        }
        return null;
    }

    public boolean removeUserDb(int id) {
        return this.db.delete(TABLE_NAME, ID + " = ?", new String[]{String.valueOf(id)}) == 1;
    }
}
