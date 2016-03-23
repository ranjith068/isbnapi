package whatnowtravel.com.isbndb.models;

import android.content.Context;

import java.util.List;

import whatnowtravel.com.isbndb.Book;
import whatnowtravel.com.isbndb.contentprovider.ITransactionProcess;
import whatnowtravel.com.isbndb.rest.SearchBookRestCall;
import whatnowtravel.com.isbndb.presenters.SearchPresenter;


public class SearchModelImp implements SearchModel {

    private SearchPresenter searchPresenter;
    private Context context;
    final private ITransactionProcess transactionProcess;

    public SearchModelImp(SearchPresenter searchPresenter, Context context, ITransactionProcess transactionProcess){
        this.searchPresenter = searchPresenter;
        this.context = context;
        this.transactionProcess = transactionProcess;
    }
    @Override
    public void search(String book) {
        new SearchBookRestCall(context, transactionProcess){

            @Override
            public void onSuccess(List<Book> response) {
                searchPresenter.onSearchSucceed(response);
            }

            @Override
            public void onFailure(String message) {
                searchPresenter.onSearchFailure(message);
            }
        }.execute(book);
    }
}
