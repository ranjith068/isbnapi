package whatnowtravel.com.isbndb.rest;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import whatnowtravel.com.isbndb.Book;
import whatnowtravel.com.isbndb.contentprovider.ITransactionProcess;
import whatnowtravel.com.isbndb.rest.exceptions.NoRestException;
import whatnowtravel.com.isbndb.rest.json.ParseBooksResult;


public abstract class SearchBookRestCall  extends AsyncTask<String, String, String> implements IRestCall<Book> {

    private Context context;
    private ITransactionProcess transactionProcess;
    private String uid;
    private boolean failing = false;

    public SearchBookRestCall(Context context, ITransactionProcess transactionProcess){
        this.context = context;
        this.transactionProcess = transactionProcess;
    }

    @Override
    protected String doInBackground(String... params) {
        uid = transactionProcess.startTransaction(params[0]);
        HttpCall getbooks = new HttpCall(context);
        transactionProcess.processTransaction(uid);
        String _result = "";
        try {
            _result = getbooks.GetBooks(params[0]);
        } catch (NoRestException e) {
            failing = true;
        }
        return _result;
    }

    @Override
    protected void onPostExecute(String books) {
        transactionProcess.finishTransaction(uid, books);
        if(failing)
            onFailure("A problem occurs, try later!");
        else {
            ParseBooksResult pRes = new ParseBooksResult(books);
            List<Book> pBooks = pRes.parse();
            onSuccess(pBooks);
        }
    }
}
