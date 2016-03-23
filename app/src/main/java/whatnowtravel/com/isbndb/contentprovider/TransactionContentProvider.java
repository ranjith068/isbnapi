package whatnowtravel.com.isbndb.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class TransactionContentProvider extends ContentProvider {


    public static final String AUTHORITY = "whatnowtravel.com.isbndb.contentprovider";
    public static final String BASE_PATH = "transaction";
    private static final int INSERT_TRANSACTION = 1;
    private static final int GET_TRANSACTION = 2;
    private static final int GET_LAST = 2;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY+ "/" + BASE_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, INSERT_TRANSACTION);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH+"/insert", INSERT_TRANSACTION);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/get", GET_TRANSACTION);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/last", GET_LAST);
    }

    private Context context;

    private TransactionDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new TransactionDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(dbHelper.TABLE_TRANSACTION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriMatch = sURIMatcher.match(uri);
        long id = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatch){
            case INSERT_TRANSACTION:
                id = db.insert(TransactionDbHelper.TABLE_TRANSACTION, null, values);
                break;
            case GET_TRANSACTION:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = 0;
        int uriMatch = sURIMatcher.match(uri);
        switch (uriMatch){
            case INSERT_TRANSACTION:
                break;
            case GET_TRANSACTION:
                id = db.delete(dbHelper.TABLE_TRANSACTION, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int uriMatch = sURIMatcher.match(uri);
        int id = 0;
        switch (uriMatch){
            case INSERT_TRANSACTION:
                break;
            case GET_TRANSACTION:
                id = db.update(dbHelper.TABLE_TRANSACTION, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return id;
    }
}