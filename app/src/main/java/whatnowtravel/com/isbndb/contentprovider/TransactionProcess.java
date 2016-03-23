package whatnowtravel.com.isbndb.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

public class TransactionProcess implements ITransactionProcess {

    TransactionContentProvider transactionContentProvider;
    private Context context;
    private Map<String, Integer> transactions;

    public TransactionProcess(Context context){
        this.context = context;
        transactionContentProvider = new TransactionContentProvider();
        transactions = new HashMap<>();
    }
    @Override
    public String startTransaction(String word) {
        String uid = String.valueOf(System.currentTimeMillis());
        ContentValues values = new ContentValues();
        values.put(TransactionDbHelper.C_ID, uid);
        values.put(TransactionDbHelper.C_STATUS, 0);
        values.put(TransactionDbHelper.C_WORD, word);
        Uri insert = context.getContentResolver().insert(TransactionContentProvider.CONTENT_URI, values);
        transactions.put(uid.toString(), 0);
        return uid;
    }

    @Override
    public void processTransaction(String uid) {
        ContentValues values = new ContentValues();
        values.put(TransactionDbHelper.C_STATUS, 1);
        Uri.Builder uriBuilder =TransactionContentProvider.CONTENT_URI.buildUpon();
        Uri uri = uriBuilder.appendPath("get").build();
        context.getContentResolver().update(uri, values, TransactionDbHelper.C_ID +"=?", new String[]{uid});
        getLast();
    }

    @Override
    public void finishTransaction(String uid, String result) {
        ContentValues values = new ContentValues();
        Uri.Builder uriBuilder =TransactionContentProvider.CONTENT_URI.buildUpon();
        Uri uri = uriBuilder.appendPath("get").build();
        values.put(TransactionDbHelper.C_STATUS, 2);
        values.put(TransactionDbHelper.C_SUMMARY, result);
        context.getContentResolver().update(uri, values, TransactionDbHelper.C_ID +"=?", new String[]{uid});
    }

    @Override
    public Transaction getLast(){
        Uri.Builder uriBuilder =TransactionContentProvider.CONTENT_URI.buildUpon();
        Uri uri = uriBuilder.appendPath("last").build();
        Cursor cursor = context.getContentResolver().query(uri, new String[]{TransactionDbHelper.C_ID, TransactionDbHelper.C_STATUS, TransactionDbHelper.C_WORD, TransactionDbHelper.C_SUMMARY},
                null, null, TransactionDbHelper.C_ID + " DESC");
        if(cursor.moveToFirst() && cursor.getCount() >= 1) {
            Transaction transaction = new Transaction.TransationBuilder().
                    id(Long.valueOf(cursor.getString(0))).status(cursor.getInt(1)).
                    word(cursor.getString(2)).summary(cursor.getString(3)).build();
            return transaction;
        }
        return null;
    }

    @Override
    public Transaction getAllToBeProcessed(){
        Uri.Builder uriBuilder =TransactionContentProvider.CONTENT_URI.buildUpon();
        Uri uri = uriBuilder.appendPath("last").build();
        Cursor cursor = context.getContentResolver().query(uri, new String[]{TransactionDbHelper.C_ID, TransactionDbHelper.C_STATUS, TransactionDbHelper.C_WORD, TransactionDbHelper.C_SUMMARY},
                TransactionDbHelper.C_STATUS +"=?", new String[]{"1"}, TransactionDbHelper.C_ID + " DESC");
        if(cursor.moveToFirst() && cursor.getCount() >= 1) {
            Transaction transaction = new Transaction.TransationBuilder().
                    id(Long.valueOf(cursor.getString(0))).status(cursor.getInt(1)).
                        word(cursor.getString(1)).summary(cursor.getString(2)).build();
            return transaction;
        }else
            return null;
    }
}
