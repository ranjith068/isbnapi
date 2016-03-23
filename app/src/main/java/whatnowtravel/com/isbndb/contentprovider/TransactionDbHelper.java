package whatnowtravel.com.isbndb.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TransactionDbHelper extends SQLiteOpenHelper {

    public TransactionDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String DATABASE_NAME = "transactions.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_TRANSACTION = "TRANSACTIONS";
    public static final String C_ID = "_ID";
    public static final String C_STATUS = "STATUS";
    public static final String C_WORD = "WORD";
    public static final String C_SUMMARY = "SUMMARY";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE "+ TABLE_TRANSACTION +" ( " + C_ID +" TEXT PRIMARY KEY NOT NULL, "+ C_STATUS + " INTEGER DEFAULT 0, " +
            ""+C_WORD+" TEXT, "+
            C_SUMMARY+" TEXT);";




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TransactionDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);
    }
}

